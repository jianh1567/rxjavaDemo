package com.minos.rxdemo.rxlib;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class RxZipObservable<T, R> extends RxObservable<R>{
    RxObservableSource<T>[] sources;
    RxBiFunction<T, R> function;

    public RxZipObservable(RxObservableSource<T>[] sources, RxBiFunction<T, R> function) {
        this.sources = sources;
        this.function = function;
    }

    @Override
    void subscribeActual(RxObserver<? super R> observer) {
        new ZipCombination(sources, function, observer).subscribe();
    }

    public class ZipCombination{
        RxObservableSource<T>[] sources;
        ZipObserver<T>[] observers;
        RxBiFunction<T, R> mapper;
        RxObserver<? super R> actual;

        public ZipCombination(RxObservableSource<T>[] source, RxBiFunction<T, R> mapper, RxObserver<? super R> observer) {
            this.sources = source;
            this.mapper = mapper;
            this.actual = observer;
        }

        public void subscribe() {
            observers = new ZipObserver[sources.length];
            for (int i = 0; i < sources.length; i++) {
                ZipObserver<T> zipObserver = new ZipObserver<>(this);
                observers[i] = zipObserver;
            }
            for (int i = 0; i < sources.length; i++) {
                sources[i].subscribe(observers[i]);
            }
        }

        public void drain() {
            outer:
            for(;;) {
                int length = observers.length;
                for (int i = 0; i < length; i++) {
                    ZipObserver<T> observer = observers[i];
                    if (observer.queue.isEmpty()) {
                        if (observer.isDone) {
                            actual.onComplete();
                        }
                        break outer;
                    }
                    if (i == 1) {
                        R r = mapper.apply(observers[0].queue.poll(), observers[1].queue.poll());
                        actual.onNext(r);
                    }
                }
            }
        }
    }

    public class ZipObserver<T> implements RxObserver<T> {
        private final ZipCombination zipCombination;
        public Queue<T> queue = new LinkedBlockingQueue<>();
        public boolean isDone = false;

        public ZipObserver(ZipCombination combination) {
            zipCombination = combination;
        }

        @Override
        public void onNext(T t) {
            queue.offer(t);
            zipCombination.drain();
        }

        @Override
        public void onError(Throwable throwable) {

        }

        @Override
        public void onComplete() {
            isDone = true;
            zipCombination.drain();
        }
    }
}
