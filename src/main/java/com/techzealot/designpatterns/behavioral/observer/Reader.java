package com.techzealot.designpatterns.behavioral.observer;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.text.MessageFormat;
import java.util.Observable;
import java.util.Observer;

@AllArgsConstructor
@Data
public class Reader implements Observer {

    private String name;

    @Override
    public void update(Observable o, Object arg) {
        //处理推模式数据
        System.out.println(MessageFormat.format("push mode: reader {0} receive {1} from {2}", name, o.getClass().getSimpleName(), arg));
        //处理拉模式数据
        if (o instanceof Newspaper ns) {
            System.out.println(MessageFormat.format("pull mode: reader {0} receive {1} from {2}", name, o.getClass().getSimpleName(), ns.getContent()));
        }
    }
}
