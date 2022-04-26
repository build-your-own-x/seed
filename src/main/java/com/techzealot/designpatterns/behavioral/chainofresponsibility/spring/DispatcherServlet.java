package com.techzealot.designpatterns.behavioral.chainofresponsibility.spring;

public abstract class DispatcherServlet {
    public void doDispatch(Request request, Response response) throws Exception {
        HandlerMethod handler = getHandler();
        HandlerExecutionChain chain = new HandlerExecutionChain(handler);
        chain.addInterceptors(getHandlerInterceptors());
        Exception dispatchException = null;
        try {
            if (!chain.applyPreHandle(request, response)) {
                //如果有一个preHandle返回false,则后续不再处理
                return;
            }
            handler.invoke();
            chain.applyPostHandle(request, response);

        } catch (Exception e) {
            dispatchException = e;
            chain.triggerAfterCompletion(request, response, e);
        }
        chain.triggerAfterCompletion(request, response, dispatchException);
    }

    protected abstract HandlerInterceptor[] getHandlerInterceptors();

    private HandlerMethod getHandler() {
        return new HandlerMethod();
    }
}
