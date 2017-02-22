package com.liqi.nohttputils.nohttp;

import android.content.Context;

/**
 * 初始化配置文件
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

    private RxUtilsConfig(Context ontext) {
        mContext = ontext;
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

    /**
     * RxUtilsConfig建筑创建对象
     */
    public static class ConfigBuilder {
        private static ConfigBuilder mConfigBuilder;
        private RxUtilsConfig mRxUtilsConfig;

        private ConfigBuilder(Context context) {
            mRxUtilsConfig = new RxUtilsConfig(context);
        }

        public static ConfigBuilder getConfigBuilder(Context context) {
            return mConfigBuilder = null == mConfigBuilder ? new ConfigBuilder(context) : mConfigBuilder;
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

        public ConfigBuilder setDebug(boolean isDebug) {
            mRxUtilsConfig.isDebug = isDebug;
            return mConfigBuilder;
        }

        public ConfigBuilder setDebugName(String debugName) {
            mRxUtilsConfig.mDebugName = debugName;
            return mConfigBuilder;
        }

        public void createInit() {
            RxRequestUtils.getRxRequestUtils().init(mRxUtilsConfig);
        }
    }
}
