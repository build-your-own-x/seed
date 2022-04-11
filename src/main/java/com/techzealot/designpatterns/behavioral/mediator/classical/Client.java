package com.techzealot.designpatterns.behavioral.mediator.classical;

public class Client {
    public static void main(String[] args) {
        MotherBoard motherBoard = new MotherBoard();
        CPU cpu = new CPU(motherBoard);
        CDDriver cdDriver = new CDDriver(motherBoard);
        VideoCard videoCard = new VideoCard(motherBoard);
        SoundCard soundCard = new SoundCard(motherBoard);
        motherBoard.setCpu(cpu);
        motherBoard.setCdDriver(cdDriver);
        motherBoard.setVideoCard(videoCard);
        motherBoard.setSoundCard(soundCard);
        //触发
        cdDriver.readCD("video data,sound data");
    }
}
