import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;



public class CommunityGUI extends JFrame {

    private JButton getCompaniesLookingForEmployees = new JButton("Companies Hiring");
    private JTextField CompanySearchName = new JTextField();
    private JButton getActiveCompetitionsByCompany = new JButton("Search Active Competitions By Company");
    private JButton getAllActiveContests = new JButton("All Active Competitions");
    private JButton getPersonwithHighestNumberOfProblemsSolved = new JButton("Get User With Most Solved");
    private JButton getProblemsSolvedByMe = new JButton("My Solved Problems");
    private String currentUser;
    private JButton logout = new JButton("Log Out");
    private JLabel id = new JLabel();
    private JLabel companyName = new JLabel("Company Name: ");
    private String[] choices = {"", "MIN", "MAX"};
    private JComboBox minMax = new JComboBox(choices);

    private JButton numberOfContest = new JButton("Number of Contests");

    Container container = getContentPane();

    public CommunityGUI(String name) {

        currentUser = name;
        setLayoutManager();
        setLocationAndSize();
        addComponents();
        id.setText("User Logged In: " + currentUser);
        id.setFont(id.getFont().deriveFont(Font.BOLD));

           getCompaniesLookingForEmployees.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed (ActionEvent e){
            try {
                ResultSet rs = SQL.getCompaniesLookingForEmployees(SQL.con);
//                SQL.printDataGUI(rs);
                JFrame frame = new JFrame("Results");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                JScrollPane window = Login.buildTableModel(rs);

                window.setOpaque(true);
                frame.setContentPane(window);
                frame.pack();
                frame.setVisible(true);
//                JTable table = new JTable(Login.buildTableModel(rs));
//                JOptionPane.showMessageDialog(null, new JScrollPane(table));
            } catch (Exception ex) {
                String errorMessage = ex.getMessage();
                JOptionPane.showMessageDialog(new JFrame(), errorMessage, "Incorrect Script ", JOptionPane.ERROR_MESSAGE);
            }

        }

    });



        getActiveCompetitionsByCompany.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String companyName = CompanySearchName.getText();

                if (companyName != null && companyName.trim().length() > 0) {
                    try {
                        ResultSet rs = SQL.getActiveContestsByCompany(SQL.con, companyName);
//                        JTable table = new JTable(Login.buildTableModel(rs));

                        JFrame frame = new JFrame("Results");
                        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        JScrollPane window = Login.buildTableModel(rs);

                        window.setOpaque(true);
                        frame.setContentPane(window);
                        frame.pack();
                        frame.setVisible(true);

//                        JOptionPane.showMessageDialog(null, new JScrollPane(table));

                    } catch (Exception ex) {
                        String errorMessage = ex.getMessage();
                        JOptionPane.showMessageDialog(new JFrame(), errorMessage, "Incorrect Script ", JOptionPane.ERROR_MESSAGE);
                    }

                }
            }
        });
////
////
        getPersonwithHighestNumberOfProblemsSolved.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ResultSet rs = SQL.getPersonwithHighestNumberOfProblemsSolved(SQL.con);
