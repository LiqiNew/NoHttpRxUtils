package com.liqi.nohttputils.nohttp;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.liqi.nohttputils.interfa.DialogGetInterfa;
import com.liqi.nohttputils.interfa.RequestOkAndNo;
import com.yanzhenjie.nohttp.OkHttpNetworkExecutor;
import com.yolanda.nohttp.Binary;
import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.NetworkExecutor;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.URLConnectionNetworkExecutor;
import com.yolanda.nohttp.cache.DBCacheStore;
import com.yolanda.nohttp.cookie.DBCookieStore;
import com.yolanda.nohttp.rest.RestRequest;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * nohttp请求工具
 * Created by LiQi on 2016/11/7.
 */
public class RxRequestUtils {
    //URLCONNECTION请求标识
    public static final int URLCONNECTION = -0X1;
    //OKHTTP请求标识
    public static final int OKHTTP = -0X2;
    /**
     * HTTPS请求map中key标识-->值必须为map
     * 例子：
     * 参数集合 ：Map<String, Object> parameterMap=new HashMap();
     * Https参数集合：Map<String,Object> httpsMap=new HashMap();
     * 需要证书格式添加参数
     * httpsMap.put(HTTPS_CERTIFICATE,InputStream);
     * 不需要证书格式添加参数
     * httpsMap.put(HTTPS_CERTIFICATE_NO,null);
     * <p>
     * //把https集合放进参数集合里面记得参数集合的键必须为 HTTPS_KEY
     * parameterMap.put(HTTPS_KEY,httpsMap);
     */
    public static final String HTTPS_KEY = "https_liqi";


    //--------------------------https---------------------
    //HTTPS请求map值map中需要证书标识-->值为一个证书输入流
    public static final String HTTPS_CERTIFICATE = "https_certificate";
    //HTTPS请求map中无证书标识-->值没有限制，因为无用
    public static final String HTTPS_CERTIFICATE_NO = "https_certificate_no";
    private static RxRequestUtils rxRequestUtils;
    //--------------------------https---------------------
    //请求URL
    private String mUrl;
    //请求模式
    private RequestMethod mRequestMethod;
    //参数集合
    private Map<String, Object> parameterMap;
    //请求头参数集合
    private Map<String, String> mMapHeader;
    //请求的bitmap最大宽度
    private int mMaxWidth = -1;
    //请求的bitmap最大高度
    private int mMaxHeight = -1;
    //bitmap配置
    private Bitmap.Config mDecodeConfig = null;
    //bitmap比例
    private ImageView.ScaleType mScaleType = null;

    private RxRequestUtils() {

    }

    public synchronized static RxRequestUtils getRxRequestUtils() {
        return rxRequestUtils = (null == rxRequestUtils ? new RxRequestUtils() : rxRequestUtils);
    }

    /**
     * 设置获取图片之后参数
     *
     * @param maxWidth     宽度
     * @param maxHeight    高度
     * @param decodeConfig 图片解码配置
     * @param scaleType    图片展示类型
     * @return
     */
    public RxRequestUtils setBitmapParameter(int maxWidth, int maxHeight, Bitmap.Config decodeConfig, ImageView.ScaleType scaleType) {
        this.mMaxWidth = maxWidth;
        this.mMaxHeight = maxHeight;
        this.mDecodeConfig = decodeConfig;
        this.mScaleType = scaleType;
        return rxRequestUtils;
    }

    /**
     * 创建自定义请求
     *
     * @param url           请求URL
     * @param requestMethod 请求方式
     * @return
     */
    public RxRequestUtils createRequest(String url, RequestMethod requestMethod) {
        mUrl = url;
        mRequestMethod = requestMethod;
        return rxRequestUtils;
    }

    /**
     * 创建Get方式请求
     *
     * @param url 请求URL
     * @return
     */
    public RxRequestUtils createRequest(String url) {
        mUrl = url;
        return rxRequestUtils;
    }


