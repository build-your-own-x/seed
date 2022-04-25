package com.techzealot.designpatterns.behavioral.observer.guava;

public interface Event<T> {
    int getType();

    T content();
}
