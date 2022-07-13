package com.techzealot.collection.list;

import java.io.Serializable;
import java.util.RandomAccess;

/**
 * 实现 增删改查 序列化优化 泛型支持 随机访问
 *
 * @param <E>
 */
public class ArrayList<E> implements RandomAccess, Serializable {

    public boolean isEmpty() {
        return true;
    }
}
