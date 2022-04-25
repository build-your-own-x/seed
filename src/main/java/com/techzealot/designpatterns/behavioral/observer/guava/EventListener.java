package com.techzealot.designpatterns.behavioral.observer.guava;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;

import java.text.MessageFormat;

public class EventListener {

    @Subscribe
    public <T> void onEvent(Event<T> event) {
        System.out.println(MessageFormat.format("thread: {0},receive event: {1}", Thread.currentThread().getId(), event));
    }

    @Subscribe
    public void onUserEvent(UserEvent event) {
        System.out.println(MessageFormat.format("thread: {0},receive user event: {1}", Thread.currentThread().getId(), event));
    }

    @Subscribe
    public void onOrderEvent(OrderEvent event) {
        System.out.println(MessageFormat.format("thread: {0},receive order event: {1}", Thread.currentThread().getId(), event));
    }

    @Subscribe
    public void onDeadEvent(DeadEvent event) {
        System.out.println(MessageFormat.format("thread: {0},receive dead event: {1}", Thread.currentThread().getId(), event));
    }

}
