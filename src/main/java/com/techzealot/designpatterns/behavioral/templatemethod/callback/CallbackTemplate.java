package com.techzealot.designpatterns.behavioral.templatemethod.callback;

public class CallbackTemplate {
    public void templateMethod(Callback callback) {
        callback.doA();
        callback.doB();
        callback.doC();
        callback.doD();
    }
}
