package com.liqi.nohttputils.nohttp;

import android.content.Context;

import com.liqi.nohttputils.interfa.DialogGetListener;
import com.yanzhenjie.nohttp.InitializationConfig;
import com.yanzhenjie.nohttp.cookie.DBCookieStore;

import java.io.InputStream;

import javax.net.ssl.HostnameVerifier;

/**
 * 初始化参数配置文件
 * Created by LiQi on 2017/2/22.
 */

public class RxUtilsConfig {
    /**
     * nohttp底层请求方式
     */
    private int mRxRequestUtilsWhy;
    /**
     * 设置全局连接超时时间，单位秒，默认30s。
     */
    private int mConnectTimeout = 30;
    /**
     * 设置全局服务器响应超时时间，单位秒，默认30s。
     */
    private int mReadTimeout = mConnectTimeout;
    /**
     * 配置缓存，是否缓存进数据库DBCacheStore
     */
    private boolean mDbEnable;
    /**
     * 配置Cookie，默认保存数据库DBCookieStore
     * 是否维护Cookie
     */
    private boolean mCookieEnable;
    /**
     * 是否debug打印
     */
    private boolean isDebug;
    /**
     * debug打印的名字
     */
    private String mDebugName = "NoHttpRxUtils";
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 网络请求全局加载框获取接口
     * (全项目请求默认加载框)
     */
    private DialogGetListener mDialogGetListener;
    /**
     * 下载线程池并发数量
     */
    private int mThreadPoolSize = 3;
    /**
     * 网络请求队列并发数量
     */
    private int mRunRequestSize = 3;
    /**
     * Cookie管理监听。
     */
    private DBCookieStore.CookieStoreListener mCookieStoreListener;
    /**
     * 是否使用安全协议通讯
     */
    private boolean isSSL;
    /**
     * 安全协议证书文件流
     */
    private InputStream mInputStreamSSL;
    /**
     * 主机验证
     */
    private HostnameVerifier mHostnameVerifier;
    /**
     * 全局重试次数，配置后每个请求失败都会重试设置的次数。
     */
    private int mRetry;
    /**
     * NoHttp初始化配置建筑类
     */
    private InitializationConfig.Builder mBuilder;

    private RxUtilsConfig(Context context) {
        mContext = context;
        mBuilder = InitializationConfig.newBuilder(context);
    }

     DialogGetListener getDialogGetListener() {
        return mDialogGetListener;
    }

     boolean isDebug() {
        return isDebug;
    }

     String getDebugName() {
        return mDebugName;
    }

     Context getContext() {
        return mContext;
    }

     int getRxRequestUtilsWhy() {
        return mRxRequestUtilsWhy;
    }

     int getConnectTimeout() {
        return mConnectTimeout * 1000;
    }

     int getReadTimeout() {
        return mReadTimeout * 1000;
    }

     boolean isDbEnable() {
        return mDbEnable;
    }

     boolean isCookieEnable() {
        return mCookieEnable;
    }

    public int getThreadPoolSize() {
        return mThreadPoolSize;
    }

     int getRunRequestSize() {
        return mRunRequestSize;
    }

     boolean isSSL() {
        return isSSL;
    }

     InputStream getInputStreamSSL() {
        return mInputStreamSSL;
    }

     HostnameVerifier getHostnameVerifier() {
        return mHostnameVerifier;
    }

     InitializationConfig.Builder getBuilder() {
        return mBuilder;
    }

     DBCookieStore.CookieStoreListener getCookieStoreListener() {
        return mCookieStoreListener;
    }

     int getRetry() {
        return mRetry;
    }

    /**
     * RxUtilsConfig建筑创建对象
     */
    public static class ConfigBuilder {
        private static ConfigBuilder mConfigBuilder;
        private RxUtilsConfig mRxUtilsConfig;

        ConfigBuilder(Context context) {
            mRxUtilsConfig = new RxUtilsConfig(context);
        }

        public static ConfigBuilder getConfigBuilder(Context context) {
            return mConfigBuilder = null == mConfigBuilder ? new ConfigBuilder(context) : mConfigBuilder;
        }

        /**
         * 获取RxUtilsConfig建筑创建对象
         *
         * @return
         */
        public static ConfigBuilder getConfigBuilder() {
            return mConfigBuilder;
        }

        /**
         * 获取RxUtilsConfig配置类
         *
         * @return
         */
        public RxUtilsConfig getRxUtilsConfig() {
            return mRxUtilsConfig;
        }

        /**
         * 设置底层请求方式
         *
         * @param rxUtilsConfig NoHttpInit.URLCONNECTION=>URLCONNECTION请求, NoHttpInit.OKHTTP=>OKHTTP请求
         * @return
         */
        public ConfigBuilder setRxRequestUtilsWhy(int rxUtilsConfig) {
            mRxUtilsConfig.mRxRequestUtilsWhy = rxUtilsConfig;
            return this;
        }

