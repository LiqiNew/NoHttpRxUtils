package com.liqi.nohttputils.nohttp;

import android.content.Context;

import com.liqi.nohttputils.interfa.DialogGetListener;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NetworkExecutor;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.OkHttpNetworkExecutor;
import com.yanzhenjie.nohttp.URLConnectionNetworkExecutor;
import com.yanzhenjie.nohttp.cache.DBCacheStore;
import com.yanzhenjie.nohttp.cookie.DBCookieStore;

/**
 * nohttp初始化辅助对象
 * Created by LiQi on 2017/3/7.
 */

public class NoHttpInit {
    //URLCONNECTION请求标识
    public static final int URLCONNECTION = -0X1;
    //OKHTTP请求标识
    public static final int OKHTTP = -0X2;

    private static NoHttpInit mNoHttpInit;
    /**
     * 加载框获取接口
     */
    private DialogGetListener mDialogGetListener;

    public DialogGetListener getDialogGetListener() {
        return mDialogGetListener;
    }

    private NoHttpInit() {

    }

    static NoHttpInit getNoHttpInit() {
        return mNoHttpInit = null == mNoHttpInit ? new NoHttpInit() : mNoHttpInit;
    }
    /**
     * 自定义初始化nohttp
     *
     * @return
     */
    void init(RxUtilsConfig rxUtilsConfig) {
        // nohttp默认设置的初始化
        //NoHttp.initialize(context);
        if (null != rxUtilsConfig) {
            Context context = rxUtilsConfig.getContext();
            NetworkExecutor networkExecutor = null;
            switch (rxUtilsConfig.getRxRequestUtilsWhy()) {
                case URLCONNECTION:
                    networkExecutor = new URLConnectionNetworkExecutor();
                    break;
                case OKHTTP:
                    networkExecutor = new OkHttpNetworkExecutor();
                    break;
                default:
                    networkExecutor = new OkHttpNetworkExecutor();
                    break;
            }
            // 自定义配置初始化：
            NoHttp.initialize(context, new NoHttp.Config()
                    // 设置全局连接超时时间，单位毫秒，默认10s。
                    .setConnectTimeout(rxUtilsConfig.getConnectTimeout())
                    // 设置全局服务器响应超时时间，单位毫秒，默认10s。
                    .setReadTimeout(rxUtilsConfig.getReadTimeout())
                    // 配置缓存，默认保存数据库DBCacheStore，保存到SD卡使用DiskCacheStore。
                    .setCacheStore(
                            new DBCacheStore(context).setEnable(rxUtilsConfig.isDbEnable()) // 如果不使用缓存，设置false禁用。
                    )
                    // 配置Cookie，默认保存数据库DBCookieStore，开发者可以自己实现。
                    .setCookieStore(
                            new DBCookieStore(context).setEnable(rxUtilsConfig.isCookieEnable()) // 如果不维护cookie，设置false禁用。
                    )
                    // 配置网络层，默认使用URLConnection，如果想用OkHttp：OkHttpNetworkExecutor。
                    .setNetworkExecutor(networkExecutor)
            );
            Logger.setDebug(rxUtilsConfig.isDebug());// 开启NoHttp的调试模式, 配置后可看到请求过程、日志和错误信息。
            Logger.setTag(rxUtilsConfig.getDebugName());// 设置NoHttp打印Log的tag。
            mDialogGetListener=rxUtilsConfig.getDialogGetListener();
        }
    }
}
