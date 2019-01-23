import javax.swing.*;
import java.sql.*;

public class SQL {


    public static Connection con = null;


    public static void connect() {
        String url = "jdbc:oracle:thin:@localhost:1522:ug";
        String username = "ora_u5b1b";
        String password = "a24649162";
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            con = DriverManager.getConnection(url, username, password);
        }
        catch (SQLException se) {
            System.out.println("Message: " + se.getMessage());
        }
    }


    public static ResultSet getCompaniesLookingForEmployees(Connection con) {
        ResultSet rs = null;
        String t = "T";
        //"SELECT a.ID, c.NAME From COMPANY c, APP_USER a WHERE c.HIRING = '" + t + "'"

        try {
            //***********************************************************************//
            String query = "SELECT distinct c.NAME, a.ID From COMPANY c, APP_USER a WHERE HIRING = '" + t + "' and c.NAME = a.NAME";
            //***********************************************************************//
            PreparedStatement ps = con.prepareStatement(query);
            rs = ps.executeQuery();

//            printDataGUI(rs);

        }
        catch (SQLException se) {
            System.out.println("Message: " + se.getMessage());
        }


        return rs;
    }

    public static ResultSet getActiveContestsByCompany(Connection con, String companyName) {
        ResultSet rs = null;
        String t = "T";

        try {
            //***********************************************************************//
            String query = "SELECT CONTEST_ID, USERNAME, RATING FROM CONTEST WHERE STATUS = '" + t + "' and USERNAME = '" + companyName + "'";
            //***********************************************************************//
            PreparedStatement ps = con.prepareStatement(query);
            rs = ps.executeQuery();

//            printDataGUI(rs);
        }
        catch (SQLException se) {
            System.out.println("Message: " + se.getMessage());
        }

        return rs;
    }

    public static ResultSet getPersonwithHighestNumberOfProblemsSolved(Connection con) {
        ResultSet rs = null;
        //SELECT a.ID, c.NAME, c.SOLVED_QUESTIONS FROM COMMUNITY c, APP_USER a WHERE c.SOLVED_QUESTIONS in (SELECT max(c.SOLVED_QUESTIONS) FROM COMMUNITY)

        try {
            //***********************************************************************//
            String query = "SELECT NAME, SOLVED_QUESTIONS FROM COMMUNITY WHERE SOLVED_QUESTIONS in (SELECT max(SOLVED_QUESTIONS) FROM COMMUNITY)";
            //***********************************************************************//
            PreparedStatement ps = con.prepareStatement(query);
            rs = ps.executeQuery();

//            printDataGUI(rs);

        }
        catch (SQLException se) {
            System.out.println("Message: " + se.getMessage());
        }

        return rs;
    }

    public static ResultSet getProblemsSolvedByMe(Connection con, String currentUser) {
        ResultSet rs = null;

        try {
            //***********************************************************************//
            String query = "SELECT DISTINCT p.PROBLEM_ID, p.PROBLEM_NAME From SOLUTION s, PROBLEM p WHERE s.USERNAME = '" + currentUser + "' and s.PROBLEM_ID = p.PROBLEM_ID";
            //***********************************************************************//
            PreparedStatement ps = con.prepareStatement(query);
            rs = ps.executeQuery();

//            printDataGUI(rs);

        }
        catch (SQLException se) {
            System.out.println("Message: " + se.getMessage());
        }

        return rs;
    }

    public static ResultSet getUsersLookingForJobs(Connection con) {
        ResultSet rs = null;
        String t = "T";

        try {
            //***********************************************************************//
            String query = "SELECT NAME, RANK, SOLVED_QUESTIONS FROM COMMUNITY WHERE LOOKING_FOR_JOB = '" + t + "'";
            //***********************************************************************//
            PreparedStatement ps = con.prepareStatement(query);
            rs = ps.executeQuery();

//            printDataGUI(rs);

        }
        catch (SQLException se) {
            System.out.println("Message: " + se.getMessage());
        }

        return rs;
    }

    public static ResultSet getTopFiveUsersByRank(Connection con) {
        ResultSet rs = null;

        try {
            //***********************************************************************//
            String query = "SELECT NAME, RANK FROM COMMUNITY WHERE RANK < 6";
            //***********************************************************************//
            PreparedStatement ps = con.prepareStatement(query);
            rs = ps.executeQuery();

//            printDataGUI(rs);

        }
        catch (SQLException se) {
            System.out.println("Message: " + se.getMessage());
        }

        return rs;
    }

    // Print the ResultSet Template - works for every table - reusable
    public static void printDataGUI(ResultSet rs)
            throws SQLException {
        ResultSetMetaData rsMetaData = rs.getMetaData();
        int numberOfColumns = rsMetaData.getColumnCount();
        String header = "";
        for (int i = 1; i <= numberOfColumns; i++) {
            String columnName = rsMetaData.getColumnName(i);
            header = header + columnName + " \t";
        }
        System.out.println(header);
        while (rs.next()) {
            String row = "";
            for (int k = 1; k <= numberOfColumns; k++){
                String columnClassName = rsMetaData.getColumnClassName(k);
                Object o = rs.getObject(k);
                row = row + o.toString() + " \t";
            }
            System.out.println(row);
        }
    }

    public static ResultSet getNumberOfQuestionsSolvedBySpecificUser(Connection con, String nameOfUser) {
        ResultSet rs = null;
        try {
            //***********************************************************************//
            String query = "SELECT NAME,SOLVED_QUESTIONS FROM COMMUNITY WHERE NAME = '" + nameOfUser + "'";
            //***********************************************************************//
            PreparedStatement ps = con.prepareStatement(query);
            rs = ps.executeQuery();

//            printDataGUI(rs);
        }
        catch (SQLException se) {
            System.out.println("Message: " + se.getMessage());
        }

        return rs;
    }

    public static ResultSet createAd(Connection con, String companyName, int adID, String adType)  {
        ResultSet rs = null;
        String t = "T";

        try {
        //***********************************************************************//
        String query = "INSERT INTO ADVERTISEMENT VALUES (" + adID + ",'" + companyName + "','" + adType + "','" + t + "')";
        //***********************************************************************//
        PreparedStatement ps = con.prepareStatement(query);
        rs = ps.executeQuery();
        con.commit();
        }

        catch (SQLException se) {
//            String errorMessage = se.getMessage();
//            JOptionPane.showMessageDialog(new JFrame(), errorMessage, "Incorrect Script ", JOptionPane.ERROR_MESSAGE);

            System.out.println("Message: " + se.getMessage());
            if (se.getErrorCode() == 00001) {
                JOptionPane.showMessageDialog(null, "Ad ID already exist, CHOOSE ANOTHER");
            }
        }
        return rs;
    }

    public static ResultSet updateContestStatus(Connection con, int contestToUpdate) {
        ResultSet rs = null;
        try {
            //***********************************************************************//
            String query = "SELECT STATUS FROM CONTEST WHERE CONTEST_ID = " + contestToUpdate + "";
            //***********************************************************************//
            PreparedStatement ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            String query2 = "";

            while (rs.next()) {
                if (rs.getString(1).equals("T")) {
                    //***********************************************************************//
                    query2 = "UPDATE CONTEST SET STATUS = '" + "F" + "'WHERE CONTEST_ID = " + contestToUpdate + "";
                    //***********************************************************************//

                }
                else if (rs.getString(1).equals("F")) {
                    //***********************************************************************//
                    query2 = "UPDATE CONTEST SET STATUS = '" + "T" + "'WHERE CONTEST_ID = " + contestToUpdate + "";
                    //***********************************************************************//
                }

                PreparedStatement ps2 = con.prepareStatement(query2);
                rs = ps2.executeQuery();

            }
            con.commit();
        }
        catch (SQLException se) {
            System.out.println("Message: " + se.getMessage());
        }


        return rs;
    }

    public static ResultSet updateAdStatus(Connection con, int adToUpdate) {
        ResultSet rs = null;
        try {
            //***********************************************************************//
            String query = "SELECT STATUS FROM ADVERTISEMENT WHERE AD_ID = " + adToUpdate + "";
            //***********************************************************************//
            PreparedStatement ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            String query2 = "";

            while (rs.next()) {
                if (rs.getString(1).equals("T")) {
                    //***********************************************************************//
                    query2 = "UPDATE ADVERTISEMENT SET STATUS = '" + "F" + "'WHERE AD_ID = " + adToUpdate + "";
                    //***********************************************************************//

                }
                else if (rs.getString(1).equals("F")) {
                    //***********************************************************************//
                    query2 = "UPDATE ADVERTISEMENT SET STATUS = '" + "T" + "'WHERE AD_ID = " + adToUpdate + "";
                    //***********************************************************************//
                }

                PreparedStatement ps2 = con.prepareStatement(query2);
                rs = ps2.executeQuery();

            }
            con.commit();
        }
        catch (SQLException se) {
            System.out.println("Message: " + se.getMessage());
        }


        return rs;

    }

    public static ResultSet deleteUser(Connection con, String nameOfUser) {
        ResultSet rs = null;
        try {
            //***********************************************************************//
            String query = "DELETE FROM APP_USER WHERE NAME = '" + nameOfUser + "'";
            //***********************************************************************//
            PreparedStatement ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            con.commit();
        }
        catch (SQLException se) {
            System.out.println("Message: " + se.getMessage());
        }

        return rs;
    }

    public static ResultSet getIDOfUser(Connection con, String nameOfUser) {
        ResultSet rs = null;
        try {
            //***********************************************************************//
            String query = "SELECT ID FROM APP_USER WHERE NAME = '" + nameOfUser + "'";
            //***********************************************************************//
            PreparedStatement ps = con.prepareStatement(query);
            rs = ps.executeQuery();

//            printDataGUI(rs);
        }
        catch (SQLException se) {
            System.out.println("Message: " + se.getMessage());
        }

        return rs;
    }
