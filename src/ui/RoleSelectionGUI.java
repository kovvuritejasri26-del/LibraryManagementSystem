package ui;

import javax.swing.*;
import java.awt.*;

public class RoleSelectionGUI extends JFrame {

    public RoleSelectionGUI(JFrame prev) {

        setTitle("Select Role");
        setSize(400,300);
        setLayout(null);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(230,240,255));

        JLabel title = new JLabel("Select Your Role");
        title.setBounds(120,20,200,30);
        title.setFont(new Font("Arial",Font.BOLD,22));
        title.setForeground(new Color(0,102,204));
        add(title);

        JButton adminBtn = new JButton("Admin");
        adminBtn.setBounds(140,70,120,35);
        adminBtn.setBackground(new Color(0,153,76));
        adminBtn.setForeground(Color.WHITE);
        adminBtn.setFont(new Font("Arial",Font.BOLD,13));
        adminBtn.setFocusPainted(false);
        add(adminBtn);

        JButton librarianBtn = new JButton("Librarian");
        librarianBtn.setBounds(140,110,120,35);
        librarianBtn.setBackground(new Color(0,153,76));
        librarianBtn.setForeground(Color.WHITE);
        librarianBtn.setFont(new Font("Arial",Font.BOLD,13));
        librarianBtn.setFocusPainted(false);
        add(librarianBtn);

        JButton facultyBtn = new JButton("Faculty");
        facultyBtn.setBounds(140,150,120,35);
        facultyBtn.setBackground(new Color(0,102,204));
        facultyBtn.setForeground(Color.WHITE);
        facultyBtn.setFont(new Font("Arial",Font.BOLD,13));
        facultyBtn.setFocusPainted(false);
        add(facultyBtn);

        JButton studentBtn = new JButton("Student");
        studentBtn.setBounds(140,190,120,35);
        studentBtn.setBackground(new Color(0,102,204));
        studentBtn.setForeground(Color.WHITE);
        studentBtn.setFont(new Font("Arial",Font.BOLD,13));
        studentBtn.setFocusPainted(false);
        add(studentBtn);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(10,10,80,30);
        backBtn.setBackground(new Color(204,0,0));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        add(backBtn);

        // Admin
        adminBtn.addActionListener(e -> {
            String pass = JOptionPane.showInputDialog(this,"Enter Admin Password");

            if(pass != null && pass.equals("admin123")){
                new AdminGUI(this);
                dispose();
            }else{
                JOptionPane.showMessageDialog(this,"Invalid Password");
            }
        });

        // Librarian
        librarianBtn.addActionListener(e -> {
            String pass = JOptionPane.showInputDialog(this,"Enter Librarian Password");

            if(pass != null && pass.equals("lib123")){
                new LibrarianGUI(this);
                dispose();
            }else{
                JOptionPane.showMessageDialog(this,"Invalid Password");
            }
        });

        // Faculty
        facultyBtn.addActionListener(e -> {
            String pass = JOptionPane.showInputDialog(this,"Enter Faculty Password");

            if(pass != null && pass.equals("fac123")){
                new LoginGUI(this);
                dispose();
            }else{
                JOptionPane.showMessageDialog(this,"Invalid Password");
            }
        });

        // Student
        studentBtn.addActionListener(e -> {
            String pass = JOptionPane.showInputDialog(this,"Enter Student Password");

            if(pass != null && pass.equals("stu123")){
                new LoginGUI(this);
                dispose();
            }else{
                JOptionPane.showMessageDialog(this,"Invalid Password");
            }
        });

        // Back button
        backBtn.addActionListener(e -> {
            prev.setVisible(true);
            dispose();
        });

        setVisible(true);
    }
}