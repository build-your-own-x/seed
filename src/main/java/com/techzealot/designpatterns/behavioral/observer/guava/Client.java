package com.techzealot.designpatterns.behavioral.observer.guava;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.util.concurrent.MoreExecutors;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Client {
    public static void main(String[] args) {
        EventBus eventBus = getEventBus(true);
        eventBus.register(new EventListener());
        User tom = new User(1, "tom", 22);
        Order order = new Order(1234L, "202204251517", 1200);
        eventBus.post(new UserEvent(tom));
        eventBus.post(new OrderEvent(order));
        eventBus.post("dead event");
    }

    private static EventBus getEventBus(boolean async) {
        //支持jvm退出时自动关闭的线程池
        ExecutorService exitingExecutorService = MoreExecutors.getExitingExecutorService((ThreadPoolExecutor) Executors.newFixedThreadPool(4), Duration.ofSeconds(5));
        return async ? new AsyncEventBus(exitingExecutorService)
                : new EventBus();
    }
}
