package com.liqi.nohttprxutils;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.liqi.nohttprxutils.base.BaseActivity;
import com.liqi.nohttprxutils.presenter.DemoHttpPresenter;

/**
 * 图片请求演示界面
 * Created by LiQi on 2016/12/30.
 */
public class ImageDownloadDemoActivity extends BaseActivity<Bitmap> implements View.OnClickListener {
    private ImageView mImageView;
    private DemoHttpPresenter<Bitmap> mBitmapDemoHttpPresenter;

    @Override
    protected void onCreate() {
        setContentView(R.layout.image_download_demo_activity);
        mImageView = find(R.id.imageView);
        find(R.id.image_button).setOnClickListener(this);
        mBitmapDemoHttpPresenter = new DemoHttpPresenter<>(this, Bitmap.class);
    }

    @Override
    public void onNext(Bitmap response) {
        if (null != response)
            mImageView.setImageBitmap(response);
    }

    @Override
    public void onClick(View v) {
        mBitmapDemoHttpPresenter.startGetHttpImage(StaticHttpUrl.IMAGE_URL);
    }

    @Override
    protected String requestHint() {
        return "正在请求图片中,请稍后...";
    }
}
