package com.liqi.nohttprxutils.presenter;

import com.liqi.nohttprxutils.FileUploadingDemoActivity;
import com.liqi.nohttprxutils.interfa.RequestHttpInterfa;
import com.liqi.nohttputils.nohttp.BinaryFactory;
import com.liqi.nohttputils.nohttp.RxRequestUtils;
import com.yanzhenjie.nohttp.BasicBinary;
import com.yanzhenjie.nohttp.Binary;
import com.yanzhenjie.nohttp.OnUploadListener;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 网络请求辅助对象
 * Created by LiQi on 2016/12/30.
 */
public class DemoHttpPresenter<T> {
    private RequestHttpInterfa<T> mRequestHttpInterfa;
    private Class<T> mClass;

    private DemoHttpPresenter() {

    }

    public DemoHttpPresenter(RequestHttpInterfa<T> requestHttpInterfa, Class<T> clazz) {
        this.mRequestHttpInterfa = requestHttpInterfa;
        this.mClass = clazz;
    }

    /**
     * Https有证书请求
     *
     * @param url
     * @param inputStream
     */
    public void httpsGetData(String url, InputStream inputStream) {
        if (null != mRequestHttpInterfa)
            mRequestHttpInterfa.request(url, getHttpsParameterMap(inputStream), null, mClass);
    }

    /**
     * Https无证书请求
     *
     * @param url
     */
    public void httpsGetData(String url) {
        if (null != mRequestHttpInterfa)
            mRequestHttpInterfa.request(url, getHttpsParameterMap(null), null, mClass);
    }

    /**
     * 文件上传(为什么一样要用file，那是因为达哥建议使用file去上传)
     *
     * @param fileList         文件上传集合
     * @param onUploadListener 文件上传过程监听器
     */
    public void fileUpload(String url, ArrayList<File> fileList, OnUploadListener onUploadListener) {
        if (null != mRequestHttpInterfa) {
            List<Binary> binaries = new ArrayList<>();
            for (int i = 0; i < fileList.size(); i++) {
                BasicBinary binary = BinaryFactory.getBinary(fileList.get(i));
                int tag = -1;
                if (i == 0) {
                    tag = FileUploadingDemoActivity.FILE_ONE;
                } else if (i == 1) {
                    tag = FileUploadingDemoActivity.FILE_TWO;
                } else if (i == 2) {
                    tag = FileUploadingDemoActivity.FILE_THREE;
                } else {
                    tag = -1;
                }
                binary.setUploadListener(tag, onUploadListener);
                binaries.add(binary);
            }
            Map<String, Object> mapParame = new HashMap<>();
            mapParame.put("user", "LiQi");
            mapParame.put("image1", binaries);
            mRequestHttpInterfa.request(url, mapParame, null, mClass);
        }

    }

    /**
     * get请求图片（POST请求图片，请带上参数即可）
     *
     * @param url
     */
    public void startGetHttpImage(String url) {
        if (null != mRequestHttpInterfa)
            mRequestHttpInterfa.request(url, null, null, mClass);
    }

    /**
     * get请求
     *
     * @param url
     */
    public void startGetHttp(String url) {
        if (null != mRequestHttpInterfa)
            mRequestHttpInterfa.request(url, null, null, mClass);
    }

    /**
     * POST请求
     *
     * @param url
     * @param userName
     * @param userPass
     * @param userAge
     * @param userSex
     */
    public void startPostHttp(String url, String userName, String userPass, int userAge, String userSex) {
        if (null != mRequestHttpInterfa)
            mRequestHttpInterfa.request(url, getParameterMap(userName, userPass, userAge, userSex), null, mClass);
    }

    /**
     * post请求参数集合获取
     *
     * @param userName
     * @param userPass
     * @param userAge
     * @param userSex
     * @return
     */
    private Map<String, Object> getParameterMap(String userName, String userPass, int userAge, String userSex) {
        Map<String, Object> map = new HashMap<>();
//        map.put("userName", "yolanda");// String类型
//        map.put("userPass", "yolanda.pass");
//        map.put("userAge", 20);// int类型
//        map.put("userSex", '1');// char类型，还支持其它类型
        map.put("userName", userName);// String类型
        map.put("userPass", userPass);
        map.put("userAge", userAge);// int类型
        map.put("userSex", userSex);// char类型，还支持其它类型
        return map;
    }

    /**
     * 获取https协议参数集合
     *
     * @param inputStream 证书流
     * @return
     */
    public Map<String, Object> getHttpsParameterMap(InputStream inputStream) {
        //https是否需要证书协议参数集合
        Map<String, Object> httpsMap = new HashMap<>();
        if (null != inputStream)
            httpsMap.put(RxRequestUtils.HTTPS_CERTIFICATE, inputStream);
        else
            httpsMap.put(RxRequestUtils.HTTPS_CERTIFICATE_NO, null);
        //请求参数集合
        Map<String, Object> map = new HashMap<>();

        map.put(RxRequestUtils.HTTPS_KEY, httpsMap);
        return map;
    }
}
