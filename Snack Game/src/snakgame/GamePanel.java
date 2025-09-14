package snakegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements ActionListener {
    static final int WIDTH = 600;
    static final int HEIGHT = 600;
    static final int UNIT = 25;
    static final int DELAY = 100;

    Snake snake;
    Apple apple;
    int applesEaten = 0;
    Timer timer;
    boolean running = false;

    public GamePanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame() {
        snake = new Snake(WIDTH / 2, HEIGHT / 2);
        apple = new Apple();
        apple.newApple(WIDTH, HEIGHT, UNIT, snake);

        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
            g.setColor(Color.RED);
            g.fillOval(apple.position.x, apple.position.y, UNIT, UNIT);

            for (int i = 0; i < snake.body.size(); i++) {
                g.setColor(i == 0 ? Color.YELLOW : Color.GREEN);
                Point p = snake.body.get(i);
                g.fillRect(p.x, p.y, UNIT, UNIT);
            }

            g.setColor(Color.WHITE);
            g.drawString("Score: " + applesEaten, 10, 20);
        } else {
            gameOver(g);
        }
    }

    public void move() {
        Point head = snake.getHead();
        Point newHead = switch (snake.direction) {
            case 'U' -> new Point(head.x, head.y - UNIT);
            case 'D' -> new Point(head.x, head.y + UNIT);
            case 'L' -> new Point(head.x - UNIT, head.y);
            case 'R' -> new Point(head.x + UNIT, head.y);
            default -> head;
        };

        boolean eat = newHead.equals(apple.position);
        if (snake.checkCollision(newHead, WIDTH, HEIGHT)) {
            running = false;
            return;
        }

        snake.move(newHead, eat);
        if (eat) {
            applesEaten++;
            apple.newApple(WIDTH, HEIGHT, UNIT, snake);
        }
    }

    public void gameOver(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 60));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over",
                (WIDTH - metrics.stringWidth("Game Over")) / 2,
                HEIGHT / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) move();
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT -> { if (snake.direction != 'R') snake.direction = 'L'; }
                case KeyEvent.VK_RIGHT -> { if (snake.direction != 'L') snake.direction = 'R'; }
                case KeyEvent.VK_UP -> { if (snake.direction != 'D') snake.direction = 'U'; }
                case KeyEvent.VK_DOWN -> { if (snake.direction != 'U') snake.direction = 'D'; }
            }
        }
    }
}
