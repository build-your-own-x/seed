package com.techzealot.gui;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.concurrent.TimeUnit;

public abstract class AlgoVisHelper {
    public static void setStrokeWith(Graphics2D g2d, int width) {
        g2d.setStroke(new BasicStroke(width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
    }

    public static void setColor(Graphics2D g2d, Color color) {
        g2d.setColor(color);
    }

    /**
     * 在(x,y)[屏幕坐标]处绘制圆形
     * @param g2d
     * @param x
     * @param y
     * @param r
     */
    public static void strokeCircle(Graphics2D g2d,int x,int y,int r){
        g2d.draw(new Ellipse2D.Double(x-r,y-r,2*r,2*r));
    }

    public static void fillCircle(Graphics2D g2d,int x,int y,int r){
        g2d.fill(new Ellipse2D.Double(x-r,y-r,2*r,2*r));
    }

    public static void pauseMillis(long millis){
        try {
            TimeUnit.MILLISECONDS.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
