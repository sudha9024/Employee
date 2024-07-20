import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
public class employ {
    private JPanel Main;
    private JTable table1;
    private JTextField txtName;
    private JTextField txtSalary;
    private JTextField txtMobile;
    private JButton updateButton;
    private JButton saveButton;
    private JTextField txtid;
    private JButton searchButton;
    private JButton deleteButton;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Employee");
        frame.setContentPane(new employ().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    Connection con;
    PreparedStatement pst;

    public void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/rb company", "root", "");
            System.out.println("Sucess");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }


    }

    public employ() {
        connect();
        table_load();
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String empName, Salary, Mobile;
                empName = txtName.getText();
                Salary = txtSalary.getText();
                Mobile = txtMobile.getText();
                try {
                    pst = con.prepareStatement("insert into Sudha(empname,salary,mobile) values(?,?,?)");
                    pst.setString(1, empName);
                    pst.setString(2, Salary);
                    pst.setString(3, Mobile);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Ordered!!!");
                    txtName.setText("");
                    txtMobile.setText("");
                    txtSalary.setText("");
                    txtName.requestFocus();
//                    System.out.println(pst);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e){
                try {

                    String empid = txtid.getText();

                    pst = con.prepareStatement("select empname,salary,mobile from sudha where id = ?");
                    pst.setString(1, empid);
                    ResultSet rs = pst.executeQuery();

                    if (rs.next() == true) {
                        String empname = rs.getString(1);
                        String emsalary = rs.getString(2);
                        String emmobile = rs.getString(3);

                        txtName.setText(empname);
                        txtSalary.setText(emsalary);
                        txtMobile.setText(emmobile);

                    } else {
                        txtName.setText("");
                        txtSalary.setText("");
                        txtMobile.setText("");
                        JOptionPane.showMessageDialog(null, "Invalid Employee No");

                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

        });


        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String empid,empname,salary,mobile;
                empname = txtName.getText();
                salary = txtSalary.getText();
                mobile = txtMobile.getText();
                empid = txtid.getText();

                try {
                    pst = con.prepareStatement("update sudha set empname = ?,salary = ?,mobile = ? where id = ?");
                    pst.setString(1, empname);
                    pst.setString(2, salary);
                    pst.setString(3, mobile);
                    pst.setString(4, empid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Updateee!!!!!");
                    table_load();
                    txtName.setText("");
                    txtSalary.setText("");
                    txtMobile.setText("");
                    txtName.requestFocus();
                }

                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String empid;
                empid = txtid.getText();

                try {
                    pst = con.prepareStatement("delete from sudha  where id = ?");

                    pst.setString(1, empid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Deleteeeeee!!!!!");
                    table_load();
                    txtName.setText("");
                    txtSalary.setText("");
                    txtMobile.setText("");
                    txtName.requestFocus();
                }

                catch (SQLException e1)
                {

                    e1.printStackTrace();
                }
            }
        });



        }



    void table_load()
    {
        try
        {
            pst = con.prepareStatement("select * from sudha");
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
