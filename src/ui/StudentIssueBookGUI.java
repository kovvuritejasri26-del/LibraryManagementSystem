package ui;

import db.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StudentIssueBookGUI extends JFrame {

    public StudentIssueBookGUI(JFrame prev){

        setTitle("Issue Book");
        setSize(350,300);
        setLayout(null);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(230,240,255));

        JLabel title = new JLabel("Issue Book");
        title.setBounds(120,15,150,30);
        title.setFont(new Font("Arial",Font.BOLD,18));
        title.setForeground(new Color(0,102,204));
        add(title);

        JLabel bookId = new JLabel("Book ID");
        bookId.setBounds(50,70,100,25);
        bookId.setFont(new Font("Arial",Font.BOLD,13));
        add(bookId);

        JTextField bookField = new JTextField();
        bookField.setBounds(150,70,120,25);
        bookField.setBorder(BorderFactory.createLineBorder(new Color(0,102,204)));
        add(bookField);

        JButton issue = new JButton("Issue Book");
        issue.setBounds(110,170,120,30);
        issue.setBackground(new Color(0,153,76));
        issue.setForeground(Color.WHITE);
        issue.setFocusPainted(false);
        add(issue);

        JButton back = new JButton("Back");
        back.setBounds(10,10,70,25);
        back.setBackground(new Color(102,0,153));
        back.setForeground(Color.WHITE);
        back.setFocusPainted(false);
        add(back);

        issue.addActionListener(e -> {

            try{

                int bookIdVal = Integer.parseInt(bookField.getText());

                int userIdVal = 1; // 🔥 TEMP (later login user id pettachu)

                Connection con = DBConnection.getConnection();

                String checkSql = "SELECT quantity FROM books WHERE id=?";
                PreparedStatement checkPs = con.prepareStatement(checkSql);
                checkPs.setInt(1,bookIdVal);

                ResultSet rs = checkPs.executeQuery();

                if(rs.next()){

                    int qty = rs.getInt("quantity");

                    if(qty > 0){

                        String issueSql = "INSERT INTO issued_books(book_id,user_id,issue_date) VALUES(?,?,CURDATE())";

                        PreparedStatement issuePs = con.prepareStatement(issueSql);
                        issuePs.setInt(1,bookIdVal);
                        issuePs.setInt(2,userIdVal);
                        issuePs.executeUpdate();

                        String updateSql = "UPDATE books SET quantity = quantity - 1 WHERE id=?";
                        PreparedStatement updatePs = con.prepareStatement(updateSql);
                        updatePs.setInt(1,bookIdVal);
                        updatePs.executeUpdate();

                        JOptionPane.showMessageDialog(this,"Book Issued Successfully");

                    }else{

                        JOptionPane.showMessageDialog(this,"Book Not Available");

                    }

                }else{

                    JOptionPane.showMessageDialog(this,"Invalid Book ID");

                }

            }catch(Exception ex){

                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,"Error Issuing Book");

            }

        });

        back.addActionListener(e -> {

            if(prev != null){
                prev.setVisible(true);
            } else {
                new LoginGUI(null);
            }

            dispose();

        });

        setVisible(true);
    }
}