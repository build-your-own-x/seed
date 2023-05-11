package com.techzealot.collection.list;

import com.techzealot.collection.MyAbstractCollectionTestCase;
import com.techzealot.collection.MyCollection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MyArrayListTestCase extends MyAbstractCollectionTestCase {
    @Override
    public <T> MyCollection<T> newWith(T... elements) {
        return MyArrayList.of(elements);
    }

    @Test
    public void testAdd() {
        MyArrayList<Integer> a = new MyArrayList<>();
        a.add(1);
        a.add(2);
        Assertions.assertEquals("[1, 2]", a.toString());
    }
}
