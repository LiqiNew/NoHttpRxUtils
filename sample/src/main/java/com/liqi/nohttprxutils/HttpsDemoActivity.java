package com.liqi.nohttprxutils;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.liqi.nohttprxutils.base.BaseActivity;
import com.liqi.nohttputils.RxNoHttpUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Https协议请求演示界面
 * Created by LiQi on 2016/12/30.
 */
public class HttpsDemoActivity extends BaseActivity<String> implements View.OnClickListener {
    private TextView textView2;
    @Override
    protected void onCreate() {
        setContentView(R.layout.https_demo_activity);
        textView2 = $(R.id.textView2);
        Button httpsYes = $(R.id.https_yes);
        httpsYes.setOnClickListener(this);
        httpsYes.setAlpha(0.6f);
        Button httpsNo= $(R.id.https_no);
        httpsNo.setOnClickListener(this);
        httpsNo.setAlpha(0.6f);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //需要证书
            case R.id.https_yes:
                try {
                    InputStream inputStream = getAssets().open("srca.cer");
                    //开始请求
                    RxNoHttpUtils.rxNohttpRequest()
                            .post()
                            .url(StaticHttpUrl.HTTPS_URL)
                            .addHttpsIsCertificate(inputStream)
                            .setDialogGetListener(this)
                            .builder(String.class,this)
                            .requestRxNoHttp();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            //不需要证书
            case R.id.https_no:
                //开始请求
                RxNoHttpUtils.rxNohttpRequest()
                        .post()
                        .url(StaticHttpUrl.HTTPS_URL)
                        .addHttpsIsCertificate()
                        .setDialogGetListener(this)
                        .builder(String.class,this)
                        .requestRxNoHttp();
                break;
        }
    }

    @Override
    public void onNext(String response) {
        textView2.setText(response);
    }
}
