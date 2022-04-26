package com.techzealot.designpatterns.behavioral.chainofresponsibility.spring;

import com.techzealot.designpatterns.behavioral.chainofresponsibility.spring.test.TestAHandlerInterceptor;
import com.techzealot.designpatterns.behavioral.chainofresponsibility.spring.test.TestBHandlerInterceptor;
import com.techzealot.designpatterns.behavioral.chainofresponsibility.spring.test.TestCHandlerInterceptor;

public class TestDispatcherServlet extends DispatcherServlet {
    @Override
    protected HandlerInterceptor[] getHandlerInterceptors() {
        return new HandlerInterceptor[]{
                new TestAHandlerInterceptor(),
                new TestBHandlerInterceptor(),
                new TestCHandlerInterceptor(),
        };
    }
}
