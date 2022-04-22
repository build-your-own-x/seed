package com.techzealot.designpatterns.structural.flyweight.forest;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.awt.*;

@Data
@AllArgsConstructor
public class Tree {
    private int x;
    private int y;
    private TreeType type;

    public void draw(Graphics g) {
        type.draw(g, x, y);
    }
}
