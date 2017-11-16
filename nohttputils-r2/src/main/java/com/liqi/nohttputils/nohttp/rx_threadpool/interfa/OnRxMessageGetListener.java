package com.liqi.nohttputils.nohttp.rx_threadpool.interfa;


import com.liqi.nohttputils.nohttp.rx_threadpool.model.BaseRxRequestModel;

import java.util.List;

/**
 * 数据源共享对象对内暴露接口
 * Created by LiQi on 2017/3/21.
 */

public interface OnRxMessageGetListener<T extends BaseRxRequestModel> {
    /**
     * 获取共享数据源
     *
     * @return
     */
    List<T> getList();

    /**
     * 获取共享数据源数据
     *
     * @param index
     * @return
     */
    T get(int index);

    /**
     * 删除共享数据源数据
     *
     * @param indxe
     */
    void delete(int indxe);

    /**
     * 获取共享数据源长度
     *
     * @return
     */
    int size();
}
