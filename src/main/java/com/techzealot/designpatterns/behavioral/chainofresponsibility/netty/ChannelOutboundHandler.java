package com.techzealot.designpatterns.behavioral.chainofresponsibility.netty;

public interface ChannelOutboundHandler extends ChannelHandler {
    void close(AbstractChannelHandlerContext ctx) throws Exception;

    void read(AbstractChannelHandlerContext ctx) throws Exception;

    void write(AbstractChannelHandlerContext ctx, Object msg) throws Exception;
}
