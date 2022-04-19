package com.techzealot.designpatterns.creational.factorymethod;

public class Ship implements Transport {
    @Override
    public void deliver() {
        System.out.println("ship");
    }
}
