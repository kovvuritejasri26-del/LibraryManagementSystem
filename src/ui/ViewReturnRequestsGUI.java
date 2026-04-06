package ui;

import db.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewReturnRequestsGUI extends JFrame {

    DefaultTableModel model;
    JTable table;

    public ViewReturnRequestsGUI(JFrame prev) {

        setTitle("Return Requests");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        getContentPane().setBackground(new Color(245, 248, 255));

        JLabel header = new JLabel("📚 Return Requests", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 22));
        header.setForeground(new Color(33, 150, 243));
        header.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        add(header, BorderLayout.NORTH);

        // 🔥 ONLY CHANGE → Issue ID added
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{
                "Issue ID", "Book ID", "User ID", "Issue Date"
        });

        table = new JTable(model);

        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(33, 150, 243));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(200, 230, 255));

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(center);
        }

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(sp, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.setBackground(new Color(245, 248, 255));

        JButton approve = new JButton("✔ Approve Return");
        JButton refresh = new JButton("🔄 Refresh");
        JButton back = new JButton("⬅ Back");

        styleButton(approve, new Color(76, 175, 80));
        styleButton(refresh, new Color(33, 150, 243));
        styleButton(back, new Color(244, 67, 54));

        bottom.add(approve);
        bottom.add(refresh);
        bottom.add(back);

        add(bottom, BorderLayout.SOUTH);

        loadData();

        // 🔥 UPDATED APPROVE LOGIC (ONLY FIX)
        approve.addActionListener(e -> {

            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(this, "⚠ Select a request first");
                return;
            }

            int issueId = (int) model.getValueAt(row, 0); // 🔥 NEW
            int bookId = (int) model.getValueAt(row, 1);

            try {

                Connection con = DBConnection.getConnection();

                // 🔥 FIX: use issue_id instead of book_id
                String sql = "UPDATE issued_books SET return_status='Returned', return_date=CURDATE() WHERE id=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, issueId);
                ps.executeUpdate();

                // Stock update same as before
                String updateStock = "UPDATE books SET quantity = quantity + 1 WHERE id=?";
                PreparedStatement ps2 = con.prepareStatement(updateStock);
                ps2.setInt(1, bookId);
                ps2.executeUpdate();

                JOptionPane.showMessageDialog(this, "✅ Book Return Approved!");

                loadData();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "❌ Error approving return");
            }

        });

        refresh.addActionListener(e -> loadData());

        back.addActionListener(e -> {
            dispose();
            if (prev != null) prev.setVisible(true);
        });

        setVisible(true);
    }

    // 🔥 LOAD DATA (ONLY ADD id)
    private void loadData() {

        model.setRowCount(0);

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT id, book_id, user_id, issue_date FROM issued_books WHERE return_status='Return Requested'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),        // 🔥 NEW
                        rs.getInt("book_id"),
                        rs.getInt("user_id"),
                        rs.getDate("issue_date")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void styleButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
    }
}