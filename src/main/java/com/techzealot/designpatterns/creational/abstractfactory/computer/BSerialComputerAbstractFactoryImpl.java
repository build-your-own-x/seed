package com.techzealot.designpatterns.creational.abstractfactory.computer;

public class BSerialComputerAbstractFactoryImpl implements ComputerAbstractFactory {
    @Override
    public CpuApi createCpu() {
        return new BCpuApiImpl();
    }

    @Override
    public MainboardApi createMainboard() {
        return new BMainboardApiImpl();
    }
}
