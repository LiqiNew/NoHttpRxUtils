package com.liqi.nohttputils.nohttp;

import android.support.annotation.NonNull;

import com.liqi.nohttputils.nohttp.interfa.OnToInputStreamEntityMethodListener;

import java.io.InputStream;

/**
 * 输入流请求实体
 * Created by LiQi on 2017/5/18.
 */
public class RxRequestInputStreamEntity extends RxRequestEntityBase implements OnToInputStreamEntityMethodListener {

    private RxRequestInputStreamEntity() {

    }

    RxRequestInputStreamEntity(@NonNull String contentType) {
        mContentType = contentType;
    }

    @Override
    public RxRequestEntityBase addEntityInputStreamParameter(@NonNull InputStream inputStream) {
        mInputStream = inputStream;
        return this;
    }
}
