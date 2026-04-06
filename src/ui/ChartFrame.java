package ui;

import javax.swing.*;
import java.awt.*;

public class ChartFrame extends JFrame {

    public ChartFrame(JFrame prev){

        setTitle("Library Graphs");
        setSize(400,300);
        setLocationRelativeTo(null);

        ChartsGUI chart = new ChartsGUI();
        add(chart);

        JButton back = new JButton("⬅ Back");
        back.setBounds(10,10,80,30);
        back.setBackground(new Color(204,0,0));
        back.setForeground(Color.WHITE);
        back.setFont(new Font("Arial",Font.BOLD,12));
        back.setFocusPainted(false);
        back.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(back);

        setLayout(null);
        chart.setBounds(0,0,400,250);

        back.addActionListener(e -> {
            prev.setVisible(true);
            dispose();
        });

        setVisible(true);
    }
}