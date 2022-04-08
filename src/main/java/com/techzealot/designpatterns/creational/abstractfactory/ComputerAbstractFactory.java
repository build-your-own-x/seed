package com.techzealot.designpatterns.creational.abstractfactory;

/**
 * 所有实现必须保证产品簇相互兼容
 */
public interface ComputerAbstractFactory {

    CpuApi createCpu();

    MainboardApi createMainboard();
}
