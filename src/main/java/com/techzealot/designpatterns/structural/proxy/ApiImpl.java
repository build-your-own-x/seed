package com.techzealot.designpatterns.structural.proxy;

import com.techzealot.designpatterns.structural.proxy.Api;

public class ApiImpl implements Api {
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
}
