package com.techzealot.designpatterns.behavioral.chainofresponsibility.servlet;

public interface FilterChain {
    void doFilter(Request request, Response response) throws Exception;
}
