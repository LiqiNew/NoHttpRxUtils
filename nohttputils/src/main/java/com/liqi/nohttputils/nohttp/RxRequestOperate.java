package com.liqi.nohttputils.nohttp;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.liqi.nohttputils.nohttp.gsonutils.JsonUtil;
import com.liqi.nohttputils.nohttp.rx_threadpool.RxMessageSource;
import com.liqi.nohttputils.nohttp.rx_threadpool.model.RxRequestModel;
import com.yanzhenjie.nohttp.Binary;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.rest.RestRequest;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * nohttp请求参数处理对象
 * Created by LiQi on 2016/11/7.
 */
public class RxRequestOperate<T> {
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
    static final String HTTPS_KEY = "https_liqi";
    //--------------------------https---------------------
    //HTTPS请求map值map中需要证书标识-->值为一个证书输入流
    static final String HTTPS_CERTIFICATE = "https_certificate";
    //HTTPS请求map中无证书标识-->值没有限制，因为无用
    static final String HTTPS_CERTIFICATE_NO = "https_certificate_no";
    private RxRequestConfig<T> mRxRequestConfig;

    //--------------------------https---------------------
    private RxRequestOperate() {

    }

    RxRequestOperate(@NonNull RxRequestConfig<T> requestConfig) {
        mRxRequestConfig = requestConfig;
    }

    /**
     * 开始请求RxNohttp
     *
     * @return
     */
    public void requestRxNoHttp() {
        if (mRxRequestConfig.isQueue()) {
            RxRequestModel<T> requestModel = new RxRequestModel<>(
                    addParameter(getTJavaBeanRequest(mRxRequestConfig.getShiftDataClazz()))
                    , mRxRequestConfig.getOnIsRequestListener());
            requestModel.setDialogGetListener(mRxRequestConfig.getDialogGetListener());

            Object sign = mRxRequestConfig.getSign();
            if (sign != null) {
                requestModel.setSign(sign);
            }
            RxThreadInterchange.getRxThreadInterchange().start(RxMessageSource.getRxMessageSource().add(requestModel));
        } else {
            RxNoHttp.getRxNoHttp().request(addParameter(getTJavaBeanRequest(mRxRequestConfig.getShiftDataClazz())), mRxRequestConfig.getDialogGetListener(), mRxRequestConfig.getOnIsRequestListener());
        }
    }

