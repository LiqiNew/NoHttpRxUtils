package com.liqi.nohttprxutils;

/**
 * 服务器请求地址
 * Created by LiQi on 2016/12/30.
 */
public class StaticHttpUrl {
    private static final String HTTP_URL = "http://api.nohttp.net/";
    private static final String GET_URL = HTTP_URL + "method?";
    public static final String POST_URL = HTTP_URL + "method";
    public static final String IMAGE_URL = HTTP_URL + "image";
    public static final String UPLOAD_URL= HTTP_URL + "upload";
    public static final String HTTPS_URL="https://kyfw.12306.cn/otn";
    //图片文件存储本地路径
    public static final String FILE_PATH="/LiQi_nohttp_utils";
    /**
     * 获取get请求拼接的URL地址
     *
     * @param userName
     * @param userPass
     * @param userAge
     * @param userSex
     * @return
     */
    public static String getGetUrl(String userName, String userPass, int userAge, String userSex) {
        String getHttpUrl = GET_URL + "userName="
                + userName + "&userPass="
                + userPass + "&userAge=" + userAge + "&userSex=" + userSex;
        return getHttpUrl;
    }
}