        /**
         * 设置全局连接超时时间，单位秒，默认30s。
         *
         * @param connectTimeout 全局连接超时时间，单位秒，默认30s。
         * @return
         */
        public ConfigBuilder setConnectTimeout(int connectTimeout) {
            mRxUtilsConfig.mConnectTimeout = connectTimeout;
            return this;
        }

        /**
         * 设置全局服务器响应超时时间，单位秒，默认30s。
         *
         * @param readTimeout 全局服务器响应超时时间，单位秒，默认30s。
         * @return
         */
        public ConfigBuilder setReadTimeout(int readTimeout) {
            mRxUtilsConfig.mReadTimeout = readTimeout;
            return this;
        }

        /**
         * 配置缓存，设置是否缓存进数据库DBCacheStore
         *
         * @param dbEnable 设置是否缓存进数据库DBCacheStore
         * @return
         */
        public ConfigBuilder setDbEnable(boolean dbEnable) {
            mRxUtilsConfig.mDbEnable = dbEnable;
            return this;
        }

        /**
         * 设置是否自动维护Cookie
         *
         * @param cookieEnable 是否自动维护Cookie. yes true,else false
         * @return
         */
        public ConfigBuilder setCookieEnable(boolean cookieEnable) {
            mRxUtilsConfig.mCookieEnable = cookieEnable;
            return this;
        }

        /**
         * 设置是否debug打印
         *
         * @param isDebug 是否debug打印。yes true,else false
         * @return
         */
        public ConfigBuilder isDebug(boolean isDebug) {
            mRxUtilsConfig.isDebug = isDebug;
            return this;
        }

        /**
         * 设置debug打印的名字
         *
         * @param debugName debug打印的名字
         * @return
         */
        public ConfigBuilder setDebugName(String debugName) {
            mRxUtilsConfig.mDebugName = debugName;
            return this;
        }

        /**
         * 设置全局网络请求队列并发数量
         *
         * @param runRequestSize 全局网络请求队列并发数量
         * @return
         */
        public ConfigBuilder setRunRequestSize(int runRequestSize) {
            mRxUtilsConfig.mRunRequestSize = runRequestSize;
            return this;
        }

        /**
         * 设置下载线程池并发数量
         *
         * @param threadPoolSize 下载线程池并发数量
         * @return
         */
        public ConfigBuilder setThreadPoolSize(int threadPoolSize) {
            mRxUtilsConfig.mThreadPoolSize = threadPoolSize;
            return this;
        }

        /**
         * 网络请求全局加载框获取接口
         *
         * @param dialogGetListener
         * @return
         */
        public ConfigBuilder setDialogGetListener(DialogGetListener dialogGetListener) {
            mRxUtilsConfig.mDialogGetListener = dialogGetListener;
            return this;
        }

        /**
         * 添加全局请求头
         *
         * @param key   请求头键
         * @param value 请求头值
         * @return
         */
        public ConfigBuilder addHeader(String key, String value) {
            mRxUtilsConfig.mBuilder.addHeader(key, value);
            return this;
        }

        /**
         * 添加全局请求参数-只支持String类型
         *
         * @param key   请求参数键
         * @param value 请求参数值
         * @return
         */
        public ConfigBuilder addParam(String key, String value) {
            mRxUtilsConfig.mBuilder.addParam(key, value);
            return this;
        }

        /**
         * 设置Cookie管理监听。
         *
         * @param cookieStoreListener Cookie管理监听
         * @return
         */
        public ConfigBuilder setCookieStoreListener(DBCookieStore.CookieStoreListener cookieStoreListener) {
            mRxUtilsConfig.mCookieStoreListener = cookieStoreListener;
            return this;
        }

        /**
         * 设置有安全协议证书文件流安全通讯
         *
         * @param inputStreamSSL 安全协议证书文件流
         * @return
         */
        public ConfigBuilder setInputStreamSSL(InputStream inputStreamSSL) {
            mRxUtilsConfig.isSSL = true;
            mRxUtilsConfig.mInputStreamSSL = inputStreamSSL;
            return this;
        }

        /**
         * 设置无安全协议证书文件流安全通讯
         *
         * @return
         */
        public ConfigBuilder setInputStreamSSL() {
            mRxUtilsConfig.isSSL = true;
            return this;
        }

        /**
         * 设置主机验证
         *
         * @param hostnameVerifier 主机验证
         * @return
         */
        public ConfigBuilder setHostnameVerifier(HostnameVerifier hostnameVerifier) {
            mRxUtilsConfig.isSSL = true;
            mRxUtilsConfig.mHostnameVerifier = hostnameVerifier;
            return this;
        }

        /**
         * 设置全局重试次数，配置后每个请求失败都会重试设置的次数。
         * @param retry 全局重试次数
         * @return
         */
        public ConfigBuilder setRetry(int retry) {
            mRxUtilsConfig.mRetry=retry;
            return this;
        }
        /**
         * 开始初始化
         */
        public void startInit() {
            NoHttpInit.getNoHttpInit().init(mRxUtilsConfig);
        }
    }
}
