// We need to import the java.sql package to use JDBC

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginFrame extends JFrame implements ActionListener {

    private Connection con;

    Container container = getContentPane();
    private JTextField emailField = new JTextField();
    private JLabel emailLabel = new JLabel("Email:");
    private JLabel passwordLabel = new JLabel("Password:");
    private JPasswordField passwordField = new JPasswordField();
    private JCheckBox showPasswordCheckBox = new JCheckBox("Show Password");
    private JButton loginButton = new JButton("Login");
    private JButton createAccountButton = new JButton("Create Account");

    // user is allowed 3 login attempts
    private int loginAttempts = 0;

    private String username;



    // Constructor for Login Class
    //   - creates login frame
    //   - loads JDBC driver
    //   - connects to Oracle database ug

    public LoginFrame(Connection con) {
        this.con = con;

        // setup for login frame
        setLayoutManager();
        setLocationAndSize();
        addComponents();
        addActionEvent();
        emailField.requestFocus();


    }

    public void setLayoutManager() {
        container.setLayout(null);
    }

    public void setLocationAndSize() {
        emailLabel.setBounds(50,150,100,30);
        passwordLabel.setBounds(50,220,100,30);
        emailField.setBounds(150,150,150,30);
        passwordField.setBounds(150,220,150,30);
        showPasswordCheckBox.setBounds(150,250,150,30);
        loginButton.setBounds(50,300,200,30);
        //createAccountButton.setBounds(200,300,150,30);
    }

    public void addComponents() {
        container.add(emailLabel);
        container.add(emailField);
        container.add(passwordField);
        container.add(passwordLabel);
        container.add(showPasswordCheckBox);
        container.add(loginButton);
        //container.add(createAccountButton);
    }

    public void addActionEvent() {
        loginButton.addActionListener(this);
        //createAccountButton.addActionListener(this);
        showPasswordCheckBox.addActionListener(this);
    }

//    public void loadDriver() {
//        try {
//            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
//        }
//        catch (SQLException ex) {
//            System.out.println("Message: " + ex.getMessage());
//            System.exit(-1);
//        }
//    }

//    private void connect() {
//        String connectURL = "jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug";
//        String username = "ora_u5b1b";
//        String password = "a24649162";
//        try {
//            con = DriverManager.getConnection(connectURL, username, password);
//            System.out.println("\nConnected to Oracle!");
//        }
//        catch (SQLException ex) {
//            System.out.println("Message: " + ex.getMessage());
//        }
//    }


    // *****
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String email = emailField.getText();
            String password = String.valueOf(passwordField.getPassword());


            if (email != null && email.trim().length() > 0 && password.trim().length() > 0) {

                    if (authenticate(email, password)) {


                        if (checkCommunity(username)) {
                            if (checkAdmin(username)) {
                                this.dispose();
                                AdminGUI adminGUI = new AdminGUI(username);
                                adminGUI.setBounds(290, 10, 600, 600);
                                adminGUI.setVisible(true);
                                adminGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                adminGUI.setResizable(false);
                            }
                            else {
                                this.dispose();
                                CommunityGUI communityPage = new CommunityGUI(username);
                                communityPage.setBounds(80, 10, 600, 600);
                                communityPage.setVisible(true);
                                communityPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                communityPage.setResizable(false);
                            }

                        }
                        else if (checkCompany(username)) {
                            this.dispose();
                            CompanyGUI companyGUI = new CompanyGUI(username);
                            companyGUI.setBounds(150, 10, 600, 600);
                            companyGUI.setVisible(true);
                            companyGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            companyGUI.setResizable(false);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Invalid Email or Password, TRY AGAIN");
                        loginAttempts++;

                        if (loginAttempts >= 3) {
                            System.exit(-1);
                        } else {
                            emailField.setText("");
                            passwordField.setText("");
                        }
                    }
                }
            }

//        if (e.getSource() == createAccountButton) {
//            emailField.setText("");
//            passwordField.setText("");
//        }

        if (e.getSource() == showPasswordCheckBox) {
            if (showPasswordCheckBox.isSelected()) {
                passwordField.setEchoChar((char) 0);
            }
            else {
                passwordField.setEchoChar('*');
            }
        }
   }

    public boolean authenticate(String inputEmail, String inputPassword) {

        boolean valid = false;

        ResultSet rs;
        try {
            //***********************************************************************//
            String query = "SELECT EMAIL,PASSWORD, NAME FROM APP_USER WHERE EMAIL = ? and  PASSWORD = ?";
            //***********************************************************************//
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, inputEmail);
            ps.setString(2, inputPassword);
            rs = ps.executeQuery();

            while (rs.next()) {
                String dbEmail = rs.getString("EMAIL");
                String dbPassword = rs.getString("PASSWORD");
                username = rs.getString("NAME");


                if (dbEmail.equals(inputEmail) && dbPassword.equals(inputPassword)) {
                    valid = true;
                }
            }
            ps.close();
            rs.close();


        }
        catch (SQLException se) {
            System.out.println("Message: " + se.getMessage());
            System.exit(-1);
        }

        return valid;
    }

    public boolean checkCommunity(String username) {
        boolean valid = false;
        String query;

        ResultSet rs;
        try {
            //***********************************************************************//
            query = "SELECT NAME FROM COMMUNITY WHERE NAME = ?";
            //***********************************************************************//
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, username);
            rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString("NAME");


                if (name != null && name.equals(username)) {
                    valid = true;
                }
            }

            ps.close();
            rs.close();


        }
        catch (SQLException se) {
            System.out.println("Message: " + se.getMessage());
            System.exit(-1);
        }

        return valid;
    }

    public boolean checkAdmin(String username) {
        boolean valid = false;
        String query;

        ResultSet rs;
        try {
            //***********************************************************************//
            query = "SELECT ADMIN_STATUS FROM COMMUNITY WHERE ADMIN_STATUS = ? and NAME = ?";
            //***********************************************************************//
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, "T");
            ps.setString(2, username);
            rs = ps.executeQuery();

            while (rs.next()) {
                String adminStatus = rs.getString("ADMIN_STATUS");


                if (adminStatus != null && adminStatus.equals("T")) {
                    valid = true;
                }
            }

            ps.close();
            rs.close();


        }
        catch (SQLException se) {
            System.out.println("Message: " + se.getMessage());
            System.exit(-1);
        }

        return valid;
    }

    public boolean checkCompany(String username) {
        boolean valid = false;
        String query;

        ResultSet rs;
        try {
            //***********************************************************************//
            query = "SELECT NAME FROM COMPANY WHERE NAME = ?";
            //***********************************************************************//
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, username);
            rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString("NAME");


                if (name != null && name.equals(username)) {
                    valid = true;
                }
            }

            ps.close();
            rs.close();


        }
        catch (SQLException se) {
            System.out.println("Message: " + se.getMessage());
            System.exit(-1);
        }

        return valid;
    }
}
