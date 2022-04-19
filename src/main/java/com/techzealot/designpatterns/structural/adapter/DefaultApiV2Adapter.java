package com.techzealot.designpatterns.structural.adapter;

/**
 * 假如新接口有多个抽象方法，可以通过默认适配器让用户选择实现需要的接口，其他接口给出默认实现，参考netty ChannelHandlerAdapter
 */
public class DefaultApiV2Adapter implements ApiV2 {
    @Override
    public String doV2(String args) {
        return "";
    }
}