    /**
     * 自定义初始化nohttp
     *
     * @return
     */
    void init(RxUtilsConfig rxUtilsConfig) {
        // nohttp默认设置的初始化
        //NoHttp.initialize(context);
        if (null != rxUtilsConfig) {
            Context context = rxUtilsConfig.getContext();
            NetworkExecutor networkExecutor = null;
            switch (rxUtilsConfig.getRxRequestUtilsWhy()) {
                case URLCONNECTION:
                    networkExecutor = new URLConnectionNetworkExecutor();
                    break;
                case OKHTTP:
                    networkExecutor = new OkHttpNetworkExecutor();
                    break;
                default:
                    networkExecutor = new OkHttpNetworkExecutor();
                    break;
            }
            // 自定义配置初始化：
            NoHttp.initialize(context, new NoHttp.Config()
                    // 设置全局连接超时时间，单位毫秒，默认10s。
                    .setConnectTimeout(rxUtilsConfig.getConnectTimeout())
                    // 设置全局服务器响应超时时间，单位毫秒，默认10s。
                    .setReadTimeout(rxUtilsConfig.getReadTimeout())
                    // 配置缓存，默认保存数据库DBCacheStore，保存到SD卡使用DiskCacheStore。
                    .setCacheStore(
                            new DBCacheStore(context).setEnable(rxUtilsConfig.isDbEnable()) // 如果不使用缓存，设置false禁用。
                    )
                    // 配置Cookie，默认保存数据库DBCookieStore，开发者可以自己实现。
                    .setCookieStore(
                            new DBCookieStore(context).setEnable(rxUtilsConfig.isCookieEnable()) // 如果不维护cookie，设置false禁用。
                    )
                    // 配置网络层，默认使用URLConnection，如果想用OkHttp：OkHttpNetworkExecutor。
                    .setNetworkExecutor(networkExecutor)
            );
            Logger.setDebug(rxUtilsConfig.isDebug());// 开启NoHttp的调试模式, 配置后可看到请求过程、日志和错误信息。
            Logger.setTag(rxUtilsConfig.getDebugName());// 设置NoHttp打印Log的tag。
        }
    }

    /**
     * 设置请求参数
     *
     * @param parameterMap 参数MAP集合
     * @return
     */
    public RxRequestUtils setRequestParameterMap(Map<String, Object> parameterMap) {
        this.parameterMap = parameterMap;
        return rxRequestUtils;
    }

    /**
     * 设置请求头参数
     *
     * @param mMapHeader 请求头参数集合
     * @return
     */
    public RxRequestUtils setMapHeader(Map<String, String> mMapHeader) {
        this.mMapHeader = mMapHeader;
        return rxRequestUtils;
    }

    /**
     * 请求RxNohttp
     *
     * @param clazz             响应数据转换对象
     * @param mDialogGetInterfa dialog提示接口
     * @param responseInterfa   请求成功和失败接口
     * @param <T>
     * @return
     */
    public <T> RxRequestUtils requestRxNoHttp(Class<T> clazz, DialogGetInterfa mDialogGetInterfa, RequestOkAndNo<T> responseInterfa) {
        RestRequest<T> beanRequest = addParameter(getTJavaBeanRequest(clazz));
        RxNoHttp.getRxNoHttp().request(beanRequest, mDialogGetInterfa, responseInterfa);
        return rxRequestUtils;
    }

