import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class AdminGUI extends JFrame{


    private JButton deleteUserButton = new JButton("Delete User");
    private JTextField deleteUserTextBox = new JTextField();
    private JButton IDofUserButton = new JButton("View ID");
    private JTextField IDofUserTextbox = new JTextField();
    private JButton makeAdmin = new JButton("Make Admin");
    private JButton revokeAdmin = new JButton("Revoke Admin Status");
    private JTextField userToUpdate = new JTextField();
    private JTextField deleteAd = new JTextField();
    private JButton deleteAdButton = new JButton("Delete Ad");
    private JButton logout = new JButton("Logout");
    private JButton viewAllAdmin = new JButton("View All Admins");

    private JLabel username1 = new JLabel("Username: ");
    private JLabel username2 = new JLabel("Username: ");
    private JLabel username3 = new JLabel("Username: ");
    private JLabel ad = new JLabel("Ad ID: ");

    private JLabel id = new JLabel();

    Container container = getContentPane();

    public AdminGUI(String username) {
        id.setText("User Logged In: " + username);
        id.setFont(id.getFont().deriveFont(Font.BOLD));
        setLayoutManager();
        setLocationAndSize();
        addComponents();


        deleteUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String nameOfUser = deleteUserTextBox.getText();

                if (nameOfUser != null && nameOfUser.trim().length() > 0) {
                    try {
                        ResultSet rs = SQL.deleteUser(SQL.con, nameOfUser);

                        JOptionPane.showMessageDialog(null, "User with name: " + nameOfUser + " has been deleted");

                    } catch (Exception ex) {
                        String errorMessage = ex.getMessage();
                        JOptionPane.showMessageDialog(new JFrame(), errorMessage, "Incorrect Script ", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
////
        IDofUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String nameOfUser = IDofUserTextbox.getText();

                if (nameOfUser != null && nameOfUser.trim().length() > 0) {
                    try {
//
                        ResultSet rs = SQL.getIDOfUser(SQL.con, nameOfUser);
//                        JTable table = new JTable(Login.buildTableModel(rs));

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
////        // make admin by constraints
////
        makeAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = userToUpdate.getText();

                if (user != null && user.trim().length() > 0) {
                    try {
//                           ResultSet rs = SQL.updateContestStatus(SQL.con, userToUpdate, updated);
                        ResultSet rs = SQL.makeAdmin(SQL.con, user);

                        JOptionPane.showMessageDialog(null, "The User: " + user + " has been made an admin");
                    } catch (Exception ex) {
                        String errorMessage = ex.getMessage();
                        JOptionPane.showMessageDialog(new JFrame(), errorMessage, "Incorrect Script ", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        revokeAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = userToUpdate.getText();

                if (user != null && user.trim().length() > 0) {
                    try {
//                           ResultSet rs = SQL.updateContestStatus(SQL.con, userToUpdate, updated);
                        ResultSet rs = SQL.revokeAdmin(SQL.con, user);

                        JOptionPane.showMessageDialog(null, "The User: " + user + " has been revoked his admin status");
                    } catch (Exception ex) {
                        String errorMessage = ex.getMessage();
                        JOptionPane.showMessageDialog(new JFrame(), errorMessage, "Incorrect Script ", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
////        // delete
        deleteAdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    int eventToDelete = Integer.parseInt(deleteAd.getText());

                    try {
//                        ResultSet rs = SQL.updateContestStatus(SQL.con, userToUpdate, updated);
                        ResultSet rs = SQL.deleteAdvertisement(SQL.con, eventToDelete);
                        JOptionPane.showMessageDialog(null, "AD with id: " + eventToDelete + " has been deleted");

                    } catch (Exception ex) {
                        String errorMessage = ex.getMessage();
                        JOptionPane.showMessageDialog(new JFrame(), errorMessage, "Incorrect Script ", JOptionPane.ERROR_MESSAGE);
                    }

                }
                catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(null, "Enter a valid numerical AD ID");

                }
            }
        });

        viewAllAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e){
                try {
                    ResultSet rs = SQL.viewAllAdmins(SQL.con);

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
        username1.setBounds(100, 60, 75, 30);
        deleteUserTextBox.setBounds(175, 60, 250, 30);
        deleteUserButton.setBounds(175,110, 250, 30);

        username2.setBounds(100, 150, 75, 30);
        IDofUserTextbox.setBounds(175, 150, 250, 30);
        IDofUserButton.setBounds(175, 180, 250, 30);

        username3.setBounds(100, 220, 75, 30);
        userToUpdate.setBounds(175, 220, 250, 30);
        makeAdmin.setBounds(50, 260, 200, 30);
        revokeAdmin.setBounds(350, 260, 200, 30);

        ad.setBounds(100, 340, 75, 30);
        deleteAd.setBounds(175,340, 250, 30);
        deleteAdButton.setBounds(175, 380, 250, 30);
        viewAllAdmin.setBounds(175, 420, 250, 30);
        logout.setBounds(440, 540, 150, 30);

        id.setBounds(10, 5 ,250, 50);

    }

    public void addComponents() {
        container.add(deleteUserTextBox);
        container.add(deleteUserButton);
        container.add(IDofUserButton);
        container.add(IDofUserTextbox);
        container.add(userToUpdate);
        container.add(makeAdmin);
        container.add(revokeAdmin);
        container.add(deleteAd);
        container.add(deleteAdButton);
        container.add(viewAllAdmin);
        container.add(logout);
        container.add(username1);
        container.add(username2);
        container.add(username3);
        container.add(ad);
        container.add(id);
    }
}



