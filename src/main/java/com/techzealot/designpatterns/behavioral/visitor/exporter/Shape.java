package com.techzealot.designpatterns.behavioral.visitor.exporter;

public interface Shape {
    void move(int x, int y);

    void draw();

    String accept(Visitor visitor);
}