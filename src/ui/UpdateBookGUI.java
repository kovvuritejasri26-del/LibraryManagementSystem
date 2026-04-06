
package ui;

import db.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class UpdateBookGUI extends JFrame {

    public UpdateBookGUI(JFrame prev){

        setTitle("Update Book");
        setSize(400,300);
        setLayout(null);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(230,240,255));

        JLabel title = new JLabel("Update Book");
        title.setBounds(130,20,150,30);
        title.setFont(new Font("Arial",Font.BOLD,20));
        title.setForeground(new Color(0,102,204));
        add(title);

        JLabel idLabel = new JLabel("Book ID");
        idLabel.setBounds(60,70,100,25);
        idLabel.setFont(new Font("Arial",Font.BOLD,13));
        add(idLabel);

        JTextField idField = new JTextField();
        idField.setBounds(160,70,150,25);
        idField.setBorder(BorderFactory.createLineBorder(new Color(0,102,204)));
        add(idField);

        JLabel titleLabel = new JLabel("New Title");
        titleLabel.setBounds(60,110,100,25);
        titleLabel.setFont(new Font("Arial",Font.BOLD,13));
        add(titleLabel);

        JTextField titleField = new JTextField();
        titleField.setBounds(160,110,150,25);
        titleField.setBorder(BorderFactory.createLineBorder(new Color(0,102,204)));
        add(titleField);

        JLabel authorLabel = new JLabel("New Author");
        authorLabel.setBounds(60,150,100,25);
        authorLabel.setFont(new Font("Arial",Font.BOLD,13));
        add(authorLabel);

        JTextField authorField = new JTextField();
        authorField.setBounds(160,150,150,25);
        authorField.setBorder(BorderFactory.createLineBorder(new Color(0,102,204)));
        add(authorField);

        JLabel qtyLabel = new JLabel("New Quantity");
        qtyLabel.setBounds(60,190,100,25);
        qtyLabel.setFont(new Font("Arial",Font.BOLD,13));
        add(qtyLabel);

        JTextField qtyField = new JTextField();
        qtyField.setBounds(160,190,150,25);
        qtyField.setBorder(BorderFactory.createLineBorder(new Color(0,102,204)));
        add(qtyField);

        JButton updateBtn = new JButton("Update");
        updateBtn.setBounds(150,230,100,35);
        updateBtn.setBackground(new Color(0,153,76));
        updateBtn.setForeground(Color.WHITE);
        updateBtn.setFont(new Font("Arial",Font.BOLD,13));
        updateBtn.setFocusPainted(false);
        add(updateBtn);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(10,10,80,30);
        backBtn.setBackground(new Color(204,0,0));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        add(backBtn);

        updateBtn.addActionListener(e -> {

            try{

                int id = Integer.parseInt(idField.getText());
                String titleText = titleField.getText();
                String authorText = authorField.getText();
                int qty = Integer.parseInt(qtyField.getText());

                Connection con = DBConnection.getConnection();

                String sql = "UPDATE books SET title=?, author=?, quantity=? WHERE id=?";

                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1,titleText);
                ps.setString(2,authorText);
                ps.setInt(3,qty);
                ps.setInt(4,id);

                int result = ps.executeUpdate();

                if(result > 0){

                    JOptionPane.showMessageDialog(this,"Book Updated Successfully");

                }else{

                    JOptionPane.showMessageDialog(this,"Book Not Found");

                }

            }catch(Exception ex){

                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,"Error Updating Book");

            }

        });

        backBtn.addActionListener(e -> {

            prev.setVisible(true);
            dispose();

        });

        setVisible(true);
    }
}

