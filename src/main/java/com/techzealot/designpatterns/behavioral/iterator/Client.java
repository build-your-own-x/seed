package com.techzealot.designpatterns.behavioral.iterator;

import java.util.Iterator;

public class Client {
    public static void main(String[] args) {
        ArrayWrapper<Integer> ar = new ArrayWrapper<>(new Integer[]{1, 2, 3, 4, 5, 6});
        System.out.println("enhanced for loop:");
        for (Integer i : ar) {
            System.out.println(i);
        }
        System.out.println("iterator loop:");
        Iterator<Integer> it = ar.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
}
