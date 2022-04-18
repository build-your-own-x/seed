package com.techzealot.designpatterns.behavioral.state.player;

public class Client {
    public static void main(String[] args) {
        Player player = new Player();
        UI ui = new UI(player);
        ui.init();
    }
}
