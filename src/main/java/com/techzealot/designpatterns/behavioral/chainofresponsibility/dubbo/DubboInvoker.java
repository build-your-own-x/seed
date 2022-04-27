package com.techzealot.designpatterns.behavioral.chainofresponsibility.dubbo;

public class DubboInvoker implements Invoker {
    @Override
    public Result invoke(Invocation invocation) throws RpcException {
        System.out.println("do dubbo invoke");
        return new Result();
    }
}
