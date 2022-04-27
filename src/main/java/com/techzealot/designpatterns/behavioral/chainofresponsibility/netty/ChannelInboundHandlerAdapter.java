package com.techzealot.designpatterns.behavioral.chainofresponsibility.netty;

public class ChannelInboundHandlerAdapter implements ChannelInboundHandler {
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.fireExceptionCaught(cause);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ctx.fireChannelRead(msg);
    }
}
