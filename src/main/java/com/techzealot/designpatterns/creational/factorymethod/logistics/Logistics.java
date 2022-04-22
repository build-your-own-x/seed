package com.techzealot.designpatterns.creational.factorymethod.logistics;

public abstract class Logistics {

    public void planDelivery(){
        createTransport().deliver();
    }

    protected abstract Transport createTransport();
}
