package com.techzealot.designpatterns.behavioral.chainofresponsibility.servlet.test;

import com.techzealot.designpatterns.behavioral.chainofresponsibility.servlet.Filter;
import com.techzealot.designpatterns.behavioral.chainofresponsibility.servlet.FilterChain;
import com.techzealot.designpatterns.behavioral.chainofresponsibility.servlet.Request;
import com.techzealot.designpatterns.behavioral.chainofresponsibility.servlet.Response;

public class FilterB implements Filter {
    @Override
    public void doFilter(Request request, Response response, FilterChain chain) throws Exception {
        System.out.println("before filter B");
        chain.doFilter(request, response);
        System.out.println("after filter B");
    }
}
