package ui;

import javax.swing.*;
import java.awt.*;

public class AdminGUI extends JFrame {

    public AdminGUI(JFrame prev){

        setTitle("Admin Dashboard");
        setSize(400,420); // 🔥 little increased height
        setLayout(null);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(230,240,255));

        JLabel title = new JLabel("Admin Dashboard");
        title.setBounds(100,20,200,30);
        title.setFont(new Font("Arial",Font.BOLD,22));
        title.setForeground(new Color(0,102,204));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title);

        JButton viewUsers = new JButton("👥 View Users");
        viewUsers.setBounds(120,70,160,35);
        viewUsers.setBackground(new Color(0,153,76));
        viewUsers.setForeground(Color.WHITE);
        viewUsers.setFont(new Font("Arial",Font.BOLD,13));
        viewUsers.setFocusPainted(false);
        add(viewUsers);

        JButton updateUsers = new JButton("✏ Update Users");
        updateUsers.setBounds(120,110,160,35);
        updateUsers.setBackground(new Color(0,153,76));
        updateUsers.setForeground(Color.WHITE);
        updateUsers.setFont(new Font("Arial",Font.BOLD,13));
        updateUsers.setFocusPainted(false);
        add(updateUsers);

        JButton deleteUsers = new JButton("🗑 Delete Users");
        deleteUsers.setBounds(120,150,160,35);
        deleteUsers.setBackground(new Color(0,153,76));
        deleteUsers.setForeground(Color.WHITE);
        deleteUsers.setFont(new Font("Arial",Font.BOLD,13));
        deleteUsers.setFocusPainted(false);
        add(deleteUsers);

        // 🔥 NEW BUTTON
        JButton manageBooks = new JButton("📚 Manage Books");
        manageBooks.setBounds(120,190,160,35);
        manageBooks.setBackground(new Color(0,153,76));
        manageBooks.setForeground(Color.WHITE);
        manageBooks.setFont(new Font("Arial",Font.BOLD,13));
        manageBooks.setFocusPainted(false);
        add(manageBooks);

        JButton statistics = new JButton("📊 Statistics");
        statistics.setBounds(120,230,160,35);
        statistics.setBackground(new Color(0,153,76));
        statistics.setForeground(Color.WHITE);
        statistics.setFont(new Font("Arial",Font.BOLD,13));
        statistics.setFocusPainted(false);
        add(statistics);
        
        JButton graphBtn = new JButton("📊 Graphs");
        graphBtn.setBounds(120,270,160,35);
        graphBtn.setBackground(new Color(0,153,76));
        graphBtn.setForeground(Color.WHITE);
        graphBtn.setFont(new Font("Arial",Font.BOLD,13));
        graphBtn.setFocusPainted(false);
        add(graphBtn);
        

        JButton back = new JButton("⬅ Back");
        back.setBounds(10,10,80,30);
        back.setBackground(new Color(204,0,0));
        back.setForeground(Color.WHITE);
        back.setFocusPainted(false);
        add(back);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(300,10,90,30);
        logoutBtn.setBackground(new Color(102,0,153));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFocusPainted(false);
        add(logoutBtn);

        // 🔥 ACTIONS

        logoutBtn.addActionListener(e -> {
            new LoginGUI(null);
            dispose();
        });

        viewUsers.addActionListener(e -> {
            new ViewUsersGUI(this);
            setVisible(false);
        });

        updateUsers.addActionListener(e -> {
            new UpdateUsersGUI(this);
            setVisible(false);
        });

        deleteUsers.addActionListener(e -> {
            new DeleteUsersGUI(this);
            setVisible(false);
        });

        // 🔥 NEW ACTION
        manageBooks.addActionListener(e -> {
            new LibrarianGUI(this); // reuse librarian screen
            setVisible(false);
        });

        statistics.addActionListener(e -> {
            new StatisticsGUI(this);
            setVisible(false);
        });
        
        graphBtn.addActionListener(e -> {
            new ChartFrame(this);
            setVisible(false);
        });

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