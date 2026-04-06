
package ui;

import db.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SearchBooksGUI extends JFrame {

    JTextField searchField;
    JTable table;
    DefaultTableModel model;

    public SearchBooksGUI(JFrame prev){

        setTitle("Search Books");
        setSize(600,400);
        setLayout(null);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(230,240,255));

        JLabel title = new JLabel("Search Books");
        title.setBounds(230,10,200,30);
        title.setFont(new Font("Arial",Font.BOLD,20));
        title.setForeground(new Color(0,102,204));
        add(title);

        JLabel label = new JLabel("Book Title:");
        label.setBounds(40,50,100,25);
        label.setFont(new Font("Arial",Font.BOLD,13));
        add(label);

        searchField = new JTextField();
        searchField.setBounds(120,50,200,25);
        searchField.setBorder(BorderFactory.createLineBorder(new Color(0,102,204)));
        add(searchField);

        JButton searchBtn = new JButton("Search");
        searchBtn.setBounds(340,50,100,30);
        searchBtn.setBackground(new Color(0,153,76));
        searchBtn.setForeground(Color.WHITE);
        searchBtn.setFocusPainted(false);
        add(searchBtn);

        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Title");
        model.addColumn("Author");
        model.addColumn("Quantity");

        table = new JTable(model);
        table.setRowHeight(25);

        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(40,100,500,230);
        add(sp);

        JButton back = new JButton("Back");
        back.setBounds(250,340,100,30);
        back.setBackground(new Color(204,0,0));
        back.setForeground(Color.WHITE);
        back.setFocusPainted(false);
        add(back);

        searchBtn.addActionListener(e -> searchBook());

        back.addActionListener(e -> {
            prev.setVisible(true);
            dispose();
        });

        setVisible(true);
    }

    private void searchBook(){

        model.setRowCount(0);

        try{

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM books WHERE title LIKE ?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, "%" + searchField.getText() + "%");

            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("quantity")
                });

            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }
}

