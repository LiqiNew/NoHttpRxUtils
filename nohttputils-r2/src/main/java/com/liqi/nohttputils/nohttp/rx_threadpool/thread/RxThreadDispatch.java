package com.liqi.nohttputils.nohttp.rx_threadpool.thread;

import android.support.annotation.NonNull;

import com.liqi.nohttputils.nohttp.rx_threadpool.model.BaseRxRequestModel;
import com.yanzhenjie.nohttp.Logger;

import java.util.List;


/**
 * 分配线程（核心算法）
 * Created by LiQi on 2017/3/20.
 */

public class RxThreadDispatch extends Thread {
    //固定并发最大值
    private int mFixedRunSize;
    //要操作的并发最大值
    private int mRunSize;
    //数据源
    private List<BaseRxRequestModel> mList;
    //交个其它线程处理接口
    private OnRunDataDisListener mOnRunDataDisListener;
    //线程状态
    private boolean isRunState = true;
    //是否把数据源数据运行标识
    private boolean isRunSize;
    //是否终止运行标识
    private boolean isRunTag = true;

    public RxThreadDispatch(int runSize, @NonNull List<BaseRxRequestModel> list) {
        this.mRunSize = runSize;
        this.mFixedRunSize = runSize;
        this.mList = list;
    }

    private RxThreadDispatch() {
    }

    public boolean isRunState() {
        return isRunState;
    }

    public RxThreadDispatch setRunSize(boolean runSize) {
        isRunSize = runSize;
        return this;
    }

    public void setRunTag(boolean runTag) {
        this.isRunTag = runTag;
    }

    public void setOnRunDataDisListener(OnRunDataDisListener onRunDataDisListener) {
        mOnRunDataDisListener = onRunDataDisListener;
    }

    @Override
    public void run() {
        while (isRunTag) {
            isRunState = true;
            Logger.e("运行次数前mRunSize：" + mRunSize + "<<<<数据源总长度：>>>>" + mList.size());

            //开启多少条线程并发
            tag:
            while (!isRunSize && mRunSize - 1 >= 0) {
                mRunSize = mRunSize - 1;
                // Logger.e("运行次数后mRunSize："+mRunSize);

                if (!mList.isEmpty()) {
                    int runDisposeSize = 0;
                    for (int i = 0; i < mList.size(); i++) {
                        if (i < mList.size()) {
                            BaseRxRequestModel baseRxRequestModel = mList.get(i);
                            if (null != baseRxRequestModel) {
                                //判断是否有线程处理此对象
                                if (!baseRxRequestModel.isRunDispose()) {
                                    baseRxRequestModel.setRunDispose(true);
                                    if (null != mOnRunDataDisListener) {
                                        mOnRunDataDisListener.getRunData(baseRxRequestModel);
                                        continue tag;
                                    }
                                } else {
                                    ++runDisposeSize;
                                }
                            }
                        } else {
                            break;
                        }
                    }

                    if (runDisposeSize >= mList.size()) {
                        //如果全部数据源对象全部在处理，那么线程用掉的次数回滚
                        ++mRunSize;
                        isRunState = false;
                        isRunSize = true;
                        // Logger.e("线程休眠01："+"已经运行>>" + runDisposeSize + "总数据源：" + size);
                        if (null != mOnRunDataDisListener) {
                            mOnRunDataDisListener.waitThread();
                        }
                    }

                } else {
                    //如果数据源对象为空，那么线程用掉的次数回滚
                    ++mRunSize;
                    isRunState = false;
                    isRunSize = true;
                    // Logger.e("线程休眠02：数据源为空");
                    if (null != mOnRunDataDisListener) {
                        mOnRunDataDisListener.waitThread();
                    }
                }
            }
            isRunState = false;
            if (null != mOnRunDataDisListener) {
                // Logger.e("线程休眠03：运行次数用完");
                mOnRunDataDisListener.waitThread();
            }
            //  Logger.e("线程运行：线程运行中");
        }
    }

    /**
     * rxjava线程处理完数据，退回分配线程的次数
     */
    public void addRunSize() {
        synchronized (this) {
            ++mRunSize;

            if (mRunSize > mFixedRunSize) {
                mRunSize = mFixedRunSize;
            }
            //Logger.e("运行次数：++运行次数相加" + mRunSize);
        }
    }

    /**
     * 分配线程用来跟中转对象通讯的接口
     */
    public interface OnRunDataDisListener {
        //获取分配拿出来的要处理数据
        void getRunData(BaseRxRequestModel runData);

        //休眠分配线程
        void waitThread();
    }
}
