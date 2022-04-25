package com.techzealot.designpatterns.behavioral.observer.guava;

public record OrderEvent(Order content) implements Event<Order> {

    @Override
    public int getType() {
        return 1;
    }
}
