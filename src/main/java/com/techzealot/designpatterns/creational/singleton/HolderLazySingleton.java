package com.techzealot.designpatterns.creational.singleton;

public class HolderLazySingleton {

    private static HolderLazySingleton instance;

    private HolderLazySingleton() {

    }

    public static HolderLazySingleton getInstance() {
        return Holder.instance;
    }

    private static class Holder {
        //虚拟机保障线程安全
        private static final HolderLazySingleton instance = new HolderLazySingleton();
    }
}
