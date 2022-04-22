package com.techzealot.designpatterns.creational.abstractfactory.computer;

public class BCpuApiImpl implements CpuApi {
    @Override
    public void calculate() {
        System.out.println("use B cpu to calculate");
    }
}
