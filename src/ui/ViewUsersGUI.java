package ui;

import db.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ViewUsersGUI extends JFrame {

    JTable table;
    DefaultTableModel model;

    public ViewUsersGUI(JFrame prev){

        setTitle("View Users");
        setSize(500,400);
        setLocationRelativeTo(null);

        model = new DefaultTableModel();

        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Email");
        model.addColumn("Role");

        table = new JTable(model);

        add(new JScrollPane(table));

        JButton back = new JButton("Back");
        add(back,"South");

        loadUsers();

        back.addActionListener(e -> {

            prev.setVisible(true);
            dispose();

        });

        setVisible(true);
    }

    private void loadUsers(){

        try{

            Connection con = DBConnection.getConnection();

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM users");

            while(rs.next()){

                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("role")
                });

            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }
}