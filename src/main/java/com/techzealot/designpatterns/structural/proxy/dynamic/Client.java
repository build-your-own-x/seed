package com.techzealot.designpatterns.structural.proxy.dynamic;

import com.techzealot.designpatterns.structural.proxy.Api;
import com.techzealot.designpatterns.structural.proxy.ApiImpl;

import java.util.Arrays;

public class Client {
    public static void main(String[] args) {
        //show proxy classes
        System.setProperty("jdk.proxy.ProxyGenerator.saveGeneratedFiles", "false");
        Api api = ProxyFactory.getProxy(new ApiImpl(), new Class[]{Api.class});
        System.out.println(Arrays.toString(api.getClass().getInterfaces()));
        System.out.println(Arrays.toString(ApiImpl.class.getInterfaces()));
        System.out.println(Arrays.toString(Api.class.getInterfaces()));
        api.doA();
        api.doB();
        api.doC();
        api.toString();
        api.hashCode();
        api.equals(new ApiImpl());
    }
}
