package com.liqi.nohttprxutils;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.liqi.nohttprxutils.base.BaseActivity;
import com.liqi.nohttprxutils.utils.FileUtil;
import com.liqi.nohttputils.RxNoHttpUtils;
import com.liqi.nohttputils.nohttp.BinaryFactory;
import com.yanzhenjie.nohttp.BasicBinary;
import com.yanzhenjie.nohttp.Binary;
import com.yanzhenjie.nohttp.OnUploadListener;
import com.yanzhenjie.nohttp.tools.IOUtils;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
        Button uploading= $(R.id.uploading);
        uploading.setOnClickListener(this);
        uploading.setAlpha(0.6f);
        mPbProgress01 = $(R.id.pb_progress01);
        mPbProgress02 = $(R.id.pb_progress02);
        mPbProgress03 = $(R.id.pb_progress03);
        textView = $(R.id.textView);
    }

    private void startRun(Runnable runnable) {
        new Thread(runnable).start();
    }

    @Override
    public void onClick(View v) {
        if (!MFILEARRAYLIST.isEmpty()) {
            //开始请求
            RxNoHttpUtils.rxNohttpRequest()
                    .post()
                    .url(StaticHttpUrl.UPLOAD_FORM)
                    .addParameter("name","nohttp")
                    .addParameter("age", 18)
                    .addParameter("file1",getBinaries().get(0))
                    .setOnDialogGetListener(this)
                    .builder(String.class,this)
                    .requestRxNoHttp();
        } else {
            Toast.makeText(this, "文件写入失败,无法上传", Toast.LENGTH_SHORT).show();
        }
    }

    private List<Binary> getBinaries() {
        List<Binary> binaries = new ArrayList<>();
        for (int i = 0; i < MFILEARRAYLIST.size(); i++) {
            BasicBinary binary = BinaryFactory.getBinary(MFILEARRAYLIST.get(i));
            int tag = -1;
            if (i == 0) {
                tag =FILE_ONE;
            } else if (i == 1) {
                tag =FILE_TWO;
            } else if (i == 2) {
                tag =FILE_THREE;
            } else {
                tag = -1;
            }
            binary.setUploadListener(tag, this);
            binaries.add(binary);
        }
        return binaries;
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
