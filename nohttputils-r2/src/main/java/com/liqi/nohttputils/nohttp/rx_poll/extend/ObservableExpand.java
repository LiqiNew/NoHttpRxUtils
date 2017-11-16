package com.liqi.nohttputils.nohttp.rx_poll.extend;


import com.liqi.nohttputils.nohttp.rx_poll.operators.OnObserverEventListener;
import com.liqi.nohttputils.nohttp.rx_poll.operators.OnSubscribeTimerPeriodically;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;


/**
 * 继承rxjava-Observable扩展类
 * Created by LiQi on 2017/9/6.
 */

public class ObservableExpand {


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
     *
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
         * @param transferValue  待处理或者待传输的对象
         * @return
         */
        public Observable<T> subscribeOn(Scheduler eventScheduler, V transferValue) {

            OnSubscribeTimerPeriodically<V, T> timerPeriodically = new OnSubscribeTimerPeriodically<>(Math.max(0L, initialDelay), Math.max(0L, period), unit, Schedulers.computation());
            timerPeriodically.setOnObserverEventListener(observerEventListener);
            timerPeriodically.setTransferValue(transferValue);
            timerPeriodically.setEventScheduler(eventScheduler);
            return RxJavaPlugins.onAssembly(timerPeriodically);
        }
    }
}