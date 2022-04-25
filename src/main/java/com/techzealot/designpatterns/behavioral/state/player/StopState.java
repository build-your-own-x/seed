package com.techzealot.designpatterns.behavioral.state.player;

public class StopState extends State {
    public StopState(Player player) {
        super(player);
        player.setPlaying(false);
    }

    @Override
    public String onStop() {
        return "Stop song " + player.getCurrentSong();
    }

    @Override
    public String onPlay() {
        player.changeState(new PlayingState(player));
        return "Play song " + player.getCurrentSong();
    }

    @Override
    public String onNext() {
        return "Stopped";
    }

    @Override
    public String onPrevious() {
        return "Stopped";
    }
}
