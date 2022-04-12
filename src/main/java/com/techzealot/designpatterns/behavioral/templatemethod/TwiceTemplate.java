package com.techzealot.designpatterns.behavioral.templatemethod;


public class TwiceTemplate extends AbstractTemplate {
    @Override
    protected void doA() {
        System.out.println("A".repeat(2));
    }

    @Override
    protected void doB() {
        System.out.println("B".repeat(2));
    }

    @Override
    protected void doC() {
        System.out.println("C".repeat(2));
    }

    @Override
    protected void doD() {
        System.out.println("D".repeat(2));
    }
}
