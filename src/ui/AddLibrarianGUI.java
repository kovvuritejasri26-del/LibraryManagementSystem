
package ui;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import db.DBConnection;

public class AddLibrarianGUI extends JFrame {

    private JTextField nameField, emailField, passwordField;
    private JButton addBtn;

    public AddLibrarianGUI() {
        setTitle("Add Librarian");
        setSize(400, 250);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        getContentPane().setBackground(new Color(230,240,255));

        JLabel title = new JLabel("Add Librarian");
        title.setBounds(130,5,200,25);
        title.setFont(new Font("Arial",Font.BOLD,18));
        title.setForeground(new Color(0,102,204));
        add(title);

        JLabel nameLbl = new JLabel("Name:");
        nameLbl.setBounds(30, 40, 100, 25);
        nameLbl.setFont(new Font("Arial",Font.BOLD,13));
        add(nameLbl);

        nameField = new JTextField();
        nameField.setBounds(150, 40, 180, 25);
        nameField.setBorder(BorderFactory.createLineBorder(new Color(0,102,204)));
        add(nameField);

        JLabel emailLbl = new JLabel("Email:");
        emailLbl.setBounds(30, 80, 100, 25);
        emailLbl.setFont(new Font("Arial",Font.BOLD,13));
        add(emailLbl);

        emailField = new JTextField();
        emailField.setBounds(150, 80, 180, 25);
        emailField.setBorder(BorderFactory.createLineBorder(new Color(0,102,204)));
        add(emailField);

        JLabel passLbl = new JLabel("Password:");
        passLbl.setBounds(30, 120, 100, 25);
        passLbl.setFont(new Font("Arial",Font.BOLD,13));
        add(passLbl);

        passwordField = new JTextField();
        passwordField.setBounds(150, 120, 180, 25);
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(0,102,204)));
        add(passwordField);

        addBtn = new JButton("Add Librarian");
        addBtn.setBounds(130, 170, 150, 30);
        addBtn.setBackground(new Color(0,153,76));
        addBtn.setForeground(Color.WHITE);
        addBtn.setFocusPainted(false);
        add(addBtn);

        addBtn.addActionListener(e -> addLibrarian());

        setVisible(true);
    }

    private void addLibrarian() {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {

            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO users(name,email,password,type) VALUES(?,?,?,?)"
            );

            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setString(4, "librarian");

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Librarian added successfully");

            nameField.setText("");
            emailField.setText("");
            passwordField.setText("");

        } catch (SQLException ex) {

            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding librarian: " + ex.getMessage());

        }
    }
}

