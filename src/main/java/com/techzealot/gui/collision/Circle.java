package com.techzealot.gui.collision;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.awt.*;

@AllArgsConstructor
@Data()
public class Circle {
    private final int r;
    public int x, y;
    public int vx, vy;

    private boolean filled = false;

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

    /**
     * (x-x0)^2 +(y-y0)^2 &lt; r^2 说明点在圆内
     *
     * @param point
     * @return
     */
    public boolean contains(Point point) {
        return (point.x - x) * (point.x - x) + (point.y - y) * (point.y - y) < r * r;
    }
}
