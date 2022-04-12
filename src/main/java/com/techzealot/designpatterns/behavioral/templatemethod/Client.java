package com.techzealot.designpatterns.behavioral.templatemethod;

public class Client {
    public static void main(String[] args) {
        System.out.println("once:");
        AbstractTemplate once = new OnceTemplate();
        once.templateMethod();
        System.out.println("twice:");
        AbstractTemplate twice = new TwiceTemplate();
        twice.templateMethod();
    }
}
