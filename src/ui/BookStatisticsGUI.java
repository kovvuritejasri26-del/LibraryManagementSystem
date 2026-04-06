
package ui;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import db.DBConnection;

public class BookStatisticsGUI extends JFrame {

    private JLabel totalBooksLbl, issuedBooksLbl, returnedBooksLbl;

    public BookStatisticsGUI() {
        setTitle("Book Statistics");
        setSize(400, 250);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        getContentPane().setBackground(new Color(230,240,255));

        JLabel title = new JLabel("📊 Book Statistics");
        title.setBounds(110,10,200,30);
        title.setFont(new Font("Arial",Font.BOLD,20));
        title.setForeground(new Color(0,102,204));
        add(title);

        totalBooksLbl = new JLabel("Total Books: 0");
        totalBooksLbl.setBounds(80,60,250,30);
        totalBooksLbl.setFont(new Font("Arial",Font.BOLD,15));
        totalBooksLbl.setForeground(new Color(0,102,153));
        add(totalBooksLbl);

        issuedBooksLbl = new JLabel("Issued Books: 0");
        issuedBooksLbl.setBounds(80,100,250,30);
        issuedBooksLbl.setFont(new Font("Arial",Font.BOLD,15));
        issuedBooksLbl.setForeground(new Color(204,102,0));
        add(issuedBooksLbl);

        returnedBooksLbl = new JLabel("Returned Books: 0");
        returnedBooksLbl.setBounds(80,140,250,30);
        returnedBooksLbl.setFont(new Font("Arial",Font.BOLD,15));
        returnedBooksLbl.setForeground(new Color(0,153,76));
        add(returnedBooksLbl);

        loadBookStats();

        setVisible(true);
    }

    private void loadBookStats() {

        try (Connection conn = DBConnection.getConnection()) {

            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM books");
            if (rs.next()) 
                totalBooksLbl.setText("Total Books: " + rs.getInt("total"));

            rs = stmt.executeQuery("SELECT COUNT(*) AS issued FROM books WHERE status='issued'");
            if (rs.next()) 
                issuedBooksLbl.setText("Issued Books: " + rs.getInt("issued"));

            rs = stmt.executeQuery("SELECT COUNT(*) AS returned FROM books WHERE status='returned'");
            if (rs.next()) 
                returnedBooksLbl.setText("Returned Books: " + rs.getInt("returned"));

        } catch (SQLException ex) {

            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading book statistics: " + ex.getMessage());

        }
    }
}

