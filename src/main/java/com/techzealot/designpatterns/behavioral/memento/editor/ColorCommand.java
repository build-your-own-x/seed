package com.techzealot.designpatterns.behavioral.memento.editor;

import com.techzealot.designpatterns.behavioral.memento.editor.shapes.IShape;

import java.awt.*;

public class ColorCommand implements Command {
    private Editor editor;
    private Color color;

    public ColorCommand(Editor editor, Color color) {
        this.editor = editor;
        this.color = color;
    }

    @Override
    public String getName() {
        return "Colorize: " + color.toString();
    }

    @Override
    public void execute() {
        for (IShape child : editor.getShapes().getSelected()) {
            child.setColor(color);
        }
    }
}