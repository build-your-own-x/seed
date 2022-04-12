package com.techzealot.designpatterns.behavioral.strategy.variant;

public class AStrategy implements IStrategy {
    @Override
    public void doSomething(Context ctx,Params params) {
        System.out.println("do A strategy:"+params);
        ctx.commonCallbackMethods(params);
    }
}
