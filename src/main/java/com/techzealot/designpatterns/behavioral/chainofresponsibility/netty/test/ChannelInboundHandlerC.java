package com.techzealot.designpatterns.behavioral.chainofresponsibility.netty.test;

import com.techzealot.designpatterns.behavioral.chainofresponsibility.netty.AbstractChannelHandlerContext;
import com.techzealot.designpatterns.behavioral.chainofresponsibility.netty.ChannelInboundHandlerAdapter;

public class ChannelInboundHandlerC extends ChannelInboundHandlerAdapter {
    @Override
    public void exceptionCaught(AbstractChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exceptionCaught C");
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelRead(AbstractChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead C");
        super.channelRead(ctx, msg);
    }
}
