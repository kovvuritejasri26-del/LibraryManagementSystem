package ui;

import javax.swing.*;
import java.awt.*;

public class LibrarianGUI extends JFrame {

    public LibrarianGUI(JFrame prev){

        setTitle("Librarian Dashboard");
        setSize(420, 680);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setBackground(new Color(230,240,255));

        JLabel title = new JLabel("Librarian Dashboard");
        title.setBounds(60,20,300,30);
        title.setFont(new Font("Segoe UI",Font.BOLD,22));
        title.setForeground(new Color(33,150,243));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title);

        JButton addBook = createButton("📚 Add Book",80);
        JButton viewBooks = createButton("📖 View Books",130);
        JButton searchBook = createButton("🔍 Search Book",180);
        JButton updateBook = createButton("✏ Update Book",230);
        JButton deleteBook = createButton("🗑 Delete Book",280);
        
        JButton issuedBooks = createButton("📋 Issued Books",330);
        JButton returnRequests = createButton("📤 Return Requests",380);
        JButton manageRequests = createButton("📬 Manage Requests",430);

        add(addBook);
        add(viewBooks);
        add(searchBook);
        add(updateBook);
        add(deleteBook);
        
        add(issuedBooks);
        add(returnRequests);
        add(manageRequests);

        JButton back = new JButton("⬅ Back");
        back.setBounds(10,10,80,30);
        back.setBackground(new Color(244,67,54));
        back.setForeground(Color.WHITE);
        back.setFocusPainted(false);
        add(back);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(320,10,90,30);
        logoutBtn.setBackground(new Color(103,58,183));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFocusPainted(false);
        add(logoutBtn);

        // 🔥 UPDATED LOGOUT (styled confirmation)
        logoutBtn.addActionListener(e -> {

            boolean confirm = showStyledConfirm("Are you sure you want to logout?", "Logout");

            if(confirm){
                new LoginGUI(null);
                dispose();
            }
        });

        addBook.addActionListener(e -> {
            new AddBookGUI(this);
            setVisible(false);
        });

        viewBooks.addActionListener(e -> {
            new ViewBooksGUI(this);
            setVisible(false);
        });

        searchBook.addActionListener(e -> {
            new SearchBooksGUI(this);
            setVisible(false);
        });

        updateBook.addActionListener(e -> {
            new UpdateBookGUI(this);
            setVisible(false);
        });

        deleteBook.addActionListener(e -> {
            new DeleteBookGUI(this);
            setVisible(false);
        });

       

        issuedBooks.addActionListener(e -> {
            new ViewIssuedBooksGUI(this);
            setVisible(false);
        });

        manageRequests.addActionListener(e -> {
            new ViewRequestsGUI(this);
            setVisible(false);
        });

        returnRequests.addActionListener(e -> {
            new ViewReturnRequestsGUI(this);
            setVisible(false);
        });

        back.addActionListener(e -> {
            if(prev != null){
                prev.setVisible(true);
            }
            dispose();
        });

        setVisible(true);
    }

    // 🔥 Styled Confirm Dialog
    private boolean showStyledConfirm(String message, String title){

        final boolean[] result = {false};

        JDialog dialog = new JDialog(this, title, true);
        dialog.setSize(320,180);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(null);

        dialog.getContentPane().setBackground(new Color(245,248,255));

        JLabel msg = new JLabel(message);
        msg.setBounds(20,30,280,30);
        msg.setFont(new Font("Segoe UI", Font.BOLD, 14));
        msg.setHorizontalAlignment(SwingConstants.CENTER);
        dialog.add(msg);

        JButton yesBtn = new JButton("Yes");
        yesBtn.setBounds(60,90,80,30);
        yesBtn.setBackground(new Color(46,204,113));
        yesBtn.setForeground(Color.WHITE);
        dialog.add(yesBtn);

        JButton noBtn = new JButton("No");
        noBtn.setBounds(170,90,80,30);
        noBtn.setBackground(new Color(231,76,60));
        noBtn.setForeground(Color.WHITE);
        dialog.add(noBtn);

        yesBtn.addActionListener(e -> {
            result[0] = true;
            dialog.dispose();
        });

        noBtn.addActionListener(e -> {
            dialog.dispose();
        });

        dialog.setVisible(true);

        return result[0];
    }

    // 🔹 Button Styling (same as before)
    private JButton createButton(String text, int y){

        JButton btn = new JButton(text);
        btn.setBounds(120,y,180,35);

        btn.setBackground(new Color(33,150,243));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI",Font.BOLD,13));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Color original = btn.getBackground();

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(original.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(original);
            }
        });

        return btn;
    }
}