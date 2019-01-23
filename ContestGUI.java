import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class ContestGUI extends JFrame implements ActionListener {
    String currentCompany;
    private JLabel contestID = new JLabel("Contest ID: ");
    // private JLabel rating = new JLabel("Rating");

    private JTextField contestIDText = new JTextField();

    private JButton createContest = new JButton("Create Contest");
    private JButton cancel = new JButton("Cancel");

    Container container = getContentPane();



    public ContestGUI(String company) {
        currentCompany = company;

        setLayoutManager();
        setLocationAndSize();
        addComponents();
        addActionEvents();
    }

    public void setLayoutManager() {
        container.setLayout(null);
    }

    public void setLocationAndSize() {
        contestID.setBounds(50, 150, 100, 30);
        contestIDText.setBounds(150, 150, 100, 30);
        createContest.setBounds(100, 190, 250, 30);
        cancel.setBounds(100, 230, 100, 30);

    }

    public void addComponents() {
        container.add(contestID);
        container.add(contestIDText);
        container.add(createContest);
        container.add(cancel);
    }

    public void addActionEvents() {
        createContest.addActionListener(this);
        cancel.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createContest) {

            try {
                int id = Integer.parseInt(contestIDText.getText());

                try {
                    ResultSet rs = SQL.createContest(SQL.con, currentCompany, id);
                    JOptionPane.showMessageDialog(this, "Contest Was Created");

                    this.dispose();
                } catch (Exception ex) {
                    String errorMessage = ex.getMessage();
                    JOptionPane.showMessageDialog(new JFrame(), errorMessage, "Incorrect Script ", JOptionPane.ERROR_MESSAGE);
                }
            }
            catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Make sure Contest ID is a numerical value");
            }
        }

        if (e.getSource() == cancel) {
            this.dispose();
        }

    }
}
