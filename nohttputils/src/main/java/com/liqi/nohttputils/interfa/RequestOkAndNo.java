package com.liqi.nohttputils.interfa;

/**请求网络成功，回调接口
 * Created by LiQi on 2016/12/8.
 */
public interface RequestOkAndNo <T> {
    public void onNext(T response);
    public void onError(Throwable e);
}
