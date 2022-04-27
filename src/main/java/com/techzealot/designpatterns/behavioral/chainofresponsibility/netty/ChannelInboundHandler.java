package com.techzealot.designpatterns.behavioral.chainofresponsibility.netty;

public interface ChannelInboundHandler extends ChannelHandler {
    void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception;

    void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception;
}
