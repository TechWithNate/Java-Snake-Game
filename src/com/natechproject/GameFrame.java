package com.natechproject;

import javax.swing.*;

public class GameFrame extends JFrame {

    // Game frame constructor for holding all Game panel components
    GameFrame(){
        this.add(new GamePanel());
        this.setTitle("Snake Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }
}
