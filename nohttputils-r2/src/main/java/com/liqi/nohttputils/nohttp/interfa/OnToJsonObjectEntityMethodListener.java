package com.liqi.nohttputils.nohttp.interfa;

import android.support.annotation.NonNull;

import com.liqi.nohttputils.nohttp.RxRequestJsonObjectEntity;


/**
 * 请求Json实体暴露给外界调用方法
 * Created by LiQi on 2017/5/19.
 */

public interface OnToJsonObjectEntityMethodListener {
    /**
     * 添加参数
     * @param entityKey key
     * @param entityValu Valu
     * @return
     */
    RxRequestJsonObjectEntity addEntityParameter(String entityKey, @NonNull Object entityValu);
}
