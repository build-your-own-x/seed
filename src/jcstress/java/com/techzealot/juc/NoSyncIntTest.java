package com.techzealot.juc;

import org.openjdk.jcstress.annotations.*;

import static org.openjdk.jcstress.annotations.Expect.ACCEPTABLE;
import static org.openjdk.jcstress.annotations.Expect.ACCEPTABLE_INTERESTING;

@JCStressTest(Mode.Termination)
@Outcome(id = "TERMINATED", expect = ACCEPTABLE, desc = "Gracefully finished.")
@Outcome(id = "STALE", expect = ACCEPTABLE_INTERESTING, desc = "Test hung up.")
@State()
public class NoSyncIntTest {

    private int value;

    @Actor
    public void actor1() {
        while (value == 0) {

        }
    }

    @Signal
    public void actor2() {
        value = 1;
    }
}
