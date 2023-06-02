package com.techzealot.collection

import org.junit.jupiter.api.Test

interface TestParentCase {
    def getObject();

    @Test
    default void testA() {
        assert 1 + 1 == 2
    }

    @Test
    default void testB() {

    }

}