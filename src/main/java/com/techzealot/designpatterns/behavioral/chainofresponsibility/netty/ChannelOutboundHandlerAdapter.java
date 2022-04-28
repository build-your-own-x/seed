package com.techzealot.designpatterns.behavioral.chainofresponsibility.netty;

public class ChannelOutboundHandlerAdapter implements ChannelOutboundHandler {
    @Override
    @ChannelHandlerMask.Skip
    public void close(AbstractChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.handler().getClass().getSimpleName());
        ctx.close();
    }

    @Override
    @ChannelHandlerMask.Skip
    public void read(AbstractChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.handler().getClass().getSimpleName());
        ctx.read();
    }

    @Override
    @ChannelHandlerMask.Skip
    public void write(AbstractChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(ctx.handler().getClass().getSimpleName());
        ctx.write(msg);
    }
}
