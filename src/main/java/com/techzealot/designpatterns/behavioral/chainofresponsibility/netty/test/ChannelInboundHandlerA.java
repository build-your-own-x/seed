package com.techzealot.designpatterns.behavioral.chainofresponsibility.netty.test;

import com.techzealot.designpatterns.behavioral.chainofresponsibility.netty.AbstractChannelHandlerContext;
import com.techzealot.designpatterns.behavioral.chainofresponsibility.netty.ChannelInboundHandlerAdapter;

public class ChannelInboundHandlerA extends ChannelInboundHandlerAdapter {
    @Override
    public void exceptionCaught(AbstractChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exceptionCaught A");
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelRead(AbstractChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead A");
        super.channelRead(ctx, msg);
    }
}
