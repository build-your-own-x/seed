package com.techzealot.designpatterns.behavioral.command;

public class OpenCommand implements Command {

    private MainBoard mainBoard;

    public OpenCommand(MainBoard mainBoard) {
        this.mainBoard = mainBoard;
    }

    @Override
    public void execute() {
        mainBoard.open();
    }
}
