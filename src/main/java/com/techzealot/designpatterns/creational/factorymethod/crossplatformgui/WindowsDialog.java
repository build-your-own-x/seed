package com.techzealot.designpatterns.creational.factorymethod.crossplatformgui;

public class WindowsDialog extends Dialog {
    @Override
    protected Button createButton() {
        return new WindowsButton();
    }
}
