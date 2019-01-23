import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;


public class CompanyGUI extends JFrame {
    private JButton goToAdvertisementCreationWindow = new JButton("Create New Advertisement"); //  is an insert into the advertisements table.
    private JButton getNumberOfQuestionsSolvedBySpecifiedUser = new JButton("Get Questions Solved");  // search user
    private JButton getUsersLookingForJobs = new JButton("Get Users Looking For Jobs");
    private JTextField searchUserTextField = new JTextField();
    private JButton getTopFiveUsersByRank = new JButton("Get Top 5 Users By Rank");
    private JButton getPersonwithHighestNumberOfProblemsSolved = new JButton("Get User With The Most Solves");
    private JButton updateContestStatus = new JButton("Update");
    private JButton updateAdStatus = new JButton("Update");
    private JTextField updateAdText = new JTextField();
    private JTextField updateContestText = new JTextField();
    String currentCompany;
    private JButton getAllUsersThatFinishedAllHardProblem = new JButton("User Who Solved All Hard Questions");
    //private JButton nestedAg = new JButton("Nested Ag");
    private JButton logout = new JButton("Logout");
    private JButton createContest = new JButton("Create Contest");
    private JLabel id = new JLabel();
    private JLabel username = new JLabel("Username: ");
    private JLabel adID = new JLabel("Ad ID: ");
    private JLabel contestID = new JLabel("Contest ID: ");
    private JButton viewAd = new JButton("View");
    private JButton viewContest = new JButton("view");



    private JPanel Company;

    Container container = getContentPane();

    public CompanyGUI(String company) {
        this.currentCompany = company;
        setLayoutManager();
        setLocationAndSize();
        addComponents();

        id.setText("Company Logged In: " + currentCompany);
        id.setFont(id.getFont().deriveFont(Font.BOLD));

        getUsersLookingForJobs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ResultSet rs = SQL.getUsersLookingForJobs(SQL.con);
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
        getNumberOfQuestionsSolvedBySpecifiedUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String nameOfUser = searchUserTextField.getText();

                if (nameOfUser != null && nameOfUser.trim().length() > 0) {
                    try {
                        ResultSet rs = SQL.getNumberOfQuestionsSolvedBySpecificUser(SQL.con, nameOfUser);
//                        JTable table = new JTable(Login.buildTableModel(rs));
//
//                        JOptionPane.showMessageDialog(null, new JScrollPane(table));

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
        getTopFiveUsersByRank.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ResultSet rs = SQL.getTopFiveUsersByRank(SQL.con);
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
        goToAdvertisementCreationWindow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdvertisementGUI ad = new AdvertisementGUI(currentCompany);
                ad.setBounds(220, 10, 370, 600);
                ad.setVisible(true);
                ad.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                ad.setResizable(false);


            }
        });
//
        createContest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ContestGUI contest = new ContestGUI(currentCompany);
                contest.setBounds(220, 10, 370, 600);
                contest.setVisible(true);
                contest.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                contest.setResizable(false);
            }
        });
////
////
//        // update status of advertisement or contest
//
        updateContestStatus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int contestToUpdate = Integer.parseInt(updateContestText.getText());

                    try {
                        ResultSet rs = SQL.updateContestStatus(SQL.con, contestToUpdate);

                        JOptionPane.showMessageDialog(null, "Contest with id: " + contestToUpdate + " was updated");
                    } catch (Exception ex) {
                        String errorMessage = ex.getMessage();
                        JOptionPane.showMessageDialog(new JFrame(), errorMessage, "Incorrect Script ", JOptionPane.ERROR_MESSAGE);
                    }
                }

                catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(null, "Make sure Contest ID is a numerical value");
                }

                //else print to screen that is it incorrect.
            }
        });

        viewContest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int contestToUpdate = Integer.parseInt(updateContestText.getText());

                    try {
                        ResultSet rs = SQL.viewContest(SQL.con, contestToUpdate);

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

                catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(null, "Make sure Contest ID is a numerical value");
                }

                //else print to screen that is it incorrect.
            }
        });
//
        updateAdStatus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    int adToUpdate = Integer.parseInt(updateAdText.getText());

                    try {
                        ResultSet rs = SQL.updateAdStatus(SQL.con, adToUpdate);

                        JOptionPane.showMessageDialog(null, "Ad with id: " + adToUpdate + " was updated");
                    } catch (Exception ex) {
                        String errorMessage = ex.getMessage();
                        JOptionPane.showMessageDialog(new JFrame(), errorMessage, "Incorrect Script ", JOptionPane.ERROR_MESSAGE);
                    }

                }

                catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(null, "Make sure Ad ID is a numerical value");
                }
                //else print to screen that is it incorrect.
            }
        });

        viewAd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    int adToUpdate = Integer.parseInt(updateAdText.getText());

                    try {
                        ResultSet rs = SQL.viewAd(SQL.con, adToUpdate);
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

                catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(null, "Make sure Ad ID is a numerical value");
                }
                //else print to screen that is it incorrect.
            }
        });
//
        getAllUsersThatFinishedAllHardProblem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e){
                try {
                    ResultSet rs = SQL.getAllUsersThatFinishedAllHardProblems(SQL.con);
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
//
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
        getUsersLookingForJobs.setBounds(30, 70, 250 , 30);
        getTopFiveUsersByRank.setBounds(320, 70, 250, 30);


        getPersonwithHighestNumberOfProblemsSolved.setBounds(30, 115, 250, 30);
        getAllUsersThatFinishedAllHardProblem.setBounds(320, 115, 250, 30);


        username.setBounds(137, 175, 75, 30);
        searchUserTextField.setBounds(212, 175, 250, 30);
        getNumberOfQuestionsSolvedBySpecifiedUser.setBounds(175, 215, 250, 30);


        goToAdvertisementCreationWindow.setBounds(30, 315, 250, 30);
        adID.setBounds(275, 285, 75, 30 );
        updateAdText.setBounds(320, 285, 250, 30);
        updateAdStatus.setBounds(320, 335, 125, 30);
        viewAd.setBounds(445, 335, 125, 30);


        updateContestText.setBounds(320, 420, 250, 30);
        updateContestStatus.setBounds(320, 470, 125, 30);
        viewContest.setBounds(445, 470, 125, 30);
        createContest.setBounds(30, 450, 250, 30);
        contestID.setBounds(245, 420, 75, 30);
        //nestedAg.setBounds(10, 515, 250, 30);
        logout.setBounds(440, 540, 150, 30);
    }

    public void addComponents() {
        container.add(getUsersLookingForJobs);
        container.add(getTopFiveUsersByRank);
        container.add(getPersonwithHighestNumberOfProblemsSolved);
        container.add(searchUserTextField);
        container.add(getNumberOfQuestionsSolvedBySpecifiedUser);
        container.add(goToAdvertisementCreationWindow);
        container.add(updateContestText);
        container.add(updateContestStatus);
        container.add(updateAdText);
        container.add(updateAdStatus);
        container.add(createContest);
        container.add(getAllUsersThatFinishedAllHardProblem);
        //container.add(nestedAg);
        container.add(id);
        container.add(username);
        container.add(adID);
        container.add(contestID);
        container.add(viewAd);
        container.add(viewContest);
        container.add(logout);
    }
}













