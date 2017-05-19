package com.liqi.nohttputils.nohttp.interfa;

import android.support.annotation.NonNull;

import com.liqi.nohttputils.nohttp.RxRequestEntityBase;

import java.io.InputStream;

/**
 * 请求InputStream实体暴露给外界调用方法
 * Created by LiQi on 2017/5/19.
 */

public interface OnToInputStreamEntityMethodListener {
    /**
     * 添加输入流进实体中
     * @param inputStream 输入流
     * @return
     */
    RxRequestEntityBase addEntityInputStreamParameter(@NonNull InputStream inputStream);
}
