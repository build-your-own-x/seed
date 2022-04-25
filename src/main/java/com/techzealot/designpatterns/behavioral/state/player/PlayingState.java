package com.techzealot.designpatterns.behavioral.state.player;

public class PlayingState extends State {
    public PlayingState(Player player) {
        super(player);
        player.setPlaying(true);
    }

    @Override
    public String onStop() {
        player.changeState(new StopState(player));
        return "Stop song " + player.getCurrentSong();
    }

    @Override
    public String onPlay() {
        return "Play song " + player.getCurrentSong();
    }

    @Override
    public String onNext() {
        return player.nextTrack();
    }

    @Override
    public String onPrevious() {
        return player.previousTrack();
    }
}
