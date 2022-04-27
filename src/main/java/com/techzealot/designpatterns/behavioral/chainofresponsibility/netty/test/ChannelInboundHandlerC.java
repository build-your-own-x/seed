package com.techzealot.designpatterns.behavioral.chainofresponsibility.netty.test;

import com.techzealot.designpatterns.behavioral.chainofresponsibility.netty.ChannelHandlerContext;
import com.techzealot.designpatterns.behavioral.chainofresponsibility.netty.ChannelInboundHandlerAdapter;

public class ChannelInboundHandlerC extends ChannelInboundHandlerAdapter {
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exceptionCaught C");
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead C");
        super.channelRead(ctx, msg);
    }
}
