package com.techzealot.collection.map;

import java.util.Comparator;

/**
 *
 */
public class MyTreeMap<K, V> {

    /**
     * 实现自定义compare方法统一比较的逻辑消除重复代码
     *
     * @param comparator
     * @param o1
     * @param o2
     * @return
     */
    private static int compare(Comparator comparator, Object o1, Object o2) {
        if (comparator != null) {
            return comparator.compare(o1, o2);
        }
        return ((Comparable) o1).compareTo(o2);
    }
}
