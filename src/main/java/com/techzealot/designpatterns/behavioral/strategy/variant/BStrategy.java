package com.techzealot.designpatterns.behavioral.strategy.variant;

public class BStrategy implements IStrategy {
    @Override
    public void doSomething(Context ctx,Params params) {
        System.out.println("do B strategy:"+params);
        ctx.commonCallbackMethods(params);
    }
}
