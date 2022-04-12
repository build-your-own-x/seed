package com.techzealot.designpatterns.behavioral.strategy;

public class OldCustomerStrategy implements Strategy {
    @Override
    public double calcPrice(double goodsPrice) {
        return goodsPrice * 0.95;
    }
}
