package com.techzealot.designpatterns.behavioral.strategy.price;

public class Client {
    public static void main(String[] args) {
        PriceContext ctx = new PriceContext(new LargeCustomerStrategy());
        System.out.println(ctx.quote(1200.0));
    }
}
