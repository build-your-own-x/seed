package com.techzealot.designpatterns.behavioral.chainofresponsibility.netty;

public interface ChannelOutboundHandler extends ChannelHandler {
    void close(ChannelHandlerContext ctx) throws Exception;

    void read(ChannelHandlerContext ctx) throws Exception;

    void write(ChannelHandlerContext ctx, Object msg) throws Exception;
}
