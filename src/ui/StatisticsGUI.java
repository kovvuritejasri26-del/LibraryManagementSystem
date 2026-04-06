package ui;

import db.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class StatisticsGUI extends JFrame {

    public StatisticsGUI(JFrame prev){

        setTitle("Library Statistics");
        setSize(400,350); // 🔥 size increase
        setLayout(null);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(230,240,255));

        JLabel title = new JLabel("Library Statistics");
        title.setBounds(100,20,220,30);
        title.setFont(new Font("Arial",Font.BOLD,20));
        title.setForeground(new Color(0,102,204));
        add(title);

        JLabel users = new JLabel();
        users.setBounds(80,70,250,25);
        users.setFont(new Font("Arial",Font.BOLD,14));
        add(users);

        JLabel books = new JLabel();
        books.setBounds(80,100,250,25);
        books.setFont(new Font("Arial",Font.BOLD,14));
        add(books);

        JLabel issued = new JLabel();
        issued.setBounds(80,130,250,25);
        issued.setFont(new Font("Arial",Font.BOLD,14));
        add(issued);

        JLabel available = new JLabel();
        available.setBounds(80,160,250,25);
        available.setFont(new Font("Arial",Font.BOLD,14));
        add(available);

        JLabel returned = new JLabel();
        returned.setBounds(80,190,250,25);
        returned.setFont(new Font("Arial",Font.BOLD,14));
        add(returned);

        JButton back = new JButton("Back");
        back.setBounds(10,10,80,30);
        back.setBackground(new Color(204,0,0));
        back.setForeground(Color.WHITE);
        back.setFocusPainted(false);
        add(back);

        try{

            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();

            // 🔥 Total Users
            ResultSet rs1 = st.executeQuery("SELECT COUNT(*) FROM users");
            rs1.next();
            users.setText("👥 Total Users: " + rs1.getInt(1));

            // 🔥 Total Books
            ResultSet rs2 = st.executeQuery("SELECT COUNT(*) FROM books");
            rs2.next();
            books.setText("📚 Total Books: " + rs2.getInt(1));

            // 🔥 Issued Books
            ResultSet rs3 = st.executeQuery("SELECT COUNT(*) FROM issued_books WHERE return_date IS NULL");
            rs3.next();
            issued.setText("📖 Issued Books: " + rs3.getInt(1));

            // 🔥 Returned Books
            ResultSet rs4 = st.executeQuery("SELECT COUNT(*) FROM issued_books WHERE return_date IS NOT NULL");
            rs4.next();
            returned.setText("✅ Returned Books: " + rs4.getInt(1));

            // 🔥 Available Books (sum of quantity)
            ResultSet rs5 = st.executeQuery("SELECT SUM(quantity) FROM books");
            rs5.next();
            available.setText("📦 Available Books: " + rs5.getInt(1));

        }catch(Exception e){
            e.printStackTrace();
        }

        back.addActionListener(e -> {

            if(prev != null){
                prev.setVisible(true);
            } else {
                new LoginGUI(null);
            }

            dispose();

        });

        setVisible(true);
    }
}