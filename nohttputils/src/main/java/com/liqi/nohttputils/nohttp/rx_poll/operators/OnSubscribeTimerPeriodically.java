package com.liqi.nohttputils.nohttp.rx_poll.operators;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * 订阅间隔计时器对象
 * Created by LiQi on 2017/9/6.
 */

public final class OnSubscribeTimerPeriodically<V, T> implements Observable.OnSubscribe<T> {
    /**
     * 初始化加载延迟
     */
    final long initialDelay;
    /**
     * 轮询间隔时间
     */
    final long period;
    /**
     * 时间单位
     */
    final TimeUnit unit;
    /**
     * 订阅者线程线路
     */
    final Scheduler scheduler;
    /**
     * 可观察者事件监听器
     */
    private OnObserverEventListener<V, T> eventListener;
    /**
     * 可观察者线程线路-事件处理默认在子线程
     */
    private Scheduler eventScheduler = Schedulers.io();
    /**
     * 传输给被观察者接受的对象
     */
    private V transferValue;

    public OnSubscribeTimerPeriodically(long initialDelay, long period, TimeUnit unit, Scheduler scheduler) {
        this.initialDelay = initialDelay;
        this.period = period;
        this.unit = unit;
        this.scheduler = scheduler;
    }

    /**
     * 赋值传输给被观察者接受的对象
     *
     * @param transferValue 被观察者接受的对象
     */
    public void setTransferValue(V transferValue) {
        this.transferValue = transferValue;
    }

    /**
     * 设置事件执行线程线路
     *
     * @param eventScheduler 线程线路
     */
    public void setEventScheduler(Scheduler eventScheduler) {
        if (null != eventScheduler) {
            this.eventScheduler = eventScheduler;
        }
    }

    /**
     * 设置可观察者事件监听器
     *
     * @param eventListener 可观察者事件监听器
     */
    public void setOnObserverEventListener(OnObserverEventListener<V, T> eventListener) {
        this.eventListener = eventListener;
    }

    @Override
    public void call(final Subscriber<? super T> subscriber) {
        //子线程调度器执行
        final Scheduler.Worker worker = Schedulers.io().createWorker();
        subscriber.add(worker);
        //开启子线程调度器定时器执行
        worker.schedulePeriodically(new Action0() {
            @Override
            public void call() {
                try {
                    synchronized (OnSubscribeTimerPeriodically.this) {
                        //被观察事件处理的调度器
                        final Scheduler.Worker workerEvent = eventScheduler.createWorker();
                        workerEvent.schedule(new Action0() {
                            @Override
                            public void call() {
                                if (null != eventListener) {
                                    try {
                                        final T observerEvent = eventListener.onObserverEvent(transferValue);

                                        //观察者事件处理的调度器
                                        final Scheduler.Worker workerDispose = scheduler.createWorker();
                                        workerDispose.schedule(new Action0() {
                                            @Override
                                            public void call() {
                                                try {
                                                    subscriber.onNext(observerEvent);
                                                    awake();
                                                } catch (Throwable e) {
                                                    try {
                                                        worker.unsubscribe();
                                                        workerEvent.unsubscribe();
                                                        workerDispose.unsubscribe();
                                                        awake();
                                                    } finally {
                                                        Exceptions.throwOrReport(e, subscriber);
                                                    }
                                                }
                                            }
                                        });
                                    } catch (Throwable e) {
                                        try {
                                            worker.unsubscribe();
                                            workerEvent.unsubscribe();
                                            awake();
                                        } finally {
                                            Exceptions.throwOrReport(e, subscriber);
                                        }
                                    }
                                }
                            }
                        });
                        //当前线程休眠，等待"被观察者"事件逻辑处理完毕
                        OnSubscribeTimerPeriodically.this.wait();
                    }
                } catch (Throwable e) {
                    try {
                        worker.unsubscribe();
                    } finally {
                        Exceptions.throwOrReport(e, subscriber);
                    }
                }
            }

        }, initialDelay, period, unit);
    }

    /**
     * 观察者已经根据被观察者的动作做出相应处理后唤醒调度器定时器继续往下走
     */
    private void awake() {
        synchronized (OnSubscribeTimerPeriodically.this) {
            OnSubscribeTimerPeriodically.this.notify();
        }
    }
}
