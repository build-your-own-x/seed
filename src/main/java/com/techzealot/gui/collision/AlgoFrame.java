package com.techzealot.gui.collision;

import javax.swing.*;
import java.awt.*;

public class AlgoFrame extends JFrame {

    private int canvasWidth;
    private int canvasHeight;

    public AlgoFrame(String title, int canvasWidth, int canvasHeight) throws HeadlessException {
        super(title);
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        //创建画布
        AlgoCanvas canvas = new AlgoCanvas();
        this.setContentPane(canvas);
        //自动根据内容调整窗口大小
        this.pack();
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public AlgoFrame(String title) {
        this(title, 1024, 768);
    }

    public int getCanvasWidth() {
        return canvasWidth;
    }

    public int getCanvasHeight() {
        return canvasHeight;
    }

    private Circle[] circles;

    public void renders(Circle[] circles) {
        this.circles = circles;
        this.repaint();
    }

    private class AlgoCanvas extends JPanel {

        public AlgoCanvas() {
            //开启双缓存，避免动画闪烁
            super(true);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            //抗锯齿
            RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.addRenderingHints(hints);
            AlgoVisHelper.setColor(g2d, Color.RED);
            AlgoVisHelper.setStrokeWith(g2d, 1);
            for (Circle circle : circles) {
                if(circle.isFilled()){
                    AlgoVisHelper.fillCircle(g2d,circle.x,circle.y,circle.getR());
                }else{
                    AlgoVisHelper.strokeCircle(g2d,circle.x,circle.y,circle.getR());
                }
            }
        }

        /**
         * 控制画布大小
         */
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(canvasWidth, canvasHeight);
        }
    }
}
