package ui;

import db.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class DeleteBookGUI extends JFrame {

    public DeleteBookGUI(JFrame prev){

        setTitle("Delete Book");
        setSize(400,250);
        setLayout(null);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(230,240,255));

        JLabel title = new JLabel("Delete Book");
        title.setBounds(130,20,150,30);
        title.setFont(new Font("Arial",Font.BOLD,20));
        title.setForeground(new Color(204,0,0));
        add(title);

        JLabel idLabel = new JLabel("Book ID");
        idLabel.setBounds(60,80,100,25);
        idLabel.setFont(new Font("Arial",Font.BOLD,13));
        add(idLabel);

        JTextField idField = new JTextField();
        idField.setBounds(160,80,150,25);
        idField.setBorder(BorderFactory.createLineBorder(new Color(204,0,0)));
        add(idField);

        JButton deleteBtn = new JButton("Delete");
        deleteBtn.setBounds(150,130,100,35);
        deleteBtn.setBackground(new Color(204,0,0));
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setFont(new Font("Arial",Font.BOLD,13));
        deleteBtn.setFocusPainted(false);
        add(deleteBtn);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(10,10,80,30);
        backBtn.setBackground(new Color(0,102,204));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        add(backBtn);

        deleteBtn.addActionListener(e -> {

            try{

                int id = Integer.parseInt(idField.getText());

                Connection con = DBConnection.getConnection();

                String sql = "DELETE FROM books WHERE id=?";

                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1,id);

                int result = ps.executeUpdate();

                if(result > 0){

                    JOptionPane.showMessageDialog(this,"Book Deleted Successfully");

                }else{

                    JOptionPane.showMessageDialog(this,"Book Not Found");

                }

            }catch(Exception ex){

                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,"Error Deleting Book");

            }

        });

        backBtn.addActionListener(e -> {

            if(prev != null){
                prev.setVisible(true);
            }
            dispose();

        });

        setVisible(true);
    }

    // Optional main method (run cheyyadaniki)
    public static void main(String[] args) {
        new DeleteBookGUI(null);
    }
}