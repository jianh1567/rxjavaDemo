package com.minos.rxdemo.rxlib;

public class RxMapObservable<T, R> extends RxObservable<R> {
    private final RxObservableSource<T> source;
    private final RxFunction<? super T, ? extends R> function;

    public RxMapObservable(RxObservableSource<T> source, RxFunction<? super T, ? extends R> function) {
        this.source = source;
        this.function = function;
    }

    @Override
    void subscribeActual(RxObserver<? super R> observer) {
        source.subscribe(new RxMapObserver<>(observer, function));
    }

    private static class RxMapObserver<T, R> implements RxObserver<T> {
        RxObserver<? super R> actual;
        RxFunction<? super T, ? extends R> mapper;

        public RxMapObserver(RxObserver<? super R> observer, RxFunction<? super T, ? extends R> mapper) {
            this.actual = observer;
            this.mapper = mapper;
        }

        @Override
        public void onNext(T o) {
            R result = mapper.apply(o);
            actual.onNext(result);
        }

        @Override
        public void onError(Throwable throwable) {

        }

        @Override
        public void onComplete() {
            actual.onComplete();
        }
    }
}
