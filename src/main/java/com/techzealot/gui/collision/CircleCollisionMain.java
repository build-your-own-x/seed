package com.techzealot.gui.collision;

/**
 * TODO 小球之间的碰撞检测
 * 已实现小球与边缘的碰撞检测
 */
public class CircleCollisionMain {
    public static void main(String[] args) {
        int sceneWidth = 800;
        int sceneHeight = 800;
        int N = 20;
        int R = 30;
        AlgoVisualizer av=new AlgoVisualizer(sceneWidth,sceneHeight,N,R);
    }
}
