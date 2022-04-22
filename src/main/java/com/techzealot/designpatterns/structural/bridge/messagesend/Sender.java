package com.techzealot.designpatterns.structural.bridge.messagesend;

public interface Sender {
    void send(AbstractMessage message,String target);
}
