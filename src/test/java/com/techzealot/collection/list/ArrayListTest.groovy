package com.techzealot.collection.list

class ArrayListTest {
    def "test isEmpty"() {
        when:
        ArrayList list = new ArrayList();
        then:
        list.isEmpty()
    }

    def "test null equals null"() {
        expect:
        Objects.equals(null, null)
    }
}
