package com.liqi.nohttputils.nohttp.rx_poll.pond;


import com.liqi.nohttputils.nohttp.rx_poll.interfa.OnRxInformationCancelListener;
import com.liqi.nohttputils.nohttp.rx_poll.interfa.OnRxInformationListListener;
import com.liqi.nohttputils.nohttp.rx_poll.model.RxInformationPoolModel;

import java.util.LinkedList;
import java.util.List;

/**
 * RxJava轮询数据共享对象
 * Created by LiQi on 2017/9/8.
 */

public class RxInformationPool implements OnRxInformationListListener, OnRxInformationCancelListener {
    private static RxInformationPool mRxInformationPool;
    private final List<RxInformationPoolModel> mPoolModels;

    private RxInformationPool() {
        mPoolModels = new LinkedList<>();
    }

    public static OnRxInformationListListener getRxInformationPoolList() {
        return mRxInformationPool = null == mRxInformationPool ? new RxInformationPool() : mRxInformationPool;
    }

    public static OnRxInformationCancelListener getRxInformationPoolCancel() {
        return mRxInformationPool = null == mRxInformationPool ? new RxInformationPool() : mRxInformationPool;
    }

    @Override
    public <T> void add(RxInformationPoolModel<T> informationPoolModel) {
        mPoolModels.add(informationPoolModel);
    }

    @Override
    public RxInformationPoolModel get(int index) {
        return mPoolModels.get(index % mPoolModels.size());
    }

    @Override
    public int size() {
        return mPoolModels.size();
    }

    @Override
    public void delete(int index) {
        mPoolModels.remove(index % mPoolModels.size());
    }

    private void deleteAll() {
        if (!mPoolModels.isEmpty()) {
            mPoolModels.clear();
        }
    }

    @Override
    public void cancel(Object sign) {
        if (!mPoolModels.isEmpty()) {
            for (int i = 0; i < mPoolModels.size(); i++) {
                RxInformationPoolModel poolModel = mPoolModels.get(i);
                if (poolModel.isCancel(sign)) {
                    poolModel.cancel();
                    mPoolModels.remove(i);
                }
            }
        }
    }

    @Override
    public void cancel(Object[] sign) {
        if (null != sign && sign.length > 0) {
            for (int i = 0; i < sign.length; i++) {
                cancel(sign[i]);
            }
        }
    }

    @Override
    public void cancelAll() {
        if (!mPoolModels.isEmpty()) {
            for (int i = 0; i < size(); i++) {
                mPoolModels.get(i).cancel();

            }
        }
        deleteAll();
    }
}
