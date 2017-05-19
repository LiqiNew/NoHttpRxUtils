package com.liqi.nohttputils.nohttp;

import android.support.annotation.CallSuper;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 请求实体基类
 * Created by LiQi on 2017/5/18.
 */

public class RxRequestEntityBase {
    protected String mContentType;
    protected String mStringEntity;
    protected InputStream mInputStream;
    protected Map<String, Object> mStringJsonMap;
    protected OnGetConfigBuilderListener mOnGetConfigBuilderListener;
    protected List<Map<String, Object>> mJsonMapList;

    RxRequestEntityBase() {

    }

    List<Map<String, Object>> getJsonMapList() {
        return mJsonMapList;
    }

    String getContentType() {
        return mContentType;
    }

    String getStringEntity() {
        return mStringEntity;
    }

    InputStream getInputStream() {
        return mInputStream;
    }

    Map<String, Object> getStringJsonMap() {
        return mStringJsonMap;
    }

    void setOnGetConfigBuilderListener(OnGetConfigBuilderListener onGetConfigBuilderListener) {
        mOnGetConfigBuilderListener = onGetConfigBuilderListener;
    }

    /**
     * 切换对象到配置对象
     *
     * @return
     */
    @CallSuper
    public RxRequestConfig.ConfigBuilder transitionToRequest() {
        return mOnGetConfigBuilderListener.getConfigBuilder();
    }

    public interface OnGetConfigBuilderListener {
        RxRequestConfig.ConfigBuilder getConfigBuilder();
    }
}
