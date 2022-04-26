package com.techzealot.designpatterns.behavioral.chainofresponsibility.spring;

public interface HandlerInterceptor {
    default boolean preHandle(Request request, Response response, Object handler) throws Exception {
        return true;
    }

    default void postHandle(Request request, Response response, Object handler) throws Exception {
    }

    default void afterCompletion(Request request, Response response, Object handler, Exception ex) throws Exception {
    }
}
