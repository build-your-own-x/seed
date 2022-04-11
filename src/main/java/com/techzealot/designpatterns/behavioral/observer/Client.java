package com.techzealot.designpatterns.behavioral.observer;

public class Client {
    public static void main(String[] args) {
        Newspaper newspaper=new Newspaper();
        Reader r1=new Reader("r1");
        Reader r2=new Reader("r2");
        Reader r3=new Reader("r3");
        Reader r4=new Reader("r4");
        newspaper.addObserver(r1);
        newspaper.addObserver(r2);
        newspaper.addObserver(r3);
        newspaper.addObserver(r4);
        newspaper.setContent("news: 1+1=2");
    }
}
