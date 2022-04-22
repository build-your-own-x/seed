package com.techzealot.designpatterns.structural.composite.menutree;

public abstract class Component {

    //--- common method
    public abstract void printStructure(String prefix);

    // ---composite method start
    public void addChild(Component... children){
        throw new UnsupportedOperationException();
    }

    public void removeChild(Component... children){
        throw new UnsupportedOperationException();
    }

    public Component getChild(int index){
        throw new UnsupportedOperationException();
    }

    // --- composite method end

}
