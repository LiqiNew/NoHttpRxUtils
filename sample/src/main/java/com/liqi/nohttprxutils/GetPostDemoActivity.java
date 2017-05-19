package com.liqi.nohttprxutils;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.liqi.nohttprxutils.base.BaseActivity;
import com.liqi.nohttputils.RxNoHttpUtils;

/**
 * GET POST请求演示界面
 * Created by LiQi on 2016/12/30.
 */
public class GetPostDemoActivity extends BaseActivity<String> implements View.OnClickListener {
    private Button mGet, mPost;
    private TextView mContent;

    @Override
    protected void onCreate() {
        setContentView(R.layout.get_post_demo_activity);
        mGet = $(R.id.get);
        mGet.setOnClickListener(this);
        mGet.setAlpha(0.6f);
        mPost = $(R.id.post);
        mPost.setOnClickListener(this);
        mPost.setAlpha(0.6f);
        mContent = $(R.id.content);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get:
                //开始请求
                RxNoHttpUtils.rxNohttpRequest()
                        .get()
                        .url(StaticHttpUrl.getGetUrl("LiQi", "LiQi.pass", 20, "1"))
                        .setDialogGetListener(this)
                        .setQueue(false)
                        //单个请求设置读取时间(单位秒，默认以全局读取超时时间。)
                        // .setReadTimeout(40)
                        //单个请求设置链接超时时间(单位秒，默认以全局链接超时时间。)
                        // .setConnectTimeout(30)
                        //单个请求设置请求失败重试计数。默认值是0,也就是说,失败后不会再次发起请求。
                        //.setRetryCount(3)
                        //单个请求设置缓存key
                        //.setCacheKey("get请求Key")
                        //单个请求设置缓存模式
                        // .setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE)
                        .builder(String.class, this)
                        .requestRxNoHttp();
                break;
            case R.id.post:
                //开始请求
                RxNoHttpUtils.rxNohttpRequest()
                        .post()
                        .url(StaticHttpUrl.POST_URL)
                        .addParameter("userName", "LiQi")
                        .addParameter("userPass", "LiQi.pass")
                        .addParameter("userAge", 20)
                        .addParameter("userSex", "1")
                        .setDialogGetListener(this)
                        .setSign(this)
                        .builder(String.class, this)
                        .requestRxNoHttp();
                RxNoHttpUtils.cancel(this);
                break;
        }
    }

    @Override
    public void onNext(String response) {
        mContent.setText(response);
    }
}
