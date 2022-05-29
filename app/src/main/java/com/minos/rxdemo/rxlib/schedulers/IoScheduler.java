package com.minos.rxdemo.rxlib.schedulers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class IoScheduler extends RxScheduler{
    private ExecutorService executorService;

    public static IoScheduler getInstance() {
        return IoScheduler.holder;
    }

    private static IoScheduler holder = new IoScheduler();

    @Override
    public void schedule(Worker worker) {
        executorService().execute(worker);
    }

    public ExecutorService executorService() {
        if (executorService == null) {
            executorService = Executors.newScheduledThreadPool(5, new IoSchedulerFactory());
        }
        return executorService;
    }

    public class IoSchedulerFactory implements ThreadFactory{
        @Override
        public Thread newThread(Runnable r) {
            AtomicInteger atomicInteger = new AtomicInteger(1);
            Thread thread = new Thread(r, "RxIoSchedule " + atomicInteger.getAndIncrement());
            thread.setDaemon(true);
            return thread;
        }
    }
}
