package com.techzealot.collection;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class MyAbstractCollectionTestCase implements MyCollectionTestCase {

    @Test
    public void testToString() {
        assertEquals("[]", this.newWith().toString());
        assertEquals("[1]", this.newWith(1).toString());
        assertEquals("[1, 2]", this.newWith(1, 2).toString());
    }
}
