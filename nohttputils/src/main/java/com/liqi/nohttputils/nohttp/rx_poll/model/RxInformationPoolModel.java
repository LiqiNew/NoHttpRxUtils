package com.liqi.nohttputils.nohttp.rx_poll.model;


import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.liqi.nohttputils.R;
import com.liqi.nohttputils.interfa.DialogGetListener;
import com.liqi.nohttputils.interfa.OnIsRequestListener;
import com.liqi.nohttputils.nohttp.rx_poll.operators.OnObserverEventListener;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.error.NotFoundCacheError;
import com.yanzhenjie.nohttp.error.TimeoutError;
import com.yanzhenjie.nohttp.error.URLError;
import com.yanzhenjie.nohttp.error.UnKnownHostError;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.RestRequest;

import java.net.ProtocolException;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * rxJava轮询操作数据源对象
 * Created by LiQi on 2017/9/8.
 */

public class RxInformationPoolModel<T> {
    private Object mSign;
    private DialogGetListener mDialogGetListener;
    private RestRequest<T> mRestRequest;
    private OnIsRequestListener<T> mOnIsRequestListener;
    private RxInformationModel<T> mRxInformationModel;


    private Func1<RxInformationModel<T>, Boolean> mBooleanFunc1;
    private OnObserverEventListener<RestRequest<T>,RxInformationModel<T>> mOnObserverEventListener;
    private Action1<RxInformationModel<T>> mRxInformationModelAction1;

    public RxInformationPoolModel(@NonNull OnIsRequestListener<T> onIsRequestListener, DialogGetListener dialogGetListener) {
        mOnIsRequestListener = onIsRequestListener;
        mDialogGetListener = dialogGetListener;
        mRxInformationModel = new RxInformationModel<>();
        initOnObserverEventListener();
        initBooleanFunc1();
        initRxInformationModelAction1();


    }

