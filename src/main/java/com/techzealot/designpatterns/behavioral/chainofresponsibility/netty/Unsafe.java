package com.techzealot.designpatterns.behavioral.chainofresponsibility.netty;

public class Unsafe {
    public void beginRead() {
        System.out.println("start read buffer");
    }

    public void close() {
        System.out.println("start close channel");
    }

    public void write(Object msg) {
        System.out.println("start write msg");
    }

    public void read() {
        System.out.println("start read msg");
    }
}
