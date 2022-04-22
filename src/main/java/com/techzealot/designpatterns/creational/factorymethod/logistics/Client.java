package com.techzealot.designpatterns.creational.factorymethod.logistics;

public class Client {
    public static void main(String[] args) {
        Logistics roadLogistics = new RoadLogistics();
        roadLogistics.planDelivery();
        Logistics seaLogistics = new SeaLogistics();
        seaLogistics.planDelivery();
    }
}
