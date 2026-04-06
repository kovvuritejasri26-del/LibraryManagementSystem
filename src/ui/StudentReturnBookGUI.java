package ui;

import db.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StudentReturnBookGUI extends JFrame {

    int userId;

    public StudentReturnBookGUI(JFrame prev, int userId) {

        this.userId = userId;

        setTitle("Return Book");
        setSize(500, 350);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        getContentPane().setBackground(new Color(245, 248, 255));

        // 🔹 Title
        JLabel title = new JLabel("Return Book", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(new Color(33, 150, 243));
        add(title, BorderLayout.NORTH);

        // 🔹 Table
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Issue ID", "Book Name", "Issue Date"});

        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        // 🔹 Load Data (only issued books)
        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT i.id, b.title, i.issue_date " +
                    "FROM issued_books i " +
                    "JOIN books b ON i.book_id = b.id " +
                    "WHERE i.user_id=? AND i.return_status='Issued'";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("issue_date")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 🔹 Buttons
        JPanel panel = new JPanel();

        JButton returnBtn = new JButton("Return");
        styleButton(returnBtn, new Color(76, 175, 80));

        JButton back = new JButton("Back");
        styleButton(back, new Color(244, 67, 54));

        panel.add(returnBtn);
        panel.add(back);

        add(panel, BorderLayout.SOUTH);

        // 🔥 RETURN ACTION
        returnBtn.addActionListener(e -> {

            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select a book");
                return;
            }

            int issueId = (int) model.getValueAt(row, 0);

            try {
                Connection con = DBConnection.getConnection();

                String sql = "UPDATE issued_books SET return_status='Return Requested' WHERE id=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, issueId);

                ps.executeUpdate();

                JOptionPane.showMessageDialog(this, "Return request sent");

                model.removeRow(row); // remove from table

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error");
            }
        });

        // 🔹 Back
        back.addActionListener(e -> {
            if (prev != null) prev.setVisible(true);
            dispose();
        });

        setVisible(true);
    }

    private void styleButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}