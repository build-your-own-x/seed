package com.techzealot.designpatterns.behavioral.strategy.variant;

import java.util.HashMap;
import java.util.Map;

/**
 * 实现为单例(在spring环境下通过Map自动注入所有实现),不共享状态,由单独的参数对象实现数据传递
 */
public class Context {

    private Map<String, IStrategy> strategyMap = new HashMap<>(8);

    public Context() {
        autowire();
    }

    private void autowire() {
        strategyMap.put("A", new AStrategy());
        strategyMap.put("B", new BStrategy());
        strategyMap.put("C", new CStrategy());
    }

    public void doSome(String selectParam, Params params) {
        strategyMap.getOrDefault(selectParam, new DefaultStrategy())
                .doSomething(this,params);
    }

    /**
     * 策略使用的公共方法，可减少一个类
     * @param params
     */
    public void commonCallbackMethods(Params params) {
        System.out.println("common callback method");
    }
}
