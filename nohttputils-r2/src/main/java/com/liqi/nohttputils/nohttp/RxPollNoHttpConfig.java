package com.liqi.nohttputils.nohttp;

import android.support.annotation.NonNull;

import com.liqi.nohttputils.interfa.OnRequestRxNoHttpListener;
import com.liqi.nohttputils.nohttp.rx_poll.RxPollUtils;
import com.liqi.nohttputils.nohttp.rx_poll.interfa.OnRxPollConfigBuilderListener;
import com.liqi.nohttputils.nohttp.rx_poll.model.RxInformationModel;
import com.liqi.nohttputils.nohttp.rx_poll.operators.OnObserverEventListener;
import com.yanzhenjie.nohttp.rest.Request;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * NoHttp轮询配置类
 * Created by LiQi on 2017/9/12.
 */

public class RxPollNoHttpConfig<T> {
    /**
     * 初始化加载延迟
     */
    private long mInitialDelay;
    /**
     * 轮询间隔时间-默认3秒
     */
    private long mPeriod = 3 * 1000;
    /**
     * 设置数据拦截监听对象
     */
    private Predicate<RxInformationModel<T>> mBooleanFunc1;
    /**
     * 被观察者产生的行为事件监听器
     */
    private OnObserverEventListener<Request<T>, RxInformationModel<T>> mOnObserverEventListener;
    /**
     * 观察者根据被观察产生的行为做出相应处理监听器
     */
    private Consumer<RxInformationModel<T>> mRxInformationModelAction1;
    /**
     * 网络请求参数对象
     */
    private RxRequestOperate<T> mRxRequestOperate;

    private RxPollNoHttpConfig() {
    }

    public long getInitialDelay() {
        return mInitialDelay;
    }

    public long getPeriod() {
        return mPeriod;
    }

    public Predicate<RxInformationModel<T>> getBooleanFunc1() {
        return mBooleanFunc1;
    }

    public RxRequestOperate<T> getRxRequestOperate() {
        return mRxRequestOperate;
    }

    public Consumer<RxInformationModel<T>> getRxInformationModelAction1() {
        return mRxInformationModelAction1;
    }

    public OnObserverEventListener<Request<T>, RxInformationModel<T>> getOnObserverEventListener() {
        return mOnObserverEventListener;
    }

    /**
     * 构建轮询配置类
     *
     * @param <T>
     */
    public static class ConfigBuilder<T> implements OnRxPollConfigBuilderListener<T>{
        private RxPollNoHttpConfig<T> mRxPollNoHttpConfig;

        private ConfigBuilder() {

        }

        private ConfigBuilder(@NonNull RxRequestOperate<T> requestOperate) {
            mRxPollNoHttpConfig = new RxPollNoHttpConfig<>();
            mRxPollNoHttpConfig.mRxRequestOperate = requestOperate;
        }

        public static <T> ConfigBuilder<T> getConfigBuilder(@NonNull RxRequestOperate<T> requestOperate) {
            return new ConfigBuilder<>(requestOperate);
        }

        /**
         * 设置初始化加载延迟
         *
         * @param initialDelay 初始化加载延迟 (时间单位：毫秒)
         * @return 构建轮询配置类
         */
        public ConfigBuilder<T> setInitialDelay(long initialDelay) {
            if (initialDelay >= 0) {
                mRxPollNoHttpConfig.mInitialDelay = initialDelay;
            }
            return this;
        }

        /**
         * 设置轮询间隔时间-默认3秒
         *
         * @param period 轮询间隔时间(时间单位：毫秒)
         * @return 构建轮询配置类
         */
        public ConfigBuilder<T> setPeriod(long period) {
            if (period >= 0) {
                mRxPollNoHttpConfig.mPeriod = period;
            }
            return this;
        }

        /**
         * 设置设置数据拦截监听对象
         *
         * @param booleanFunc1 设置数据拦截监听对象
         * @return 构建轮询配置类
         */
        public ConfigBuilder<T> setBooleanFunc1(Predicate<RxInformationModel<T>> booleanFunc1) {
            mRxPollNoHttpConfig.mBooleanFunc1 = booleanFunc1;
            return this;
        }

        /**
         * 设置观察者根据被观察产生的行为做出相应处理监听器
         *
         * @param rxInformationModelAction1 观察者根据被观察产生的行为做出相应处理监听器
         * @return 构建轮询配置类
         */
        public ConfigBuilder<T> setRxInformationModelAction1(Consumer<RxInformationModel<T>> rxInformationModelAction1) {
            mRxPollNoHttpConfig.mRxInformationModelAction1 = rxInformationModelAction1;
            return this;
        }

        /**
         * 设置被观察者产生的行为事件监听器
         *
         * @param onObserverEventListener 被观察者产生的行为事件监听器
         * @return 构建轮询配置类
         */
        public OnRxPollConfigBuilderListener<T> setOnObserverEventListener(OnObserverEventListener<Request<T>, RxInformationModel<T>> onObserverEventListener) {
            mRxPollNoHttpConfig.mOnObserverEventListener = onObserverEventListener;
            return this;
        }

        /**
         * 转换成轮询请求类
         *
         * @return 轮询开始请求类
         */
        public OnRequestRxNoHttpListener switchPoll() {
            return RxPollUtils.getRxPollUtilsNoHttpPoll(mRxPollNoHttpConfig);
        }
    }
}
