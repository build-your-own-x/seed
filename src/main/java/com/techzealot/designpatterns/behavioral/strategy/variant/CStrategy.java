package com.techzealot.designpatterns.behavioral.strategy.variant;

public class CStrategy implements IStrategy {
    @Override
    public void doSomething(Context ctx, Params params) {
        System.out.println("do C strategy:" + params);
        ctx.commonCallbackMethods(params);
    }
}
