package com.liqi.nohttputils.nohttp;

import android.support.annotation.NonNull;

import com.liqi.nohttputils.nohttp.interfa.OnToStringEntityMethodListener;

/**
 * 请求String实体
 * Created by LiQi on 2017/5/18.
 */
public class RxRequestStringEntity extends RxRequestEntityBase implements OnToStringEntityMethodListener {
    private RxRequestStringEntity() {

    }

    RxRequestStringEntity(@NonNull String contentType) {
        mContentType = contentType;
    }

    @Override
    public RxRequestEntityBase addStringEntityParameter(@NonNull String stringEntity) {
        mStringEntity = stringEntity;
        return this;
    }


}
