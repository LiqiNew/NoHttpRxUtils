package com.liqi.nohttputils.nohttp.rx_threadpool;


import android.support.annotation.NonNull;

import com.liqi.nohttputils.nohttp.rx_threadpool.interfa.OnRxMessageGetListener;
import com.liqi.nohttputils.nohttp.rx_threadpool.interfa.OnRxMessageSetListener;
import com.liqi.nohttputils.nohttp.rx_threadpool.model.RxRequestModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据源共享对象
 * Created by LiQi on 2017/3/20.
 */

public class RxMessageSource implements OnRxMessageSetListener<RxRequestModel>, OnRxMessageGetListener<RxRequestModel> {
    private static RxMessageSource mRxMessageSource;
    //信息源集合
    private final List<RxRequestModel> mRequestModels;

    private RxMessageSource() {
        mRequestModels = new ArrayList<>();
    }

    public static RxMessageSource getRxMessageSource() {
        return mRxMessageSource = null == mRxMessageSource ? new RxMessageSource() : mRxMessageSource;
    }

    @Override
    public RxMessageSource add(@NonNull RxRequestModel parameter, @NonNull Object sign) {
        parameter.setSign(sign);
        mRequestModels.add(parameter);
        return mRxMessageSource;
    }

    @Override
    public List<RxRequestModel> getList() {
        return mRequestModels;
    }

    @Override
    public RxRequestModel get(int index) {
        return mRequestModels.get(index % size());
    }

    @Override
    public void delete(int index) {
        mRequestModels.remove(index % size());
    }

    @Override
    public int size() {
        return mRequestModels.size();
    }

    private void deleteAll() {
        if (!mRequestModels.isEmpty()) {
            mRequestModels.clear();
        }
    }


    @Override
    public RxMessageSource cancel(Object sign) {
        if (!mRequestModels.isEmpty()) {
            for (int i = 0; i < mRequestModels.size(); i++) {
                RxRequestModel requestModel = mRequestModels.get(i);
                if (requestModel.isCancel(sign)) {
                    requestModel.cancelBySign(sign);
                    mRequestModels.remove(i);
                }
            }
        }
        return mRxMessageSource;
    }

    @Override
    public RxMessageSource cancel(Object[] sign) {
        if (null != sign && sign.length > 0) {
            for (int i = 0; i < sign.length; i++) {
                cancel(sign[i]);
            }
        }
        return mRxMessageSource;
    }

    @Override
    public RxMessageSource cancelAll() {
        if (!mRequestModels.isEmpty()) {
            for (int i = 0; i < size(); i++) {
                mRequestModels.get(i).cancel();

            }
        }
        deleteAll();
        return mRxMessageSource;
    }

    @Override
    public RxMessageSource add(@NonNull RxRequestModel parameter) {
        mRequestModels.add(parameter);
        return mRxMessageSource;
    }
}
