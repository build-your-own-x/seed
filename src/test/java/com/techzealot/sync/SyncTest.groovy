//package com.techzealot.sync
//
//import spock.lang.Specification
//
//
//class SyncTest extends Specification {
//
//    def "call another object wait/notify in sync"() {
//        when:
//        Object o1 = new Object()
//        Object o2 = new Object()
//        synchronized (o1) {
//            o2.wait(5000)
//        }
//        then:
//        thrown(IllegalMonitorStateException.class)
//    }
//
//    def "call object wait/notify without sync"() {
//        when:
//        Object o1 = new Object();
//        o1.wait(5000)
//        then:
//        thrown(IllegalMonitorStateException.class)
//    }
//
//}