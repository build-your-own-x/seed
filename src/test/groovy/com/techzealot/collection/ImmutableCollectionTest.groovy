package com.techzealot.collection

import com.google.common.collect.ImmutableList
import org.eclipse.collections.api.factory.Lists
import spock.lang.Specification;

class ImmutableCollectionTest extends Specification {

    /**
     * 兼容Java集合接口，但实际使用时返回ImmutableList和MutableList来区分适用场景
     */
    def "test eclipse-collections"() {
        when: "创建集合"
        def list = Lists.immutable.of(*1..10)
        then:
        list == [*1..10]
        when: "修改集合"
        def add = [*1..5]
        def list1 = list.newWithAll(add)
        then:
        list == [*1..10]
        list1 == [*1..10, *1..5]
    }

    /**
     * 兼容Java集合接口,但是对所有修改方法的调用都会触发UnsupportedOperationException
     */
    def "test guava"() {
        when: "创建集合"
        def list = ImmutableList.of(*1..10)
        then:
        list == [*1..10]
        when:
        list.add(1)
        then:
        thrown(UnsupportedOperationException)
        when: "修改集合"
        def add = ImmutableList.of(*1..5)
        def list1 = ImmutableList.builder().addAll(list).addAll(add).build()
        then:
        list == [*1..10]
        list1 == [*1..10, *1..5]
    }
}
