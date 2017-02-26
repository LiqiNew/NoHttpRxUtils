/*
 * Copyright © Yan Zhenjie. All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.liqi.nohttputils.nohttp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.liqi.nohttputils.nohttp.gsonutils.JsonUtil;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.RestRequest;
import com.yanzhenjie.nohttp.rest.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Locale;

/**
 * <p>自定义JavaBean请求。</p>
 *
 * @param <T> 请求成功后的数据转换对象
 *            Created by Liqi on 2016/10/15.
 */
class RequestBeanObj<T> extends RestRequest<T> {

    /**
     * Decoding lock so that we don't decode more than one image at a time (to avoid OOM's).
     */
    private static final Object DECODE_LOCK = new Object();
    private Class<T> clazz;
    //请求的bitmap最大宽度
    private int mMaxWidth = 1000;
    //请求的bitmap最大高度
    private int mMaxHeight = 1000;
    //bitmap配置
    private Bitmap.Config mDecodeConfig = Bitmap.Config.ARGB_8888;
    //bitmap比例
    private ImageView.ScaleType mScaleType = ImageView.ScaleType.CENTER_INSIDE;

    RequestBeanObj(String url, Class<T> clazz) {
        this(url, RequestMethod.GET, clazz);
    }

    RequestBeanObj(String url, RequestMethod requestMethod, Class<T> clazz) {
        super(url, requestMethod);
        this.clazz = clazz;
    }

    /**
     * bitmap创建GET请求对象函数
     *
     * @param url          URL地址
     * @param maxWidth     请求的bitmap最大宽度
     * @param maxHeight    请求的bitmap最大高度
     * @param decodeConfig bitmap配置
     * @param scaleType    bitmap比例
     */
    RequestBeanObj(String url, int maxWidth, int maxHeight, Bitmap.Config decodeConfig, ImageView.ScaleType scaleType, Class<T> clazz) {
        this(url, RequestMethod.GET, maxWidth, maxHeight, decodeConfig, scaleType, clazz);
    }

    /**
     * bitmap创建自定义请求对象函数
     *
     * @param url           URL地址
     * @param requestMethod 请求模式
     * @param maxWidth      请求的bitmap最大宽度
     * @param maxHeight     请求的bitmap最大高度
     * @param decodeConfig  bitmap配置
     * @param scaleType     bitmap比例
     */
    RequestBeanObj(String url, RequestMethod requestMethod, int maxWidth, int maxHeight, Bitmap.Config decodeConfig, ImageView.ScaleType scaleType, Class<T> clazz) {
        super(url, requestMethod);
        if (maxWidth >= 0)
            this.mMaxWidth = maxWidth;
        if (maxHeight >= 0)
            this.mMaxHeight = maxHeight;
        if (null != decodeConfig)
            this.mDecodeConfig = decodeConfig;
        if (null != scaleType)
            this.mScaleType = scaleType;
        this.clazz = clazz;
        setAccept("image/*");
    }

    private RequestBeanObj(String url, RequestMethod requestMethod) {
        super(url, requestMethod);
    }

    private static int getResizedDimension(int maxPrimary, int maxSecondary, int actualPrimary, int actualSecondary, ImageView.ScaleType scaleType) {

        // If no dominant value at all, just return the actual.
        if ((maxPrimary == 0) && (maxSecondary == 0)) {
            return actualPrimary;
        }

        // If ScaleType.FIT_XY fill the whole rectangle, ignore ratio.
        if (scaleType == ImageView.ScaleType.FIT_XY) {
            if (maxPrimary == 0) {
                return actualPrimary;
            }
            return maxPrimary;
        }

        // If primary is unspecified, scale primary to match secondary's scaling ratio.
        if (maxPrimary == 0) {
            double ratio = (double) maxSecondary / (double) actualSecondary;
            return (int) (actualPrimary * ratio);
        }

        if (maxSecondary == 0) {
            return maxPrimary;
        }

        double ratio = (double) actualSecondary / (double) actualPrimary;
        int resized = maxPrimary;

        // If ScaleType.CENTER_CROP fill the whole rectangle, preserve aspect ratio.
        if (scaleType == ImageView.ScaleType.CENTER_CROP) {
            if ((resized * ratio) < maxSecondary) {
                resized = (int) (maxSecondary / ratio);
            }
            return resized;
        }

        if ((resized * ratio) > maxSecondary) {
            resized = (int) (maxSecondary / ratio);
        }
        return resized;
    }

