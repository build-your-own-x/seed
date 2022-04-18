package com.techzealot.designpatterns.behavioral.state.player;

public class StopState extends State {
    public StopState(Player player) {
        super(player);
        player.setPlaying(false);
    }

    @Override
    public String onStop() {
        return "Stop song " + player.getCurrentTrack();
    }

    @Override
    public String onPlay() {
        player.setState(new PlayingState(player));
        return "Play song " + player.getCurrentTrack();
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
