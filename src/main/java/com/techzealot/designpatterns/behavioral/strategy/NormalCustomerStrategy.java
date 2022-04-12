package com.techzealot.designpatterns.behavioral.strategy;

public class NormalCustomerStrategy implements Strategy {
    @Override
    public double calcPrice(double goodsPrice) {
        return goodsPrice*1.0;
    }
}
