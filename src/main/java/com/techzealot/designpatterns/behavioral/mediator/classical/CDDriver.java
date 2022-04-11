package com.techzealot.designpatterns.behavioral.mediator.classical;

import lombok.Getter;

public class CDDriver extends Colleague {

    @Getter
    private String data="";

    public CDDriver(Mediator mediator) {
        super(mediator);
    }

    public void readCD(String data){
        this.data=data;
        this.getMediator().changed(this);
    }
}
