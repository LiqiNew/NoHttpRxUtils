package com.liqi.nohttputils.nohttp.rx_threadpool.model;

import android.support.annotation.NonNull;

import com.liqi.nohttputils.interfa.DialogGetListener;
import com.liqi.nohttputils.interfa.OnIsRequestListener;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.RestRequest;


/**
 * 要处理网络请求数据模型
 * Created by LiQi on 2017/3/20.
 */

public class RxRequestModel<T> extends BaseRxRequestModel<T> {
    private DialogGetListener mDialogGetListener;
    private RestRequest<T> mRestRequest;
    private OnIsRequestListener<T> mOnIsRequestListener;
    private Object mSign;
    //未知错误提示语
    private String mAnUnknownErrorHint;

    private RxRequestModel() {

    }

    public RxRequestModel(@NonNull RestRequest<T> restRequest, @NonNull OnIsRequestListener<T> onIsRequestListener) {
        mRestRequest = restRequest;
        mOnIsRequestListener = onIsRequestListener;
    }

    public String getAnUnknownErrorHint() {
        return mAnUnknownErrorHint;
    }

    public void setAnUnknownErrorHint(String anUnknownErrorHint) {
        mAnUnknownErrorHint = anUnknownErrorHint;
    }

    /**
     * 释放当前对象内存
     */
    public void clear() {
        mDialogGetListener = null;
        mOnIsRequestListener = null;
        mSign = null;
    }

    /**
     * 释放当前全部对象内存
     */
    public void clearAll() {
        clear();
        mRestRequest = null;
    }

    /**
     * 判断此标识是否是当前对象,并取消当前sign的请求
     *
     * @param sign 标识
     * @return
     */
    public boolean isCancel(@NonNull Object sign) {
        if (null != mRestRequest) {
            if (mSign == sign) {
                return true;
            }
        }
        return false;
    }

    public void setSign(@NonNull Object sign) {
        mSign = sign;
    }

    public void cancel() {
        if (null != mRestRequest) {
            mRestRequest.cancel();
            setRunOff(true);
        }
    }

    public DialogGetListener getDialogGetListener() {
        return mDialogGetListener;
    }

    public RxRequestModel setDialogGetListener(DialogGetListener dialogGetListener) {
        mDialogGetListener = dialogGetListener;
        return this;
    }

    public OnIsRequestListener<T> getOnIsRequestListener() {
        return mOnIsRequestListener;
    }

    @Override
    protected T run() {
        if (null != mRestRequest) {
            Logger.e(mRestRequest.url() + "线程运行>>>");
            Response<T> response = NoHttp.startRequestSync(mRestRequest);
            if (response.isSucceed() || response.isFromCache()) {
                if (!isRunOff()) {
                    return response.get();
                } else {
                    setThrowable(new Exception(mRestRequest.url() + "　-->撤销请求"));
                }
            } else {
                setThrowable(response.getException());
            }
        } else {
            setThrowable(new NullPointerException());
        }
        return (T) mRestRequest.url();
    }
}