    /**
     * 添加参数
     *
     * @param entityRequest 请求网络参数对象
     * @param <T>           请求网络返回对象
     * @return
     */
    private <T> RestRequest<T> addParameter(RestRequest<T> entityRequest) {
        if (null != entityRequest) {
            //参数设置
            if (null != parameterMap && !parameterMap.isEmpty()) {
                mapP:
                for (Map.Entry<String, Object> entry : parameterMap.entrySet()) {
                    String keyParameter = entry.getKey();
                    Object valueParameter = entry.getValue();
                    //判断是否需要https协议通讯
                    if (HTTPS_KEY.equals(keyParameter)) {
                        if (null != valueParameter) {
                            if (valueParameter instanceof Map) {
                                mapValueHttps(entityRequest, (Map<String, Object>) valueParameter);
                            } else {
                                Logger.e("Https协议需要请求参数定义为map集合");
                            }
                        }
                    } else {
                        if (null != valueParameter) {
                            if (valueParameter instanceof String) {
                                entityRequest.add(keyParameter, valueParameter.toString());
                                continue mapP;
                            }
                            if (valueParameter instanceof Integer) {
                                entityRequest.add(keyParameter, Integer.parseInt(valueParameter.toString()));
                                continue mapP;
                            }
                            if (valueParameter instanceof Boolean) {
                                entityRequest.add(keyParameter, Boolean.parseBoolean(valueParameter.toString()));
                                continue mapP;
                            }
                            if (valueParameter instanceof Byte) {
                                entityRequest.add(keyParameter, Byte.parseByte(valueParameter.toString()));
                                continue mapP;
                            }
                            if (valueParameter instanceof Double) {
                                entityRequest.add(keyParameter, Double.valueOf(valueParameter.toString()));
                                continue mapP;
                            }
                            if (valueParameter instanceof File) {
                                entityRequest.add(keyParameter, (File) valueParameter);
                                continue mapP;
                            }
                            if (valueParameter instanceof Float) {
                                entityRequest.add(keyParameter, Float.parseFloat(valueParameter.toString()));
                                continue mapP;
                            }
                            if (valueParameter instanceof Binary) {
                                entityRequest.add(keyParameter, (Binary) valueParameter);
                                continue mapP;
                            }
                            if (valueParameter instanceof Long) {
                                entityRequest.add(keyParameter, Long.parseLong(valueParameter.toString()));
                                continue mapP;
                            }
                            if (valueParameter instanceof List) {
                                entityRequest.add(keyParameter, (List<Binary>) valueParameter);
                                continue mapP;
                            }
                            if (valueParameter instanceof Short) {
                                entityRequest.add(keyParameter, Short.parseShort(valueParameter.toString()));
                                continue mapP;
                            }
                            if (valueParameter instanceof Map) {
                                entityRequest.add((Map<String, String>) valueParameter);
                                continue mapP;
                            }
                        }
                    }
                }
            }
            //请求头数据设置
            if (null != mMapHeader && !mMapHeader.isEmpty()) {
                for (Map.Entry<String, String> header : mMapHeader.entrySet()) {
                    entityRequest.addHeader(header.getKey(), header.getValue());
                }
            }
        }
        emptyThisData();
        return entityRequest;
    }

    /**
     * 把https证书设置进参数对象里面
     *
     * @param entityRequest 参数对象
     * @param mapHttps      https参数集合
     * @param <T>
     */
    private <T> void mapValueHttps(RestRequest<T> entityRequest, Map<String, Object> mapHttps) {
        mapHs:
        for (Map.Entry<String, Object> entryHttps : mapHttps.entrySet()) {
            String keyHttps = entryHttps.getKey();
            Object valueHttps = entryHttps.getValue();
            //需要证书
            if (HTTPS_CERTIFICATE.equals(keyHttps)) {
                if (null != valueHttps) {
                    if (valueHttps instanceof InputStream) {
                        InputStream inputStream = (InputStream) valueHttps;
                        entityRequest.setSSLSocketFactory(SSLContextUtil.getSSLContext(inputStream).getSocketFactory());
                        break mapHs;
                    } else {
                        Logger.e("Https集合需要证书值需要InputStream类型");
                    }
                } else {
                    Logger.e("Https参数集合值为空");
                }
            }
            //不需要证书
            if (HTTPS_CERTIFICATE_NO.equals(keyHttps)) {
                entityRequest.setSSLSocketFactory(SSLContextUtil.getDefaultSLLContext().getSocketFactory());
                entityRequest.setHostnameVerifier(SSLContextUtil.HOSTNAME_VERIFIER);
                break mapHs;
            }
        }
    }

    /**
     * 创建请求对象
     *
     * @param clazz 请求网络返回对象
     * @param <T>   请求网络返回对象
     * @return
     */
    private <T> RestRequest<T> getTJavaBeanRequest(Class<T> clazz) {
        RestRequest<T> ntityRequest = null;
        if (clazz != Bitmap.class) {
            if (null == mRequestMethod)
                ntityRequest = new RequestBeanObj(mUrl, clazz);
            else
                ntityRequest = new RequestBeanObj<>(mUrl, mRequestMethod, clazz);
        } else {
            if (null == mRequestMethod)
                ntityRequest = new RequestBeanObj(mUrl, mMaxWidth, mMaxHeight, mDecodeConfig, mScaleType, clazz);
            else
                ntityRequest = new RequestBeanObj(mUrl, mRequestMethod, mMaxWidth, mMaxHeight, mDecodeConfig, mScaleType, clazz);

        }
        return ntityRequest;
    }

    /**
     * 清空当前对象里面的数据
     */
    private void emptyThisData() {
        mMaxWidth = -1;
        mMaxHeight = -1;
        mDecodeConfig = null;
        mScaleType = null;
        mRequestMethod = null;
        if (null != parameterMap) {
            parameterMap.clear();
            parameterMap = null;
        }
        mUrl = null;
    }
}
