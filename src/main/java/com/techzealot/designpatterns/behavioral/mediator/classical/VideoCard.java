package com.techzealot.designpatterns.behavioral.mediator.classical;

public class VideoCard extends Colleague {
    public VideoCard(Mediator mediator) {
        super(mediator);
    }

    public void showData(String data){
        System.out.println("video: "+data);
    }
}
