package snakgame;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class panel extends JPanel implements ActionListener {
    final int WIDTH = 600;
    final int HEIGHT = 600;
    final int unit = 25;
    final int gameUnits = (WIDTH*WIDTH)/unit;
    final int [] x = new int [gameUnits];
    final int [] y = new int [gameUnits];
    int appleX;
    int appleY;
    int delay =100;
    Random random;
    Timer timer;
    int bodyParts = 6;
    int appleEaten;
    char direction ='R';
    boolean running = false;
    public panel() {
      super();
      this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
      this.setBackground(Color.BLACK);
      startGame();
      this.setFocusable(true);
      this.addKeyListener(new key());
    }
    @Override
    public void paintComponent (Graphics G) {
        super.paintComponent(G);
        draw(G);
    }
    public void startGame(){
        running = true;
        timer = new Timer(delay,this);
        random = new Random();
        timer.start();
        newApple();
    }
    public void move (){
        for (int i = bodyParts; i >0; i--) {
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        switch(direction){
            case 'U':
                y[0]-=unit;
                break;
            case 'D':
                y[0]+=unit;
                break;
            case 'L':
                x[0]-=unit;
                break;
            case 'R':
                x[0]+=unit;
                break;
            default:
                break;

        }
    }
    public void checkEat(){
        if (appleX==x[0] && appleY==y[0]){
            appleEaten++;
            bodyParts++;
            newApple();
        }
    }
    public void checkCollision(){
        if (x[0]<0||y[0]<0||x[0]>=WIDTH||y[0]>=HEIGHT){
            running = false;
        }
        for (int i = 3; i < bodyParts ; i++) {
            if (x[0]==x[i] && y[0]==y[i]){
                running = false;
            }
        }
    }
    public void newApple() {
        appleX = random.nextInt((int)(WIDTH/unit))*unit;
        appleY = random.nextInt((int)(HEIGHT/unit))*unit;
    }
    public void draw (Graphics G) {
        if (running) {
            for (int i = 0; i < (gameUnits/unit); i++) {
                G.drawLine(i*unit,0, i*unit, HEIGHT);
                G.drawLine(0, i*unit, WIDTH, i*unit);
            }
            G.setColor(Color.RED);
            G.fillOval(appleX, appleY, unit, unit);
            for (int i = 0; i < bodyParts; i++) {
                if (i==0){
                    G.setColor(Color.YELLOW);
                    G.fillRect(x[i], y[i], unit, unit);
                }
                else {
                    G.setColor(Color.WHITE);
                    G.fillRect(x[i], y[i], unit, unit);
                }
            }
        }
        else
            gameOver(G);
    }
    public void gameOver(Graphics G) {
        G.setColor(Color.RED);
        G.setFont(new Font("Ink free", Font.ITALIC, 85));
        FontMetrics fm = G.getFontMetrics(G.getFont());
        G.drawString("Game Over", WIDTH/2-fm.stringWidth("Game Over")/2, HEIGHT/2);
        G.setFont(new Font("MV Boli ", Font.ITALIC, 35));
        G.drawString("    Score: "+ appleEaten,WIDTH/2-fm.stringWidth("Score: ")/2,HEIGHT/2+100);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkEat();
            checkCollision();
        }
        repaint();
    }
    public class key extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    if (direction!='D')
                        direction='U';
                    break;
                    case KeyEvent.VK_DOWN:
                        if (direction!='U')
                            direction = 'D';
                        break;
                        case KeyEvent.VK_LEFT:
                            if (direction!='R')
                                direction = 'L';
                            break;
                            case KeyEvent.VK_RIGHT:
                                if (direction!='L')
                                    direction = 'R';
                                break;
            }
        }
    }
}

