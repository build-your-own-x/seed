package com.techzealot.gui.collision;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * controller
 */
public class AlgoVisualizer {

    //model
    private Circle[] circles;

    //view
    private AlgoFrame frame;

    private volatile boolean isAnimated = true;

    public AlgoVisualizer(int sceneWidth, int sceneHeight, int N, int R) {
        circles = new Circle[N];
        //随机生成不超出场景的圆
        for (int i = 0; i < N; i++) {
            //约束小球不跳出场景
            int x = (int) (Math.random() * (sceneWidth - 2 * R) + R);
            int y = (int) (Math.random() * (sceneHeight - 2 * R) + R);
            int vx = (int) (Math.random() * 11 - 5);
            int vy = (int) (Math.random() * 11 - 5);
            circles[i] = new Circle(x, y, R, vx, vy, false);
        }
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("AlgoVisualizer", sceneWidth, sceneHeight);
            frame.addKeyListener(new AlgoKeyListener());
            frame.addMouseListener(new AlgoMouseListener());
            new Thread(() -> {
                while (true) {
                    run();
                }
            }).start();
        });
    }

    private void run() {
        frame.renders(circles);
        AlgoVisHelper.pauseMillis(50);
        for (Circle circle : circles) {
            System.out.println("get:" + Thread.currentThread().getName());
            if (isAnimated) {
                circle.move(0, 0, frame.getCanvasWidth(), frame.getCanvasHeight());
            }
        }
    }

    private class AlgoKeyListener extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent event) {
            if (event.getKeyChar() == ' ') {
                System.out.println("set" + Thread.currentThread().getName());
                isAnimated = !isAnimated;
            }
        }
    }

    private class AlgoMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent event) {
            //去除frame的menubar高度得到相对于画布的坐标
            event.translatePoint(0, -(frame.getBounds().height - frame.getCanvasHeight()));
            for (Circle circle : circles) {
                if (circle.contains(event.getPoint())) {
                    circle.setFilled(!circle.isFilled());
                }
            }
        }
    }

    /**
     * TODO 小球之间的碰撞检测
     *  已实现小球与边缘的碰撞检测
     *
     * @param args
     */
    public static void main(String[] args) {
        int sceneWidth = 800;
        int sceneHeight = 800;
        int N = 20;
        int R = 30;
        AlgoVisualizer av = new AlgoVisualizer(sceneWidth, sceneHeight, N, R);
    }
}
