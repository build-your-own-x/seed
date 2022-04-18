package com.techzealot.designpatterns.behavioral.command.computer;

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
