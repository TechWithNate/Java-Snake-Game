package com.natechproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    // Variables for game panel
    static final int SCREEN_WIDTH = 600;    // Screen height
    static final int SCREEN_HEIGHT = 600;   // Screen width
    static final int UNIT_SIZE = 25;        // Single unit box size
    static final int GAME_UNIT = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;    // Game Unit
    static final int DELAY = 90;            // Delay time while playing
    final int[] x = new int[GAME_UNIT];     // Total Distance of the x-coordinate
    final int[] y = new int[GAME_UNIT];     // Total Distance of the y-coordinate
    int bodyParts = 5;  // Initial number of snake body part
    int applesEaten;    // Total number of apples eaten
    int appleX;         // X dimension of apple
    int appleY;         // Y dimension of apple
    char direction = 'R';       // Initial Direction of apple
    boolean running = false;     // Moving or running state of apple
    Timer timer;        // Timer
    Random random;      // Random class responsible for random generation of apples

    // Game Panel where all component are added and displayed.
    GamePanel(){

        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(new Color(0, 0, 0));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        start();
    }

    // Methods needed to be used in the game
    public void start(){

        newApple(); // Generate new apple on game start
        running = true;
        timer = new Timer(DELAY, this);     // Timer delay initialized
        timer.start();
    }

    // Paint component method
    public void paintComponent(Graphics g){
        super.paintComponent(g);    // Paint components
        draw(g);
    }

    // Draw component method
    public void draw(Graphics g) {
        if (running){

            // Display grind on screen to show the unit size of each item
//            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
//                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
//                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
//            }


            // Paint apple
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            //
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.blue);
                } else {
                    g.setColor(new Color(16, 201, 201));
                }
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }
            g.setColor(new Color(200, 0,20));
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/ 2, g.getFont().getSize());
    }
        else {
            gameOver(g);
        }
    }

    // Move Method
    public void move(){
        for (int i = bodyParts; i > 0; i--){
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction){
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
        }


    }

    public void newApple(){
        appleX = random.nextInt((int)(SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    // Check Apple eaten method
    public void checkApple(){
        if ((x[0] == appleX) && (y[0] == appleY)){
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    // Check collision method
    public void checkCollisions(){
        // Check if snake head touches any part of the body
        for (int i = bodyParts; i > 0; i--){
            if ((x[0] == x[i]) && (y[0] == y[i])){
                running = false;
            }
        }

        // Check if snake head touches left border
        if ((x[0] < 0)){
            running = false;
        }

        // Check if snake head touches right border
        if ((x[0] > SCREEN_WIDTH)){
            running = false;
        }

        // Check if snake head touches top border
        if ((y[0] < 0)){
            running = false;
        }

        // Check if snake head touches bottom border
        if ((y[0] > SCREEN_HEIGHT)){
            running = false;
        }

        // Stop timer if game not running
        if (!running){
            timer.stop();
        }

    }

    // Game Over Method
    public void gameOver(Graphics g){
        // Final Score
        g.setColor(new Color(200, 0,20));
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: "+applesEaten))/ 2, g.getFont().getSize());

        // Set game over text
        g.setColor(new Color(200, 0,20));
        g.setFont(new Font("Ink Free", Font.BOLD, 50));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game OVer"))/ 2, SCREEN_HEIGHT / 2);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (running){
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    // Inner class for detecting action performed.
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if (direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U'){
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
