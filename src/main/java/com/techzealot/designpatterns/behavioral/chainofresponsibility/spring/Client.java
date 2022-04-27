package com.techzealot.designpatterns.behavioral.chainofresponsibility.spring;

/**
 * spring的实现方式更灵活,使用起来更简单
 */
public class Client {
    public static void main(String[] args) throws Exception {
        DispatcherServlet dispatcherServlet = new TestDispatcherServlet();
        dispatcherServlet.doDispatch(new Request(), new Response());
    }
}
