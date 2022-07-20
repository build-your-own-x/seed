package com.techzealot.collection.list

class ArrayListTest {
    def "test isEmpty"() {
        when:
        MyArrayList list = new MyArrayList();
        then:
        list.isEmpty()
    }

    def "test null equals null"() {
        expect:
        Objects.equals(null, null)
    }
}
