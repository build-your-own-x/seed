package com.techzealot.collection.list

import com.techzealot.collection.list.ArrayList
import spock.lang.Specification

class ArrayListTest extends Specification {
    def "test isEmpty"() {
        given:

        when:
        ArrayList list=new ArrayList();
        then:
        list.isEmpty()
    }
}
