package com.minos.rxdemo.rxlib;

public interface RxObservableOnSubscribe<T> {
    void subscribe(RxEmitter<T> emitter);
}
