package com.techzealot.designpatterns.behavioral.mediator.classical;

import lombok.Data;

@Data
public class MotherBoard implements Mediator {

    private CDDriver cdDriver;

    private CPU cpu;

    private VideoCard videoCard;

    private SoundCard soundCard;

    @Override
    public void changed(Colleague colleague) {
        switch (colleague) {
            case CDDriver cdDriver -> onCDRead(cdDriver);
            case CPU cpu -> onCpuExecute(cpu);
            default -> throw new IllegalStateException("Unexpected value: " + colleague);
        }
    }

    private void onCpuExecute(CPU cpu) {
        String videoData = cpu.getVideoData();
        String soundData = cpu.getSoundData();
        this.videoCard.showData(videoData);
        this.soundCard.soundData(soundData);
    }

    private void onCDRead(CDDriver cdDriver) {
        String cdDriverData = cdDriver.getData();
        this.cpu.execute(cdDriverData);
    }
}
