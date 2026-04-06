
package ui;

import db.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReturnBookGUI extends JFrame {

    public ReturnBookGUI(JFrame prev){

        setTitle("Return Book");
        setSize(350,250);
        setLayout(null);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(230,240,255));

        JLabel title = new JLabel("Return Book");
        title.setBounds(110,20,150,30);
        title.setFont(new Font("Arial",Font.BOLD,20));
        title.setForeground(new Color(0,102,204));
        add(title);

        JLabel issueId = new JLabel("Issue ID");
        issueId.setBounds(50,80,100,25);
        issueId.setFont(new Font("Arial",Font.BOLD,13));
        add(issueId);

        JTextField issueField = new JTextField();
        issueField.setBounds(150,80,120,25);
        issueField.setBorder(BorderFactory.createLineBorder(new Color(0,102,204)));
        add(issueField);

        JButton returnBtn = new JButton("Return Book");
        returnBtn.setBounds(110,130,130,35);
        returnBtn.setBackground(new Color(0,153,76));
        returnBtn.setForeground(Color.WHITE);
        returnBtn.setFocusPainted(false);
        returnBtn.setFont(new Font("Arial",Font.BOLD,13));
        add(returnBtn);

        JButton back = new JButton("Back");
        back.setBounds(10,10,80,30);
        back.setBackground(new Color(204,0,0));
        back.setForeground(Color.WHITE);
        back.setFocusPainted(false);
        add(back);

        returnBtn.addActionListener(e -> {

            try{

                int issueIdVal = Integer.parseInt(issueField.getText());

                Connection con = DBConnection.getConnection();

                String getBookSql = "SELECT book_id, return_date FROM issued_books WHERE id=?";
                PreparedStatement ps1 = con.prepareStatement(getBookSql);
                ps1.setInt(1, issueIdVal);

                ResultSet rs = ps1.executeQuery();

                if(rs.next()){

                    int bookId = rs.getInt("book_id");
                    java.sql.Date returnDate = rs.getDate("return_date");

                    if(returnDate != null){

                        JOptionPane.showMessageDialog(this,"Book already returned!");
                        return;
                    }

                    String returnSql = "UPDATE issued_books SET return_date=CURDATE() WHERE id=?";
                    PreparedStatement ps2 = con.prepareStatement(returnSql);
                    ps2.setInt(1, issueIdVal);
                    ps2.executeUpdate();

                    String updateQty = "UPDATE books SET quantity = quantity + 1 WHERE id=?";
                    PreparedStatement ps3 = con.prepareStatement(updateQty);
                    ps3.setInt(1, bookId);
                    ps3.executeUpdate();

                    JOptionPane.showMessageDialog(this,"Book Returned Successfully");

                }else{

                    JOptionPane.showMessageDialog(this,"Invalid Issue ID");

                }

            }catch(Exception ex){

                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,"Error Returning Book");

            }

        });

        back.addActionListener(e -> {

            prev.setVisible(true);
            dispose();

        });

        setVisible(true);
    }
}

