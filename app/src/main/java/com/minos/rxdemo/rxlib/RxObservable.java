package com.minos.rxdemo.rxlib;

import com.minos.rxdemo.rxlib.schedulers.RxScheduler;

public abstract class RxObservable<T> implements RxObservableSource<T>{

    public static <T> RxObservable<T> create(RxObservableOnSubscribe<T> onSubscribe) {
        return new RxObservableCreate<>(onSubscribe);
    }

    public <R> RxObservable<R> map(RxFunction<? super T, ? extends R> function) {
        return new RxMapObservable<>(this, function);
    }

    public <R> RxObservable<R> flatmap(RxFunction<T, RxObservableSource<R>> function) {
        return new RxFlatMapObservable<>(this, function);
    }

    public static <T, R> RxObservable<R> zip(RxObservableSource<T>[] source, RxBiFunction<T, R> function) {
        return new RxZipObservable<>(source, function);
    }

    public RxObservable<T> subscribeOn(RxScheduler scheduler) {
        return new RxObservableSubscribeOn<>(this, scheduler);
    }

    public RxObservable<T> observerOn(RxScheduler scheduler) {
        return new RxObserverOn<>(this, scheduler);
    }

    @Override
    public void subscribe(RxObserver<? super T> observer) {
        subscribeActual(observer);
    }

    abstract void subscribeActual(RxObserver<? super T> observer);
}
