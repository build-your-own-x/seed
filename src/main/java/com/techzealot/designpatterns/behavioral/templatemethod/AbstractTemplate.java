package com.techzealot.designpatterns.behavioral.templatemethod;

public abstract class AbstractTemplate {
    protected abstract void doA();

    protected abstract void doB();

    protected abstract void doC();

    protected abstract void doD();

    protected void common(){

    }

    public void templateMethod() {
        doA();
        doB();
        doC();
        doD();
    }
}
