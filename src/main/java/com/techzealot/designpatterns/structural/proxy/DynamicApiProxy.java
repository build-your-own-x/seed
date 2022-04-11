package com.techzealot.designpatterns.structural.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicApiProxy implements InvocationHandler {

    private Api api;

    public DynamicApiProxy(Api api) {
        this.api = api;
    }

    public Api getProxy() {
        return (Api) Proxy.newProxyInstance(
                api.getClass().getClassLoader(),
                api.getClass().getInterfaces(),
                this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        if (methodName.startsWith("do")) {
            System.out.println("before " + methodName.replace("do", ""));
        }
        System.out.println(proxy.getClass().getName());
        System.out.println(proxy.getClass().getClassLoader());
        System.out.println(Api.class.getClassLoader());
        method.invoke(api, args);
        if (methodName.startsWith("do")) {
            System.out.println("after " + methodName.replace("do", ""));
        }
        return null;
    }
}
