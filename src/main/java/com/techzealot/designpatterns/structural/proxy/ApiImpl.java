package com.techzealot.designpatterns.structural.proxy;

public class ApiImpl implements Api, Cloneable {
    @Override
    public void doA() {
        System.out.println("A");
    }

    @Override
    public void doB() {
        System.out.println("B");
    }

    @Override
    public void doC() {
        System.out.println("C");
    }

    public boolean equals(Api obj) {
        return true;
    }
}
