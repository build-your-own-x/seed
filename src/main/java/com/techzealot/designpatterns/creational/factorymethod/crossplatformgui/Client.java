package com.techzealot.designpatterns.creational.factorymethod.crossplatformgui;

public class Client {
    public static void main(String[] args) {
        //System.setProperty("os.name","windows");
        Dialog dialog = Dialog.getPlatformDialog();
        dialog.renderWindow();
    }
}
