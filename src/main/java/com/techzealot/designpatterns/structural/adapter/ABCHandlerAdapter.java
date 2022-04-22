package com.techzealot.designpatterns.structural.adapter;

public class ABCHandlerAdapter extends HandlerAdapter {
    @Override
    public void doA() {
        System.out.println("override A action");
    }

    @Override
    public void doB() {
        System.out.println("override A action");
    }

    @Override
    public void doC() {
        System.out.println("override A action");
    }
}
