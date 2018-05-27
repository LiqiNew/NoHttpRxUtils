package com.liqi.nohttputils.nohttp;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.liqi.nohttputils.interfa.DialogGetListener;
import com.liqi.nohttputils.interfa.OnIsRequestListener;
import com.liqi.nohttputils.interfa.OnRequestRxNoHttpListener;
import com.liqi.nohttputils.nohttp.interfa.OnToInputStreamEntityMethodListener;
import com.liqi.nohttputils.nohttp.interfa.OnToJsonListEntityMethodListener;
import com.liqi.nohttputils.nohttp.interfa.OnToJsonObjectEntityMethodListener;
import com.liqi.nohttputils.nohttp.interfa.OnToStringEntityMethodListener;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.CacheMode;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 网络请求参数配置对象
 * Created by LiQi on 2017/3/7.
 */

public class RxRequestConfig<T> {
    private RequestMethod mRequestMethod;
    private String mUrl;
    private Map<String, Object> mParameterMap;
    private Map<String, String> mMapHeader;
    private int mMaxWidth = -1;
    private int mMaxHeight = -1;
    private Bitmap.Config mDecodeConfig;
    private ImageView.ScaleType mScaleType;
    private DialogGetListener mDialogGetListener;
    private OnIsRequestListener<T> mOnIsRequestListener;
    private Class<T> mClazz;
    private Object mSign;
    private boolean isQueue = true;
    private int mConnectTimeout = -1;
    private int mReadTimeout = -1;
    private int mRetryCount = -1;
    private String mCacheKey;
    private CacheMode mCacheMode = CacheMode.DEFAULT;
    private RxRequestEntityBase mRxRequestEntityBase;
    private String mAnUnknownErrorHint;

    private RxRequestConfig() {

    }

    RxRequestConfig(Class<T> clazz, OnIsRequestListener<T> onIsRequestListener) {
        mClazz = clazz;
        mOnIsRequestListener = onIsRequestListener;
    }

   public String getAnUnknownErrorHint() {
        return mAnUnknownErrorHint;
    }

    RxRequestEntityBase getRxRequestEntityBase() {
        return mRxRequestEntityBase;
    }

    String getCacheKey() {
        return mCacheKey;
    }

    CacheMode getCacheMode() {
        return mCacheMode;
    }

    int getRetryCount() {
        return mRetryCount;
    }

    int getConnectTimeout() {
        if (mConnectTimeout > 0) {
            return mConnectTimeout * 1000;
        } else {
            return mConnectTimeout;
        }
    }

    int getReadTimeout() {
        if (mReadTimeout > 0) {
            return mReadTimeout * 1000;
        } else {
            return mReadTimeout;
        }
    }

    String getUrl() {
        return mUrl;
    }

    RequestMethod getRequestMethod() {
        return mRequestMethod;
    }

    Map<String, Object> getParameterMap() {
        return mParameterMap;
    }

    Map<String, String> getMapHeader() {
        return mMapHeader;
    }

    int getMaxWidth() {
        return mMaxWidth;
    }

    int getMaxHeight() {
        return mMaxHeight;
    }

    Bitmap.Config getDecodeConfig() {
        return mDecodeConfig;
    }

    ImageView.ScaleType getScaleType() {
        return mScaleType;
    }

    public Object getSign() {
        return mSign;
    }

    public boolean isQueue() {
        return isQueue;
    }

    public DialogGetListener getDialogGetListener() {
        return mDialogGetListener = null == mDialogGetListener ? NoHttpInit.getNoHttpInit().getDialogGetListener() : mDialogGetListener;
    }

    public OnIsRequestListener<T> getOnIsRequestListener() {
        return mOnIsRequestListener;
    }

    Class<T> getShiftDataClazz() {
        return mClazz;
    }

    public static class ConfigBuilder {
        /**
         * 请求模式
         */
        private RequestMethod mRequestMethod;
        /**
         * 请求URL
         */
        private String mUrl;
        /**
         * 参数集合
         */
        private Map<String, Object> mParameterMap;
        /**
         * 请求头参数集合
         */
        private Map<String, String> mMapHeader;
        /**
         * 请求的bitmap最大宽度
         */
        private int mMaxWidth = -1;
        /**
         * 请求的bitmap最大高度
         */
        private int mMaxHeight = -1;
        /**
         * bitmap配置
         */
        private Bitmap.Config mDecodeConfig;
        /**
         * bitmap比例
         */
        private ImageView.ScaleType mScaleType;
        /**
         * 加载框获取接口
         */
        private DialogGetListener mDialogGetListener;
        /**
         * 请求标识
         */
        private Object mSign;
        /**
         * 是否队列请求
         */
        private boolean isQueue = true;
        /**
         * 下载链接超时时间（默认以全局链接超时时间）
         */
        private int mConnectTimeout = -1;
        /**
         * 读取超时时间（默认以全局读取超时时间）
         */
        private int mReadTimeout = -1;
        /**
         * 请求失败重试计数
         */
        private int mRetryCount = -1;
        /**
         * 数据缓存对应的KEY（Nohttp底层默认是请求对应的url）
         */
        private String mCacheKey;
        /**
         * 缓存模式（默认：CacheMode.DEFAULT）
         */
        private CacheMode mCacheMode = CacheMode.DEFAULT;
        /**
         * 请求实体（body）
         */
        private RxRequestEntityBase mRxRequestEntityBase;
        /**
         * 未知错误提示语
         */
        private String mAnUnknownErrorHint;

