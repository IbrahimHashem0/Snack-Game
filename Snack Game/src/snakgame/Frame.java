package snakgame;

import javax.swing.*;

public class Frame extends JFrame {
    public Frame() {
        super("Snake Game");
        this.add(new panel());
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        String path = "E:\\Courses&Skills\\Programming\\New folder\\download.PNG";
        ImageIcon image = new ImageIcon(path);
        this.setVisible(true);
    }
}
