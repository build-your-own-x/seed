package com.techzealot.designpatterns.creational.abstractfactory.computer;

public class BMainboardApiImpl implements MainboardApi {
    @Override
    public void installCpu() {
        System.out.println("B cpu is installed");
    }
}
