package com.techzealot.designpatterns.behavioral.memento.editor;

import com.techzealot.designpatterns.behavioral.memento.editor.shapes.Circle;
import com.techzealot.designpatterns.behavioral.memento.editor.shapes.CompoundShape;
import com.techzealot.designpatterns.behavioral.memento.editor.shapes.Dot;
import com.techzealot.designpatterns.behavioral.memento.editor.shapes.RectangleShape;

import java.awt.*;

public class Client {
    public static void main(String[] args) {
        Editor editor = new Editor();
        editor.loadShapes(
                new Circle(10, 10, 10, Color.BLUE),

                new CompoundShape(
                        new Circle(110, 110, 50, Color.RED),
                        new Dot(160, 160, Color.RED)
                ),

                new CompoundShape(
                        new RectangleShape(250, 250, 100, 100, Color.GREEN),
                        new Dot(240, 240, Color.GREEN),
                        new Dot(240, 360, Color.GREEN),
                        new Dot(360, 360, Color.GREEN),
                        new Dot(360, 240, Color.GREEN)
                )
        );
    }
}
