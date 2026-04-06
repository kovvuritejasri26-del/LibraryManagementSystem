package ui;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import java.awt.*;

public class RegisterGUI extends JFrame {

    public RegisterGUI(JFrame prev) {

        setTitle("User Registration");
        setSize(400,420);
        setLayout(null);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(230,240,255));

        JLabel title = new JLabel("Register");
        title.setBounds(150,20,150,30);
        title.setFont(new Font("Arial",Font.BOLD,22));
        title.setForeground(new Color(0,102,204));
        add(title);

        JLabel nameLabel = new JLabel("Name");
        nameLabel.setBounds(50,70,100,25);
        nameLabel.setFont(new Font("Arial",Font.BOLD,13));
        add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(150,70,180,25);
        nameField.setBorder(BorderFactory.createLineBorder(new Color(0,102,204)));
        add(nameField);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setBounds(50,110,100,25);
        emailLabel.setFont(new Font("Arial",Font.BOLD,13));
        add(emailLabel);

        JTextField emailField = new JTextField();
        emailField.setBounds(150,110,180,25);
        emailField.setBorder(BorderFactory.createLineBorder(new Color(0,102,204)));
        add(emailField);

        JLabel passLabel = new JLabel("Password");
        passLabel.setBounds(50,150,100,25);
        passLabel.setFont(new Font("Arial",Font.BOLD,13));
        add(passLabel);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(150,150,180,25);
        passField.setBorder(BorderFactory.createLineBorder(new Color(0,102,204)));
        add(passField);

        JLabel confirmLabel = new JLabel("Confirm Password");
        confirmLabel.setBounds(20,190,130,25);
        confirmLabel.setFont(new Font("Arial",Font.BOLD,13));
        add(confirmLabel);

        JPasswordField confirmField = new JPasswordField();
        confirmField.setBounds(150,190,180,25);
        confirmField.setBorder(BorderFactory.createLineBorder(new Color(0,102,204)));
        add(confirmField);

        JLabel roleLabel = new JLabel("Role");
        roleLabel.setBounds(50,230,100,25);
        roleLabel.setFont(new Font("Arial",Font.BOLD,13));
        add(roleLabel);

        String[] roles = {"Librarian","student","faculty"};
        JComboBox<String> roleBox = new JComboBox<>(roles);
        roleBox.setBounds(150,230,180,25);
        roleBox.setBorder(BorderFactory.createLineBorder(new Color(0,102,204)));
        add(roleBox);

        JButton registerBtn = new JButton("Register");
        registerBtn.setBounds(140,270,110,35);
        registerBtn.setBackground(new Color(0,153,76));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFont(new Font("Arial",Font.BOLD,13));
        registerBtn.setFocusPainted(false);
        add(registerBtn);

        JButton loginBtn = new JButton("Already Registered? Login");
        loginBtn.setBounds(95,320,210,30);
        loginBtn.setBackground(new Color(0,102,204));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Arial",Font.BOLD,12));
        loginBtn.setFocusPainted(false);
        add(loginBtn);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(10,10,80,30);
        backBtn.setBackground(new Color(204,0,0));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        add(backBtn);

        registerBtn.addActionListener(e -> {

            String name = nameField.getText();
            String email = emailField.getText();
            String password = new String(passField.getPassword());
            String confirm = new String(confirmField.getPassword());
            String role = roleBox.getSelectedItem().toString();

            if(name.isEmpty() || email.isEmpty() || password.isEmpty()){

                showStyledMessage("Please fill all fields ❗", "Error", Color.RED);
                return;
            }

            if(password.length() < 6){

                showStyledMessage("Password must be at least 6 characters ❗", "Error", Color.RED);
                return;
            }

            if(!password.equals(confirm)){

                showStyledMessage("Passwords do not match ❌", "Error", Color.RED);
                return;
            }

            if(!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")){

                showStyledMessage("Invalid Email Format ❌", "Error", Color.RED);
                return;
            }

            try{

                User user = new User();
                user.setName(name);
                user.setEmail(email);
                user.setPassword(password);
                user.setRole(role);

                UserDAO dao = new UserDAO();
                dao.registerUser(user);

                showStyledMessage("Registration Successful ✅", "Success", new Color(0,153,76));

                new LoginGUI(null);
                dispose();

            }catch(Exception ex){
                ex.printStackTrace();
            }

        });

        loginBtn.addActionListener(e -> {

            new LoginGUI(null);
            dispose();

        });

        backBtn.addActionListener(e -> {

            if(prev != null){
                prev.setVisible(true);
            }
            dispose();

        });

        setVisible(true);
    }

    // 🔥 Styled Message Method (only new addition)
    private void showStyledMessage(String message, String title, Color color) {

        JDialog dialog = new JDialog(this, title, true);
        dialog.setSize(320,180);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(null);

        dialog.getContentPane().setBackground(new Color(245,248,255));

        JLabel msg = new JLabel(message);
        msg.setBounds(30,30,260,30);
        msg.setFont(new Font("Segoe UI", Font.BOLD, 14));
        msg.setForeground(color);
        msg.setHorizontalAlignment(SwingConstants.CENTER);
        dialog.add(msg);

        JButton okBtn = new JButton("OK");
        okBtn.setBounds(110,80,100,30);
        okBtn.setBackground(new Color(33,150,243));
        okBtn.setForeground(Color.WHITE);
        okBtn.setFocusPainted(false);
        okBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        dialog.add(okBtn);

        okBtn.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }
}