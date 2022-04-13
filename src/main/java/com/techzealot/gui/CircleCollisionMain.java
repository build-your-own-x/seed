package com.techzealot.gui;

import java.awt.*;

/**
 * TODO 小球之间的碰撞检测
 * 已实现小球与边缘的碰撞检测
 */
public class CircleCollisionMain {
    public static void main(String[] args) {
        int sceneWidth = 800;
        int sceneHeight = 800;
        int N = 10;
        int R = 50;
        Circle[] circles = new Circle[10];
        //随机生成不超出场景的圆
        for (int i = 0; i < 10; i++) {
            //约束小球不跳出场景
            int x = (int) (Math.random() * (sceneWidth - 2 * R) + R);
            int y = (int) (Math.random() * (sceneHeight - 2 * R) + R);
            int vx = (int) (Math.random() * 11 - 5);
            int vy = (int) (Math.random() * 11 - 5);
            circles[i] = new Circle(x, y, R, vx, vy);
        }
        EventQueue.invokeLater(() -> {
            AlgoFrame frame = new AlgoFrame("welcome", sceneWidth, sceneHeight);
            new Thread(() -> {
                while (true) {
                    frame.renders(circles);
                    AlgoVisHelper.pauseMillis(20);
                    for (Circle circle : circles) {
                        circle.move(0, 0, sceneWidth, sceneHeight);
                    }
                }
            }).start();
        });
    }
}
