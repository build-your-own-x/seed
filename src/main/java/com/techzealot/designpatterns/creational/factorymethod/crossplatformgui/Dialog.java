package com.techzealot.designpatterns.creational.factorymethod.crossplatformgui;

public abstract class  Dialog {
    public void renderWindow() {
        Button button = createButton();
        button.render();
    }

    protected abstract Button createButton();

    /**
     * 简单工厂
     * @return
     */
    public static Dialog getPlatformDialog(){
        if(System.getProperty("os.name").contains("win")){
            return new WindowsDialog();
        }else {
            return new HtmlDialog();
        }
    }
}