//                    JTable table = new JTable(Login.buildTableModel(rs));
//                    JOptionPane.showMessageDialog(null, new JScrollPane(table));

                    JFrame frame = new JFrame("Results");
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    JScrollPane window = Login.buildTableModel(rs);

                    window.setOpaque(true);
                    frame.setContentPane(window);
                    frame.pack();
                    frame.setVisible(true);


                } catch (Exception ex) {
                    String errorMessage = ex.getMessage();
                    JOptionPane.showMessageDialog(new JFrame(), errorMessage, "Incorrect Script ", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
////
////
        getProblemsSolvedByMe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ResultSet rs = SQL.getProblemsSolvedByMe(SQL.con, currentUser);
//                    JTable table = new JTable(Login.buildTableModel(rs));
//                    JOptionPane.showMessageDialog(null, new JScrollPane(table));

                    JFrame frame = new JFrame("Results");
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    JScrollPane window = Login.buildTableModel(rs);

                    window.setOpaque(true);
                    frame.setContentPane(window);
                    frame.pack();
                    frame.setVisible(true);
                } catch (Exception ex) {
                    String errorMessage = ex.getMessage();
                    JOptionPane.showMessageDialog(new JFrame(), errorMessage, "Incorrect Script ", JOptionPane.ERROR_MESSAGE);
                }

            }
        });


        getAllActiveContests.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                try {
                    ResultSet rs = SQL.getAllActiveContests(SQL.con);
//                        JTable table = new JTable(Login.buildTableModel(rs));

                    JFrame frame = new JFrame("Results");
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    JScrollPane window = Login.buildTableModel(rs);

                    window.setOpaque(true);
                    frame.setContentPane(window);
                    frame.pack();
                    frame.setVisible(true);

//                        JOptionPane.showMessageDialog(null, new JScrollPane(table));

                } catch (Exception ex) {
                    String errorMessage = ex.getMessage();
                    JOptionPane.showMessageDialog(new JFrame(), errorMessage, "Incorrect Script ", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        numberOfContest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String value = minMax.getSelectedItem().toString();


                try {
                    ResultSet rs = SQL.numberOfContests(SQL.con, value);
//                        JTable table = new JTable(Login.buildTableModel(rs));

                    JFrame frame = new JFrame("Results");
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    JScrollPane window = Login.buildTableModel(rs);

                    window.setOpaque(true);
                    frame.setContentPane(window);
                    frame.pack();
                    frame.setVisible(true);

//                        JOptionPane.showMessageDialog(null, new JScrollPane(table));

                } catch (Exception ex) {
                    String errorMessage = ex.getMessage();
                    JOptionPane.showMessageDialog(new JFrame(), errorMessage, "Incorrect Script ", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
//
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e){
                try {
                    setVisible(false);
                    dispose();
                    JOptionPane.showMessageDialog(null, "User has been logged out");
                    LoginFrame userLogin = new LoginFrame(SQL.con);
                    userLogin.setTitle("Login Form");
                    userLogin.setVisible(true);
                    userLogin.setBounds(10, 10, 370, 600);
                    userLogin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    userLogin.setResizable(false);

                } catch (Exception ex) {
                    String errorMessage = ex.getMessage();
                    JOptionPane.showMessageDialog(new JFrame(), errorMessage, "Incorrect Script ", JOptionPane.ERROR_MESSAGE);
                }

            }

        });
    }


    public void setLayoutManager() {
        container.setLayout(null);
    }

    public void setLocationAndSize() {
        id.setBounds(10, 5 ,250, 50);
        getCompaniesLookingForEmployees.setBounds(175, 75, 250, 30);
        getPersonwithHighestNumberOfProblemsSolved.setBounds(175, 125, 250, 30);
        getProblemsSolvedByMe.setBounds(175, 200, 250, 30);
        getAllActiveContests.setBounds(175, 275, 250, 30);

        numberOfContest.setBounds(200, 350, 275, 30);
        minMax.setBounds(125, 350, 80, 30);
        CompanySearchName.setBounds(229, 425, 242, 30);
        getActiveCompetitionsByCompany.setBounds(125, 475, 350, 30);
        companyName.setBounds(127, 425, 150, 30);

        logout.setBounds(440, 540, 150, 30);
    }

    public void addComponents() {
        container.add(id);
        container.add(getCompaniesLookingForEmployees);
        container.add(getPersonwithHighestNumberOfProblemsSolved);
        container.add(getProblemsSolvedByMe);
        container.add(CompanySearchName);
        container.add(getActiveCompetitionsByCompany);
        container.add(logout);
        container.add(getAllActiveContests);
        container.add(companyName);
        container.add(numberOfContest);
        container.add(minMax);
    }


}













