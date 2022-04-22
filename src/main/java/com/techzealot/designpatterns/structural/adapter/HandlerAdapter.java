package com.techzealot.designpatterns.structural.adapter;

/**
 * 假如新接口有多个抽象方法，可以通过默认适配器让用户选择实现需要的接口，其他接口给出默认实现
 * 参考netty ChannelHandlerAdapter
 */
public class HandlerAdapter implements Handler {

    @Override
    public void doA() {
        System.out.println("default A action");
    }

    @Override
    public void doB() {
        System.out.println("default B action");
    }

    @Override
    public void doC() {
        System.out.println("default C action");
    }

    @Override
    public void doD() {
        System.out.println("default D action");
    }

    @Override
    public void doE() {
        System.out.println("default E action");
    }
}
