package com.techzealot.designpatterns.structural.proxy.statical;

import com.techzealot.designpatterns.structural.proxy.Api;
import com.techzealot.designpatterns.structural.proxy.ApiImpl;

public class Client {
    public static void main(String[] args) {
        Api api = new StaticApiProxy(new ApiImpl());
        api.doA();
        api.doB();
        api.doC();
    }
}
