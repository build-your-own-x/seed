package com.techzealot.collection.immutable;


/**
 * 实现思路:
 * 1.两种策略:
 * 1.1 guava实现对应集合接口，变更相关操作全部返回UnsupportedOperationException
 * 1.2 eclipse-collections返回自定义类型，不提供更改方法
 * 2.删除内部相关并发保护逻辑以及额外空间消除(无需扩缩容逻辑及相关存储空间)以优化性能
 * 3.在变更操作中返回新的集合而不是变更原集合
 *
 * @param <E>
 * @see org.eclipse.collections.impl.list.immutable.ImmutableArrayList
 * @see com.google.common.collect.RegularImmutableList
 */
public class MyImmutableArrayList<E> {
}
