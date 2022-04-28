package com.techzealot.designpatterns.behavioral.chainofresponsibility.netty;

public interface ChannelInboundHandler extends ChannelHandler {
    void exceptionCaught(AbstractChannelHandlerContext ctx, Throwable cause) throws Exception;

    void channelRead(AbstractChannelHandlerContext ctx, Object msg) throws Exception;
}
