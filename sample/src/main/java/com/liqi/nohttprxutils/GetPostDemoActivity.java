package com.liqi.nohttprxutils;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.liqi.nohttprxutils.base.BaseActivity;
import com.liqi.nohttprxutils.presenter.DemoHttpPresenter;

/**
 * GET POST请求演示界面
 * Created by LiQi on 2016/12/30.
 */
public class GetPostDemoActivity extends BaseActivity<String> implements View.OnClickListener {
    private Button mGet, mPost;
    private TextView mContent;
    private DemoHttpPresenter<String> mStringGetPostDemoPresenter;
    @Override
    protected void onCreate() {
        setContentView(R.layout.get_post_demo_activity);
        mGet = find(R.id.get);
        mGet.setOnClickListener(this);
        mGet.setAlpha(0.6f);
        mPost = find(R.id.post);
        mPost.setOnClickListener(this);
        mPost.setAlpha(0.6f);
        mContent = find(R.id.content);
        mStringGetPostDemoPresenter=new DemoHttpPresenter<>(this,String.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get:
                mStringGetPostDemoPresenter.startGetHttp(StaticHttpUrl.getGetUrl("LiQi","LiQi.pass",20,"1"));
                break;
            case R.id.post:
                mStringGetPostDemoPresenter.startPostHttp(StaticHttpUrl.POST_URL,"LiQi","LiQi.pass",20,"1");
                break;
        }
    }

    @Override
    public void onNext(String response) {
        mContent.setText(response);
    }
}
