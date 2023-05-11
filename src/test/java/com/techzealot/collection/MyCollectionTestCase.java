package com.techzealot.collection;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public interface MyCollectionTestCase {
    @Test
    default void testSize() {
        assertEquals(this.newWith().size(), 0);
    }

    <T> MyCollection<T> newWith(T... elements);
}