    /**
     * 内部实现可观察者事件产生对应行动监听器
     */
    private void initRxInformationModelAction1() {

        mRxInformationModelAction1 = new Action1<RxInformationModel<T>>() {
            @Override
            public void call(RxInformationModel<T> tRxInformationModel) {
                Logger.e(mRestRequest.url() + "：轮询运行完毕");

                Dialog dialog = null == mDialogGetListener ? null : mDialogGetListener.getDialog();
                if (null != dialog && dialog.isShowing()) {
                    dialog.dismiss();
                }

                if (!tRxInformationModel.isStop()) {
                    //正常
                    if (!tRxInformationModel.isException()) {
                        if (null != mOnIsRequestListener) {
                            mOnIsRequestListener.onNext(tRxInformationModel.getData());
                        }
                    }
                    //异常
                    else {
                        Throwable e = tRxInformationModel.getThrowable();
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
                        } else {
                            Logger.e("NoHttpUtils捕获轮询异常：" + e.toString());
                            StackTraceElement[] stackTrace = e.getStackTrace();
                            if (null != stackTrace) {
                                for (StackTraceElement traceElement : stackTrace) {
                                    Logger.e("NoHttpUtils捕获轮询异常：" + traceElement.toString());
                                }
                            }
                            show(dialog, R.string.error_unknow);
                        }


                        if (null != mOnIsRequestListener) {
                            mOnIsRequestListener.onError(e);
                        }
                    }
                } else {
                    Logger.e(mRestRequest.url() + "：取消轮询请求线程");
                }
            }
        };
    }

    /**
     * 内部实现是否取消轮询-拦截器
     */
    private void initBooleanFunc1() {
        mBooleanFunc1 = new Func1<RxInformationModel<T>, Boolean>() {
            @Override
            public Boolean call(RxInformationModel<T> tRxInformationModel) {
                //拦截传输过来的对象为null之时，创建一个对象关闭临时关闭轮询对象
                if (null == tRxInformationModel) {
                    tRxInformationModel = new RxInformationModel<>();
                    tRxInformationModel.setStop(true);
                }
                Logger.e(mRestRequest.url() + "：轮询运行拦截>>拦截状态："+tRxInformationModel.isStop());
                return tRxInformationModel.isStop();
            }
        };
    }

    /**
     * 内部实现轮询操作处理
     */
    private void initOnObserverEventListener() {
        mOnObserverEventListener = new OnObserverEventListener<RestRequest<T>,RxInformationModel<T>>() {
            @Override
            public RxInformationModel<T> onObserverEvent(RestRequest<T> restRequest) {
                mRestRequest=restRequest;
                initTransitionModel();
                if (null != mDialogGetListener) {
                    //对话框放到主线程去运行
                    AndroidSchedulers.mainThread().createWorker().schedule(new Action0() {
                        @Override
                        public void call() {
                            Dialog dialog = mDialogGetListener.getDialog();
                            if (null != dialog) {
                                dialog.show();
                            }
                        }
                    });
                }
                if (null != mRestRequest) {
                    Logger.e(mRestRequest.url() + "：轮询运行开始");
                    Response<T> response = NoHttp.startRequestSync(mRestRequest);
                    //正确
                    if (response.isSucceed() || response.isFromCache()) {
                        mRxInformationModel.setData(response.get());
                    }
                    //错误
                    else {
                        mRxInformationModel.setException(true);
                        mRxInformationModel.setThrowable(response.getException());
                    }
                } else {
                    mRxInformationModel.setException(true);
                    mRxInformationModel.setThrowable(new NullPointerException("轮询运行开始请求参数为null,请反馈给框架提供者"));
                }
                return mRxInformationModel;
            }
        };
    }


    /**
     * 获取数据拦截监听对象
     *
     * @return 数据拦截监听对象
     */
    public Func1<RxInformationModel<T>, Boolean> getBooleanFunc1() {
        return mBooleanFunc1;
    }

    /**
     * 设置数据拦截监听对象
     *
     * @param booleanFunc1 数据拦截监听对象
     */
    public void setBooleanFunc1(Func1<RxInformationModel<T>, Boolean> booleanFunc1) {
        if (null != booleanFunc1) {
            mBooleanFunc1 = booleanFunc1;
        }
    }

    /**
     * 获取可观察者事件对象
     *
     * @return 可观察者事件对象
     */
    public OnObserverEventListener<RestRequest<T>,RxInformationModel<T>> getOnObserverEventListener() {
        return mOnObserverEventListener;
    }

    /**
     * 设置可观察者事件对象
     *
     * @param onObserverEventListener 可观察者事件对象
     */
    public void setOnObserverEventListener(OnObserverEventListener<RestRequest<T>,RxInformationModel<T>> onObserverEventListener) {
        mOnObserverEventListener = onObserverEventListener;
    }

    /**
     * 获取可观察者事件产生对应行动监听器
     *
     * @return 可观察者事件产生对应行动监听器
     */
    public Action1<RxInformationModel<T>> getRxInformationModelAction1() {
        return mRxInformationModelAction1;
    }

    /**
     * 赋值可观察者事件产生对应行动监听器
     *
     * @param rxInformationModelAction1 可观察者事件产生对应行动监听器
     */
    public void setRxInformationModelAction1(Action1<RxInformationModel<T>> rxInformationModelAction1) {
        mRxInformationModelAction1 = rxInformationModelAction1;
    }

    /**
     * 判断此标识是否是当前对象,并取消当前sign的请求
     *
     * @param sign 标识
     * @return ok is true ,else false.
     */
    public boolean isCancel(Object sign) {
        return mSign==sign;
    }

    /**
     * 设置标识
     *
     * @param sign 标识
     */
    public void setSign(Object sign) {
        mSign=sign;
    }



    /**
     * 取消请求
     */
    public void cancel() {
        if (null != mRestRequest) {
            mRestRequest.cancel();
        }
        setRxPollStopState(true);
    }

    /**
     * 设置是否停止轮询
     *
     * @param isStop true是停止，false是不停止
     */
    private void setRxPollStopState(boolean isStop) {
        mRxInformationModel.setStop(isStop);
    }

    /**
     * 初始化转换对象内部信息
     */
    private void initTransitionModel() {
        if (null != mRxInformationModel) {
            mRxInformationModel.setData(null);
            mRxInformationModel.setException(false);
            mRxInformationModel.setThrowable(null);
        }
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
}
