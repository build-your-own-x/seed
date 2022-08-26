package com.techzealot.collection.immutable;


/**
 * 实现思路:
 * 1.实现对应集合接口，变更相关操作全部返回UnsupportedOperationException
 * 2.删除内部相关并发保护逻辑以及额外空间消除以优化性能
 * 3.在变更操作中返回新的集合而不是变更原集合
 *
 * @param <E>
 * @see org.eclipse.collections.impl.list.immutable.ImmutableArrayList
 * @see com.google.common.collect.RegularImmutableList
 */
public class MyImmutableArrayList<E> {
}
