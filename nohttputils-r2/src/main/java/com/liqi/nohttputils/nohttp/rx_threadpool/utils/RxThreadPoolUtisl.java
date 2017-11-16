package com.liqi.nohttputils.nohttp.rx_threadpool.utils;


import com.liqi.nohttputils.nohttp.RxThreadInterchange;

/**
 * 分配线程唤醒和休眠工具
 * Created by LiQi on 2017/3/20.
 */

public class RxThreadPoolUtisl {

    public static void threadWait(RxThreadInterchange rxThreadInterchange) {
        synchronized (rxThreadInterchange) {
            try {
                rxThreadInterchange.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void threadNotify(RxThreadInterchange rxThreadInterchange) {
        synchronized (rxThreadInterchange) {
            rxThreadInterchange.notify();
        }
    }
}
