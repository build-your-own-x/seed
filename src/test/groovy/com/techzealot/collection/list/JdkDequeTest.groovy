package com.techzealot.collection.list

import spock.lang.Specification


class JdkDequeTest extends Specification {
    /**
     * LinkedList作为Queue和Deque返回null值时具有歧义
     * @return
     */
    def "test LinkedList add null and take null"() {
        given:
        def list = new LinkedList<Integer>()
        when:
        list.offer(1)
        list.offer(2)
        list.offer(null)
        list.offer(3)
        then:
        list.poll() == 1
        list.poll() == 2
        list.poll() == null
        !list.isEmpty()
        list.poll() == 3
        list.isEmpty()
    }

    /**
     * 建议使用ArrayDeque,遵循接口定义
     */
    def "test ArrayDeque as Deque"() {
        given:
        def deque = new ArrayDeque<Integer>()
        when:
        deque.offer(null)
        then:
        thrown(NullPointerException)
        when:
        deque.offer(1)
        deque.offer(2)
        deque.offer(3)
        then:
        deque.poll() == 1
        deque.poll() == 2
        deque.poll() == 3
        deque.poll() == null
        deque.isEmpty()
    }
}