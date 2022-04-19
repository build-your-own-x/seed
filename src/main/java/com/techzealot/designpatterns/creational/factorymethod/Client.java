package com.techzealot.designpatterns.creational.factorymethod;

public class Client {
    public static void main(String[] args) {
        Logistics roadLogistics = new RoadLogistics();
        roadLogistics.planDelivery();
        Logistics seaLogistics = new SeaLogistics();
        seaLogistics.planDelivery();
    }
}
