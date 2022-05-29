package com.minos.rxdemo.rxlib;

import com.minos.rxdemo.rxlib.schedulers.RxScheduler;

public class RxObservableSubscribeOn<T> extends RxObservable<T>{
    public RxObservableSource<T> source;
    public RxScheduler scheduler;

    public RxObservableSubscribeOn(RxObservableSource<T> source, RxScheduler scheduler) {
        this.source = source;
        this.scheduler = scheduler;
    }

    @Override
    void subscribeActual(RxObserver<? super T> observer) {
        scheduler.schedule(new RxScheduler.Worker(){
            @Override
            protected void execute() {
                source.subscribe(observer);
            }
        });
    }
}
