package com.techzealot.collection.list

import spock.lang.Specification

class ArrayListTest extends Specification {
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
