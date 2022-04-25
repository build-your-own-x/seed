package com.techzealot.designpatterns.behavioral.strategy.price;

public class OldCustomerStrategy implements Strategy {
    @Override
    public double calcPrice(double goodsPrice) {
        return goodsPrice * 0.95;
    }
}
