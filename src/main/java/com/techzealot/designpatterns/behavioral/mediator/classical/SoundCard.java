package com.techzealot.designpatterns.behavioral.mediator.classical;

public class SoundCard extends VideoCard {
    public SoundCard(Mediator mediator) {
        super(mediator);
    }

    public void soundData(String data){
        System.out.println("sound: "+data);
    }
}
