package com.liqi.nohttputils.nohttp;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.liqi.nohttputils.interfa.DialogGetListener;
import com.liqi.nohttputils.interfa.OnIsRequestListener;
import com.yanzhenjie.nohttp.RequestMethod;

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
    private boolean isOpenCache;
    private Object mSign;
    private boolean isQueue = true;

    private RxRequestConfig() {

    }

    RxRequestConfig(Class<T> clazz, OnIsRequestListener<T> onIsRequestListener) {
        mClazz = clazz;
        mOnIsRequestListener = onIsRequestListener;
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

    boolean isOpenCache() {
        return isOpenCache;
    }

    Object getSign() {
        return mSign;
    }

    boolean isQueue() {
        return isQueue;
    }

    DialogGetListener getDialogGetListener() {
        return mDialogGetListener = null == mDialogGetListener ? NoHttpInit.getNoHttpInit().getDialogGetListener() : mDialogGetListener;
    }

    OnIsRequestListener<T> getOnIsRequestListener() {
        return mOnIsRequestListener;
    }

    Class<T> getShiftDataClazz() {
        return mClazz;
    }

    public static class ConfigBuilder {
        //请求模式
        private RequestMethod mRequestMethod;
        //请求URL
        private String mUrl;
        //参数集合
        private Map<String, Object> mParameterMap;
        //请求头参数集合
        private Map<String, String> mMapHeader;
        //请求的bitmap最大宽度
        private int mMaxWidth = -1;
        //请求的bitmap最大高度
        private int mMaxHeight = -1;
        //bitmap配置
        private Bitmap.Config mDecodeConfig;
        //bitmap比例
        private ImageView.ScaleType mScaleType;
        //加载框获取接口
        private DialogGetListener mDialogGetListener;
        //是否开启缓存
        private boolean isOpenCache;
        //请求标识
        private Object mSign;
        //是否队列请求
        private boolean isQueue = true;

        public ConfigBuilder() {

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
         * 是否设置缓存
         *
         * @param openCache
         */
        public ConfigBuilder setOpenCache(boolean openCache) {
            isOpenCache = openCache;
            return this;
        }

        /**
         * 设置请求标识（必须唯一）
         *
         * @param sign
         */
        public ConfigBuilder setSign(Object sign) {
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
         * 创建请求参数处理对象
         *
         * @param clazz               请求成功后返回数据转换对象
         * @param onIsRequestListener 请求成功或者失败回调接口
         * @param <T>
         * @return
         */
        public <T> RxRequestOperate builder(@NonNull Class<T> clazz, OnIsRequestListener<T> onIsRequestListener) {
            RxRequestConfig<T> requestConfig = new RxRequestConfig<T>(clazz, onIsRequestListener);
            requestConfig.mRequestMethod = mRequestMethod;
            requestConfig.mUrl = mUrl;
            requestConfig.mParameterMap = mParameterMap;
            requestConfig.mMapHeader = mMapHeader;
            requestConfig.mMaxWidth = mMaxWidth;
            requestConfig.mMaxHeight = mMaxHeight;
            requestConfig.mDecodeConfig = mDecodeConfig;
            requestConfig.mScaleType = mScaleType;
            requestConfig.mDialogGetListener = mDialogGetListener;
            requestConfig.isOpenCache = isOpenCache;
            requestConfig.mSign = mSign;
            requestConfig.isQueue = isQueue;
            return new RxRequestOperate<T>(requestConfig);
        }
    }
}
