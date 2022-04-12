package com.techzealot.designpatterns.behavioral.strategy.variant;

public class DefaultStrategy implements IStrategy {
    @Override
    public void doSomething(Context ctx, Params params) {
        System.out.println("do default strategy:" + params);
        ctx.commonCallbackMethods(params);
    }
}
