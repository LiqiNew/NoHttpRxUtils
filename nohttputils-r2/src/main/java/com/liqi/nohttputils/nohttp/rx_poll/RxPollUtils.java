package com.liqi.nohttputils.nohttp.rx_poll;

import android.support.annotation.NonNull;

import com.liqi.nohttputils.interfa.OnRequestRxNoHttpListener;
import com.liqi.nohttputils.nohttp.RxPollNoHttpConfig;
import com.liqi.nohttputils.nohttp.RxRequestConfig;
import com.liqi.nohttputils.nohttp.RxRequestOperate;
import com.liqi.nohttputils.nohttp.rx_poll.extend.ObservableExpand;
import com.liqi.nohttputils.nohttp.rx_poll.model.RxInformationModel;
import com.liqi.nohttputils.nohttp.rx_poll.model.RxInformationPoolModel;
import com.liqi.nohttputils.nohttp.rx_poll.operators.OnObserverEventListener;
import com.liqi.nohttputils.nohttp.rx_poll.pond.RxInformationPool;
import com.yanzhenjie.nohttp.rest.RestRequest;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;


/**
 * RxJava轮询控制类
 * Created by LiQi on 2017/9/12.
 */

public class RxPollUtils<T> implements OnRequestRxNoHttpListener {

    private RxPollNoHttpConfig<T> mRxPollNoHttpConfig;

    private RxPollUtils() {

    }

    private RxPollUtils(@NonNull RxPollNoHttpConfig<T> rxPollNoHttpConfig) {
        mRxPollNoHttpConfig = rxPollNoHttpConfig;
    }

    /**
     * 获取NoHttp轮询操作接口
     *
     * @param rxPollNoHttpConfig NoHttp轮询配置类
     * @param <T>
     * @return NoHttp轮询操作接口
     */
    public static <T> OnRequestRxNoHttpListener getRxPollUtilsNoHttpPoll(@NonNull RxPollNoHttpConfig<T> rxPollNoHttpConfig) {
        return new RxPollUtils<>(rxPollNoHttpConfig);
    }

    @Override
    public void requestRxNoHttp() {
        RxRequestOperate<T> rxRequestOperate = mRxPollNoHttpConfig.getRxRequestOperate();

        RxRequestConfig<T> rxRequestConfig = rxRequestOperate.getRxRequestConfig();

        RxInformationPoolModel<T> informationPoolModel = new RxInformationPoolModel<>(
                rxRequestConfig.getOnIsRequestListener(),
                rxRequestConfig.getDialogGetListener(),
                rxRequestConfig.getAnUnknownErrorHint());
        Object sign = rxRequestConfig.getSign();
        if (null != sign) {
            informationPoolModel.setSign(sign);
        }
        //赋值拦截器
        Predicate<RxInformationModel<T>> booleanFunc1 = mRxPollNoHttpConfig.getBooleanFunc1();
        if (null != booleanFunc1) {
            informationPoolModel.setBooleanFunc1(booleanFunc1);
        }

        //设置观察者相应处理事件
        Consumer<RxInformationModel<T>> rxInformationModelAction1 = mRxPollNoHttpConfig.getRxInformationModelAction1();
        if (null != rxInformationModelAction1) {
            informationPoolModel.setRxInformationModelAction1(rxInformationModelAction1);
        }

        //赋值被观察者处理事件
        OnObserverEventListener<RestRequest<T>, RxInformationModel<T>> onObserverEventListener = mRxPollNoHttpConfig.getOnObserverEventListener();
        if (null != onObserverEventListener) {
            informationPoolModel.setOnObserverEventListener(onObserverEventListener);
        }
        //如果没有实现被观察者处理事件就内部管理
        else{
            RxInformationPool.getRxInformationPoolList().add(informationPoolModel);
        }

        //开启rxJava扩展轮询
        ObservableExpand.intervalPolling(mRxPollNoHttpConfig.getInitialDelay(),
                mRxPollNoHttpConfig.getPeriod(),
                TimeUnit.MILLISECONDS,
                informationPoolModel.getOnObserverEventListener())
                .subscribeOn(Schedulers.io(), rxRequestOperate.getRestRequest())
                .takeUntil(informationPoolModel.getBooleanFunc1())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(informationPoolModel.getRxInformationModelAction1());
    }
}
