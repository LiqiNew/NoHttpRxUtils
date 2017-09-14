package com.liqi.nohttprxutils.application;

import android.app.Application;

import com.liqi.nohttputils.nohttp.NoHttpInit;
import com.liqi.nohttputils.RxNoHttpUtils;
import com.yanzhenjie.nohttp.cookie.DBCookieStore;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpCookie;
import java.net.URI;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * Created by LiQi on 2016/12/30.
 */
public class DemoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        try {
           // InputStream inputStream =getApplicationContext().getAssets().open("srca.cer");
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
                //设置全局连接超时时间。单位秒，默认30s。
                //.setConnectTimeout(40)
                //设置全局服务器响应超时时间，单位秒，默认30s。
                //.setReadTimeout(40)
                //设置全局默认加载对话框
                //.setDialogGetListener("全局加载框获取接口")
                //设置底层用那种方式去请求
                .setRxRequestUtilsWhy(NoHttpInit.OKHTTP)
                //设置下载线程池并发数量
                .setThreadPoolSize(3)
                //设置网络请求队列并发数量
                .setRunRequestSize(4)
                //设置带证书安全协议请求
                //.setInputStreamSSL(new InputStream())
                //设置无证书安全协议请求
                //.setInputStreamSSL()
                //添加全局请求头
                //.addHeader("app>>head","app_head_global")
                //添加全局请求参数-只支持String类型
               // .addParam("app_param","app_param_global")
                //设置Cookie管理监听。
               // .setCookieStoreListener(new DBCookieStore.CookieStoreListener())
                //设置主机验证
               // .setHostnameVerifier(new HostnameVerifier())
                //设置全局重试次数，配置后每个请求失败都会重试设置的次数。
                //.setRetry(5)
                //开始初始化Nohttp
                .startInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
