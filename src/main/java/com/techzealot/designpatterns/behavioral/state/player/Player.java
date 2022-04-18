package com.techzealot.designpatterns.behavioral.state.player;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * context
 */
@Data
public class Player {

    private State state;

    private boolean playing = false;

    private List<String> playList = new ArrayList<>();

    private int currentTrack = 0;

    public Player() {
        this.state = new ReadyState(this);
        for (int i = 0; i < 5; i++) {
            playList.add("Track " + i);
        }
    }

    public String nextTrack() {
        currentTrack++;
        if (currentTrack > playList.size() - 1) {
            currentTrack = 0;
        }
        return "Play song " + playList.get(currentTrack);
    }

    public String previousTrack() {
        currentTrack--;
        if (currentTrack < 0) {
            currentTrack = playList.size() - 1;
        }
        return "Play song " + playList.get(currentTrack);
    }
}
