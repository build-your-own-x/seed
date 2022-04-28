package com.techzealot.designpatterns.behavioral.chainofresponsibility.netty;

public class DefaultChannelHandlerContext extends AbstractChannelHandlerContext {

    private final ChannelHandler handler;

    public DefaultChannelHandlerContext(Channel channel, ChannelPipeline pipeline, ChannelHandler handler) {
        super(channel, pipeline, handler.getClass());
        this.handler = handler;
    }

    @Override
    public ChannelHandler handler() {
        return handler;
    }
}
