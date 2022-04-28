package com.techzealot.designpatterns.behavioral.chainofresponsibility.netty;


public class ChannelInboundHandlerAdapter implements ChannelInboundHandler {
    @Override
    @ChannelHandlerMask.Skip
    public void exceptionCaught(AbstractChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(ctx.handler().getClass().getSimpleName());
        ctx.fireExceptionCaught(cause);
    }

    @Override
    @ChannelHandlerMask.Skip
    public void channelRead(AbstractChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(ctx.handler().getClass().getSimpleName());
        ctx.fireChannelRead(msg);
    }
}
