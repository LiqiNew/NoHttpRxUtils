package com.liqi.nohttprxutils.interfa;

import android.content.Context;

import java.util.Map;

/**
 * 网络请求方法调用接口
 * Created by LiQi on 2016/12/30.
 */
public interface RequestHttpInterfa<T> {
    /**
     * 请求网络方法
     *
     * @param url       请求地址
     * @param mapParame 请求参数集合（null断定是Get请求，非null断定为POST请求）
     * @param mapHeader 请求头集合（允许为null）
     * @param tClass    数据获取成功转换对象
     */
    public void request(String url, Map<String, Object> mapParame, Map<String, String> mapHeader, Class<T> tClass);

    /**
     * 获取上下文
     *
     * @return
     */
    public Context getContext();
}
