package com.techzealot.designpatterns.behavioral.observer.guava;

public record UserEvent(User content) implements Event<User> {

    @Override
    public int getType() {
        return 0;
    }
}
