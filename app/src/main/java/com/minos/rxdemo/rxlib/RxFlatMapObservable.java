package com.minos.rxdemo.rxlib;

public class RxFlatMapObservable<T, R> extends RxObservable<R>{
    RxObservableSource<T> source;
    RxFunction<T, RxObservableSource<R>> function;

    public RxFlatMapObservable(RxObservableSource<T> source, RxFunction<T, RxObservableSource<R>> function) {
        this.source = source;
        this.function = function;
    }

    @Override
    void subscribeActual(RxObserver<? super R> observer) {
        source.subscribe(new MergeObserver<>(observer, function));
    }

    public static class MergeObserver<T, R> implements RxObserver<T> {
        RxObserver<? super R> observer;
        RxFunction<T, RxObservableSource<R>> mapper;

        MergeObserver(RxObserver<? super R> observer,  RxFunction<T, RxObservableSource<R>> mapper) {
            this.observer = observer;
            this.mapper = mapper;
        }

        @Override
        public void onNext(T t) {
            RxObservableSource<R> observableSource = mapper.apply(t);
            observableSource.subscribe(new RxObserver<R>() {
                @Override
                public void onNext(R r) {
                    observer.onNext(r);
                }

                @Override
                public void onError(Throwable throwable) {

                }

                @Override
                public void onComplete() {

                }
            });
        }

        @Override
        public void onError(Throwable throwable) {
            observer.onError(throwable);
        }

        @Override
        public void onComplete() {
            observer.onComplete();
        }
    }

}
