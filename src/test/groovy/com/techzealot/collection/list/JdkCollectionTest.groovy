package com.techzealot.collection.list

import spock.lang.Specification


class JdkCollectionTest extends Specification {
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

    def "test init PriorityQueue with TreeSet which contains null"() {
        when:
        def arr = new TreeSet<Integer>((a, b) -> {
            if (a == null && b == null) {
                return 0;
            }
            if (b == null) {
                return 1;
            }
            if (a == null) {
                return -1;
            }
            return a - b
        });
        arr.add(1)
        //自定义支持null值的Comparator在有序集合中可以存放null
        arr.add(null)
        def pq = new PriorityQueue(arr)
        then:
        true
        def e = thrown(NullPointerException)
        e.asString().contains("PriorityQueue.initElementsFromCollection")
    }
}