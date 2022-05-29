package com.minos.rxdemo.rxlib.schedulers;

public class RxSchedulers{
    public static RxScheduler IO = IoScheduler.getInstance();
    public static RxScheduler MAIN = RxMainThreadScheduler.getInstance();
}
