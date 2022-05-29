package com.minos.rxdemo.rxlib;

public interface RxBiFunction<T, R> {
    R apply(T t1, T t2);
}
