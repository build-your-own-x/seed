package com.techzealot.designpatterns.creational.abstractfactory.computer;

public class ASerialComputerAbstractFactoryImpl implements ComputerAbstractFactory {
    @Override
    public CpuApi createCpu() {
        return new ACpuApiImpl();
    }

    @Override
    public MainboardApi createMainboard() {
        return new AMainboardApiImpl();
    }
}
