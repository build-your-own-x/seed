package com.techzealot.designpatterns.behavioral.state.player;

public abstract class State {
    //context可以作为字段也可以作为方法参数传入
    protected Player player;

    public State(Player player) {
        this.player = player;
    }

    public abstract String onStop();
    public abstract String onPlay();
    public abstract String onNext();
    public abstract String onPrevious();
}
