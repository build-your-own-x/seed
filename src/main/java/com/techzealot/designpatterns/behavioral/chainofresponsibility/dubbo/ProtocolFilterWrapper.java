package com.techzealot.designpatterns.behavioral.chainofresponsibility.dubbo;

import java.util.List;

public abstract class ProtocolFilterWrapper {
    protected abstract List<Filter> getFilters();

    public Invoker buildInvokerChain(Invoker invoker) {
        List<Filter> filters = getFilters();
        Invoker last = invoker;
        for (int i = filters.size() - 1; i >= 0; i--) {
            Filter filter = filters.get(i);
            Invoker next = last;
            last = new Invoker() {
                @Override
                public Result invoke(Invocation invocation) throws RpcException {
                    return filter.invoke(next, invocation);
                }
            };
        }
        return last;
    }
}
