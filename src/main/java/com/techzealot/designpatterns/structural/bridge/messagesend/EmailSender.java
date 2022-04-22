package com.techzealot.designpatterns.structural.bridge.messagesend;

public class EmailSender implements Sender {
    @Override
    public void send(AbstractMessage message, String target) {
        System.out.println("send by email");
    }
}
