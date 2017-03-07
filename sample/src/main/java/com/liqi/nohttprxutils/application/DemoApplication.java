package com.liqi.nohttprxutils.application;

import android.app.Application;

import com.liqi.nohttputils.nohttp.NoHttpInit;
import com.liqi.nohttputils.nohttp.RxNoHttpUtils;

/**
 * Created by LiQi on 2016/12/30.
 */
public class DemoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化nohttp（在此处其实可以调用setDialogGetListener设置全局请求加载框）
        RxNoHttpUtils.rxNoHttpInit(getApplicationContext())
                //是否维护Cookie
                .setCookieEnable(false)
                //是否缓存进数据库DBCacheStore
                .setDbEnable(true)
                //是否开启debug调试
                .isDebug(true)
                //设置debug打印Name
                .setDebugName("LiQi-NoHttpUtils")
                //设置全局连接超时时间。单位毫秒，默认30s。
                //.setConnectTimeout(100*1000)
                //设置全局服务器响应超时时间，单位毫秒，默认30s。
                //.setReadTimeout(100*1000)
                //设置全局默认加载对话框
                //.setDialogGetListener("全局加载框获取接口")
                //设置底层用那种方式去请求
                .setRxRequestUtilsWhy(NoHttpInit.OKHTTP)
                //开始初始化Nohttp
                .startInit();
    }
}
