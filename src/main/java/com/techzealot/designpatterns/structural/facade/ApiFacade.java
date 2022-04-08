package com.techzealot.designpatterns.structural.facade;

public class ApiFacade {

    private ComponentA a;
    private ComponentB b;
    private ComponentC c;

    public ApiFacade(ComponentA a, ComponentB b, ComponentC c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public void doSomething() {
        //封装多个组件的操作，简化用户使用
        a.doA();
        b.doB();
        c.doC();
    }
}
