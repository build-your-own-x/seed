package com.techzealot.designpatterns.creational.factorymethod.logistics;

public class SeaLogistics extends Logistics {
    @Override
    public Transport createTransport() {
        return new Ship();
    }
}
