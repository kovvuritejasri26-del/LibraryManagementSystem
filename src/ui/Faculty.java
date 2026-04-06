package ui;

import javax.swing.*;
import java.awt.*;

public class Faculty extends JFrame {

    int userId;   // 🔥 ADD

    public Faculty(int userId) {   // 🔥 UPDATED CONSTRUCTOR

        this.userId = userId;   // 🔥 STORE USER ID

        setTitle("Faculty Dashboard");
        setSize(450, 500);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setBackground(new Color(245, 248, 255));

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(50, 60, 330, 370);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        add(panel);

        JLabel title = new JLabel("Faculty Dashboard");
        title.setBounds(90, 20, 250, 30);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(new Color(33, 150, 243));
        add(title);

        JLabel bookLabel = new JLabel("📚 Books");
        bookLabel.setBounds(30, 20, 200, 20);
        bookLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panel.add(bookLabel);

        JButton viewBooks = new JButton("View Books");
        viewBooks.setBounds(60, 50, 200, 35);
        styleButton(viewBooks, new Color(33, 150, 243));
        panel.add(viewBooks);

        JButton searchBook = new JButton("Search Book");
        searchBook.setBounds(60, 90, 200, 35);
        styleButton(searchBook, new Color(33, 150, 243));
        panel.add(searchBook);

        JLabel reqLabel = new JLabel("🔄 Requests");
        reqLabel.setBounds(30, 140, 200, 20);
        reqLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panel.add(reqLabel);

        JButton requestBook = new JButton("Request Book");
        requestBook.setBounds(60, 170, 200, 35);
        styleButton(requestBook, new Color(76, 175, 80));
        panel.add(requestBook);

        JButton myRequests = new JButton("My Requests");
        myRequests.setBounds(60, 210, 200, 35);
        styleButton(myRequests, new Color(255, 152, 0));
        panel.add(myRequests);

        JLabel returnLabel = new JLabel("🔁 Return");
        returnLabel.setBounds(30, 260, 200, 20);
        returnLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panel.add(returnLabel);

        JButton returnBook = new JButton("Return Book");
        returnBook.setBounds(60, 290, 200, 35);
        styleButton(returnBook, new Color(244, 67, 54));
        panel.add(returnBook);

        JButton back = new JButton("← Back");
        back.setBounds(10, 10, 90, 30);
        styleButton(back, new Color(158, 158, 158));
        add(back);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(330, 10, 90, 30);
        styleButton(logoutBtn, new Color(103, 58, 183));
        add(logoutBtn);

        // ACTIONS

        logoutBtn.addActionListener(e -> {
            new LoginGUI(null);
            dispose();
        });

        viewBooks.addActionListener(e -> {
            new ViewBooksGUI(this);
            setVisible(false);
        });

        searchBook.addActionListener(e -> {
            new SearchBooksGUI(this);
            setVisible(false);
        });

        // 🔥 IMPORTANT FIX
        requestBook.addActionListener(e -> {
            new RequestBookGUI(userId);   // ✅ PASS USER ID
            setVisible(false);
        });

        myRequests.addActionListener(e -> {
            new MyRequestsGUI(this);
            setVisible(false);
        });

        returnBook.addActionListener(e -> {
            new StudentReturnBookGUI(this,userId);
            setVisible(false);
        });

        back.addActionListener(e -> {
            new LoginGUI(null);
            dispose();
        });

        setVisible(true);
    }

    private void styleButton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}