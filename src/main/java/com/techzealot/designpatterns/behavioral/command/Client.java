package com.techzealot.designpatterns.behavioral.command;

public class Client {

    public static void main(String[] args) {
        MainBoard a = new ASerialMainBoard();
        OpenCommand openCommand = new OpenCommand(a);
        ResetCommand resetCommand = new ResetCommand(a);
        Box box = new Box();
        box.setOpenCommand(openCommand);
        box.setResetCommand(resetCommand);
        box.openButtonPressed();
        box.resetButtonPressed();
    }
}
