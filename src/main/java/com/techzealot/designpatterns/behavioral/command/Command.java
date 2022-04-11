package com.techzealot.designpatterns.behavioral.command;

public interface Command {

    void execute();

    /**
     * 可实现支持撤销,可通过undoStack实现
     */
    default void undo() {

    }

    /**
     * 可实现支持重做,可通过redoStack实现
     */
    default void redo() {

    }
}
