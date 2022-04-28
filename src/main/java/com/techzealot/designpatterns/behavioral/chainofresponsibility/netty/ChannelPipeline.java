package com.techzealot.designpatterns.behavioral.chainofresponsibility.netty;

public interface ChannelPipeline {

    ChannelPipeline addLast(ChannelHandler... handlers);

    ChannelPipeline fireChannelRead(Object msg);

    ChannelPipeline fireExceptionCaught(Throwable cause);

    void close(AbstractChannelHandlerContext ctx) throws Exception;

    void read(AbstractChannelHandlerContext ctx) throws Exception;

    void write(AbstractChannelHandlerContext ctx, Object msg) throws Exception;
}
