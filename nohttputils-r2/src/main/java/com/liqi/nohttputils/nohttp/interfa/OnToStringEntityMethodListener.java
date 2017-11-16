package com.liqi.nohttputils.nohttp.interfa;

import android.support.annotation.NonNull;

import com.liqi.nohttputils.nohttp.RxRequestEntityBase;

/**
 * 请求String实体暴露给外界调用方法
 * Created by LiQi on 2017/5/19.
 */

public interface OnToStringEntityMethodListener {
    /**
     * 添加String进请求实体
     *
     * @param stringEntity String类型值
     * @return
     */
    RxRequestEntityBase addStringEntityParameter(@NonNull String stringEntity);
}
