package com.techzealot.designpatterns.behavioral.command.editor;

import com.google.common.base.Strings;

public class PasteCommand extends Command {
    public PasteCommand(Editor editor) {
        super(editor);
    }

    @Override
    public boolean execute() {
        if (Strings.isNullOrEmpty(editor.clipboard)) {
            return false;
        }
        backup();
        editor.textField.insert(editor.clipboard, editor.textField.getCaretPosition());
        return true;
    }
}
