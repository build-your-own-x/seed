package com.techzealot.designpatterns.behavioral.command.computer;

public class ASerialMainBoard implements MainBoard {
    @Override
    public void open() {
        System.out.println("A open");
    }

    @Override
    public void reset() {
        System.out.println("A reset");
    }
}
