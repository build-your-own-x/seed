package com.techzealot.designpatterns.creational.factorymethod.logistics;

public class RoadLogistics extends Logistics {
    @Override
    public Transport createTransport() {
        return new Truck();
    }
}
