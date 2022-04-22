package com.techzealot.designpatterns.structural.bridge.messagesend;

public class UrgentMessage extends AbstractMessage {
    public UrgentMessage(Sender sender) {
        super(sender);
    }

    public void additionalAction(){
        System.out.println("do additional action");
    }
}
