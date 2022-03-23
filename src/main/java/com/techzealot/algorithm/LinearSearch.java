package com.techzealot.algorithm;

import java.util.List;
import java.util.Objects;

public class LinearSearch {
    /**
     *
     * @param list
     * @param target
     * @param <E>
     * @return if exist return index,else return -1
     */
    public static <E> int search(List<E> list, E target) {
        for (int i = 0; i < list.size(); i++) {
            if(Objects.equals(list.get(i), target)){
                return i;
            }
        }
        return -1;
    }
}
