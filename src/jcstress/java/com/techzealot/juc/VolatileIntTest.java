package com.techzealot.juc;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.II_Result;

@JCStressTest
@State
//多个结果需要加空格隔开
@Outcome(id = "1, 1", expect = Expect.ACCEPTABLE_INTERESTING, desc = "both get 0 then increment,atomic failure")
@Outcome(id = "1, 2", expect = Expect.ACCEPTABLE, desc = "actor1 increment,then actor2")
@Outcome(id = "2, 1", expect = Expect.ACCEPTABLE, desc = "actor2 increment,then actor1")
public class VolatileIntTest {

    private volatile int sequencer = 0;

    @Actor
    public void actor1(II_Result r) {
        r.r1 = ++sequencer;
    }

    @Actor
    public void actor2(II_Result r) {
        r.r2 = ++sequencer;
    }

}
