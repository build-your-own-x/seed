package com.techzealot.designpatterns.behavioral.chainofresponsibility.spring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HandlerExecutionChain {
    private final Object handler;
    private List<HandlerInterceptor> interceptors = new ArrayList<>();
    private int handlerIndex = -1;

    public HandlerExecutionChain(Object handler) {
        this.handler = handler;
    }

    public void addInterceptors(HandlerInterceptor... interceptors) {
        this.interceptors.addAll(Arrays.asList(interceptors));
    }

    public boolean applyPreHandle(Request request, Response response) throws Exception {
        for (int i = 0; i < interceptors.size(); i++) {
            if (!interceptors.get(i).preHandle(request, response, handler)) {
                triggerAfterCompletion(request, response, null);
                return false;
            }
            this.handlerIndex = i;
        }
        return true;
    }

    public void applyPostHandle(Request request, Response response) throws Exception {
        for (int i = interceptors.size() - 1; i >= 0; i--) {
            interceptors.get(i).postHandle(request, response, handler);
        }
    }

    public void triggerAfterCompletion(Request request, Response response, Exception ex) throws Exception {
        for (int i = handlerIndex; i >= 0; i--) {
            interceptors.get(i).afterCompletion(request, response, handler, ex);
        }
    }

}
