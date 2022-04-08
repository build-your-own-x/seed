package com.techzealot.juc;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.I_Result;

@JCStressTest
@State
@Outcome(id = "0", expect = Expect.FORBIDDEN)
@Outcome(id = "1", expect = Expect.ACCEPTABLE)
public class UnsafeSingletonObjectTest {

    private IntWrapper intWrapper;

    @Actor
    public void actor1(I_Result r) {
        if (intWrapper == null) {
            synchronized (UnsafeSingletonObjectTest.class) {
                if (intWrapper == null) {
                    intWrapper = new IntWrapper(1);
                }
            }
        }
        r.r1 = intWrapper.getValue();
    }

    @Actor
    public void actor2() {
        if (intWrapper == null) {
            synchronized (UnsafeSingletonObjectTest.class) {
                if (intWrapper == null) {
                    intWrapper = new IntWrapper(1);
                }
            }
        }
    }

    static class IntWrapper {
        private int value;

        public static volatile int y;

        public IntWrapper(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

}
