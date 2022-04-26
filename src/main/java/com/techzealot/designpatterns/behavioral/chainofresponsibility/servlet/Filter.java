package com.techzealot.designpatterns.behavioral.chainofresponsibility.servlet;

public interface Filter {
    default void init() {
    }

    default void destroy() {
    }

    void doFilter(Request request, Response response, FilterChain chain) throws Exception;
}
