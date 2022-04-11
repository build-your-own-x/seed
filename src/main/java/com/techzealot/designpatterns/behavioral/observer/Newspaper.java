package com.techzealot.designpatterns.behavioral.observer;


import java.util.Observable;

public class Newspaper extends Observable {

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        //开始通知
        this.setChanged();
        //主动通知推模式
        this.notifyObservers(content);
        //也可以使用拉模式
        this.notifyObservers();
    }
}
