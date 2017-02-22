package com.liqi.nohttputils.interfa;

/**请求网络成功，回调接口
 * Created by LiQi on 2016/12/8.
 */
public interface RequestOkAndNo <T> {
    /**
     * 请求成功
     * @param response
     */
    public void onNext(T response);

    /**
     * 请求失败
     * @param e
     */
    public void onError(Throwable e);
}
