package com.techzealot.designpatterns.creational.factorymethod;

public class Truck implements Transport {
    @Override
    public void deliver() {
        System.out.println("truck");
    }
}
