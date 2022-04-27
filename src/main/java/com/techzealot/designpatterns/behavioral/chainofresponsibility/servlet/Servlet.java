package com.techzealot.designpatterns.behavioral.chainofresponsibility.servlet;

public class Servlet {
    void service(Request request, Response response) throws Exception {
        System.out.println("here we handle get/post request and return response");
    }
}
