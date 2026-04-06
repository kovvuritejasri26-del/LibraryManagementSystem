package ui;

import db.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class IssuedBooksGUI extends JFrame {

    public IssuedBooksGUI() {

        setTitle("Issued Books");
        setSize(600, 400);
        setLocationRelativeTo(null);

        String[] columns = {"Student Name", "Book Title", "Issue Date", "Return Date"};

        DefaultTableModel model = new DefaultTableModel(columns, 0);

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane);

        // 🔥 LOAD FROM DATABASE
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();

            String sql = "SELECT u.name, b.title, i.issue_date, i.return_date " +
                         "FROM issued_books i " +
                         "JOIN users u ON i.user_id = u.id " +
                         "JOIN books b ON i.book_id = b.id";

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("name"),
                        rs.getString("title"),
                        rs.getString("issue_date"),
                        rs.getString("return_date")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        setVisible(true);
    }
}