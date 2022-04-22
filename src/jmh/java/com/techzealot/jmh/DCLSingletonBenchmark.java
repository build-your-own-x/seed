package com.techzealot.jmh;

import com.techzealot.designpatterns.creational.singleton.DCLLazySingleton;
import org.openjdk.jmh.annotations.Benchmark;

public class DCLSingletonBenchmark {

    @Benchmark
    public DCLLazySingleton testGetInstance() {
        return DCLLazySingleton.getInstance();
    }

    @Benchmark
    public DCLLazySingleton testBetterGetInstance() {
        return DCLLazySingleton.betterGetInstance();
    }
}
