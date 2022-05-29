package com.minos.rxdemo.rxlib.schedulers;

public abstract class RxScheduler {

    public abstract void schedule(Worker worker);

    public abstract static class Worker implements Runnable{
        @Override
        public void run() {
            execute();
        }

        protected abstract void execute();
    }
}
