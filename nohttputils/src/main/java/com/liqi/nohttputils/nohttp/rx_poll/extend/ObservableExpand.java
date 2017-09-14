package com.liqi.nohttputils.nohttp.rx_poll.extend;


import android.support.annotation.NonNull;

import com.liqi.nohttputils.nohttp.rx_poll.operators.OnObserverEventListener;
import com.liqi.nohttputils.nohttp.rx_poll.operators.OnSubscribeTimerPeriodically;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import rx.plugins.RxJavaHooks;
import rx.schedulers.Schedulers;

/**
 * 继承rxjava-Observable扩展类
 * Created by LiQi on 2017/9/6.
 */

public class ObservableExpand<V, T> extends Observable<T> {

    final OnSubscribeTimerPeriodically<V, T> onSubscribeTimerPeriodically;

    /**
     * Creates an Observable with a Function to execute when it is subscribed to.
     * <p>
     * <em>Note:</em> Use {@link #create(OnSubscribe)} to create an Observable, instead of this constructor,
     * unless you specifically have a need for inheritance.
     *
     * @param f {@link OnSubscribe} to be executed when {@link #subscribe(Subscriber)} is called
     */
    protected ObservableExpand(OnSubscribe<T> f, OnSubscribeTimerPeriodically<V, T> onSubscribe1) {
        super(f);
        onSubscribeTimerPeriodically = onSubscribe1;
    }


    /**
     * 轮询间隔方法
     *
     * @param initialDelay          初始化加载延迟
     * @param period                轮询间隔时间
     * @param unit                  时间单位
     * @param observerEventListener 可观察者事件监听器
     * @param <T>
     * @return 订阅间隔计时Builder
     */
    public static <V, T> Builder<V, T> intervalPolling(long initialDelay, long period, TimeUnit unit, OnObserverEventListener<V, T> observerEventListener) {

        return new Builder<>(initialDelay, period, unit, observerEventListener);
    }

    /**
     * 订阅间隔计时Builder
     * @param <V>
     * @param <T>
     */
    public static class Builder<V, T> {
        /**
         * 初始化加载延迟
         */
        private long initialDelay;
        /**
         * 轮询间隔时间
         */
        private long period;
        /**
         * 时间单位
         */
        private TimeUnit unit;
        /**
         * 可观察者事件监听器
         */
        private OnObserverEventListener<V, T> observerEventListener;

        public Builder(long initialDelay, long period, TimeUnit unit, OnObserverEventListener<V, T> observerEventListener) {
            this.initialDelay = initialDelay;
            this.period = period;
            this.unit = unit;
            this.observerEventListener = observerEventListener;
        }

        /**
         * 设置可观察者监听器线程线路
         *
         * @param eventScheduler 线程线路
         * @param transferValue  传输给被观察者对象
         * @return
         */
        public ObservableExpand<V, T> subscribeOn(Scheduler eventScheduler, @NonNull V transferValue) {

            OnSubscribeTimerPeriodically<V, T> timerPeriodically = new OnSubscribeTimerPeriodically<>(initialDelay, period, unit, Schedulers.computation());
            timerPeriodically.setOnObserverEventListener(observerEventListener);
            timerPeriodically.setTransferValue(transferValue);
            timerPeriodically.setEventScheduler(eventScheduler);
            return new ObservableExpand<>(RxJavaHooks.onCreate(timerPeriodically), timerPeriodically);
        }
    }
}
