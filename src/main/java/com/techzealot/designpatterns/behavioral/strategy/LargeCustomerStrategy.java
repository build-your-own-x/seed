package com.techzealot.designpatterns.behavioral.strategy;

public class LargeCustomerStrategy implements Strategy {
    @Override
    public double calcPrice(double goodsPrice) {
        //大客户九折
        return goodsPrice * 0.9;
    }
}
