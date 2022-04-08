package com.techzealot.designpatterns.creational.factorymethod;

public abstract class FactoryMethodObject {


    public void doSomething() {
        getA().doA();
    }

    protected abstract A getA();
}
