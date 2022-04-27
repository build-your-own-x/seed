package com.techzealot.designpatterns.behavioral.chainofresponsibility.netty;

public class DefaultChannelPipeline implements ChannelPipeline {

    ChannelHandlerContext head;
    ChannelHandlerContext tail;
    Channel channel;

    public DefaultChannelPipeline(Channel channel) {
        this.channel = channel;
        head = new HeadContext(channel, this);
        tail = new TailContext(channel, this);
        head.next = tail;
        tail.prev = head;
    }

    @Override
    public ChannelPipeline addLast(ChannelHandler... handlers) {
        for (ChannelHandler handler : handlers) {
            DefaultChannelHandlerContext ctx = new DefaultChannelHandlerContext(channel, this, handler);
            ChannelHandlerContext prev = tail.prev;
            prev.next = ctx;
            ctx.next = tail;
            ctx.prev = prev;
            tail.prev = ctx;
        }
        return this;
    }

    @Override
    public ChannelPipeline fireChannelRead(Object msg) {
        ChannelHandlerContext.invokeChannelRead(head, msg);
        return this;
    }

    @Override
    public ChannelPipeline fireExceptionCaught(Throwable cause) {
        ChannelHandlerContext.invokeExceptionCaught(head, cause);
        return this;
    }

    @Override
    public void close(ChannelHandlerContext ctx) throws Exception {
        tail.close();
    }

    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        tail.read();
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg) throws Exception {
        tail.write(msg);
    }


    final class HeadContext extends ChannelHandlerContext implements ChannelInboundHandler, ChannelOutboundHandler {

        private Unsafe unsafe = new Unsafe();

        public HeadContext(Channel channel, ChannelPipeline pipeline) {
            super(channel, pipeline);
        }

        @Override
        public ChannelHandler handler() {
            return this;
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            ctx.fireExceptionCaught(cause);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ctx.fireChannelRead(msg);
        }

        @Override
        public void close(ChannelHandlerContext ctx) throws Exception {
            unsafe.close();
        }

        @Override
        public void read(ChannelHandlerContext ctx) throws Exception {
            unsafe.read();
        }

        @Override
        public void write(ChannelHandlerContext ctx, Object msg) throws Exception {
            unsafe.write(msg);
        }
    }

    final class TailContext extends ChannelHandlerContext implements ChannelInboundHandler {

        public TailContext(Channel channel, ChannelPipeline pipeline) {
            super(channel, pipeline);
        }

        @Override
        public ChannelHandler handler() {
            return this;
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            System.out.println("unhandled exception found: " + cause.getMessage());
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println("unhandled read msg found: " + msg);
        }
    }

}
