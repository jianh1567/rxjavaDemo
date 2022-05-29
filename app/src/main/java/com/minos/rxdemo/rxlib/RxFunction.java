package com.minos.rxdemo.rxlib;

public interface RxFunction<T, R> {
    R apply(T t);
}
