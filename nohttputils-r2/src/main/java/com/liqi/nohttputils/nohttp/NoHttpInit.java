package com.liqi.nohttputils.nohttp;

import android.content.Context;

import com.liqi.nohttputils.interfa.DialogGetListener;
import com.yanzhenjie.nohttp.InitializationConfig;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NetworkExecutor;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.OkHttpNetworkExecutor;
import com.yanzhenjie.nohttp.URLConnectionNetworkExecutor;
import com.yanzhenjie.nohttp.cache.DBCacheStore;
import com.yanzhenjie.nohttp.cookie.DBCookieStore;
import com.yanzhenjie.nohttp.ssl.SSLUtils;

import java.io.InputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

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

    private NoHttpInit() {

    }

    static NoHttpInit getNoHttpInit() {
        return mNoHttpInit = null == mNoHttpInit ? new NoHttpInit() : mNoHttpInit;
    }

    public DialogGetListener getDialogGetListener() {
        return mDialogGetListener;
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
            //Cookie管理监听。
            DBCookieStore.CookieStoreListener cookieStoreListener = rxUtilsConfig.getCookieStoreListener();
            DBCookieStore dbCookieStore = new DBCookieStore(context);
            if (null != cookieStoreListener) {
                dbCookieStore.setCookieStoreListener(cookieStoreListener);
            }

            //网络请求方式
            NetworkExecutor networkExecutor;
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

            InitializationConfig.Builder builder = rxUtilsConfig.getBuilder()
                    // nohttp底层注释=>设置全局连接超时时间，单位毫秒，默认10s。
                    .connectionTimeout(rxUtilsConfig.getConnectTimeout())
                    // nohttp底层注释=>设置全局服务器响应超时时间，单位毫秒，默认10s。
                    .readTimeout(rxUtilsConfig.getReadTimeout())
                    //（此处只支持缓存到数据库中） nohttp底层注释=>配置缓存，默认保存数据库DBCacheStore，保存到SD卡使用DiskCacheStore。
                    .cacheStore(new DBCacheStore(context).setEnable(rxUtilsConfig.isDbEnable()))
                    // （此处只支持保存到数据库中)nohttp底层注释=>配置Cookie，默认保存数据库DBCookieStore，开发者可以自己实现。如果不使用缓存，设置false禁用。
                    .cookieStore(dbCookieStore.setEnable(rxUtilsConfig.isCookieEnable()))
                    // 配置网络层，默认使用URLConnection，如果想用OkHttp：OkHttpNetworkExecutor。
                    .networkExecutor(networkExecutor)
                    .retry(rxUtilsConfig.getRetry());

            //是否可以使用SSL安全通讯
            boolean ssl = rxUtilsConfig.isSSL();
            if (ssl) {
                InputStream inputStreamSSL = rxUtilsConfig.getInputStreamSSL();

                SSLSocketFactory socketFactory;
                //是否有证书
                if (null != inputStreamSSL) {
                    socketFactory = SSLContextUtil.getSSLContext(inputStreamSSL).getSocketFactory();
                } else {
                    socketFactory =SSLContextUtil.getDefaultSLLContext().getSocketFactory();
                }
                builder.sslSocketFactory(socketFactory);
                //主机名验证
                HostnameVerifier hostnameVerifier = rxUtilsConfig.getHostnameVerifier();
                if (null != hostnameVerifier) {
                    builder.hostnameVerifier(hostnameVerifier);
                } else {
                    builder.hostnameVerifier(SSLContextUtil.HOSTNAME_VERIFIER);
                }
            }

            // 自定义配置初始化：
            NoHttp.initialize(builder.build());
            Logger.setDebug(rxUtilsConfig.isDebug());// 开启NoHttp的调试模式, 配置后可看到请求过程、日志和错误信息。
            Logger.setTag(rxUtilsConfig.getDebugName());// 设置NoHttp打印Log的tag。
            mDialogGetListener = rxUtilsConfig.getDialogGetListener();
        }
    }
}
