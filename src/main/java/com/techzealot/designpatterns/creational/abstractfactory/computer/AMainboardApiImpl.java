package com.techzealot.designpatterns.creational.abstractfactory.computer;

public class AMainboardApiImpl implements MainboardApi {
    @Override
    public void installCpu() {
        System.out.println("A cpu is installed");
    }
}
