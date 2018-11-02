package com.liqi.nohttprxutils;

/**
 * 服务器请求地址
 * Created by LiQi on 2016/12/30.
 */
public class StaticHttpUrl {
    private static final String HTTP_URL = "http://kalle.nohttp.net/";
    /**
     * Login.
     */
    public static final String LOGIN = HTTP_URL + "/login";

    public static final String IMAGE_GET ="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1541153455963&di=8830bd21c6f786f0b425e6c9d41e709f&imgtype=0&src=http%3A%2F%2Fimg3.redocn.com%2Ftupian%2F20150106%2Faixinxiangkuang_3797284.jpg";

    /**
     * Form.
     */
    public static final String UPLOAD_FORM = HTTP_URL + "upload/form";

    public static final String HTTPS_URL="https://kyfw.12306.cn/otn";
    //图片文件存储本地路径
    public static final String FILE_PATH="/LiQi_nohttp_utils";
}
