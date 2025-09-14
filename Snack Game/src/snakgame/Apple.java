package snakegame;

import java.awt.*;
import java.util.Random;

public class Apple {
    Point position;
    Random rand = new Random();

    public void newApple(int width, int height, int unit, Snake snake) {
        while (true) {
            int x = rand.nextInt(width / unit) * unit;
            int y = rand.nextInt(height / unit) * unit;
            Point p = new Point(x, y);
            if (!snake.occupied.contains(p)) {
                position = p;
                break;
            }
        }
    }
}
