package com.minos.rxdemo;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.minos.rxdemo.rxlib.RxEmitter;
import com.minos.rxdemo.rxlib.RxObservable;
import com.minos.rxdemo.rxlib.RxObservableOnSubscribe;
import com.minos.rxdemo.rxlib.RxObserver;
import com.minos.rxdemo.rxlib.schedulers.RxSchedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        textView.setText(R.string.test);
        setContentView(textView);

        RxObservable.create((RxObservableOnSubscribe<Integer>) emitter -> {
                emitter.onNext(123);
                emitter.onComplete();})
                .flatmap(data -> RxObservable.create(new RxObservableOnSubscribe<String>() {
                @Override
                public void subscribe(RxEmitter<String> emitter) {
                    emitter.onNext("hello" + data);
                    emitter.onNext("world" + data);
                    Log.d(TAG, " observable thread = " + Thread.currentThread().getName());
                }})).subscribeOn(RxSchedulers.IO)
                .observerOn(RxSchedulers.MAIN)
                .subscribe(new RxObserver<String>() {
                      @Override
                      public void onNext(String data) {
                          Log.d(TAG, " onNext data = " + data);
                      }

                      @Override
                      public void onError(Throwable throwable) {
                          Log.d(TAG, " onError throwable");
                      }

                      @Override
                      public void onComplete() {
                          Log.d(TAG, " onComplete ");
                          Log.d(TAG, " observer thread = " + Thread.currentThread().getName());
                      }
                });
    }
}
