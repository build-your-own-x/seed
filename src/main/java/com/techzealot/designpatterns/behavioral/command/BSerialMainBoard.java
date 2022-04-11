package com.techzealot.designpatterns.behavioral.command;

public class BSerialMainBoard implements MainBoard {
    @Override
    public void open() {
        System.out.println("B open");
    }

    @Override
    public void reset() {
        System.out.println("B open");
    }
}
