package com.techzealot.designpatterns.structural.proxy.dynamic;

import com.techzealot.designpatterns.structural.proxy.Api;
import com.techzealot.designpatterns.structural.proxy.ApiImpl;
import com.techzealot.designpatterns.structural.proxy.DynamicApiProxy;

public class Client {
    public static void main(String[] args) {
        Api api = new DynamicApiProxy(new ApiImpl()).getProxy();
        api.doA();
        api.doB();
        api.doC();
    }
}
