package ui;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {

    public Dashboard(String type){

        setTitle(type + " Dashboard");
        setSize(400,300);
        setLayout(null);
        setLocationRelativeTo(null);

        // Background color
        getContentPane().setBackground(new Color(230,240,255));

        // Main panel for better styling
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(30,40,330,180);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(new Color(0,102,204),2));
        add(panel);

        // Title
        JLabel title = new JLabel(type + " Dashboard");
        title.setBounds(70,10,200,30);
        title.setFont(new Font("Arial",Font.BOLD,22));
        title.setForeground(new Color(0,102,204));
        panel.add(title);

        // Welcome label
        JLabel label = new JLabel("👋 Welcome " + type);
        label.setBounds(80,80,200,30);
        label.setFont(new Font("Arial",Font.BOLD,18));
        label.setForeground(new Color(0,153,76));
        panel.add(label);

        // Bottom footer text
        JLabel footer = new JLabel("Library Management System");
        footer.setBounds(110,240,200,20);
        footer.setFont(new Font("Arial",Font.PLAIN,12));
        footer.setForeground(Color.GRAY);
        add(footer);

        setVisible(true);
    }
}