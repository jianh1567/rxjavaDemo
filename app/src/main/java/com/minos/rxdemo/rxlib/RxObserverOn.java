package com.minos.rxdemo.rxlib;

import com.minos.rxdemo.rxlib.schedulers.RxScheduler;

public class RxObserverOn<T> extends RxObservable<T> {
    private RxObservableSource<T> source;
    private RxScheduler scheduler;

    public RxObserverOn(RxObservableSource<T> source, RxScheduler scheduler) {
        this.source = source;
        this.scheduler = scheduler;
    }

    @Override
    void subscribeActual(RxObserver<? super T> observer) {
        source.subscribe(new RxNewObserver<>(observer, scheduler));
    }

    public static class RxNewObserver<T> implements RxObserver<T>{
        RxObserver<? super T> actual;
        RxScheduler scheduler;

        public RxNewObserver(RxObserver<? super T> observer, RxScheduler scheduler) {
            this.actual = observer;
            this.scheduler = scheduler;
        }

        @Override
        public void onNext(T t) {
            scheduler.schedule(new RxScheduler.Worker() {
                @Override
                protected void execute() {
                    actual.onNext(t);
                }
            });
        }

        @Override
        public void onError(Throwable throwable) {
            scheduler.schedule(new RxScheduler.Worker() {
                @Override
                protected void execute() {
                    actual.onError(throwable);
                }
            });
        }

        @Override
        public void onComplete() {
            scheduler.schedule(new RxScheduler.Worker() {
                @Override
                protected void execute() {
                    actual.onComplete();
                }
            });
        }
    }
}
