package com.liqi.nohttputils.nohttp.rx_poll.operators;

import com.yanzhenjie.nohttp.Logger;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.schedulers.Schedulers;


/**
 * 订阅者间隔计时器(轮询)对象
 * Created by LiQi on 2017/9/6.
 */

public final class OnSubscribeTimerPeriodically<V, T> extends Observable<T> {
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
    private Disposable disposable;

    public OnSubscribeTimerPeriodically(long initialDelay, long period, TimeUnit unit, Scheduler scheduler) {
        this.initialDelay = initialDelay;
        this.period = period;
        this.unit = unit;
        this.scheduler = scheduler;
    }

    private static void toThrowableString(Throwable e) {
        Logger.e("Expand-轮询异常捕获：" + e.toString());
        StackTraceElement[] stackTrace = e.getStackTrace();
        if (null != stackTrace) {
            for (StackTraceElement traceElement : stackTrace) {
                Logger.e("Expand-轮询异常捕获：" + traceElement.toString());
            }
        }
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

    /**
     * 观察者已经根据被观察者的动作做出相应处理后唤醒调度器定时器继续往下走
     */
    private void awake() {
        synchronized (OnSubscribeTimerPeriodically.this) {
            OnSubscribeTimerPeriodically.this.notify();
        }
    }

    @Override
    protected void subscribeActual(Observer<? super T> observer) {

        final Observer<? super T> observerNew = observer;

        //子线程调度器执行
        final Scheduler.Worker worker = Schedulers.io().createWorker();

        IntervalObserver is = new IntervalObserver() {

            @Override
            void runStart() throws Exception {
                synchronized (OnSubscribeTimerPeriodically.this) {
                    //被观察事件处理的调度器
                    final Scheduler.Worker workerEvent = eventScheduler.createWorker();
                    workerEvent.schedule(new Runnable() {
                        @Override
                        public void run() {
                            if (null != eventListener) {
                                try {
                                    final T observerEvent = eventListener.onObserverEvent(transferValue);

                                    //观察者事件处理的调度器
                                    final Scheduler.Worker workerDispose = scheduler.createWorker();
                                    workerDispose.schedule(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                observerNew.onNext(observerEvent);
                                                end();
                                            } catch (Throwable e) {
                                                disposable.dispose();
                                                disposable = null;
                                                eventScheduler = null;
                                                eventListener = null;
                                                end();
                                                toThrowableString(e);
                                            }
                                        }
                                    });
                                } catch (Throwable e) {
                                    disposable.dispose();
                                    disposable = null;
                                    eventScheduler = null;
                                    eventListener = null;
                                    end();
                                    toThrowableString(e);
                                }
                            }
                        }
                    });
                    //当前线程休眠，等待"被观察者"事件逻辑处理完毕
                    OnSubscribeTimerPeriodically.this.wait();
                }
            }
        };
        observerNew.onSubscribe(is);
        disposable = worker.schedulePeriodically(is, initialDelay, period, unit);
    }

    private void end() {
        awake();
        System.gc();
    }

    private static abstract class IntervalObserver
            extends AtomicReference<Disposable>
            implements Disposable, Runnable {


        private static final long serialVersionUID = 346773832286157679L;

        private IntervalObserver() {
        }

        @Override
        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override
        public boolean isDisposed() {
            return get() == DisposableHelper.DISPOSED;
        }

        @Override
        public void run() {
            if (get() != DisposableHelper.DISPOSED) {
                try {
                    runStart();
                } catch (Exception e) {
                    toThrowableString(e);
                }
            }
        }


        abstract void runStart() throws Exception;
    }
}
