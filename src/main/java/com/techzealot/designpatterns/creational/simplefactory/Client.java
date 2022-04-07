package com.techzealot.designpatterns.creational.simplefactory;

public class Client {
    private Client() {
    }

    public static void main(String[] args) {
        //最好通过spring等ioc容器管理实现类，通过beanName进行选择
        Api api=ApiFactory.createApi("A");
        api.doSomething("test");
    }
}
