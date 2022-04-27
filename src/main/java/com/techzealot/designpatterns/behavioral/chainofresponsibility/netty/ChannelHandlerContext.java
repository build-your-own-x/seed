package com.techzealot.designpatterns.behavioral.chainofresponsibility.netty;

/**
 * 作用是可以实现ChannelHandler的无状态化,可复用
 */
public abstract class ChannelHandlerContext {

    private final Channel channel;
    private final ChannelPipeline pipeline;
    ChannelHandlerContext prev;
    ChannelHandlerContext next;

    public ChannelHandlerContext(Channel channel, ChannelPipeline pipeline) {
        this.channel = channel;
        this.pipeline = pipeline;
    }

    public static void invokeChannelRead(ChannelHandlerContext next, Object msg) {
        next.invokeChannelRead(msg);
    }

    public static void invokeExceptionCaught(ChannelHandlerContext next, Throwable cause) {
        next.invokeExceptionCaught(cause);
    }

    public Channel channel() {
        return this.channel;
    }

    public abstract ChannelHandler handler();

    public ChannelPipeline pipeline() {
        return this.pipeline;
    }

    public ChannelHandlerContext fireChannelRead(Object msg) {
        invokeChannelRead(findContextInbound(), msg);
        return this;
    }

    private void invokeChannelRead(Object msg) {
        if (handler() instanceof ChannelInboundHandler in) {
            try {
                in.channelRead(this, msg);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            System.err.println(next.getClass().getSimpleName());
        }
    }

    public ChannelHandlerContext fireExceptionCaught(Throwable cause) {
        invokeExceptionCaught(findContextInbound(), cause);
        return this;
    }

    private void invokeExceptionCaught(Throwable cause) {
        if (handler() instanceof ChannelInboundHandler in) {
            try {
                in.exceptionCaught(this, cause);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            System.err.println(next.getClass().getSimpleName());
        }
    }

    public void close() {
        ChannelHandlerContext next = findContextOutbound();
        if (next.handler() instanceof ChannelOutboundHandler out) {
            try {
                out.close(next);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            System.err.println(next.getClass().getSimpleName());
        }
    }

    public void read() {
        ChannelHandlerContext next = findContextOutbound();
        if (next.handler() instanceof ChannelOutboundHandler out) {
            try {
                out.read(next);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            System.err.println(next.getClass().getSimpleName());
        }
    }

    public void write(Object msg) {
        ChannelHandlerContext next = findContextOutbound();
        if (next.handler() instanceof ChannelOutboundHandler out) {
            try {
                out.write(next, msg);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            System.err.println(next.getClass().getSimpleName());
        }
    }

    private ChannelHandlerContext findContextOutbound() {
        ChannelHandlerContext ctx = this;
        do {
            ctx = ctx.prev;
        } while (!(ctx.handler() instanceof ChannelOutboundHandler));
        return ctx;
    }

    private ChannelHandlerContext findContextInbound() {
        ChannelHandlerContext ctx = this;
        do {
            ctx = ctx.next;
        } while (!(ctx.handler() instanceof ChannelInboundHandler));
        return ctx;
    }
}
