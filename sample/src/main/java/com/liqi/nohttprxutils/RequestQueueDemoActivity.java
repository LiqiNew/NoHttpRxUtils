package com.liqi.nohttprxutils;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.liqi.nohttprxutils.base.BaseActivity;
import com.liqi.nohttputils.RxNoHttpUtils;

import java.text.SimpleDateFormat;

/**
 * 队列请求演示界面
 * Created by LiQi on 2016/12/30.
 */
public class RequestQueueDemoActivity extends BaseActivity<String> implements View.OnClickListener {
    private Button mRequestButton, mRequestButton1;
    private TextView mContent;
    private String mResponse = "";
    //测试请求标签
    private Object mSign[] = {new Object(), new Object(),
            new Object(), new Object(), new Object(),
            new Object(), new Object(), new Object(),
            new Object(), new Object()};

    @Override
    protected void onCreate() {
        setContentView(R.layout.request_queue_activity);
        mRequestButton = $(R.id.request_button);
        mRequestButton.setOnClickListener(this);
        mRequestButton.setAlpha(0.6f);
        mContent = $(R.id.request_content);
        mRequestButton1 = $(R.id.request_button1);
        mRequestButton1.setOnClickListener(this);
        mRequestButton1.setAlpha(0.6f);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //开始请求
            case R.id.request_button:
                for (int i = 0; i < mSign.length; i++) {
                    //发送多个请求
                    RxNoHttpUtils.rxNohttpRequest()
                            .get()
                            .url(StaticHttpUrl.LOGIN)
                            .addParameter("pageNum",1)
                            .addParameter("pageSize",10)
                            .setSign(mSign[i])
                            .builder(String.class, this)
                            .requestRxNoHttp();
                }
                break;
            //撤销请求
            case R.id.request_button1:
                RxNoHttpUtils.cancel(mSign[9]);
                RxNoHttpUtils.cancel(mSign[7]);
                RxNoHttpUtils.cancel(mSign[8]);
                break;
        }
    }

    @Override
    public void onNext(String response) {
        mResponse += "喂,叫你了!不要看美女,看我,看我呀....\n请求成功时间：" + getTime() + "\n<<<请求成功内容：" + response + ">>>\n\n";
        mContent.setText(mResponse);
    }

    @Override
    public void onError(Throwable e) {
        mResponse += "哎!叫你看美女,现在请求失败了吧!....\n请求失败时间：" + getTime() + "\n<<<握草,网络请求失败>>>\n\n";
        mContent.setText(mResponse);
    }

    private String getTime() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return sDateFormat.format(new java.util.Date());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消RX线程池中所有的请求
       // RxNoHttpUtils.cancelAll();
        //取消批量Sign对应的请求
        RxNoHttpUtils.cancel(mSign);
    }
}
