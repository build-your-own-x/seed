package com.techzealot.generic

import spock.lang.Specification


class GenericTest extends Specification {

    def "test array covariance"() {
        when:
        //数组是协变的
        Animal[] animals = new Cat[2]
        animals[0] = new Cat()
        //编译不会报错，运行时报错
        animals[1] = new Dog()
        then:
        noExceptionThrown()
    }

    def "test collection covariance"() {
        when:
        List<Animal> animals = new ArrayList<>()
        List<Cat> cats = new ArrayList<>()
        animals = cats
        then:
        noExceptionThrown()
    }

}