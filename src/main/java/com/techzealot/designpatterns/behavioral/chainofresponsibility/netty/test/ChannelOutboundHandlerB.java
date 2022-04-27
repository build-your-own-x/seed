package com.techzealot.designpatterns.behavioral.chainofresponsibility.netty.test;

import com.techzealot.designpatterns.behavioral.chainofresponsibility.netty.ChannelHandlerContext;
import com.techzealot.designpatterns.behavioral.chainofresponsibility.netty.ChannelOutboundHandlerAdapter;

public class ChannelOutboundHandlerB extends ChannelOutboundHandlerAdapter {

    @Override
    public void close(ChannelHandlerContext ctx) throws Exception {
        System.out.println("close B");
        super.close(ctx);
    }

    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        System.out.println("read B");
        super.read(ctx);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("write B");
        super.write(ctx, msg);
    }
}
