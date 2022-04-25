package com.techzealot.designpatterns.behavioral.strategy.price;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PriceContext {

    private Strategy strategy;

    public double quote(double goodsPrice) {
        return this.strategy.calcPrice(goodsPrice);
    }
}
