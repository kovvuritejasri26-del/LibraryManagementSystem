package ui;

import db.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ViewIssuedBooksGUI extends JFrame {

    public ViewIssuedBooksGUI(JFrame prev){

        setTitle("Issued Books");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Main panel with padding
        JPanel mainPanel = new JPanel(new BorderLayout(10,10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        mainPanel.setBackground(new Color(245, 247, 250));

        // Table Model
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{
                "Book Name","User Name","Issue Date","Return Date"
        });

        JTable table = new JTable(model);

        // Table Styling
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setBackground(Color.WHITE);
        table.setGridColor(new Color(220, 220, 220));
        table.setShowGrid(true);

        // Header Styling
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(33, 150, 243));
        header.setForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        mainPanel.add(scroll, BorderLayout.CENTER);

        // Back Button Panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(245, 247, 250));

        JButton back = new JButton("← Back");
        back.setFont(new Font("Segoe UI", Font.BOLD, 14));
        back.setBackground(new Color(33, 150, 243));
        back.setForeground(Color.WHITE);
        back.setFocusPainted(false);
        back.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));

        bottomPanel.add(back);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // 🔥 FIXED QUERY (ONLY CHANGE)
        try{

            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();

            String sql =  "SELECT b.title, u.name, i.issue_date, i.return_date "
                    + "FROM issued_books i "
                    + "LEFT JOIN books b ON i.book_id = b.id "
                    + "LEFT JOIN users u ON i.user_id = u.id";
                    // ❌ removed: WHERE i.return_date IS NULL

            ResultSet rs = st.executeQuery(sql);

            while(rs.next()){
                model.addRow(new Object[]{
                        rs.getString("title"),
                        rs.getString("name"),
                        rs.getString("issue_date"),
                        rs.getString("return_date")
                });
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        back.addActionListener(e -> {
            prev.setVisible(true);
            dispose();
        });

        setVisible(true);
    }
}