//
    public static ResultSet makeAdmin(Connection con, String userToUpdate) {
        ResultSet rs = null;
        try {
            //***********************************************************************//
            String query = "UPDATE COMMUNITY SET ADMIN_STATUS = '" + "T" + "' WHERE NAME = '" + userToUpdate + "'";
            //***********************************************************************//
            PreparedStatement ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            con.commit();
        }
        catch (SQLException se) {
            System.out.println("Message: " + se.getMessage());
        }

        return rs;
    }

    public static ResultSet revokeAdmin(Connection con, String user) {
        ResultSet rs = null;
        try {
            //***********************************************************************//
            String query = "UPDATE COMMUNITY SET ADMIN_STATUS = '" + "F" + "' WHERE NAME = '" + user + "'";
            //***********************************************************************//
            PreparedStatement ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            con.commit();
        }
        catch (SQLException se) {
            System.out.println("Message: " + se.getMessage());
        }

        return rs;
    }
//
    public static ResultSet deleteAdvertisement(Connection con, int eventToAdvertisement) {
        ResultSet rs = null;

        try {
            //***********************************************************************//
            String query = "DELETE FROM ADVERTISEMENT WHERE AD_ID = " + eventToAdvertisement + "";
            //***********************************************************************//
            PreparedStatement ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            con.commit();
        }
        catch (SQLException se) {
            System.out.println("Message: " + se.getMessage());
        }

        return rs;
    }

    public static ResultSet createContest(Connection con, String currentCompany, int id) {
        ResultSet rs = null;
        String t = "T";
        try {
            //***********************************************************************//
            String query = "INSERT into CONTEST values (" + id + ",'" + currentCompany + "','" + t + "','" + 50 + "','" + 0 + "')";
            //***********************************************************************//
            PreparedStatement ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            con.commit();
        }
        catch (SQLException se) {
            System.out.println("Message: " + se.getMessage());
            if (se.getErrorCode() == 00001) {
                JOptionPane.showMessageDialog(null, "Ad ID already exist, CHOOSE ANOTHER");
            }
        }
        return rs;
    }

    public static ResultSet getAllUsersThatFinishedAllHardProblems(Connection con) {
        ResultSet rs = null;

        String hard = "hard";
        String t = "T";

//        SELECT NAME
//        FROM   APP_USER AU
//        WHERE  NOT EXISTS (  (SELECT PROBLEM_ID FROM PROBLEM WHERE DIFFICULTY = 'hard' )
//        MINUS  ( SELECT PROBLEM_ID FROM PROBLEM PRO WHERE EXISTS
//                ( SELECT * FROM SOLUTION SOL WHERE SOL.PROBLEM_ID = PRO.PROBLEM_ID AND AU.NAME = SOL.USERNAME AND SOL.STATUS = 'T' ) ) ) ;
        try {
            //***********************************************************************//
            String query = "SELECT NAME FROM APP_USER au WHERE NOT EXISTS((SELECT PROBLEM_ID FROM PROBLEM WHERE  DIFFICULTY = '" + hard + "') " +
                    "MINUS (SELECT PROBLEM_ID FROM PROBLEM pro WHERE EXISTS(SELECT * FROM SOLUTION sol where sol.PROBLEM_ID = pro.PROBLEM_ID and " +
                    "au.NAME = sol.USERNAME and sol.STATUS = '" + t + "')))";
            //***********************************************************************//
            PreparedStatement ps = con.prepareStatement(query);
            rs = ps.executeQuery();
        }
        catch (SQLException se) {
            System.out.println("Message: " + se.getMessage());
        }


        return rs;
    }

    public static ResultSet viewAllAdmins(Connection con) {
        ResultSet rs = null;

        String t = "T";
        try {
            //***********************************************************************//
            String query = "SELECT distinct a.ID, c.NAME FROM COMMUNITY c, APP_USER a where c.ADMIN_STATUS = '" + t + "' and c.NAME = a.NAME";
            //***********************************************************************//
            PreparedStatement ps = con.prepareStatement(query);
            rs = ps.executeQuery();
        }
        catch (SQLException se) {
            System.out.println("Message: " + se.getMessage());
        }

        return rs;
    }

    public static ResultSet getAllActiveContests(Connection con) {
        ResultSet rs = null;
        try {
            //***********************************************************************//
            String query = "SELECT * from CONTEST";
            //***********************************************************************//
            PreparedStatement ps = con.prepareStatement(query);
            rs = ps.executeQuery();
        }
        catch (SQLException se) {
            System.out.println("Message: " + se.getMessage());
        }

        return rs;
    }

    public static ResultSet numberOfContests(Connection con, String value) {
        ResultSet rs = null;
        try {
            String query = "";
            if (value.equals("")) {
                query = "SELECT Company, " + value + "(Amount) FROM (SELECT USERNAME Company, count(CONTEST_ID) Amount from contest group by USERNAME)";
            }
            else {
                //***********************************************************************//
                query = "SELECT " + value + "(Amount) FROM (SELECT USERNAME Company, count(CONTEST_ID) Amount from contest group by USERNAME)";
                //***********************************************************************//
            }
            PreparedStatement ps = con.prepareStatement(query);
            rs = ps.executeQuery();
        }
        catch (SQLException se) {
            System.out.println("Message: " + se.getMessage());
        }

        return rs;
    }

    public static ResultSet viewAd(Connection con, int adToUpdate) {
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM ADVERTISEMENT WHERE AD_ID = " + adToUpdate + "";
            PreparedStatement ps = con.prepareStatement(query);
            rs = ps.executeQuery();
        }
        catch (SQLException se) {
            System.out.println("Message: " + se.getMessage());
        }

        return rs;
    }

    public static ResultSet viewContest(Connection con, int contestToUpdate) {
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM ADVERTISEMENT WHERE AD_ID = " + contestToUpdate + "";
            PreparedStatement ps = con.prepareStatement(query);
            rs = ps.executeQuery();
        }
        catch (SQLException se) {
            System.out.println("Message: " + se.getMessage());
        }
        return rs;

    }

//    public static ResultSet signIn(Connection con, String username, String password) {
//        ResultSet rs = null;
//        try {
//            //***********************************************************************//
//            String query = "SELECT NAME, PASSWORD FROM APP_USER WHERE NAME = '" + username + "' + and PASSWORD = '" + password + "'";
//            //***********************************************************************//
//            PreparedStatement ps = con.prepareStatement(query);
//            rs = ps.executeQuery();
//        }
//        catch (SQLException se) {
//            System.out.println("Message: " + se.getMessage());
//        }
//
//        return rs;
//
//    }
//
//    public static ResultSet CreateContestFunc(Connection con, int contestID, int rating, int numParticipants, int companyID) {
//        ResultSet rs = null;
//
//        try {
//            String query = "INSERT INTO TABLE VALUES (" + companyID + "," + contestID + "," + "Y" + "," + rating + "," + numParticipants +")";
//
//            PreparedStatement ps = con.prepareStatement(query);
//            rs = ps.executeQuery();
//        }
//        catch (SQLException se) {
//            System.out.println("Message: " + se.getMessage());
//        }
//
//        return rs;
//    }
    //
    //
}
