package ui;

import db.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MyRequestsGUI extends JFrame {

    public MyRequestsGUI(JFrame prev){

        setTitle("📋 My Requests");
        setSize(700,450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 🔹 Background Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245,248,255));
        add(mainPanel);

        // 🔹 Title
        JLabel title = new JLabel("My Book Requests", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(new Color(0,102,204));
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        mainPanel.add(title, BorderLayout.NORTH);

        // 🔹 Table Model
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"📚 Book","📌 Status"});

        JTable table = new JTable(model);
        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setSelectionBackground(new Color(200,220,255));
        table.setGridColor(new Color(220,220,220));

        // 🔹 Table Header Styling
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(new Color(0,102,204));
        header.setForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        mainPanel.add(scroll, BorderLayout.CENTER);

        // 🔹 Bottom Panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(245,248,255));

        JButton back = new JButton("⬅ Back");
        styleButton(back, new Color(204,0,0));

        bottomPanel.add(back);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // 🔹 Database Fetch (UNCHANGED LOGIC)
        try{
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(
                    "SELECT b.title, r.status FROM requests r " +
                    "JOIN books b ON r.book_id = b.id"
            );

            while(rs.next()){
                model.addRow(new Object[]{
                        rs.getString(1),
                        rs.getString(2)
                });
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        // 🔹 Back Action
        back.addActionListener(e -> {
            prev.setVisible(true);
            dispose();
        });

        setVisible(true);
    }

    // 🔥 Button Styling
    private void styleButton(JButton btn, Color color){
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));

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
