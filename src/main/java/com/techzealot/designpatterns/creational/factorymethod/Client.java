package com.techzealot.designpatterns.creational.factorymethod;

public class Client {
    public static void main(String[] args) {
        FactoryMethodObject fm=new FactoryMethodObjectImpl();
        fm.doSomething();
    }
}
