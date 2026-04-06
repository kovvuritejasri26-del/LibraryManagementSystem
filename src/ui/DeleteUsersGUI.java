
package ui;

import db.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class DeleteUsersGUI extends JFrame {

    public DeleteUsersGUI(JFrame prev){

        setTitle("Delete User");
        setSize(300,220);
        setLayout(null);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(230,240,255));

        JLabel title = new JLabel("Delete User");
        title.setBounds(90,15,150,30);
        title.setFont(new Font("Arial",Font.BOLD,18));
        title.setForeground(new Color(0,102,204));
        add(title);

        JLabel id = new JLabel("User ID");
        id.setBounds(50,70,100,25);
        id.setFont(new Font("Arial",Font.BOLD,13));
        add(id);

        JTextField idField = new JTextField();
        idField.setBounds(120,70,120,25);
        idField.setBorder(BorderFactory.createLineBorder(new Color(0,102,204)));
        add(idField);

        JButton delete = new JButton("Delete");
        delete.setBounds(90,120,100,30);
        delete.setBackground(new Color(204,0,0));
        delete.setForeground(Color.WHITE);
        delete.setFocusPainted(false);
        add(delete);

        JButton back = new JButton("Back");
        back.setBounds(10,10,70,25);
        back.setBackground(new Color(102,0,153));
        back.setForeground(Color.WHITE);
        back.setFocusPainted(false);
        add(back);

        delete.addActionListener(e -> {

            try{

                Connection con = DBConnection.getConnection();

                String sql = "DELETE FROM users WHERE id=?";

                PreparedStatement ps = con.prepareStatement(sql);

                ps.setInt(1,Integer.parseInt(idField.getText()));

                ps.executeUpdate();

                JOptionPane.showMessageDialog(this,"User Deleted");

            }catch(Exception ex){
                ex.printStackTrace();
            }

        });

        back.addActionListener(e -> {

            prev.setVisible(true);
            dispose();

        });

        setVisible(true);
    }
}

