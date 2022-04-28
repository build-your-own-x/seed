package com.techzealot.designpatterns.behavioral.chainofresponsibility.netty;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * 用于实现handler中未覆盖父类相关方法时快速跳过以优化性能
 */
final class ChannelHandlerMask {

    // Using to mask which methods must be called for a ChannelHandler.
    //inbound
    static final int MASK_EXCEPTION_CAUGHT = 1 << 0;
    static final int MASK_CHANNEL_READ = 1 << 1;
    //outbound
    static final int MASK_CLOSE = 1 << 2;
    static final int MASK_READ = 1 << 3;
    static final int MASK_WRITE = 1 << 4;

    static final int MASK_ALL_INBOUND = MASK_EXCEPTION_CAUGHT | MASK_CHANNEL_READ;
    static final int MASK_ALL_OUTBOUND = MASK_CLOSE | MASK_READ | MASK_WRITE;

    private static final ThreadLocal<Map<Class<?>, Integer>> MASKS = new ThreadLocal<>() {
        @Override
        protected Map<Class<?>, Integer> initialValue() {
            return new WeakHashMap<>(32);
        }
    };

    private ChannelHandlerMask() {
    }

    static int mask(Class<? extends ChannelHandler> clazz) {
        Map<Class<?>, Integer> cache = MASKS.get();
        Integer mask = cache.get(clazz);
        if (mask == null) {
            mask = mask0(clazz);
            cache.put(clazz, mask);
        }
        return mask;
    }

    private static Integer mask0(Class<? extends ChannelHandler> handlerType) {
        int mask = 0;
        try {
            if (ChannelInboundHandler.class.isAssignableFrom(handlerType)) {
                mask |= MASK_ALL_INBOUND;
                if (isSkippable(handlerType, "channelRead", AbstractChannelHandlerContext.class, Object.class)) {
                    mask &= ~MASK_CHANNEL_READ;
                }
                if (isSkippable(handlerType, "exceptionCaught", AbstractChannelHandlerContext.class, Throwable.class)) {
                    mask &= ~MASK_EXCEPTION_CAUGHT;
                }
            }
            if (ChannelOutboundHandler.class.isAssignableFrom(handlerType)) {
                mask |= MASK_ALL_OUTBOUND;
                if (isSkippable(handlerType, "close", AbstractChannelHandlerContext.class)) {
                    mask &= ~MASK_CLOSE;
                }
                if (isSkippable(handlerType, "read", AbstractChannelHandlerContext.class)) {
                    mask &= ~MASK_READ;
                }
                if (isSkippable(handlerType, "write", AbstractChannelHandlerContext.class, Object.class)) {
                    mask &= ~MASK_WRITE;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return mask;
    }

    private static boolean isSkippable(final Class<?> handlerType, final String methodName, final Class<?>... paramTypes) throws Exception {
        return handlerType.getMethod(methodName, paramTypes).isAnnotationPresent(Skip.class);
    }

    /**
     * handler中带有该注解的方法在调用链中跳过
     */
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface Skip {
        //mark annotation,no value
    }
}
