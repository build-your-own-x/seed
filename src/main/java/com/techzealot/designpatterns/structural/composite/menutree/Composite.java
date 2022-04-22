package com.techzealot.designpatterns.structural.composite.menutree;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class Composite extends Component {

    @EqualsAndHashCode.Exclude
    private final List<Component> children = new ArrayList<Component>();

    private String name = "";

    public Composite(String name) {
        this.name = name;
    }

    @Override
    public void printStructure(String prefix) {
        //todo 不支持环状结构
        System.out.println(MessageFormat.format("{0}-{1}", prefix, name));
        prefix += " ";
        for (Component child : children) {
            child.printStructure(prefix);
        }
    }

    @Override
    public void addChild(Component... children) {
        this.children.addAll(Arrays.asList(children));
    }

    @Override
    public void removeChild(Component... children) {
        this.children.removeAll(Arrays.asList(children));
    }

    @Override
    public Component getChild(int index) {
        if (index >= children.size()) {
            throw new IndexOutOfBoundsException();
        }
        return children.get(index);
    }
}
