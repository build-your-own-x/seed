package com.techzealot.designpatterns.behavioral.chainofresponsibility.netty.test;

import com.techzealot.designpatterns.behavioral.chainofresponsibility.netty.ChannelHandlerContext;
import com.techzealot.designpatterns.behavioral.chainofresponsibility.netty.ChannelOutboundHandlerAdapter;

public class ChannelOutboundHandlerA extends ChannelOutboundHandlerAdapter {

    @Override
    public void close(ChannelHandlerContext ctx) throws Exception {
        System.out.println("close A");
        super.close(ctx);
    }

    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        System.out.println("read A");
        super.read(ctx);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("write A");
        super.write(ctx, msg);
    }
}
