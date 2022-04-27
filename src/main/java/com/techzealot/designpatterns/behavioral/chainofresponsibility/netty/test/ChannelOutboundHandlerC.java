package com.techzealot.designpatterns.behavioral.chainofresponsibility.netty.test;

import com.techzealot.designpatterns.behavioral.chainofresponsibility.netty.ChannelHandlerContext;
import com.techzealot.designpatterns.behavioral.chainofresponsibility.netty.ChannelOutboundHandlerAdapter;

public class ChannelOutboundHandlerC extends ChannelOutboundHandlerAdapter {

    @Override
    public void close(ChannelHandlerContext ctx) throws Exception {
        System.out.println("close C");
        super.close(ctx);
    }

    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        System.out.println("read C");
        super.read(ctx);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("write C");
        super.write(ctx, msg);
    }
}
