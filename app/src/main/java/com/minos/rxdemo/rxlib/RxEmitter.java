package com.minos.rxdemo.rxlib;

public interface RxEmitter<T> {
    void onNext(T t);

    void onError(Throwable throwable);

    void onComplete();
}
