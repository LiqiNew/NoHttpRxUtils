package com.liqi.nohttputils.nohttp.rx_threadpool.interfa;

import android.support.annotation.NonNull;

import com.liqi.nohttputils.nohttp.rx_threadpool.RxMessageSource;
import com.liqi.nohttputils.nohttp.rx_threadpool.model.BaseRxRequestModel;


/**
 * 数据源共享对象外部操作暴露接口
 * Created by LiQi on 2017/3/21.
 */

public interface OnRxMessageSetListener<T extends BaseRxRequestModel> {
    /**
     * 取消指定标识运行线程
     *
     * @param sign 标识
     * @return
     */
    RxMessageSource cancel(Object sign);
    /**
     * 批量取消指定标识运行线程
     *
     * @param sign 标识
     * @return
     */
    RxMessageSource cancel(Object[] sign);
    /**
     * 取消全部运行线程
     *
     * @return
     */
    RxMessageSource cancelAll();

    /**
     * 把要处理的数据添加进共享数据源
     *
     * @param parameter 要处理的数据
     * @param sign      标识
     * @return
     */
    RxMessageSource add(@NonNull T parameter, Object sign);

    /**
     * 把要处理的数据添加进共享数据源
     *
     * @param parameter 要处理的数据
     * @return
     */
    RxMessageSource add(@NonNull T parameter);
}
