package com.techzealot.designpatterns.behavioral.chainofresponsibility.netty;

/**
 * 作用是可以实现ChannelHandler的无状态化,可复用
 */
public abstract class AbstractChannelHandlerContext {

    private final Channel channel;
    private final ChannelPipeline pipeline;
    private final int executionMask;
    AbstractChannelHandlerContext prev;
    AbstractChannelHandlerContext next;

    public AbstractChannelHandlerContext(Channel channel, ChannelPipeline pipeline, Class<? extends ChannelHandler> handlerClass) {
        this.channel = channel;
        this.pipeline = pipeline;
        this.executionMask = ChannelHandlerMask.mask(handlerClass);
    }

    public static void invokeChannelRead(AbstractChannelHandlerContext next, Object msg) {
        next.invokeChannelRead(msg);
    }

    public static void invokeExceptionCaught(AbstractChannelHandlerContext next, Throwable cause) {
        next.invokeExceptionCaught(cause);
    }

    public Channel channel() {
        return this.channel;
    }

    public abstract ChannelHandler handler();

    public ChannelPipeline pipeline() {
        return this.pipeline;
    }

    public AbstractChannelHandlerContext fireChannelRead(Object msg) {
        invokeChannelRead(findContextInbound(ChannelHandlerMask.MASK_CHANNEL_READ), msg);
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

    public AbstractChannelHandlerContext fireExceptionCaught(Throwable cause) {
        invokeExceptionCaught(findContextInbound(ChannelHandlerMask.MASK_EXCEPTION_CAUGHT), cause);
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
        AbstractChannelHandlerContext next = findContextOutbound(ChannelHandlerMask.MASK_CLOSE);
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
        AbstractChannelHandlerContext next = findContextOutbound(ChannelHandlerMask.MASK_READ);
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
        AbstractChannelHandlerContext next = findContextOutbound(ChannelHandlerMask.MASK_WRITE);
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

    private AbstractChannelHandlerContext findContextOutbound(int mask) {
        AbstractChannelHandlerContext ctx = this;
        do {
            ctx = ctx.prev;
        } while ((ctx.executionMask & mask) == 0);
        return ctx;
    }

    private AbstractChannelHandlerContext findContextInbound(int mask) {
        AbstractChannelHandlerContext ctx = this;
        do {
            ctx = ctx.next;
        } while ((ctx.executionMask & mask) == 0);
        return ctx;
    }
}
