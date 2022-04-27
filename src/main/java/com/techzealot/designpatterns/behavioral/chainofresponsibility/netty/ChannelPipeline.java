package com.techzealot.designpatterns.behavioral.chainofresponsibility.netty;

public interface ChannelPipeline {

    ChannelPipeline addLast(ChannelHandler... handlers);

    ChannelPipeline fireChannelRead(Object msg);

    ChannelPipeline fireExceptionCaught(Throwable cause);

    void close(ChannelHandlerContext ctx) throws Exception;

    void read(ChannelHandlerContext ctx) throws Exception;

    void write(ChannelHandlerContext ctx, Object msg) throws Exception;
}
