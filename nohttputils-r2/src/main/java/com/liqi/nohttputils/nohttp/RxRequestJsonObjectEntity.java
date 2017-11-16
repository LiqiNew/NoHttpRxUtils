package com.liqi.nohttputils.nohttp;

import android.support.annotation.NonNull;

import com.liqi.nohttputils.nohttp.interfa.OnToJsonObjectEntityMethodListener;

import java.util.HashMap;

/**
 * 请求Json实体
 * Created by LiQi on 2017/5/18.
 */
public class RxRequestJsonObjectEntity extends RxRequestEntityBase implements OnToJsonObjectEntityMethodListener {

    RxRequestJsonObjectEntity() {
        mStringJsonMap = null == mStringJsonMap ? new HashMap<String, Object>() : mStringJsonMap;
    }

    @Override
    public RxRequestJsonObjectEntity addEntityParameter(String entityKey, @NonNull Object entityValu) {
        mStringJsonMap.put(entityKey, entityValu);
        return this;
    }
}
