package com.techzealot.designpatterns.creational.abstractfactory.computer;

public class ACpuApiImpl implements CpuApi {
    @Override
    public void calculate() {
        System.out.println("use A cpu to calculate");
    }
}
