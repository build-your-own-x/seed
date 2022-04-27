package com.techzealot.designpatterns.behavioral.chainofresponsibility.dubbo.test;

import com.techzealot.designpatterns.behavioral.chainofresponsibility.dubbo.Filter;
import com.techzealot.designpatterns.behavioral.chainofresponsibility.dubbo.Invocation;
import com.techzealot.designpatterns.behavioral.chainofresponsibility.dubbo.Invoker;
import com.techzealot.designpatterns.behavioral.chainofresponsibility.dubbo.Result;
import com.techzealot.designpatterns.behavioral.chainofresponsibility.dubbo.RpcException;

public class FilterC implements Filter {
    @Override
    public Result invoke(Invoker invoker, Invocation invocation) throws RpcException {
        System.out.println("before C");
        Result result = invoker.invoke(invocation);
        System.out.println("after C");
        return result;
    }
}
