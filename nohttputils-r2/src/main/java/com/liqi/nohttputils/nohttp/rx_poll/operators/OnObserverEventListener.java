package com.liqi.nohttputils.nohttp.rx_poll.operators;

/**被观察者行动事件
 * Created by LiQi on 2017/9/6.
 */

public interface OnObserverEventListener<V,T> {
    T onObserverEvent(V transferValue);
}
