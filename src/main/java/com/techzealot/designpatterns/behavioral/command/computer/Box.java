package com.techzealot.designpatterns.behavioral.command.computer;

import lombok.Data;

@Data
public class Box {
    private OpenCommand openCommand;

    private ResetCommand resetCommand;

    public void openButtonPressed() {
        openCommand.execute();
    }

    public void resetButtonPressed() {
        resetCommand.execute();
    }
}
