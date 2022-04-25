package com.techzealot.designpatterns.behavioral.state.player;

/**
 * 可以在一个状态中触发到另一个状态的转移，也可以在context中转移状态
 */
public class ReadyState extends State {
    public ReadyState(Player player) {
        super(player);
    }

    @Override
    public String onStop() {
        player.changeState(new StopState(player));
        return "Stop song " + player.getCurrentSong();
    }

    @Override
    public String onPlay() {
        player.changeState(new PlayingState(player));
        return "Play song " + player.getCurrentSong();
    }

    @Override
    public String onNext() {
        player.changeState(new PlayingState(player));
        return player.nextTrack();
    }

    @Override
    public String onPrevious() {
        player.changeState(new PlayingState(player));
        return player.previousTrack();
    }
}
