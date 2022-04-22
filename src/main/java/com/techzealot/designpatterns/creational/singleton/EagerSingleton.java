package com.techzealot.designpatterns.creational.singleton;

/**
 * 更常用
 */
public class EagerSingleton {

    private static final EagerSingleton instance = new EagerSingleton();

    private EagerSingleton() {
        //防止反射调用
        if(instance != null){
            throw new IllegalStateException("单例不允许重复创建");
        }
    }

    public static EagerSingleton getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        EagerSingleton instance = EagerSingleton.getInstance();
    }
}
