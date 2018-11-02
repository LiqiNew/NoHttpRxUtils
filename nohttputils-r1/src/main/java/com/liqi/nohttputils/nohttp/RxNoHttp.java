package com.liqi.nohttputils.nohttp;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.liqi.nohttputils.R;
import com.liqi.nohttputils.interfa.OnDialogGetListener;
import com.liqi.nohttputils.interfa.OnIsRequestListener;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.error.NotFoundCacheError;
import com.yanzhenjie.nohttp.error.TimeoutError;
import com.yanzhenjie.nohttp.error.URLError;
import com.yanzhenjie.nohttp.error.UnKnownHostError;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.net.ConnectException;
import java.net.ProtocolException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * 用rxjava去请求nohttp
 * Created by  Liqi on 2016/12/08.
 */
class RxNoHttp {
    private static RxNoHttp rxNoHttp;

    private RxNoHttp() {

    }

    synchronized static RxNoHttp getRxNoHttp() {
        return rxNoHttp = (null == rxNoHttp ? new RxNoHttp() : rxNoHttp);
    }

    /**
     * 通过nohttp去请求
     *
     * @param mOnDialogGetListener dialog获取接口
     * @param responseInterfa    请求成功或者失败回调对象
     */
    <T> void request(final Request<T> request, OnDialogGetListener mOnDialogGetListener, final OnIsRequestListener<T> responseInterfa, final String anUnknownErrorHint) {
        final Dialog dialog = null == mOnDialogGetListener ? null : mOnDialogGetListener.getDialog();
        if (null != dialog && !dialog.isShowing()) {
            try {
                dialog.show();
            } catch (Exception e) {
                Logger.e("Dialog-显示异常：由于Dialog依赖的Context不是栈顶。");
            }
        }

        Observable.create(new Observable.OnSubscribe<Response<T>>() {
            @Override
            public void call(Subscriber<? super Response<T>> subscriberOut) {
                // 最关键的就是用NoHttp的同步请求请求到response了，其它的都是rxjava做的，跟nohttp无关的。
                Response<T> response = NoHttp.startRequestSync(request);
                if (response.isSucceed() || response.isFromCache()) {
                    subscriberOut.onNext(response);
                } else {
                    subscriberOut.onError(response.getException());
                }

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<T>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        // 关闭dialog.
                        if (null != dialog && dialog.isShowing()) {
                            try {
                                dialog.dismiss();
                            } catch (Exception e1) {
                                Logger.e("Dialog-关闭异常：由于Dialog已经关闭或者依赖的Context不存在");
                            }
                        }

                        // 提示异常信息。
                        if (e instanceof NetworkError) {// 网络不好
                            show(dialog, R.string.error_please_check_network);
                        } else if (e instanceof TimeoutError) {// 请求超时
                            show(dialog, R.string.error_timeout);
                        } else if (e instanceof UnKnownHostError) {// 找不到服务器
                            show(dialog, R.string.error_not_found_server);
                        } else if (e instanceof URLError) {// URL是错的
                            show(dialog, R.string.error_url_error);
                        } else if (e instanceof NotFoundCacheError) {
                            // 这个异常只会在仅仅查找缓存时没有找到缓存时返回
                            show(dialog, R.string.error_not_found_cache);
                        } else if (e instanceof ProtocolException) {
                            show(dialog, R.string.error_system_unsupport_method);
                        } else if (e instanceof ConnectException) {//没有连接到服务器
                            show(dialog, R.string.error_no_service);
                        } else {
                            Logger.e("NoHttpUtils捕获异常：" + e.toString());
                            StackTraceElement[] stackTrace = e.getStackTrace();
                            if (null != stackTrace) {
                                for (StackTraceElement traceElement : stackTrace) {
                                    Logger.e("NoHttpUtils捕获异常：" + traceElement.toString());
                                }
                            }
                            if (TextUtils.isEmpty(anUnknownErrorHint)) {
                                show(dialog, R.string.error_unknow);
                            }else{
                                show(dialog,anUnknownErrorHint);
                            }
                        }


                        if (null != responseInterfa) {
                            responseInterfa.onError(e);
                        }
                    }

                    @Override
                    public void onNext(Response<T> tResponse) {
                        // 关闭dialog.
                        if (null != dialog && dialog.isShowing()) {
                            try {
                                dialog.dismiss();
                            } catch (Exception e1) {
                                Logger.e("Dialog-关闭异常：由于Dialog已经关闭或者依赖的Context不存在");
                            }
                        }

                        if (null != responseInterfa) {
                            responseInterfa.onNext(tResponse.get());
                        }
                    }
                });
    }

    /**
     * 土司提示
     *
     * @param stringId 提示内容资源ID
     */
    private void show(Dialog dialog, int stringId) {
        if (null != dialog) {
            Context context = dialog.getContext();
            Toast.makeText(context, context.getResources().getString(stringId), Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * 土司提示
     *
     * @param hint 提示内容
     */
    private void show(Dialog dialog, String hint) {
        if (null != dialog) {
            Context context = dialog.getContext();
            Toast.makeText(context, hint, Toast.LENGTH_SHORT).show();
        }
    }
}
