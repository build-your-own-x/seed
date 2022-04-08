package com.techzealot.designpatterns.creational.factorymethod;

public class FactoryMethodObjectImpl extends FactoryMethodObject {
    @Override
    protected A getA() {
        return new AImpl();
    }
}
