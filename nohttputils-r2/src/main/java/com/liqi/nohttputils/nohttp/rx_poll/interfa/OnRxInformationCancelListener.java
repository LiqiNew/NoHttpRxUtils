package com.liqi.nohttputils.nohttp.rx_poll.interfa;

/**
 * RxJava轮询数据共享对象中取消轮询接口
 * Created by LiQi on 2017/9/11.
 */

public interface OnRxInformationCancelListener {
    /**
     * 取消指定标识轮询线程
     *
     * @param sign 标识
     */
    void cancel(Object sign);

    /**
     * 批量取消指定标识轮询线程
     *
     * @param sign 标识
     */
    void cancel(Object[] sign);

    /**
     * 取消全部轮询线程
     */
    void cancelAll();
}
