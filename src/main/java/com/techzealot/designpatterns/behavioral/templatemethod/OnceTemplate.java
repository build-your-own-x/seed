package com.techzealot.designpatterns.behavioral.templatemethod;


public class OnceTemplate extends AbstractTemplate {
    @Override
    protected void doA() {
        System.out.println("A".repeat(1));
    }

    @Override
    protected void doB() {
        System.out.println("B".repeat(1));
    }

    @Override
    protected void doC() {
        System.out.println("C".repeat(1));
    }

    @Override
    protected void doD() {
        System.out.println("D".repeat(1));
    }
}
