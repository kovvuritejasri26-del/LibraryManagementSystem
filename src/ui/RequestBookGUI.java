package ui;

import db.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class RequestBookGUI extends JFrame {

    int userId;

    public RequestBookGUI(int userId){

        this.userId = userId;

        setTitle("📚 Request Book");
        setSize(700,450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240,248,255));
        add(panel);

        JLabel title = new JLabel("Available Books", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(new Color(0,102,204));
        panel.add(title, BorderLayout.NORTH);

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Book ID","Book Name"});

        JTable table = new JTable(model);
        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane scroll = new JScrollPane(table);
        panel.add(scroll, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(240,248,255));

        JButton requestBtn = new JButton("📩 Request Book");
        styleButton(requestBtn, new Color(0,153,76));

        JButton backBtn = new JButton("⬅ Back");
        styleButton(backBtn, new Color(204,0,0));

        btnPanel.add(requestBtn);
        btnPanel.add(backBtn);

        panel.add(btnPanel, BorderLayout.SOUTH);

        // 🔹 Load Books (unchanged)
        try{
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("SELECT id, title FROM books");

            while(rs.next()){
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("title")
                });
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        // 🔥 UPDATED LOGIC: Request button works properly
        requestBtn.addActionListener(e -> {

            int row = table.getSelectedRow();

            if(row == -1){
                JOptionPane.showMessageDialog(this,"⚠ Please select a book!");
                return;
            }

            int bookId = (int) model.getValueAt(row,0);

            try{
                Connection con = DBConnection.getConnection();

                // 🔍 Check if this student already requested this book
                String checkSql = "SELECT * FROM requests WHERE book_id=? AND user_id=? AND status='Pending'";
                PreparedStatement checkPs = con.prepareStatement(checkSql);
                checkPs.setInt(1, bookId);
                checkPs.setInt(2, userId);

                ResultSet rs = checkPs.executeQuery();

                if(rs.next()){
                    JOptionPane.showMessageDialog(this,"⚠ Already requested this book!");
                    return;
                }

                // ✅ Insert request
                String insertSql = "INSERT INTO requests (book_id,user_id,request_date,status) VALUES (?,?,CURDATE(),'Pending')";
                PreparedStatement ps = con.prepareStatement(insertSql);

                ps.setInt(1, bookId);
                ps.setInt(2, userId);

                ps.executeUpdate();

                JOptionPane.showMessageDialog(this,"✅ Request Sent Successfully!");

            }catch(Exception ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,"❌ Error while sending request");
            }

        });

        // 🔹 Back (unchanged)
        backBtn.addActionListener(e -> {
            new Student(userId);
            dispose();
        });

        setVisible(true);
    }

    private void styleButton(JButton btn, Color color){
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(150,35));

        Color original = color;
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(original.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(original);
            }
        });
    }
}