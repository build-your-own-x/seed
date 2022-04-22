package com.techzealot.designpatterns.structural.bridge.messagesend;

public abstract class AbstractMessage {
    private Sender sender;

    public AbstractMessage(Sender sender) {
        this.sender = sender;
    }

    public void send(String target) {
        sender.send(this, target);
    }
}
