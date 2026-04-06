import ui.LoginGUI;
import ui.RegisterGUI;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public Main() {

        setTitle("Library Management System");
        setSize(450,350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // 🔥 Gradient Background
        JPanel panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(58,123,213),
                        0, getHeight(), new Color(58,96,115)
                );
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        panel.setLayout(null);
        setContentPane(panel);

        // 🔹 Title
        JLabel title = new JLabel("Library Management System");
        title.setBounds(50,40,350,40);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(title);

        // 🔹 Buttons
        JButton register = createRoundedButton("Register", 140, new Color(46,204,113));
        JButton login = createRoundedButton("Login", 210, new Color(52,152,219));

        panel.add(register);
        panel.add(login);

        // 🔥 Stylish Admin Login Dialog (same logic)
        register.addActionListener(e -> {

            JDialog dialog = new JDialog(this, "Admin Login", true);
            dialog.setSize(350,250);
            dialog.setLocationRelativeTo(this);
            dialog.setLayout(null);

            dialog.getContentPane().setBackground(new Color(245,248,255));

            JLabel dTitle = new JLabel("Admin Login");
            dTitle.setBounds(100,10,200,30);
            dTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
            dTitle.setForeground(new Color(33,150,243));
            dialog.add(dTitle);

            JLabel userLabel = new JLabel("Username");
            userLabel.setBounds(40,60,100,20);
            dialog.add(userLabel);

            JTextField userField = new JTextField();
            userField.setBounds(140,60,150,28);
            dialog.add(userField);

            JLabel passLabel = new JLabel("Password");
            passLabel.setBounds(40,100,100,20);
            dialog.add(passLabel);

            JPasswordField passField = new JPasswordField();
            passField.setBounds(140,100,150,28);
            dialog.add(passField);

            JButton loginBtn = new JButton("Login");
            loginBtn.setBounds(120,150,100,35);
            loginBtn.setBackground(new Color(52,152,219));
            loginBtn.setForeground(Color.WHITE);
            loginBtn.setFocusPainted(false);
            loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            dialog.add(loginBtn);

            // 👉 Same logic
            loginBtn.addActionListener(ev -> {
                String username = userField.getText();
                String password = new String(passField.getPassword());

                if(username.equals("admin") && password.equals("admin")) {
                    dialog.dispose();
                    new RegisterGUI(this);
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(dialog, "Invalid Credentials ❌");
                }
            });

            dialog.setVisible(true);
        });

        // 🔑 Login Button Action
        login.addActionListener(e -> {
            new LoginGUI(this);
            setVisible(false);
        });

        setVisible(true);
    }

    // 🔥 Rounded Button Method
    private JButton createRoundedButton(String text, int y, Color color) {

        JButton btn = new JButton(text) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g);
            }
        };

        btn.setBounds(130, y, 180, 45);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setBorder(BorderFactory.createEmptyBorder());
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(color.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(color);
            }
        });

        return btn;
    }

    public static void main(String[] args) {
        new Main();
    }
}