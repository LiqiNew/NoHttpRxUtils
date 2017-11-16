package com.liqi.nohttputils.nohttp;

import android.support.annotation.NonNull;

import com.liqi.nohttputils.nohttp.interfa.OnToJsonListEntityMethodListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求Json集合实体
 * Created by LiQi on 2017/5/18.
 */
public class RxRequestJsonListEntity extends RxRequestEntityBase
        implements OnToJsonListEntityMethodListener, OnToJsonListEntityMethodListener.OnToJsonAddEntityMethodListener {
    private Map<String, Object> mMapParameter;

    RxRequestJsonListEntity() {
        mJsonMapList = null == mJsonMapList ? new ArrayList<Map<String, Object>>() : mJsonMapList;
    }

    public OnToJsonAddEntityMethodListener addObjectEntityParameter(String keyParameter,@NonNull Object valueParameter) {
        mMapParameter = new HashMap<>();
        mMapParameter.put(keyParameter, valueParameter);
        return this;
    }

    public OnToJsonAddEntityMethodListener addEntityParameter(String keyParameter, @NonNull Object valueParameter) {
        if (null != mMapParameter) {
            mMapParameter.put(keyParameter, valueParameter);
        }
        return this;
    }

    public OnToJsonListEntityMethodListener objectBrushIntoList() {
        if (null != mMapParameter) {
            mJsonMapList.add(mMapParameter);
        }
        return this;
    }
}
