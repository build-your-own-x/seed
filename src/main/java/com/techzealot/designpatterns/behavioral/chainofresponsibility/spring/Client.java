package com.techzealot.designpatterns.behavioral.chainofresponsibility.spring;

public class Client {
    public static void main(String[] args) throws Exception {
        DispatcherServlet dispatcherServlet = new TestDispatcherServlet();
        dispatcherServlet.doDispatch(new Request(), new Response());
    }
}
