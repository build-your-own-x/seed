package com.techzealot.designpatterns.behavioral.chainofresponsibility.netty.test;

import com.techzealot.designpatterns.behavioral.chainofresponsibility.netty.AbstractChannelHandlerContext;
import com.techzealot.designpatterns.behavioral.chainofresponsibility.netty.ChannelInboundHandlerAdapter;

public class ChannelInboundHandlerB extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(AbstractChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead B");
        ctx.close();
        ctx.read();
        ctx.write(msg);
    }
}
