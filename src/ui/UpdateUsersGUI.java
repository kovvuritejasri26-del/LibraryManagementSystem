
package ui;

import db.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class UpdateUsersGUI extends JFrame {

    public UpdateUsersGUI(JFrame prev){

        setTitle("Update User");
        setSize(400,300);
        setLayout(null);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(230,240,255));

        JLabel title = new JLabel("Update User");
        title.setBounds(130,20,150,30);
        title.setFont(new Font("Arial",Font.BOLD,20));
        title.setForeground(new Color(0,102,204));
        add(title);

        JLabel idLabel = new JLabel("User ID");
        idLabel.setBounds(60,70,100,25);
        idLabel.setFont(new Font("Arial",Font.BOLD,13));
        add(idLabel);

        JTextField idField = new JTextField();
        idField.setBounds(160,70,150,25);
        idField.setBorder(BorderFactory.createLineBorder(new Color(0,102,204)));
        add(idField);

        JLabel nameLabel = new JLabel("New Name");
        nameLabel.setBounds(60,110,100,25);
        nameLabel.setFont(new Font("Arial",Font.BOLD,13));
        add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(160,110,150,25);
        nameField.setBorder(BorderFactory.createLineBorder(new Color(0,102,204)));
        add(nameField);

        JLabel roleLabel = new JLabel("New Role");
        roleLabel.setBounds(60,150,100,25);
        roleLabel.setFont(new Font("Arial",Font.BOLD,13));
        add(roleLabel);

        JTextField roleField = new JTextField();
        roleField.setBounds(160,150,150,25);
        roleField.setBorder(BorderFactory.createLineBorder(new Color(0,102,204)));
        add(roleField);

        JButton updateBtn = new JButton("Update");
        updateBtn.setBounds(140,200,100,35);
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
                String name = nameField.getText();
                String role = roleField.getText();

                Connection con = DBConnection.getConnection();

                String sql = "UPDATE users SET name=?, role=? WHERE id=?";

                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1,name);
                ps.setString(2,role);
                ps.setInt(3,id);

                int result = ps.executeUpdate();

                if(result > 0){

                    JOptionPane.showMessageDialog(this,"User Updated Successfully");

                }else{

                    JOptionPane.showMessageDialog(this,"User Not Found");

                }

            }catch(Exception ex){

                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,"Error Updating User");

            }

        });

        backBtn.addActionListener(e -> {

            prev.setVisible(true);
            dispose();

        });

        setVisible(true);
    }
}

