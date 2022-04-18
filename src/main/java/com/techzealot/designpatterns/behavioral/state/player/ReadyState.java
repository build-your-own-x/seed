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
        player.setState(new StopState(player));
        return "Stop song " + player.getCurrentTrack();
    }

    @Override
    public String onPlay() {
        player.setState(new PlayingState(player));
        return "Play song " + player.getCurrentTrack();
    }

    @Override
    public String onNext() {
        player.setState(new PlayingState(player));
        return player.nextTrack();
    }

    @Override
    public String onPrevious() {
        player.setState(new PlayingState(player));
        return player.previousTrack();
    }
}
