package com.techzealot.designpatterns.structural.flyweight.forest;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 享元工厂
 */
public class TreeFactory {
    private static Map<String, TreeType> treeTypes = new HashMap<>();

    public static TreeType getTreeType(String name, Color color, String otherTreeData) {
        TreeType type = treeTypes.get(name);
        if (type == null) {
            type = new TreeType(name, color, otherTreeData);
            treeTypes.put(name, type);
        }
        return type;
    }
}
