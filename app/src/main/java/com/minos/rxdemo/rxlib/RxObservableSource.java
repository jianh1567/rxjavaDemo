package com.minos.rxdemo.rxlib;

public interface RxObservableSource<T> {
    void subscribe(RxObserver<? super T> observer);
}
