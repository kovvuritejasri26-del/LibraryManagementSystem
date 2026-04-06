package ui;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class LoginGUI extends JFrame {

    public LoginGUI(JFrame prev){

        setTitle("Library Login");
        setSize(400,300);
        setLayout(null);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(230,240,255));

        JLabel title = new JLabel("Library Login");
        title.setBounds(130,20,200,30);
        title.setFont(new Font("Arial",Font.BOLD,22));
        title.setForeground(new Color(0,102,204));
        add(title);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setBounds(60,80,100,25);
        emailLabel.setFont(new Font("Arial",Font.BOLD,13));
        add(emailLabel);

        JTextField emailField = new JTextField();
        emailField.setBounds(150,80,180,25);
        Border border = BorderFactory.createLineBorder(new Color(0,102,204));
        emailField.setBorder(border);
        add(emailField);

        JLabel passLabel = new JLabel("Password");
        passLabel.setBounds(60,120,100,25);
        passLabel.setFont(new Font("Arial",Font.BOLD,13));
        add(passLabel);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(150,120,180,25);
        passField.setBorder(border);
        add(passField);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(150,160,100,30);
        loginBtn.setBackground(new Color(0,153,76));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        add(loginBtn);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(10,10,70,25);
        backBtn.setBackground(new Color(204,0,0));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        add(backBtn);

        loginBtn.addActionListener(e -> {

            String email = emailField.getText();
            String password = new String(passField.getPassword());

            if(email.isEmpty() || password.isEmpty()){
                showStyledMessage("Enter Email and Password ❗", "Error", Color.RED);
                return;
            }

            UserDAO dao = new UserDAO();
            User user = dao.login(email,password);

            if(user != null){

                showStyledMessage("Login Successful ✅", "Success", new Color(0,153,76));

                String role = user.getRole();
                int userId = user.getId();   // 🔥 ADD HERE

                if(role.equals("admin")){
                    new AdminGUI(null);
                }
                else if(role.equals("student")){
                    new Student(userId);
                }
                else if(role.equals("librarian")){
                    new LibrarianGUI(null);
                }
                else if(role.equals("faculty")){
                    new Faculty(userId);
                }

                dispose();

            }else{

                showStyledMessage("Invalid Email or Password ❌", "Error", Color.RED);

            }

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