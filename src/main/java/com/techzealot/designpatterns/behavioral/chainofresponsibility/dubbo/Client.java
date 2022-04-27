package com.techzealot.designpatterns.behavioral.chainofresponsibility.dubbo;

import com.techzealot.designpatterns.behavioral.chainofresponsibility.dubbo.test.TestProtocolFilterWrapper;

public class Client {
    public static void main(String[] args) throws RpcException {
        ProtocolFilterWrapper protocolFilterWrapper = new TestProtocolFilterWrapper();
        Invoker invoker = new DubboInvoker();
        Invoker wrappedInvoker = protocolFilterWrapper.buildInvokerChain(invoker);
        wrappedInvoker.invoke(new Invocation());
    }
}
