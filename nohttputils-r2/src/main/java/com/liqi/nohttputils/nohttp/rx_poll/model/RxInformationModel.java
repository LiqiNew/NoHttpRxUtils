package com.liqi.nohttputils.nohttp.rx_poll.model;

/**
 * rxJava轮询转换对象
 * Created by LiQi on 2017/9/8.
 */

public class RxInformationModel<T> {
    //装换对象
    private T mData;

    //是否停止当前对象轮询
    private boolean isStop;

    //是否是异常
    private boolean isException;
    //异常信息
    private Throwable mThrowable;

    public RxInformationModel() {
    }

    public boolean isException() {
        return isException;
    }

    public void setException(boolean exception) {
        isException = exception;
    }

    public Throwable getThrowable() {
        return mThrowable;
    }

    public void setThrowable(Throwable throwable) {
        mThrowable = throwable;
    }

    public T getData() {
        return mData;
    }

    public void setData(T data) {
        mData = data;
    }

    public boolean isStop() {
        return isStop;
    }

    public void setStop(boolean stop) {
        isStop = stop;
    }

    @Override
    public String toString() {
        return "RxInformationModel{" +
                "mData=" + mData +
                ", isStop=" + isStop +
                ", isException=" + isException +
                ", mThrowable=" + mThrowable +
                '}';
    }
}
