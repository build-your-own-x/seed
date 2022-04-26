package com.techzealot.designpatterns.behavioral.chainofresponsibility.spring.test;

import com.techzealot.designpatterns.behavioral.chainofresponsibility.spring.HandlerInterceptor;
import com.techzealot.designpatterns.behavioral.chainofresponsibility.spring.Request;
import com.techzealot.designpatterns.behavioral.chainofresponsibility.spring.Response;

public class TestBHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(Request request, Response response, Object handler) throws Exception {
        System.out.println("pre B");
        return false;
    }

    @Override
    public void postHandle(Request request, Response response, Object handler) throws Exception {
        System.out.println("post B");
    }

    @Override
    public void afterCompletion(Request request, Response response, Object handler, Exception ex) throws Exception {
        System.out.println("after B");
    }
}
