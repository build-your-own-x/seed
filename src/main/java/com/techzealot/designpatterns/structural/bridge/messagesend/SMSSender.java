package com.techzealot.designpatterns.structural.bridge.messagesend;

public class SMSSender implements Sender {
    @Override
    public void send(AbstractMessage message, String target) {
        System.out.println("send by SMS");
    }
}
