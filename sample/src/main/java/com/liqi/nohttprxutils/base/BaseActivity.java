package com.liqi.nohttprxutils.base;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import com.liqi.nohttprxutils.interfa.RequestHttpInterfa;
import com.liqi.nohttputils.interfa.DialogGetInterfa;
import com.liqi.nohttputils.interfa.RequestOkAndNo;
import com.liqi.nohttputils.nohttp.RxRequestUtils;
import com.yanzhenjie.nohttp.RequestMethod;

import java.util.Map;

/**
 * 带网络请求的BaseActivity
 * 个人QQ:543945827
 * NoHttp作者群号：46523908
 * Created by LiQi on 2016/12/30.
 */
public abstract class BaseActivity<T> extends AppCompatActivity implements RequestHttpInterfa<T>, DialogGetInterfa, RequestOkAndNo<T> {
    protected ProgressDialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreate();
    }

    @Override
    public void request(String url, Map<String, Object> mapParame, Map<String, String> mapHeader, Class<T> tClass) {
        //请大家按照自己需求去修改

        //请求参数不为空，就断定是Post请求，
        if (null != mapParame && !mapParame.isEmpty())
            RxRequestUtils.getRxRequestUtils().createRequest(url, RequestMethod.POST).setRequestParameterMap(mapParame).setMapHeader(mapHeader)
                    .requestRxNoHttp(tClass, this, this);

            //请求参数为空，就断定是Get请求，
        else
            RxRequestUtils.getRxRequestUtils().createRequest(url).setMapHeader(mapHeader).requestRxNoHttp(tClass, this, this);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public Dialog getDialog() {
        if (isHttpShoDialog())
            return getDialog(requestHint());

        return null;
    }

    /**
     * 获取进度条框
     *
     * @param content
     * @return
     */
    protected ProgressDialog getDialog(String content) {
        if (null == mDialog) {
            mDialog = new ProgressDialog(this);
            mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        mDialog.setMessage(content);
        return mDialog;
    }

    /**
     * 手动关闭进度条加载框
     */
    protected void closeDialog() {
        if (null != mDialog && mDialog.isShowing())
            mDialog.dismiss();
    }

    /**
     * 请求网络提示语获取
     *
     * @return
     */
    protected String requestHint() {
        return "正在请求中,请稍后...";
    }

    /**
     * 是否显示网络请求加载框
     *
     * @return
     */
    private boolean isHttpShoDialog() {
        return true;
    }

    /**
     * 替代findviewById方法
     */
    protected <T extends View> T find(int id) {
        return (T) findViewById(id);
    }
    protected  abstract void onCreate();
    @Override
    public void onNext(T response) {

    }

    @Override
    public void onError(Throwable e) {

    }
}
