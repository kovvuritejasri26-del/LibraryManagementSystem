
package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import db.DBConnection;

public class AddBookGUI extends JFrame {

    JTextField titleField, authorField, quantityField;

    public AddBookGUI(JFrame prev){

        setTitle("Add Book");
        setSize(400,350);
        setLayout(null);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(230,240,255));

        JLabel title = new JLabel("Add New Book");
        title.setBounds(120,20,200,30);
        title.setFont(new Font("Arial",Font.BOLD,20));
        title.setForeground(new Color(0,102,204));
        add(title);

        JLabel l1 = new JLabel("Book Title:");
        l1.setBounds(50,80,100,25);
        l1.setFont(new Font("Arial",Font.BOLD,13));
        add(l1);

        titleField = new JTextField();
        titleField.setBounds(150,80,180,25);
        titleField.setBorder(BorderFactory.createLineBorder(new Color(0,102,204)));
        add(titleField);

        JLabel l2 = new JLabel("Author:");
        l2.setBounds(50,120,100,25);
        l2.setFont(new Font("Arial",Font.BOLD,13));
        add(l2);

        authorField = new JTextField();
        authorField.setBounds(150,120,180,25);
        authorField.setBorder(BorderFactory.createLineBorder(new Color(0,102,204)));
        add(authorField);

        JLabel l3 = new JLabel("Quantity:");
        l3.setBounds(50,160,100,25);
        l3.setFont(new Font("Arial",Font.BOLD,13));
        add(l3);

        quantityField = new JTextField();
        quantityField.setBounds(150,160,180,25);
        quantityField.setBorder(BorderFactory.createLineBorder(new Color(0,102,204)));
        add(quantityField);

        JButton addBtn = new JButton("Add Book");
        addBtn.setBounds(80,220,100,30);
        addBtn.setBackground(new Color(0,153,76));
        addBtn.setForeground(Color.WHITE);
        addBtn.setFocusPainted(false);
        add(addBtn);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(200,220,100,30);
        backBtn.setBackground(new Color(204,0,0));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        add(backBtn);

        addBtn.addActionListener(e -> addBook());

        backBtn.addActionListener(e -> {
            prev.setVisible(true);
            dispose();
        });

        setVisible(true);
    }

    private void addBook(){

        String title = titleField.getText();
        String author = authorField.getText();
        String qty = quantityField.getText();

        if(title.isEmpty() || author.isEmpty() || qty.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please fill all fields");
            return;
        }

        try{

            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO books(title,author,quantity) VALUES(?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1,title);
            ps.setString(2,author);
            ps.setInt(3,Integer.parseInt(qty));

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this,"Book Added Successfully");

            titleField.setText("");
            authorField.setText("");
            quantityField.setText("");

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

