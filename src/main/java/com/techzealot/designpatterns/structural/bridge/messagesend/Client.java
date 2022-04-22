package com.techzealot.designpatterns.structural.bridge.messagesend;

public class Client {
    public static void main(String[] args) {
        Sender emailSender = new EmailSender();
        SMSSender smsSender = new SMSSender();
        AbstractMessage messageByEmail = new UrgentMessage(emailSender);
        messageByEmail.send("A");
        AbstractMessage messageBySMS = new UrgentMessage(smsSender);
        messageBySMS.send("A");
    }
}
