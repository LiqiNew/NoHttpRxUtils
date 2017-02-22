package com.liqi.nohttprxutils.application;

import android.app.Application;

import com.liqi.nohttputils.nohttp.RxRequestUtils;
import com.liqi.nohttputils.nohttp.RxUtilsConfig;

/**
 * Created by LiQi on 2016/12/30.
 */
public class DemoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化nohttp
        RxUtilsConfig.ConfigBuilder.getConfigBuilder(getApplicationContext())
                .setCookieEnable(false)
                .setDbEnable(true)
                .setDebug(true)
                .setDebugName("LiQi-NoHttpUtils")
                .setRxRequestUtilsWhy(RxRequestUtils.OKHTTP).createInit();
    }
}
