package ui;

import db.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ChartsGUI extends JPanel {

    int totalBooks = 0;
    int issuedBooks = 0;
    int returnedBooks = 0;

    public ChartsGUI(){

        try{
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();

            ResultSet rs1 = st.executeQuery("SELECT COUNT(*) FROM books");
            rs1.next();
            totalBooks = rs1.getInt(1);

            ResultSet rs2 = st.executeQuery("SELECT COUNT(*) FROM issued_books WHERE return_date IS NULL");
            rs2.next();
            issuedBooks = rs2.getInt(1);

            ResultSet rs3 = st.executeQuery("SELECT COUNT(*) FROM issued_books WHERE return_date IS NOT NULL");
            rs3.next();
            returnedBooks = rs3.getInt(1);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setFont(new Font("Arial",Font.BOLD,14));

        int barWidth = 60;

        // 🔵 Total Books
        g.setColor(Color.BLUE);
        g.fillRect(80, 200 - totalBooks, barWidth, totalBooks);
        g.drawString("Books", 80, 220);

        // 🟢 Issued
        g.setColor(Color.GREEN);
        g.fillRect(160, 200 - issuedBooks, barWidth, issuedBooks);
        g.drawString("Issued", 160, 220);

        // 🔴 Returned
        g.setColor(Color.RED);
        g.fillRect(240, 200 - returnedBooks, barWidth, returnedBooks);
        g.drawString("Returned", 240, 220);
    }
}