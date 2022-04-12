package com.techzealot.designpatterns.behavioral.templatemethod.callback;

public class Client {
    public static void main(String[] args) {
        CallbackTemplate callbackTemplate=new CallbackTemplate();
        callbackTemplate.templateMethod(new Callback() {
            @Override
            public void doA() {
                System.out.println("A".repeat(1));
            }

            @Override
            public void doB() {
                System.out.println("B".repeat(1));
            }

            @Override
            public void doC() {
                System.out.println("C".repeat(1));
            }

            @Override
            public void doD() {
                System.out.println("D".repeat(1));
            }
        });
        callbackTemplate.templateMethod(new Callback() {
            @Override
            public void doA() {
                System.out.println("A".repeat(2));
            }

            @Override
            public void doB() {
                System.out.println("B".repeat(2));
            }

            @Override
            public void doC() {
                System.out.println("C".repeat(2));
            }

            @Override
            public void doD() {
                System.out.println("D".repeat(2));
            }
        });
    }
}
