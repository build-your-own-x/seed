package com.techzealot.designpatterns.behavioral.chainofresponsibility.dubbo;

public interface Filter {
    Result invoke(Invoker invoker, Invocation invocation) throws RpcException;
}
