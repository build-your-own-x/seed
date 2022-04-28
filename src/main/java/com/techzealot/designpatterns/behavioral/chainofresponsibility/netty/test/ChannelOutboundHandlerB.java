package com.techzealot.designpatterns.behavioral.chainofresponsibility.netty.test;

import com.techzealot.designpatterns.behavioral.chainofresponsibility.netty.AbstractChannelHandlerContext;
import com.techzealot.designpatterns.behavioral.chainofresponsibility.netty.ChannelOutboundHandlerAdapter;

public class ChannelOutboundHandlerB extends ChannelOutboundHandlerAdapter {

    @Override
    public void close(AbstractChannelHandlerContext ctx) throws Exception {
        System.out.println("close B");
        super.close(ctx);
    }

    @Override
    public void write(AbstractChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("write B");
        super.write(ctx, msg);
    }
}
