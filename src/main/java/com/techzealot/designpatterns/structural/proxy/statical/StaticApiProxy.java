package com.techzealot.designpatterns.structural.proxy.statical;

import com.techzealot.designpatterns.structural.proxy.Api;

public class StaticApiProxy implements Api {

    private Api api;

    public StaticApiProxy(Api api) {
        this.api = api;
    }

    @Override
    public void doA() {
        System.out.println("before A");
        api.doA();
        System.out.println("after A");
    }

    @Override
    public void doB() {
        System.out.println("before B");
        api.doB();
        System.out.println("after B");
    }

    @Override
    public void doC() {
        System.out.println("before C");
        api.doC();
        System.out.println("after C");
    }
}
