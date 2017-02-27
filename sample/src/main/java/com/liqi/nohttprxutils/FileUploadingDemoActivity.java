package com.liqi.nohttprxutils;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.liqi.nohttprxutils.base.BaseActivity;
import com.liqi.nohttprxutils.presenter.DemoHttpPresenter;
import com.liqi.nohttprxutils.utils.FileUtil;
import com.yanzhenjie.nohttp.OnUploadListener;
import com.yanzhenjie.nohttp.tools.IOUtils;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * 文件上传请求演示界面（文件上传也请参考此界面请求）
 * Created by LiQi on 2016/12/30.
 */
public class FileUploadingDemoActivity extends BaseActivity<String> implements View.OnClickListener, OnUploadListener {
    /**
     * 文件上传请求标识
     */
    public static final int FILE_ONE = 0x11, FILE_TWO = 0x22, FILE_THREE = 0x33;

    private final String FILEPATH = FileUtil.getRootPath().getAbsolutePath() + StaticHttpUrl.FILE_PATH;
    //写入本地文件路径，带文件扩展名
    private final String TEST01_PATH = FILEPATH + "/liqi_test01.jpg",
            TEST02_PATH = FILEPATH + "/liqi_test02.jpg",
            TEST03_PATH = FILEPATH + "/liqi_test03.png";
    //要上传的文件集合
    private final ArrayList<File> MFILEARRAYLIST = new ArrayList<>();

    private ProgressBar mPbProgress01, mPbProgress02, mPbProgress03;
    private TextView textView;
    private DemoHttpPresenter<String> mStringDemoHttpPresenter;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            closeDialog();
            File file = new File(TEST01_PATH);
            MFILEARRAYLIST.add(file);
            file = new File(TEST02_PATH);
            MFILEARRAYLIST.add(file);
            file = new File(TEST03_PATH);
            MFILEARRAYLIST.add(file);
        }
    };
    /**
     * 把资源文件写入本地
     */
    private Runnable saveFileThread = new Runnable() {
        @Override
        public void run() {
            try {
                FileUtil.initDirectory(FILEPATH);

                InputStream inputStream = getAssets().open("123.jpg");
                FileUtil.saveFile(inputStream, TEST01_PATH);
                IOUtils.closeQuietly(inputStream);

                inputStream = getAssets().open("234.jpg");
                FileUtil.saveFile(inputStream, TEST02_PATH);
                IOUtils.closeQuietly(inputStream);

                inputStream = getAssets().open("456.png");
                FileUtil.saveFile(inputStream, TEST03_PATH);
                IOUtils.closeQuietly(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mHandler.obtainMessage().sendToTarget();
        }
    };

    @Override
    protected void onCreate() {
        setContentView(R.layout.image_uploading_demo_activity);
        getDialog("文件写入中,请稍后...").show();
        //如果你的要上传的文件已经在本地存在，那么就不需要执行此方法
        startRun(saveFileThread);
        Button uploading=find(R.id.uploading);
        uploading.setOnClickListener(this);
        uploading.setAlpha(0.6f);
        mPbProgress01 = find(R.id.pb_progress01);
        mPbProgress02 = find(R.id.pb_progress02);
        mPbProgress03 = find(R.id.pb_progress03);
        textView = find(R.id.textView);
        mStringDemoHttpPresenter = new DemoHttpPresenter<>(this, String.class);
    }

    private void startRun(Runnable runnable) {
        new Thread(runnable).start();
    }

    @Override
    public void onClick(View v) {
        if (!MFILEARRAYLIST.isEmpty()) {
            mStringDemoHttpPresenter.fileUpload(StaticHttpUrl.UPLOAD_URL, MFILEARRAYLIST, this);
        } else
            Toast.makeText(this, "文件写入失败,无法上传", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart(int what) {

    }

    @Override
    public void onCancel(int what) {

    }

    @Override
    public void onProgress(int what, int progress) {
        switch (what) {
            case FILE_ONE:
                mPbProgress01.setProgress(progress);
                break;
            case FILE_TWO:
                mPbProgress02.setProgress(progress);
                break;
            case FILE_THREE:
                mPbProgress03.setProgress(progress);
                break;
        }
    }

    @Override
    public void onFinish(int what) {

    }

    @Override
    public void onError(int what, Exception exception) {

    }

    @Override
    protected String requestHint() {
        return "文件上传中,请稍后...";
    }

    @Override
    public void onNext(String response) {
        textView.setText("上传完成：" + response);
    }

    @Override
    public void onError(Throwable e) {
        textView.setText("上传错误：" + e.getMessage());
    }
}
