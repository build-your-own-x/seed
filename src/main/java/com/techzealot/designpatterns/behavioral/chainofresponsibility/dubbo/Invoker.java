package com.techzealot.designpatterns.behavioral.chainofresponsibility.dubbo;

public interface Invoker {

    Result invoke(Invocation invocation) throws RpcException;

}
