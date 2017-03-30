package com.liqi.nohttputils.nohttp.rx_threadpool.model;

import android.support.annotation.NonNull;

import com.liqi.nohttputils.interfa.DialogGetListener;
import com.liqi.nohttputils.interfa.OnIsRequestListener;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.CacheMode;
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
    //是否设置了从缓存数据库里面取数据
    private boolean isCache;

    private RxRequestModel() {

    }

    public RxRequestModel(@NonNull RestRequest<T> restRequest, @NonNull OnIsRequestListener<T> onIsRequestListener) {
        mRestRequest = restRequest;
        mOnIsRequestListener = onIsRequestListener;
    }

    /**
     * 判断此标识是否是当前对象,并取消当前sign的请求
     *
     * @param sign 标识
     * @return
     */
    public boolean isCancel(@NonNull Object sign) {
        if (null != mRestRequest) {
            if (mRestRequest.getTag() == sign) {
                return true;
            }
        }
        return false;
    }

    public void setSign(@NonNull Object sign) {
        if (null != mRestRequest) {
            mRestRequest.setTag(sign);
            mRestRequest.setCancelSign(sign);
        }
    }

    public void cancelBySign(Object sign) {
        if (null != mRestRequest) {
            mRestRequest.cancelBySign(sign);
            setRunOff(true);
        }
    }

    public void cancel() {
        if (null != mRestRequest) {
            mRestRequest.cancel();
            setRunOff(true);
        }
    }

    public void setCache(boolean cache) {
        isCache = cache;
        if (isCache) {
            cacheKeyWrite();
        }
    }

    /**
     * 设置是否写入缓存数据库
     */
    private void cacheKeyWrite() {
        if (null != mRestRequest) {
            mRestRequest.setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE);
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
                    setThrowable(new Exception(mRestRequest.url()+"撤销请求"));
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
