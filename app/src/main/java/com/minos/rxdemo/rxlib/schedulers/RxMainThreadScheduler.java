package com.minos.rxdemo.rxlib.schedulers;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class RxMainThreadScheduler extends RxScheduler{
    private Handler mHandler = new Handler(Looper.getMainLooper());

    public static RxMainThreadScheduler getInstance() {
        return RxMainThreadScheduler.Instance;
    }

    private static RxMainThreadScheduler Instance = new RxMainThreadScheduler();

    @Override
    public void schedule(Worker worker) {
        Message message = Message.obtain(mHandler, worker);
        mHandler.sendMessage(message);
    }
}
