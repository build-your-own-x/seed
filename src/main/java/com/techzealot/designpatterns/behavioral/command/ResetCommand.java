package com.techzealot.designpatterns.behavioral.command;

public class ResetCommand implements Command {

    private MainBoard mainBoard;

    public ResetCommand(MainBoard mainBoard) {
        this.mainBoard = mainBoard;
    }

    @Override
    public void execute() {
        mainBoard.reset();
    }
}
