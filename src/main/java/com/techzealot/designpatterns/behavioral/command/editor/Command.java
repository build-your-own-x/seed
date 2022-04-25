package com.techzealot.designpatterns.behavioral.command.editor;

public abstract class Command {

    public final Editor editor;
    private String backup;

    public Command(Editor editor) {
        this.editor = editor;
    }

    /**
     * @return true表示需要记录该命令使支持撤销
     */
    public abstract boolean execute();

    public void undo() {
        editor.textField.setText(backup);
    }

    void backup() {
        backup = editor.textField.getText();
    }
}
