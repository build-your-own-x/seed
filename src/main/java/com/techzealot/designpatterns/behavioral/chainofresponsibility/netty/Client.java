package com.techzealot.designpatterns.behavioral.chainofresponsibility.netty;

import com.techzealot.designpatterns.behavioral.chainofresponsibility.netty.test.ChannelInboundHandlerA;
import com.techzealot.designpatterns.behavioral.chainofresponsibility.netty.test.ChannelInboundHandlerB;
import com.techzealot.designpatterns.behavioral.chainofresponsibility.netty.test.ChannelInboundHandlerC;
import com.techzealot.designpatterns.behavioral.chainofresponsibility.netty.test.ChannelOutboundHandlerA;
import com.techzealot.designpatterns.behavioral.chainofresponsibility.netty.test.ChannelOutboundHandlerB;
import com.techzealot.designpatterns.behavioral.chainofresponsibility.netty.test.ChannelOutboundHandlerC;

public class Client {
    public static void main(String[] args) {
        ChannelPipeline pipeline = new DefaultChannelPipeline(new Channel());
        pipeline.addLast(
                new ChannelOutboundHandlerA(),
                new ChannelOutboundHandlerB(),
                new ChannelOutboundHandlerC(),
                new ChannelInboundHandlerA(),
                new ChannelInboundHandlerB(),
                new ChannelInboundHandlerC()
        );
        //inbound
        pipeline.fireChannelRead("hello world");
        pipeline.fireExceptionCaught(new RuntimeException("test"));
    }
}
