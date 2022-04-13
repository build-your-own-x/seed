package com.techzealot.gui.collision;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import java.awt.*;

@AllArgsConstructor
@Data()
public class Circle {
    public int x, y;
    private final int r;
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
     * (x-x0)^2 +(y-y0)^2< r^2 说明点在圆内
     * @param point
     * @return
     */
    public boolean contains(Point point) {
        return (point.x - x) * (point.x - x) + (point.y - y) * (point.y - y) < r * r;
    }
}
