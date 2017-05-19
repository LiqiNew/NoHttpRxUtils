package com.liqi.nohttputils;

import android.content.Context;

import com.liqi.nohttputils.nohttp.RxRequestConfig;
import com.liqi.nohttputils.nohttp.RxUtilsConfig;
import com.liqi.nohttputils.nohttp.rx_threadpool.RxMessageSource;
import com.liqi.nohttputils.nohttp.rx_threadpool.interfa.OnRxMessageSetListener;
import com.yanzhenjie.nohttp.NoHttp;

/**
 * NoHttpRxUtils网络请求静态调用对象
 * <p>
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

    /**
     * 取消Sign对应的网络请求
     *
     * @param sign
     */
    public static void cancel(Object sign) {
        getOnRxMessageSetListener().cancel(sign);
    }

    /**
     * 批量取消Sign对应的网络请求
     *
     * @param sign
     */
    public static void cancel(Object[] sign) {
        getOnRxMessageSetListener().cancel(sign);
    }

    /**
     * 取消全部网络请求
     */
    public static void cancelAll() {
        getOnRxMessageSetListener().cancelAll();
    }

    /**
     * 清除对应的key的缓存数据
     *
     * @param cacheKey 缓存KEY
     */
    public static void removeKeyCacheData(String cacheKey) {
        NoHttp.getCacheStore().remove(cacheKey);
    }

    /**
     * 清除所有缓存数据
     */
    public static void removeAllCacheData() {
        NoHttp.getCacheStore().clear();
    }

    private static OnRxMessageSetListener getOnRxMessageSetListener() {
        return RxMessageSource.getRxMessageSource();
    }
}
