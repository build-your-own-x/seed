package com.techzealot.designpatterns.creational.factorymethod.crossplatformgui;

public class HtmlDialog extends Dialog {
    @Override
    protected Button createButton() {
        return new HtmlButton();
    }
}
