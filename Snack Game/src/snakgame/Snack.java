package snakegame;

import java.awt.*;
import java.util.*;

public class Snake {
    LinkedList<Point> body = new LinkedList<>();
    HashSet<Point> occupied = new HashSet<>();
    char direction = 'R';

    public Snake(int startX, int startY) {
        body.add(new Point(startX, startY));
        occupied.add(new Point(startX, startY));
    }

    public Point getHead() {
        return body.getFirst();
    }

    public void move(Point newHead, boolean grow) {
        body.addFirst(newHead);
        occupied.add(newHead);

        if (!grow) {
            Point tail = body.removeLast();
            occupied.remove(tail);
        }
    }

    public boolean checkCollision(Point p, int width, int height) {
        if (p.x < 0 || p.y < 0 || p.x >= width || p.y >= height) return true;
        return occupied.contains(p);
    }
}
