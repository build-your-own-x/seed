package com.techzealot.designpatterns.creational.abstractfactory.computer;

/**
 * 所有实现必须自行保证产品簇内部相互兼容
 */
public interface ComputerAbstractFactory {

    CpuApi createCpu();

    MainboardApi createMainboard();
}
