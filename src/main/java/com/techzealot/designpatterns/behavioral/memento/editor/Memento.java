package com.techzealot.designpatterns.behavioral.memento.editor;

public class Memento {
    private final String backup;
    private final Editor editor;

    public Memento(Editor editor) {
        this.editor = editor;
        this.backup = editor.backup();
    }

    /**
     * 外部仅可恢复数据，不可获取保存的数据
     */
    public void restore() {
        editor.restore(backup);
    }
}