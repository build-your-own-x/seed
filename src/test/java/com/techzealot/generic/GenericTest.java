package com.techzealot.generic;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class GenericTest {

    @Test
    public void testCollectCovariance() {
        List<Animal> animals = new ArrayList<>();
        List<Cat> cats = new ArrayList<>();
        //compile error,泛型是不变的
        //animals=cats;
    }

    @Test
    /**
     * 逆变和协变针对的是带泛型的集合这一新类型赋值而言
     * 消费场景下的协变,extends代表类型上界
     */
    public void testCollectCovariance1() {
        List<? extends Animal> animals = new ArrayList<>();
        List<Cat> cats = new ArrayList<>();
        cats.add(new Cat());
        //通过extends实现协变
        animals = cats;
        //为保证类型安全，协变后不能写入,只能通过源集合写入
        //animals.add(new Dog());
        Animal animal = animals.get(0);
    }

    @Test
    /**
     * 生产场景下的逆变,super代表类型下界
     */
    public void testCollectContravariance() {
        List<? super Animal> animals = new ArrayList<Object>();
        animals.add(new Cat());
        animals.add(new Dog());
        animals.add(new Animal());
        List<Creature> creatures = new ArrayList<>();
        creatures.add(new Cat());
        animals = creatures;
        Object object = animals.get(0);
    }
}
