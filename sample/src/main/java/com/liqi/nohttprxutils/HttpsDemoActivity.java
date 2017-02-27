package com.liqi.nohttprxutils;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.liqi.nohttprxutils.base.BaseActivity;
import com.liqi.nohttprxutils.presenter.DemoHttpPresenter;

import java.io.IOException;
import java.io.InputStream;

/**
 * Https协议请求演示界面
 * Created by LiQi on 2016/12/30.
 */
public class HttpsDemoActivity extends BaseActivity<String> implements View.OnClickListener {
    private TextView textView2;
    private DemoHttpPresenter<String> mDemoHttpPresenter;
    @Override
    protected void onCreate() {
        setContentView(R.layout.https_demo_activity);
        textView2 = find(R.id.textView2);
        Button httpsYes = find(R.id.https_yes);
        httpsYes.setOnClickListener(this);
        httpsYes.setAlpha(0.6f);
        Button httpsNo=find(R.id.https_no);
        httpsNo.setOnClickListener(this);
        httpsNo.setAlpha(0.6f);
        mDemoHttpPresenter=new DemoHttpPresenter<>(this,String.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //需要证书
            case R.id.https_yes:
                try {
                    InputStream inputStream = getAssets().open("srca.cer");
                    mDemoHttpPresenter.httpsGetData(StaticHttpUrl.HTTPS_URL,inputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            //不需要证书
            case R.id.https_no:
                mDemoHttpPresenter.httpsGetData(StaticHttpUrl.HTTPS_URL);
                break;
        }
    }

    @Override
    public void onNext(String response) {
        textView2.setText(response);
    }
}
