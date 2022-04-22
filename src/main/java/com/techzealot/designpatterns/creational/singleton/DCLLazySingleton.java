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

    /**
     * Note the local variable "localRef", which seems unnecessary.
     * The effect of this is that in cases where helper is already initialized (i.e., most of the time),
     * the volatile field is only accessed once (due to "return localRef;" instead of "return helper;"),
     * which can improve the method's overall performance by as much as 40 percent
     * You can read more info DCL issues in Java here:
     * <a href="https://refactoring.guru/java-dcl-issue">https://refactoring.guru/java-dcl-issue</a>
     * 此方法可以使volatile变量在初始化后只需访问一次，通常的方法volatile变量会被访问2次
     * @return
     */
    public static DCLLazySingleton betterGetInstance() {
        var localRef = instance;
        if (localRef == null) {
            synchronized (DCLLazySingleton.class) {
                localRef = instance;
                if (localRef == null) {
                    localRef = instance = new DCLLazySingleton();
                }
            }
        }
        return localRef;
    }
}
