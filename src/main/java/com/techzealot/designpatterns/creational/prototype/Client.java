package com.techzealot.designpatterns.creational.prototype;

public class Client {

    public static void main(String[] args) throws CloneNotSupportedException {
        Person jack = new Person(true, "jack", 30);
        Person jackson = new Person(true, "jackson", 5);
        jackson.setParent(jack);
        Person jackson1 = (Person) jackson.clone();
        System.out.println("shallow copy:" + jackson1);
        jack.setAge(31);
        jack.setName("jack1");
        jackson.setName("jackson0");
        System.out.println("after modify reference field: " + jackson1);

        Person jackson2 = jackson.deepClone();
        System.out.println("deep copy:" + jackson2);
        jack.setAge(32);
        jack.setName("jack2");
        System.out.println("after modify reference field: " + jackson2);

    }
}
