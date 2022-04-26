package com.techzealot.designpatterns.behavioral.chainofresponsibility.servlet;

import com.techzealot.designpatterns.behavioral.chainofresponsibility.servlet.test.FilterA;
import com.techzealot.designpatterns.behavioral.chainofresponsibility.servlet.test.FilterB;
import com.techzealot.designpatterns.behavioral.chainofresponsibility.servlet.test.FilterC;

public class Client {
    public static void main(String[] args) throws Exception {
        ApplicationFilterChain chain = new ApplicationFilterChain();
        //当有Filter未在内部调用chain.doFilter触发递归会导致pos<size()不可能成立,从而请求不会向后传递被servlet所处理
        chain.addFilters(new FilterA(), new FilterB(), new FilterC());
        chain.doFilter(new Request(), new Response());
    }
}
