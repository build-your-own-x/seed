package com.techzealot.designpatterns.creational.abstractfactory;

public class Client {

    public static void main(String[] args) {
        ComputerAbstractFactory caf = new ASerialComputerAbstractFactoryImpl();
        caf.createCpu();
        caf.createMainboard();
    }
}
