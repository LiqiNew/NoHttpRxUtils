package com.liqi.nohttprxutils;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.liqi.nohttprxutils.base.BaseActivity;
import com.liqi.nohttputils.RxNoHttpUtils;

/**
 * 图片请求演示界面
 * Created by LiQi on 2016/12/30.
 */
public class ImageDownloadDemoActivity extends BaseActivity<Bitmap> implements View.OnClickListener {
    private ImageView mImageView;

    @Override
    protected void onCreate() {
        setContentView(R.layout.image_download_demo_activity);
        mImageView = $(R.id.imageView);
        Button imageButton = $(R.id.image_button);
        imageButton.setOnClickListener(this);
        imageButton.setAlpha(0.6f);
        mImageView.setAlpha(0.6f);
    }

    @Override
    public void onNext(Bitmap response) {
        if (null != response)
            mImageView.setImageBitmap(response);
    }

    @Override
    public void onClick(View v) {
        //开始请求
        RxNoHttpUtils.rxNohttpRequest()
                .get()
                .url(StaticHttpUrl.IMAGE_URL)
                .setDialogGetListener(this)
                .builder(Bitmap.class, this)
                .requestRxNoHttp();
    }

    @Override
    protected String requestHint() {
        return "正在请求图片中,请稍后...";
    }
}
