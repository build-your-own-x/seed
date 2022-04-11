package com.techzealot.designpatterns.behavioral.mediator.classical;

import lombok.Getter;

public class CPU extends Colleague {
    public CPU(Mediator mediator) {
        super(mediator);
    }

    @Getter
    private String videoData = "";
    @Getter
    private String soundData = "";

    public void execute(String data) {
        String[] ss = data.split(",");
        this.videoData = ss[0];
        this.soundData = ss[1];
        this.getMediator().changed(this);
    }


}
