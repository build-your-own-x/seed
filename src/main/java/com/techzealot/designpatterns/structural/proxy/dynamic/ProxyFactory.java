package com.techzealot.designpatterns.structural.proxy.dynamic;

import java.lang.reflect.Proxy;

public class ProxyFactory {

    /**
     * @param target
     * @param interfaces 此参数指定需要代理的接口，避免代理到不需要的接口
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getProxy(T target, Class<?>[] interfaces) {
        return (T) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                interfaces,
                (proxy, method, args) -> {
                    System.out.println(method.getDeclaringClass());
                    String methodName = method.getName();
                    System.out.println("before " + methodName);
                    Object result = method.invoke(target, args);
                    System.out.println("after " + methodName);
                    return result;
                });
    }
}
