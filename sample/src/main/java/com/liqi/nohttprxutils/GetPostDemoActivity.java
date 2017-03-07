package com.liqi.nohttprxutils;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.liqi.nohttprxutils.base.BaseActivity;
import com.liqi.nohttputils.nohttp.RxNoHttpUtils;

/**
 * GET POST请求演示界面
 * Created by LiQi on 2016/12/30.
 */
public class GetPostDemoActivity extends BaseActivity<String> implements View.OnClickListener{
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
                        .url(StaticHttpUrl.getGetUrl("LiQi","LiQi.pass",20,"1"))
                        .setDialogGetListener(this)
                        .builder(String.class,this)
                        .requestRxNoHttp();
                break;
            case R.id.post:
                //开始请求
                RxNoHttpUtils.rxNohttpRequest()
                        .post()
                        .url(StaticHttpUrl.POST_URL)
                        .addParameter("userName","LiQi")
                        .addParameter("userPass","LiQi.pass")
                        .addParameter("userAge",20)
                        .addParameter("userSex","1")
                        .setDialogGetListener(this)
                        .builder(String.class,this)
                        .requestRxNoHttp();
                break;
        }
    }

    @Override
    public void onNext(String response) {
        mContent.setText(response);
    }
}
