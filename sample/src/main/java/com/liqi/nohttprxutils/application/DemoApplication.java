package com.liqi.nohttprxutils.application;

import android.app.Application;

import com.liqi.nohttputils.nohttp.RxRequestUtils;

/**
 * Created by LiQi on 2016/12/30.
 */
public class DemoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化nohttp
        RxRequestUtils.getRxRequestUtils().setDebug(true, "LiQi-NoHttpUtils").init(getApplicationContext(), RxRequestUtils.OKHTTP, true, false);
    }
}
