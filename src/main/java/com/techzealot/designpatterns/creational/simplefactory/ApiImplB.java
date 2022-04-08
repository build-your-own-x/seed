package com.techzealot.designpatterns.creational.simplefactory;

import java.text.MessageFormat;

public class ApiImplB implements Api {
    @Override
    public void doSomething(String arg) {
        System.out.println(MessageFormat.format("{0}:{1}", "B", arg));
    }
}
