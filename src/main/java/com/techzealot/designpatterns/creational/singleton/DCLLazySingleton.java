package com.techzealot.designpatterns.creational.singleton;

public class DCLLazySingleton {

    //必须加volatile,否则可能获取未初始化完成对象
    private static volatile DCLLazySingleton instance;

    private DCLLazySingleton() {

    }

    public static DCLLazySingleton getInstance() {
        //若不加volatile可能获取
        if (instance == null) {
            synchronized (DCLLazySingleton.class) {
                if (instance == null) {
                    //半成品对象可能被未加锁代码第一个判空看到
                    instance = new DCLLazySingleton();
                }
            }
        }
        return instance;
    }
}
