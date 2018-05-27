package com.liqi.nohttputils.nohttp.rx_poll.interfa;

import com.liqi.nohttputils.nohttp.RxPollNoHttpConfig;
import com.liqi.nohttputils.nohttp.rx_poll.model.RxInformationModel;

import io.reactivex.functions.Predicate;

/**
 * RX轮询构建轮询配置类业务操作方法接口
 * Created by LiQi on 2017/9/13.
 */

public interface OnRxPollConfigBuilderListener<T> {
    /**
     * 设置设置数据拦截监听对象
     *
     * @param booleanFunc1 设置数据拦截监听对象
     * @return 构建轮询配置类
     */
    RxPollNoHttpConfig.ConfigBuilder<T> setBooleanFunc1(Predicate<RxInformationModel<T>> booleanFunc1);
}
