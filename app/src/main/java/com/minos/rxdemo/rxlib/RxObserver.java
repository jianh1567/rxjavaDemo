package com.minos.rxdemo.rxlib;

public interface RxObserver<T> {
    void onNext(T t);

    void onError(Throwable throwable);

    void onComplete();
}
