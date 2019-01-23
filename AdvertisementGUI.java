import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class AdvertisementGUI extends JFrame implements ActionListener {

    String currentCompany;
    private JLabel addID = new JLabel("Ad ID: ");
    private JLabel type = new JLabel("Ad Type: ");

    Container container = getContentPane();

    private JTextField addIDText = new JTextField();
    private JTextField typeText = new JTextField();

    private JButton createAd = new JButton("Create Ad");
    private JButton cancel = new JButton("Cancel");

    public AdvertisementGUI(String company) {
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
        addID.setBounds(50, 150, 100, 30);
        type.setBounds(50, 220, 100, 30);
        addIDText.setBounds(150, 150, 150, 30);
        typeText.setBounds(150, 220, 150, 30);
        createAd.setBounds(100, 300, 150, 30);
        cancel.setBounds(100, 340, 150, 30);

    }

    public void addComponents() {
        container.add(addID);
        container.add(type);
        container.add(addIDText);
        container.add(typeText);
        container.add(createAd);
        container.add(cancel);
    }

    public void addActionEvents() {
        createAd.addActionListener(this);
        cancel.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == createAd) {

            try {
                int id = Integer.parseInt(addIDText.getText());
                String adType = typeText.getText();

                if (adType != null && adType.trim().length() > 0 && (adType.equals("event") || adType.equals("product") || adType.equals("job_posting"))) {

                    ResultSet rs = SQL.createAd(SQL.con, currentCompany,id, adType);

                    this.dispose();
                }
                else {
                    JOptionPane.showMessageDialog(this, "Invalid Advertisement Type");
                }
            }
            catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Make sure Ad ID is a numerical value");
            }
        }

        if (e.getSource() == cancel) {
            this.dispose();
        }

    }
}
