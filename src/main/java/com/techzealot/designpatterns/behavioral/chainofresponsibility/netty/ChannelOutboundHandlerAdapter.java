package com.techzealot.designpatterns.behavioral.chainofresponsibility.netty;

public class ChannelOutboundHandlerAdapter implements ChannelOutboundHandler {
    @Override
    public void close(ChannelHandlerContext ctx) throws Exception {
        ctx.close();
    }

    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        ctx.read();
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg) throws Exception {
        ctx.write(msg);
    }
}
