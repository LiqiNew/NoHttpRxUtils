package com.liqi.nohttputils.nohttp;

import android.content.Context;

/**
 * NoHttpRxUtils网络请求静态调用对象
 *
 * 个人QQ:543945827
 * Nohttp作者群号：46523908
 * Created by LiQi on 2017/3/7.
 */

public class RxNoHttpUtils {
    /**
     * 获取nohttp初始化对象
     *
     * @param context
     * @return
     */
    public static RxUtilsConfig.ConfigBuilder rxNoHttpInit(Context context) {
        return RxUtilsConfig.ConfigBuilder.getConfigBuilder(context);
    }

    /**
     * 获取nohttprx网络请求配置对象
     *
     * @return
     */
    public static RxRequestConfig.ConfigBuilder rxNohttpRequest() {
        return new RxRequestConfig.ConfigBuilder();
    }
}