    // Visible for testing.
    private int findBestSampleSize(int actualWidth, int actualHeight, int desiredWidth, int desiredHeight) {
        double wr = (double) actualWidth / desiredWidth;
        double hr = (double) actualHeight / desiredHeight;
        double ratio = Math.min(wr, hr);
        float n = 1.0f;
        while ((n * 2) <= ratio) {
            n *= 2;
        }
        return (int) n;
    }

    @Override
    public T parseResponse(Headers responseHeaders, byte[] responseBody) throws Exception {
        String response = StringRequest.parseResponseString(responseHeaders, responseBody);
        if (null != clazz) {
            Logger.e("parseResponse是否执行了>>>>>" + clazz.getName());
            //不是bitmap和byte[]进入
            if (clazz != Bitmap.class && clazz != byte[].class) {
                //不是JSONObject和JSONArray类型进入
                if (clazz != JSONObject.class && clazz != JSONArray.class) {
                    if (clazz == String.class) {
                        return (T) response;
                    } else
                        // 这里如果数据格式错误，或者解析失败，会在失败的回调方法中返回 ParseError 异常。
                        return JsonUtil.jsonToBean(response, clazz);
                } else {
                    if (clazz == JSONArray.class) {
                        return (T) new JSONArray(response);
                    } else if (clazz == JSONObject.class) {
                        return (T) new JSONObject(response);
                    }
                }
            } else {
                //是bitmap类型就转换bitmap类型
                if (clazz == Bitmap.class) {
                    return getBitmap(responseBody);
                }
                //是byte[]类型就转换byte[]类型
                else if (clazz == byte[].class) {
                    return (T) (responseBody == null ? new byte[0] : responseBody);
                }
            }
        }
        return (T) response;
    }

    /**
     * 获取bitmap对象
     *
     * @param responseBody bitmap对象数组
     * @return
     */
    private T getBitmap(byte[] responseBody) {
        synchronized (DECODE_LOCK) {
            T bitmap = null;
            if (responseBody != null) {
                try {
                    bitmap = (T) doResponse(responseBody);
                } catch (OutOfMemoryError e) {
                    String errorMessage = String.format(Locale.US, "Caught OOM for %d byte image, url=%s", responseBody.length, url());
                    Logger.e(e, errorMessage);
                }
            }
            return bitmap;
        }
    }

    /**
     * The real guts of AnalyzeResponse. Broken out for readability.
     */
    private Bitmap doResponse(byte[] byteArray) throws OutOfMemoryError {
        BitmapFactory.Options decodeOptions = new BitmapFactory.Options();
        Bitmap bitmap;
        if (mMaxWidth == 0 && mMaxHeight == 0) {
            decodeOptions.inPreferredConfig = mDecodeConfig;
            bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, decodeOptions);
        } else {
            decodeOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, decodeOptions);
            int actualWidth = decodeOptions.outWidth;
            int actualHeight = decodeOptions.outHeight;

            int desiredWidth = getResizedDimension(mMaxWidth, mMaxHeight, actualWidth, actualHeight, mScaleType);
            int desiredHeight = getResizedDimension(mMaxHeight, mMaxWidth, actualHeight, actualWidth, mScaleType);

            decodeOptions.inJustDecodeBounds = false;
            decodeOptions.inSampleSize = findBestSampleSize(actualWidth, actualHeight, desiredWidth, desiredHeight);
            Bitmap tempBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, decodeOptions);

            if (tempBitmap != null && (tempBitmap.getWidth() > desiredWidth || tempBitmap.getHeight() > desiredHeight)) {
                bitmap = Bitmap.createScaledBitmap(tempBitmap, desiredWidth, desiredHeight, true);
                tempBitmap.recycle();
            } else {
                bitmap = tempBitmap;
            }
        }
        return bitmap;
    }
}
