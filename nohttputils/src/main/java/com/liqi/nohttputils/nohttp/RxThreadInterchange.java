package com.liqi.nohttputils.nohttp;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.liqi.nohttputils.R;
import com.liqi.nohttputils.interfa.DialogGetListener;
import com.liqi.nohttputils.interfa.OnIsRequestListener;
import com.liqi.nohttputils.nohttp.rx_threadpool.interfa.OnRxMessageGetListener;
import com.liqi.nohttputils.nohttp.rx_threadpool.model.BaseRxRequestModel;
import com.liqi.nohttputils.nohttp.rx_threadpool.model.RxRequestModel;
import com.liqi.nohttputils.nohttp.rx_threadpool.thread.RxThreadDispatch;
import com.liqi.nohttputils.nohttp.rx_threadpool.utils.RxThreadPoolUtisl;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.error.NotFoundCacheError;
import com.yanzhenjie.nohttp.error.TimeoutError;
import com.yanzhenjie.nohttp.error.URLError;
import com.yanzhenjie.nohttp.error.UnKnownHostError;

import java.net.ProtocolException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 把数据源中的数据转换成rxJava线程的中转站对象
 * Created by LiQi on 2017/3/20.
 */

public class RxThreadInterchange implements RxThreadDispatch.OnRunDataDisListener {
    //Rx线程池并发数量处理值
    private static final int RUNSIZE = RxUtilsConfig.ConfigBuilder.getConfigBuilder().getRxUtilsConfig().getRunRequestSize();
    private static RxThreadInterchange mRxThreadInterchange;
    private final String REQUEST_REVOCATION = "撤销请求";
    //当队列信息长度达到此长度的时候清空掉
    private final int SIZE = RUNSIZE;
    private RxThreadDispatch mRxThreadDispatch;
    private OnRxMessageGetListener mOnRxMessageDisListener;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            RxRequestModel baseRxRequestModel = (RxRequestModel) msg.obj;
            runRequest(baseRxRequestModel);
        }
    };

    private RxThreadInterchange() {

    }

    static RxThreadInterchange getRxThreadInterchange() {
        return mRxThreadInterchange = null == mRxThreadInterchange ? new RxThreadInterchange() : mRxThreadInterchange;
    }

    /**
     * 转换运行
     *
     * @param onRxMessageGetListener 数据源对内暴露接口
     * @param <T>
     */
    <T extends BaseRxRequestModel> void start(@NonNull OnRxMessageGetListener<T> onRxMessageGetListener) {
        if (null == mOnRxMessageDisListener) {
            selectOkState(onRxMessageGetListener);
        } else {
            if (mOnRxMessageDisListener != onRxMessageGetListener) {
                selectOkState(onRxMessageGetListener);
            }
        }
        if (null == mRxThreadDispatch || !mRxThreadDispatch.isAlive()) {

            runToNo();
            //开启中转线程
            mRxThreadDispatch = new RxThreadDispatch(RUNSIZE, mOnRxMessageDisListener.getList());
            mRxThreadDispatch.setOnRunDataDisListener(this);
            mRxThreadDispatch.setDaemon(true);
            mRxThreadDispatch.start();
        } else {
            mRxThreadDispatch.setRunSize(false);
            synchronized (this) {
                start();
            }
        }
    }

    /**
     * 唤醒中转线程起来做事了
     */
    private void start() {
        if (mRxThreadDispatch.isAlive() && !mRxThreadDispatch.isRunState()) {
            RxThreadPoolUtisl.threadNotify(RxThreadInterchange.this);
            //Logger.e("唤醒中转线程>>>>");
        }
    }

    /**
     * 判断当前是已经获取数据源对象
     *
     * @param onRxMessageDisListener
     */
    private void selectOkState(@NonNull OnRxMessageGetListener onRxMessageDisListener) {
        mOnRxMessageDisListener = onRxMessageDisListener;
        runToNo();
    }

    /**
     * 把当前中转线程打死
     */
    private void runToNo() {
        if (null != mRxThreadDispatch) {
            mRxThreadDispatch.setRunTag(false);
            mRxThreadDispatch = null;
        }
    }

    /**
     * 中转线程创建rxJava线程
     *
     * @param baseRxRequestModel 要处理的对象
     * @param <T>
     */
    private <T> void runRequest(final RxRequestModel<T> baseRxRequestModel) {
        if (null != baseRxRequestModel) {

            Dialog dialog = getDialog(baseRxRequestModel);
            if (null != dialog) {
                dialog.show();
            }

            Observable.create(baseRxRequestModel)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<T>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            Dialog dialog = getDialog(baseRxRequestModel);
                            if (null != dialog && dialog.isShowing()) {
                                dialog.dismiss();
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
                            } else {
                                if (e.getMessage().contains(REQUEST_REVOCATION)) {
                                    Logger.e(e.getMessage());
                                } else {
                                    Logger.e("NoHttpUtils捕获异常：" + e.toString());
                                    StackTraceElement[] stackTrace = e.getStackTrace();
                                    if (null != stackTrace) {
                                        for (StackTraceElement traceElement : stackTrace) {
                                            Logger.e("NoHttpUtils捕获异常：" + traceElement.toString());
                                        }
                                    }
                                    show(dialog, R.string.error_unknow);
                                }
                            }

                            OnIsRequestListener<T> onIsRequestListener = baseRxRequestModel.getOnIsRequestListener();
                            if (null != onIsRequestListener) {
                                onIsRequestListener.onError(e);
                            }

                            baseRxRequestModel.clearAll();
                            messageListDalete();
                        }

                        @Override
                        public void onNext(T t) {
                            Dialog dialog = getDialog(baseRxRequestModel);
                            if (null != dialog && dialog.isShowing()) {
                                dialog.dismiss();
                            }

                            OnIsRequestListener<T> onIsRequestListener = baseRxRequestModel.getOnIsRequestListener();
                            if (null != onIsRequestListener) {
                                onIsRequestListener.onNext(t);
                            }

                            baseRxRequestModel.clearAll();
                            messageListDalete();
                        }
                    });
        }

    }

    /**
     * 当数据源里面的数据长度达到一个指定的值的时候，干掉指定值长度的数据。然后继续浪(运行)
     */
    private void messageListDalete() {
        synchronized (this) {
            int size = mOnRxMessageDisListener.size();
            if (size >= SIZE) {
                for (int i = 0; i < size; i++) {
                    int index = i % size;
                    BaseRxRequestModel baseRxRequestModel = mOnRxMessageDisListener.get(index);
                    if (null != baseRxRequestModel && baseRxRequestModel.isRunDispose()) {
                        mOnRxMessageDisListener.delete(index);
                    }
                }
            }
            mRxThreadDispatch.addRunSize();
            start();
        }
    }

    @Override
    public void getRunData(BaseRxRequestModel runData) {
        Message message = mHandler.obtainMessage();
        message.obj = runData;
        mHandler.sendMessage(message);
    }

    @Override
    public void waitThread() {
        RxThreadPoolUtisl.threadWait(this);
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
     * 从RxRequestModel对象中获取dialog
     *
     * @param baseRxRequestModel RxRequestModel对象
     * @return
     */
    private Dialog getDialog(RxRequestModel baseRxRequestModel) {
        DialogGetListener dialogGetListener = baseRxRequestModel.getDialogGetListener();
        return null == dialogGetListener ? null : dialogGetListener.getDialog();
    }
}
