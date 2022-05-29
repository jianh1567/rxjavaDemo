package com.minos.rxdemo.rxlib;

public class RxObservableCreate<T> extends RxObservable<T>{
    public RxObservableOnSubscribe<T> source;

    public RxObservableCreate(RxObservableOnSubscribe<T> source) {
        this.source = source;
    }

    @Override
    void subscribeActual(RxObserver<? super T> observer) {
        EmitterImpl<T> emitter = new EmitterImpl<>(observer);
        source.subscribe(emitter);
    }

    static class EmitterImpl<T> implements RxEmitter<T> {
        RxObserver<? super T> observer;

        public EmitterImpl(RxObserver<? super T> observer) {
            this.observer = observer;
        }

        @Override
        public void onNext(T t) {
            observer.onNext(t);
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