        public ConfigBuilder() {
            try {
                mAnUnknownErrorHint = RxUtilsConfig.ConfigBuilder.getConfigBuilder().getRxUtilsConfig().getAnUnknownErrorHint();
            } catch (Exception e) {
                Logger.e("NoHttpUtils捕获异常：请先初始化框架");
                e.printStackTrace();
            }
        }

        public ConfigBuilder get() {
            mRequestMethod = RequestMethod.GET;
            return this;
        }

        public ConfigBuilder post() {
            mRequestMethod = RequestMethod.POST;
            return this;
        }

        public ConfigBuilder put() {
            mRequestMethod = RequestMethod.PUT;
            return this;
        }

        public ConfigBuilder delete() {
            mRequestMethod = RequestMethod.DELETE;
            return this;
        }

        public ConfigBuilder head() {
            mRequestMethod = RequestMethod.HEAD;
            return this;
        }

        public ConfigBuilder patch() {
            mRequestMethod = RequestMethod.PATCH;
            return this;
        }

        public ConfigBuilder options() {
            mRequestMethod = RequestMethod.OPTIONS;
            return this;
        }

        public ConfigBuilder trace() {
            mRequestMethod = RequestMethod.TRACE;
            return this;
        }

        /**
         * 设置网络请求地址
         *
         * @param url
         * @return
         */
        public ConfigBuilder url(String url) {
            mUrl = url;
            return this;
        }

        /**
         * 添加请求参数
         *
         * @param key   参数键
         * @param value 参数值
         * @return
         */
        public ConfigBuilder addParameter(String key, Object value) {
            mParameterMap = null == mParameterMap ? new HashMap<String, Object>() : mParameterMap;
            mParameterMap.put(key, value);
            return this;
        }

        /**
         * 添加请求参数
         *
         * @param mapParameter 参数map集合
         * @return
         */
        public ConfigBuilder addParameter(Map<String, Object> mapParameter) {
            mParameterMap = null == mParameterMap ? new HashMap<String, Object>() : mParameterMap;
            mParameterMap.putAll(mapParameter);
            return this;
        }

        /**
         * 设置请求参数
         *
         * @param mapParameter 请求参数Map集合
         * @return
         */
        public ConfigBuilder setParameterMap(Map<String, Object> mapParameter) {
            mParameterMap = mapParameter;
            return this;
        }

        /**
         * 添加请求头
         *
         * @param key   请求头键
         * @param value 请求头值
         * @return
         */
        public ConfigBuilder addHeader(String key, String value) {
            mMapHeader = null == mMapHeader ? new HashMap<String, String>() : mMapHeader;
            mMapHeader.put(key, value);
            return this;
        }

        /**
         * 添加请求头
         *
         * @param mapHeader 请求头Map集合
         * @return
         */
        public ConfigBuilder addHeader(Map<String, String> mapHeader) {
            mMapHeader = null == mMapHeader ? new HashMap<String, String>() : mMapHeader;
            mMapHeader.putAll(mapHeader);
            return this;
        }

        /**
         * 设置请求头
         *
         * @param mapHeader 请求头map集合
         * @return
         */
        public ConfigBuilder setHeaderMap(Map<String, String> mapHeader) {
            mMapHeader = mapHeader;
            return this;
        }

        /**
         * 为HTTPS协议添加无证书的参数
         *
         * @return
         */
        public ConfigBuilder addHttpsIsCertificate() {
            mParameterMap = null == mParameterMap ? new HashMap<String, Object>() : mParameterMap;
            //https是否需要证书协议参数集合
            Map<String, Object> httpsMap = new HashMap<>();
            httpsMap.put(RxRequestOperate.HTTPS_CERTIFICATE_NO, null);
            mParameterMap.put(RxRequestOperate.HTTPS_KEY, httpsMap);
            return this;
        }

        /**
         * 为HTTPS协议添加证书的参数
         *
         * @return
         */
        public ConfigBuilder addHttpsIsCertificate(InputStream inputStream) {
            mParameterMap = null == mParameterMap ? new HashMap<String, Object>() : mParameterMap;
            //https是否需要证书协议参数集合
            Map<String, Object> httpsMap = new HashMap<>();
            httpsMap.put(RxRequestOperate.HTTPS_CERTIFICATE, inputStream);
            mParameterMap.put(RxRequestOperate.HTTPS_KEY, httpsMap);
            return this;
        }

