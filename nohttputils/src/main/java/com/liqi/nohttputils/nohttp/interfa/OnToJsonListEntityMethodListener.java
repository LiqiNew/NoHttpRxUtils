package com.liqi.nohttputils.nohttp.interfa;

import android.support.annotation.NonNull;

import com.liqi.nohttputils.nohttp.RxRequestConfig;


/**
 * 请求Json集合实体暴露给外界调用方法
 * Created by LiQi on 2017/5/19.
 */

public interface OnToJsonListEntityMethodListener {
    /**
     * 添加对象参数
     *
     * @param keyParameter   对象里面的key
     * @param valueParameter 对象里面的value
     * @return
     */
    OnToJsonAddEntityMethodListener addObjectEntityParameter(String keyParameter, @NonNull Object valueParameter);

    /**
     * 切换对象到配置对象
     *
     * @return
     */
    RxRequestConfig.ConfigBuilder transitionToRequest();

    interface OnToJsonAddEntityMethodListener {
        /**
         * 往参数对象添加参数
         *
         * @param keyParameter   参数key
         * @param valueParameter 参数value
         * @return
         */
        OnToJsonAddEntityMethodListener addEntityParameter(String keyParameter, @NonNull Object valueParameter);

        /**
         * 把参数刷进list里面
         *
         * @return
         */
        OnToJsonListEntityMethodListener objectBrushIntoList();
    }
}

