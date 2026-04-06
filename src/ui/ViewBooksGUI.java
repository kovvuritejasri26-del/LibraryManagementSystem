package ui;

import db.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ViewBooksGUI extends JFrame {

    JTable table;
    DefaultTableModel model;

    public ViewBooksGUI(JFrame prev){

        setTitle("View Books");
        setSize(600,450);
        setLocationRelativeTo(null);

        // Main Panel
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(230, 240, 255)); // light blue

        // Title
        JLabel title = new JLabel("View Books", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        title.setOpaque(true);
        title.setBackground(new Color(70,130,180)); // blue header
        title.setPreferredSize(new Dimension(100, 40));

        panel.add(title, BorderLayout.NORTH);

        // Table Model
        model = new DefaultTableModel();

        model.addColumn("ID");
        model.addColumn("Title");
        model.addColumn("Author");
        model.addColumn("Quantity");

        table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(100,149,237));
        table.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(table);
        panel.add(scroll, BorderLayout.CENTER);

        // Back Button
        JButton back = new JButton("Back");
        back.setBackground(new Color(70,130,180));
        back.setForeground(Color.WHITE);
        back.setFont(new Font("Arial", Font.BOLD, 14));

        JPanel bottom = new JPanel();
        bottom.setBackground(new Color(230, 240, 255));
        bottom.add(back);

        panel.add(bottom, BorderLayout.SOUTH);

        add(panel);

        loadBooks();

        back.addActionListener(e -> {
            if(prev != null){
                prev.setVisible(true);
            }
            dispose();
        });

        setVisible(true);
    }

    private void loadBooks(){

        try{

            Connection con = DBConnection.getConnection();

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM books");

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

    public static void main(String[] args) {
        new ViewBooksGUI(null);
    }
}