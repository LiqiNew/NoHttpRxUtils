package com.liqi.nohttputils.nohttp.rx_poll.interfa;


import com.liqi.nohttputils.nohttp.rx_poll.model.RxInformationPoolModel;

/**
 * RxJava轮询数据共享对象中list操作接口
 * Created by LiQi on 2017/9/11.
 */

public interface OnRxInformationListListener {
    /**
     * 数据添加
     *
     * @param informationPoolModel rxJava轮询操作数据源对象
     * @param <T>
     */
    <T> void add(RxInformationPoolModel<T> informationPoolModel);

    /**
     * 根据索引获取listview中指定对象
     *
     * @param index 索引
     * @return rxJava轮询操作数据源对象
     */
    RxInformationPoolModel get(int index);

    /**
     * 获取list长度
     *
     * @return
     */
    int size();

    /**
     * 根据索引删除list指定数据
     *
     * @param index 索引
     */
    void delete(int index);
}