        /**
         * 设置请求请求位图的最大宽高
         *
         * @param maxWidth  框
         * @param maxHeight 高
         * @return
         */
        public ConfigBuilder setBitmapMaxWH(int maxWidth, int maxHeight) {
            mMaxWidth = maxWidth;
            mMaxHeight = maxHeight;
            return this;
        }

        /**
         * 设置请求位图的配置和比例
         *
         * @param config    bitmap配置
         * @param scaleType bitmap比例
         * @return
         */
        public ConfigBuilder setBitmapConfigType(Bitmap.Config config, ImageView.ScaleType scaleType) {
            mDecodeConfig = config;
            mScaleType = scaleType;
            return this;
        }

        /**
         * 设置请求加载框
         *
         * @param dialogGetListener 加载框获取接口
         * @return
         */
        public ConfigBuilder setDialogGetListener(DialogGetListener dialogGetListener) {
            mDialogGetListener = dialogGetListener;
            return this;
        }

        /**
         * 设置请求标识（必须唯一）
         *
         * @param sign 请求标识
         */
        public ConfigBuilder setSign(@NonNull Object sign) {
            mSign = sign;
            return this;
        }

        /**
         * 设置当前请求是否添加进Rx线程池队列中
         *
         * @param queue
         * @return
         */
        public ConfigBuilder setQueue(boolean queue) {
            isQueue = queue;
            return this;
        }

        /**
         * 设置链接超时时间
         *
         * @param connectTimeout 时间，单位秒
         * @return
         */
        public ConfigBuilder setConnectTimeout(int connectTimeout) {
            mConnectTimeout = connectTimeout;
            return this;
        }

        /**
         * 设置读取时间
         *
         * @param readTimeout 时间，单位秒
         * @return
         */
        public ConfigBuilder setReadTimeout(int readTimeout) {
            mReadTimeout = readTimeout;
            return this;
        }

        /**
         * 设置请求失败重试计数。默认值是0,也就是说,失败后不会再次发起请求。
         *
         * @param retryCount 重试计数
         * @return
         */
        public ConfigBuilder setRetryCount(int retryCount) {
            mRetryCount = retryCount;
            return this;
        }

        /**
         * 设置缓存模式（默认：CacheMode.Default）
         *
         * @param cacheMode 缓存模式共五大缓存模式：
         *                  1：CacheMode.Default，实现http 304重定向缓存 NoHttp本身是实现了RFC2616，所以这里不用设置或者设置为DEFAULT。
         *                  2：CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE，当请求服务器失败的时候，读取缓存 请求服务器成功则返回服务器数据，如果请求服务器失败，读取缓存数据返回。
         *                  3：CacheMode.IF_NONE_CACHE_REQUEST_NETWORK，如果发现有缓存直接成功，没有缓存才请求服务器 我们知道ImageLoader的核心除了内存优化外，
         *                  剩下一个就是发现把内地有图片则直接使用，没有则请求服务器，所以NoHttp这一点非常使用做一个ImageLoader。
         *                  4：CacheMode.ONLY_REQUEST_NETWORK，仅仅请求网络 这里不会读取缓存，也不支持Http304。
         *                  5：CacheMode.ONLY_READ_CACHE，仅仅读取缓存 仅仅读取缓存，不会请求网络和其它操作。
         * @return
         */
        public ConfigBuilder setCacheMode(@NonNull CacheMode cacheMode) {
            mCacheMode = cacheMode;
            return this;
        }

        /**
         * 设置数据缓存对应的KEY（默认是请求对应的url）
         *
         * @param cacheKey key
         * @return
         */
        public ConfigBuilder setCacheKey(String cacheKey) {
            mCacheKey = cacheKey;
            return this;
        }

        /**
         * 设置未知错误提示语
         *
         * @param anUnknownErrorHint 未知错误提示语
         * @return
         */
        public ConfigBuilder setAnUnknownErrorHint(String anUnknownErrorHint) {
            mAnUnknownErrorHint = anUnknownErrorHint;
            return this;
        }

        /**
         * 设置Json请求对象实体对象
         * json样式：{"xx":"xxx","yy":"yyy"}
         *
         * @return
         */
        public OnToJsonObjectEntityMethodListener requestJsonObjectEntity() {
            mRxRequestEntityBase = new RxRequestJsonObjectEntity();
            mRxRequestEntityBase.setOnGetConfigBuilderListener(new RxRequestEntityBase.OnGetConfigBuilderListener() {
                @Override
                public ConfigBuilder getConfigBuilder() {
                    return ConfigBuilder.this;
                }
            });
            return (OnToJsonObjectEntityMethodListener) mRxRequestEntityBase;
        }

