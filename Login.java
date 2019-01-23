import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

public class Login {
    public static void main(String[] a) {
        SQL.connect();
        dbSignUp signUp = new dbSignUp();
        signUp.setBounds(10, 10, 300, 300);
        signUp.setVisible(true);
        signUp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        signUp.setResizable(false);

//        CommunityGUI communityPage = new CommunityGUI("tobi");
//        communityPage.setBounds(80, 10, 370, 600);
//        communityPage.setVisible(true);
//        communityPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        communityPage.setResizable(false);

//        CompanyGUI companyGUI = new CompanyGUI("google");
//        companyGUI.setBounds(150, 10, 370, 600);
//        companyGUI.setVisible(true);
//        companyGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        companyGUI.setResizable(false);



//        AdminGUI adminGUI = new AdminGUI();
//        adminGUI.setBounds(290, 10, 370, 600);
//        adminGUI.setVisible(true);
//        adminGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        adminGUI.setResizable(false);


    }

    public static JScrollPane buildTableModel(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();


        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            String columnName = metaData.getColumnName(column);
            columnNames.add(columnName);
            //System.out.println(metaData.getColumnName(column));
        }


        // data of the table
        Vector<Vector> data = new Vector<Vector>();
        while (rs.next()) {

            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                Object o = rs.getObject(columnIndex);
                vector.add(o);
            }
            //System.out.println(vector);
            data.add(vector);
        }

        final JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);

        return scrollPane;
    }

}