    /**
     * 添加参数
     *
     * @param entityRequest 请求网络参数对象
     * @return
     */
    private RestRequest<T> addParameter(RestRequest<T> entityRequest) {
        if (null != entityRequest) {
            Map<String, Object> parameterMap = mRxRequestConfig.getParameterMap();
            //参数设置
            if (null != parameterMap && !parameterMap.isEmpty()) {

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
                            if (valueParameter instanceof Integer) {
                                entityRequest.add(keyParameter, Integer.parseInt(valueParameter.toString()));
                            }else if (valueParameter instanceof Boolean) {
                                entityRequest.add(keyParameter, Boolean.parseBoolean(valueParameter.toString()));
                            }else if (valueParameter instanceof Byte) {
                                entityRequest.add(keyParameter, Byte.parseByte(valueParameter.toString()));
                            }else if (valueParameter instanceof Double) {
                                entityRequest.add(keyParameter, Double.valueOf(valueParameter.toString()));
                            }else if (valueParameter instanceof File) {
                                entityRequest.add(keyParameter, (File) valueParameter);
                            }else if (valueParameter instanceof Float) {
                                entityRequest.add(keyParameter, Float.parseFloat(valueParameter.toString()));
                            }else if (valueParameter instanceof Binary) {
                                entityRequest.add(keyParameter, (Binary) valueParameter);
                            }else if (valueParameter instanceof Long) {
                                entityRequest.add(keyParameter, Long.parseLong(valueParameter.toString()));
                            }else if (valueParameter instanceof List) {
                                //确保传入的文件List集合中是指定类型
                                List list= (List) valueParameter;
                                if (!list.isEmpty()) {
                                    Object object = list.get(0);
                                    if (object instanceof Binary) {
                                        entityRequest.add(keyParameter, (List<Binary>) valueParameter);
                                    }else{
                                        Logger.e("文件上传list参数值类型不符合.需要传入的类型：Binary类型");
                                    }
                                }else{
                                    Logger.e("文件上传list参数值为空");
                                }
                            }else if (valueParameter instanceof Short) {
                                entityRequest.add(keyParameter, Short.parseShort(valueParameter.toString()));
                            }else if (valueParameter instanceof Map) {
                                try {
                                    Map<String, String> mapString= (Map<String, String>) valueParameter;
                                    entityRequest.add(mapString);
                                } catch (Exception e) {
                                    Logger.e("参数Map传入值类型错误.Map键值类型：key=String类型,value=String类型");
                                }
                            }else{
                                entityRequest.add(keyParameter, valueParameter.toString());
                            }
                        }
                    }
                }
            }
            Map<String, String> mapHeader = mRxRequestConfig.getMapHeader();
            //请求头数据设置
            if (null != mapHeader && !mapHeader.isEmpty()) {
                for (Map.Entry<String, String> header : mapHeader.entrySet()) {
                    entityRequest.addHeader(header.getKey(), header.getValue());
                }
            }
            //设置开启缓存
            entityRequest.setCacheMode(mRxRequestConfig.getCacheMode());
            String cacheKey = mRxRequestConfig.getCacheKey();
            if (null != cacheKey && !"".equals(cacheKey)) {
                //设置缓存KEY
                entityRequest.setCacheKey(cacheKey);
            }
            //设置请求实体
            RxRequestEntityBase rxRequestEntityBase = mRxRequestConfig.getRxRequestEntityBase();
            if (null != rxRequestEntityBase) {
                if (rxRequestEntityBase instanceof RxRequestJsonObjectEntity) {
                    String objectToJson = JsonUtil.objectToJson(rxRequestEntityBase.getStringJsonMap());
                    entityRequest.setDefineRequestBodyForJson(objectToJson);
                    Logger.e("JsonObject类型-Body值："+objectToJson+"\nBody-ContentType类型："+ Headers.HEAD_VALUE_ACCEPT_APPLICATION_JSON);
                }

                else if (rxRequestEntityBase instanceof RxRequestStringEntity) {
                    String stringEntity = rxRequestEntityBase.getStringEntity();
                    String contentType = rxRequestEntityBase.getContentType();
                    entityRequest.setDefineRequestBody(stringEntity, contentType);
                    Logger.e("字符串类型-Body值："+stringEntity+"\nBody-ContentType类型："+contentType);
                }

                else if (rxRequestEntityBase instanceof RxRequestInputStreamEntity) {
                    InputStream inputStream = rxRequestEntityBase.getInputStream();
                    String contentType = rxRequestEntityBase.getContentType();
                    entityRequest.setDefineRequestBody(inputStream, contentType);
                    Logger.e("字节流类型-Body值：(字节流"+")\nBody-ContentType类型："+contentType);
                }

                else if (rxRequestEntityBase instanceof RxRequestJsonListEntity) {
                    String objectToJson = JsonUtil.objectToJson(rxRequestEntityBase.getJsonMapList());
                    entityRequest.setDefineRequestBodyForJson(objectToJson);
                    Logger.e("JsonArray类型-Body值："+objectToJson+"\nBody-ContentType类型："+Headers.HEAD_VALUE_ACCEPT_APPLICATION_JSON);
                }

                else {
                    Logger.e("RxRequestEntityBase类型未知");
                }
            }
        }
        return entityRequest;
    }

    /**
     * 把https证书设置进参数对象里面
     *
     * @param entityRequest 参数对象
     * @param mapHttps      https参数集合
     */
    private void mapValueHttps(RestRequest<T> entityRequest, Map<String, Object> mapHttps) {
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
     * @return
     */
    private RestRequest<T> getTJavaBeanRequest(Class<T> clazz) {
        RestRequest<T> ntityRequest;
        if (clazz != Bitmap.class) {
            ntityRequest = new RequestBeanObj<T>(mRxRequestConfig.getUrl(), mRxRequestConfig.getRequestMethod(), clazz);
        } else {
            ntityRequest = new RequestBeanObj<T>(mRxRequestConfig.getUrl(),
                    mRxRequestConfig.getRequestMethod(),
                    mRxRequestConfig.getMaxWidth(),
                    mRxRequestConfig.getMaxHeight(),
                    mRxRequestConfig.getDecodeConfig(),
                    mRxRequestConfig.getScaleType(), clazz);
        }
        int connectTimeout = mRxRequestConfig.getConnectTimeout();
        if (connectTimeout > 0) {
            ntityRequest.setConnectTimeout(connectTimeout);
        }
        int readTimeout = mRxRequestConfig.getReadTimeout();
        if (readTimeout > 0) {
            ntityRequest.setReadTimeout(readTimeout);
        }
        int retryCount = mRxRequestConfig.getRetryCount();
        if (retryCount > 0) {
            ntityRequest.setRetryCount(retryCount);
        }
        return ntityRequest;
    }
}
