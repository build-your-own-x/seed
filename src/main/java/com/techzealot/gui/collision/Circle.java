package com.techzealot.gui.collision;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Circle {
    public int x, y;
    @Getter
    private int r;
    public int vx, vy;

    public void move(int minX, int minY, int maxX, int maxY) {
        x += vx;
        y += vy;
        checkCollision(minX, minY, maxX, maxY);
    }

    public void checkCollision(int minX, int minY, int maxX, int maxY) {
        if (x - r < minX) {
            x = r;
            vx = -vx;
        }
        if (x + r >= maxX) {
            x = maxX - r;
            vx = -vx;
        }
        if (y - r < minY) {
            y = r;
            vy = -vy;
        }
        if (y + r >= maxY) {
            y = maxY - r;
            vy = -vy;
        }
    }

}
