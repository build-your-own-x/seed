package com.techzealot.gui.collision;

import java.awt.*;

/**
 * controller
 */
public class AlgoVisualizer {

    //model
    private Circle[] circles;

    //view
    private AlgoFrame frame;

    public AlgoVisualizer(int sceneWidth, int sceneHeight, int N, int R) {
        circles = new Circle[N];
        //随机生成不超出场景的圆
        for (int i = 0; i < N; i++) {
            //约束小球不跳出场景
            int x = (int) (Math.random() * (sceneWidth - 2 * R) + R);
            int y = (int) (Math.random() * (sceneHeight - 2 * R) + R);
            int vx = (int) (Math.random() * 11 - 5);
            int vy = (int) (Math.random() * 11 - 5);
            circles[i] = new Circle(x, y, R, vx, vy);
        }
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("AlgoVis", sceneWidth, sceneHeight);
            new Thread(() -> {
                while (true) {
                    run();
                }
            }).start();
        });
    }

    private void run() {
        frame.renders(circles);
        AlgoVisHelper.pauseMillis(20);
        for (Circle circle : circles) {
            circle.move(0, 0, frame.getCanvasWidth(), frame.getCanvasHeight());
        }
    }
}
