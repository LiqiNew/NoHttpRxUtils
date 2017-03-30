package com.liqi.nohttputils.nohttp;

import android.content.Context;

import com.liqi.nohttputils.interfa.DialogGetListener;

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
     * 设置全局连接超时时间，单位毫秒，默认30s。
     */
    private int mConnectTimeout = 30 * 1000;
    /**
     * 设置全局服务器响应超时时间，单位毫秒，默认30s。
     */
    private int mReadTimeout = 30 * 1000;
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
    private String mDebugName = "NohttpRxUtils";
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
     * 指定下载线程池并发数量
     */
    private int mThreadPoolSize = 3;
    /**
     * 网络请求队列并发数量
     */
    private int mRunRequestSize = 3;

    private RxUtilsConfig(Context ontext) {
        mContext = ontext;
    }

    public DialogGetListener getDialogGetListener() {
        return mDialogGetListener;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public String getDebugName() {
        return mDebugName;
    }

    public Context getContext() {
        return mContext;
    }

    public int getRxRequestUtilsWhy() {
        return mRxRequestUtilsWhy;
    }

    public int getConnectTimeout() {
        return mConnectTimeout;
    }

    public int getReadTimeout() {
        return mReadTimeout;
    }

    public boolean isDbEnable() {
        return mDbEnable;
    }

    public boolean isCookieEnable() {
        return mCookieEnable;
    }

    public int getThreadPoolSize() {
        return mThreadPoolSize;
    }

    public int getRunRequestSize() {
        return mRunRequestSize;
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

        public static ConfigBuilder getConfigBuilder() {
            return mConfigBuilder;
        }

        public RxUtilsConfig getRxUtilsConfig() {
            return mRxUtilsConfig;
        }

        public ConfigBuilder setRxRequestUtilsWhy(int rxUtilsConfig) {
            mRxUtilsConfig.mRxRequestUtilsWhy = rxUtilsConfig;
            return mConfigBuilder;
        }

        public ConfigBuilder setConnectTimeout(int connectTimeout) {
            mRxUtilsConfig.mConnectTimeout = connectTimeout;
            return mConfigBuilder;
        }

        public ConfigBuilder setReadTimeout(int readTimeout) {
            mRxUtilsConfig.mReadTimeout = readTimeout;
            return mConfigBuilder;
        }

        public ConfigBuilder setDbEnable(boolean dbEnable) {
            mRxUtilsConfig.mDbEnable = dbEnable;
            return mConfigBuilder;
        }

        public ConfigBuilder setCookieEnable(boolean cookieEnable) {
            mRxUtilsConfig.mCookieEnable = cookieEnable;
            return mConfigBuilder;
        }

        public ConfigBuilder isDebug(boolean isDebug) {
            mRxUtilsConfig.isDebug = isDebug;
            return mConfigBuilder;
        }

        public ConfigBuilder setDebugName(String debugName) {
            mRxUtilsConfig.mDebugName = debugName;
            return mConfigBuilder;
        }

        public ConfigBuilder setRunRequestSize(int runRequestSize) {
            mRxUtilsConfig.mRunRequestSize = runRequestSize;
            return mConfigBuilder;
        }

        public ConfigBuilder setThreadPoolSize(int threadPoolSize) {
            mRxUtilsConfig.mThreadPoolSize = threadPoolSize;
            return mConfigBuilder;
        }

        /**
         * 网络请求全局加载框获取接口
         *
         * @param dialogGetListener
         * @return
         */
        public ConfigBuilder setDialogGetListener(DialogGetListener dialogGetListener) {
            mRxUtilsConfig.mDialogGetListener = dialogGetListener;
            return mConfigBuilder;
        }

        public void startInit() {
            NoHttpInit.getNoHttpInit().init(mRxUtilsConfig);
        }
    }
}
