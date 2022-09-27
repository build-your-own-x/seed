package com.techzealot.examples;

import java.util.Arrays;

/**
 * 类型安全的创建泛型数组
 *
 * @param <E>
 */
public class GenericArrayCreation<E> {

    private E[] elements;

    public GenericArrayCreation(E... elements) {
        elements = newArray(elements.length, elements);
    }

    @SafeVarargs
    public static <T> T[] newArray(int capacity, T... elements) {
        return Arrays.copyOf(elements, capacity);
    }
}
