package com.techzealot.designpatterns.creational.factorymethod;

public abstract class Logistics {

    public void planDelivery(){
        createTransport().deliver();
    }

    public abstract Transport createTransport();
}
