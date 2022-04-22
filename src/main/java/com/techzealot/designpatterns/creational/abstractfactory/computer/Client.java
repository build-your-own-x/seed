package com.techzealot.designpatterns.creational.abstractfactory.computer;

public class Client {

    public static void main(String[] args) {
        ComputerAbstractFactory caf = new ASerialComputerAbstractFactoryImpl();
        CpuApi cpu = caf.createCpu();
        caf.createMainboard().installCpu();
        cpu.calculate();
    }
}