        /**
         * 设置Json请求集合实体对象
         * json样式：[{"xx":"xxx"},{"yy":"yyy"}]
         *
         * @return
         */
        public OnToJsonListEntityMethodListener requestJsonListEntity() {
            mRxRequestEntityBase = new RxRequestJsonListEntity();
            mRxRequestEntityBase.setOnGetConfigBuilderListener(new RxRequestEntityBase.OnGetConfigBuilderListener() {
                @Override
                public ConfigBuilder getConfigBuilder() {
                    return ConfigBuilder.this;
                }
            });
            return (OnToJsonListEntityMethodListener) mRxRequestEntityBase;
        }

        /**
         * 设置String请求实体对象
         *
         * @param contentType Content-Type
         * @return
         */
        public OnToStringEntityMethodListener requestStringEntity(@NonNull String contentType) {
            mRxRequestEntityBase = new RxRequestStringEntity(contentType);
            mRxRequestEntityBase.setOnGetConfigBuilderListener(new RxRequestEntityBase.OnGetConfigBuilderListener() {
                @Override
                public ConfigBuilder getConfigBuilder() {
                    return ConfigBuilder.this;
                }
            });
            return (OnToStringEntityMethodListener) mRxRequestEntityBase;
        }

        /**
         * 请求输入流实体对象
         *
         * @param contentType Content-Type
         * @return
         */
        public OnToInputStreamEntityMethodListener requestInputStreamEntity(@NonNull String contentType) {
            mRxRequestEntityBase = new RxRequestInputStreamEntity(contentType);
            mRxRequestEntityBase.setOnGetConfigBuilderListener(new RxRequestEntityBase.OnGetConfigBuilderListener() {
                @Override
                public ConfigBuilder getConfigBuilder() {
                    return ConfigBuilder.this;
                }
            });
            return (OnToInputStreamEntityMethodListener) mRxRequestEntityBase;
        }

        /**
         * 构建请求轮询处理类
         *
         * @param clazz               请求成功后返回数据转换对象
         * @param onIsRequestListener 请求成功或者失败回调接口
         * @param <T>
         * @return
         */
        public <T> RxPollNoHttpConfig.ConfigBuilder<T> builderPoll(@NonNull Class<T> clazz, OnIsRequestListener<T> onIsRequestListener) {
            return RxPollNoHttpConfig.ConfigBuilder.getConfigBuilder(getRxRequestOperate(clazz, onIsRequestListener));
        }

        /**
         * 创建请求参数处理对象
         *
         * @param clazz               请求成功后返回数据转换对象
         * @param onIsRequestListener 请求成功或者失败回调接口
         * @param <T>
         * @return
         */
        public <T> OnRequestRxNoHttpListener builder(@NonNull Class<T> clazz, OnIsRequestListener<T> onIsRequestListener) {
            return getRxRequestOperate(clazz, onIsRequestListener);
        }

        /**
         * 获取请求参数类
         *
         * @param clazz               请求成功后返回数据转换对象
         * @param onIsRequestListener 请求成功或者失败回调接口
         * @param <T>
         * @return
         */
        private <T> RxRequestOperate<T> getRxRequestOperate(@NonNull Class<T> clazz, OnIsRequestListener<T> onIsRequestListener) {
            return new RxRequestOperate<>(getRxRequestConfig(clazz, onIsRequestListener));
        }

        private <T> RxRequestConfig<T> getRxRequestConfig(@NonNull Class<T> clazz, OnIsRequestListener<T> onIsRequestListener) {
            RxRequestConfig<T> requestConfig = new RxRequestConfig<>(clazz, onIsRequestListener);
            requestConfig.mRequestMethod = mRequestMethod;
            requestConfig.mUrl = mUrl;
            requestConfig.mParameterMap = mParameterMap;
            requestConfig.mMapHeader = mMapHeader;
            requestConfig.mMaxWidth = mMaxWidth;
            requestConfig.mMaxHeight = mMaxHeight;
            requestConfig.mDecodeConfig = mDecodeConfig;
            requestConfig.mScaleType = mScaleType;
            requestConfig.mDialogGetListener = mDialogGetListener;
            requestConfig.mSign = mSign;
            requestConfig.isQueue = isQueue;
            requestConfig.mConnectTimeout = mConnectTimeout;
            requestConfig.mReadTimeout = mReadTimeout;
            requestConfig.mRetryCount = mRetryCount;
            requestConfig.mCacheKey = mCacheKey;
            requestConfig.mCacheMode = mCacheMode;
            requestConfig.mRxRequestEntityBase = mRxRequestEntityBase;
            requestConfig.mAnUnknownErrorHint = mAnUnknownErrorHint;
            return requestConfig;
        }
    }
}
