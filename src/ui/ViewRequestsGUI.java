package ui;

import db.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ViewRequestsGUI extends JFrame {

    public ViewRequestsGUI(JFrame prev) {

        setTitle("Manage Requests");
        setSize(750, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 🔷 Background Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(230, 240, 255));
        add(mainPanel);

        // 🔷 Title
        JLabel title = new JLabel("📬 Manage Book Requests", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(new Color(0, 102, 204));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(title, BorderLayout.NORTH);

        // Table Model
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Book", "User ID", "Status"});

        JTable table = new JTable(model);

        // 🔷 Table Styling
        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(0, 153, 76));
        table.getTableHeader().setForeground(Color.WHITE);

        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        // ================= LOAD DATA =================
        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT r.id, b.title, r.user_id, r.status, r.book_id " +
                         "FROM requests r JOIN books b ON r.book_id = b.id";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getInt("user_id"),
                        rs.getString("status")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // ================= BUTTON PANEL =================
        JPanel panel = new JPanel();
        panel.setBackground(new Color(230, 240, 255));

        JButton approve = createButton("Approve");
        JButton reject = createButton("Reject");
        JButton back = createButton("⬅ Back");

        panel.add(approve);
        panel.add(reject);
        panel.add(back);

        mainPanel.add(panel, BorderLayout.SOUTH);

        // ================= APPROVE =================
        approve.addActionListener(e -> {

            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select a request!");
                return;
            }

            int requestId = (int) model.getValueAt(row, 0);
            int userId = (int) model.getValueAt(row, 2);
            String status = model.getValueAt(row, 3).toString();

            if (status.equalsIgnoreCase("Approved")) {
                JOptionPane.showMessageDialog(this, "Already Approved!");
                return;
            }

            try {

                Connection con = DBConnection.getConnection();

                String getBook = "SELECT book_id FROM requests WHERE id=?";
                PreparedStatement ps1 = con.prepareStatement(getBook);
                ps1.setInt(1, requestId);
                ResultSet rs1 = ps1.executeQuery();

                int bookId = 0;
                if (rs1.next()) {
                    bookId = rs1.getInt("book_id");
                }

                String updateReq = "UPDATE requests SET status='Approved' WHERE id=?";
                PreparedStatement ps2 = con.prepareStatement(updateReq);
                ps2.setInt(1, requestId);
                ps2.executeUpdate();

                String insertIssue = "INSERT INTO issued_books (book_id, user_id, issue_date, return_date) VALUES (?, ?, CURDATE(), NULL)";
                PreparedStatement ps3 = con.prepareStatement(insertIssue);
                ps3.setInt(1, bookId);
                ps3.setInt(2, userId);
                ps3.executeUpdate();

                JOptionPane.showMessageDialog(this, "Approved & Book Issued!");

                model.setValueAt("Approved", row, 3);

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error while approving");
            }
        });

        // ================= REJECT =================
        reject.addActionListener(e -> {

            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select a request!");
                return;
            }

            int requestId = (int) model.getValueAt(row, 0);

            try {

                Connection con = DBConnection.getConnection();

                String sql = "UPDATE requests SET status='Rejected' WHERE id=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, requestId);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(this, "Rejected!");

                model.setValueAt("Rejected", row, 3);

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error while rejecting");
            }
        });

        // ================= BACK =================
        back.addActionListener(e -> {
            if (prev != null) prev.setVisible(true);
            dispose();
        });

        setVisible(true);
    }

    // 🔥 Styled Button (same as your dashboard style)
    private JButton createButton(String text) {

        JButton btn = new JButton(text);

        btn.setBackground(new Color(0, 153, 76));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setPreferredSize(new Dimension(120, 35));

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