package com.liqi.nohttprxutils;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.liqi.nohttprxutils.base.BaseActivity;
import com.liqi.nohttputils.RxNoHttpUtils;
import com.liqi.nohttputils.interfa.OnIsRequestListener;
import com.liqi.nohttputils.nohttp.rx_poll.model.RxInformationModel;
import com.liqi.nohttputils.nohttp.rx_poll.operators.OnObserverEventListener;
import com.yanzhenjie.nohttp.rest.RestRequest;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * 轮询请求演示界面
 * Created by LiQi on 2017/9/12.
 */

public class PollDemo extends BaseActivity<String> implements View.OnClickListener {
    private Button mStartPollNohttpThree, mStopPollNohttpThree, mStartPoll, mStopPoll;
    private TextView content;
    private Object[] mSign = new Object[]{new Object(), new Object(), new Object()};
    private boolean isDiy;

    @Override
    protected void onCreate() {
        setContentView(R.layout.poll_demo_activity);
        mStartPollNohttpThree = $(R.id.start_poll_nohttp_three);
        mStartPollNohttpThree.setOnClickListener(this);
        mStartPollNohttpThree.setAlpha(0.6f);

        mStopPollNohttpThree = $(R.id.stop_poll_nohttp_three);
        mStopPollNohttpThree.setOnClickListener(this);
        mStopPollNohttpThree.setAlpha(0.6f);


        mStartPoll = $(R.id.start_poll);
        mStartPoll.setOnClickListener(this);
        mStartPoll.setAlpha(0.6f);

        mStopPoll = $(R.id.stop_poll);
        mStopPoll.setOnClickListener(this);
        mStopPoll.setAlpha(0.6f);

        content = $(R.id.content);
       // SSLUtils.fixSSLLowerThanLollipop(socketFactory);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //开启三个NoHttp网络轮询请求
            case R.id.start_poll_nohttp_three:
                isDiy = false;
                //停止所有轮询
                RxNoHttpUtils.cancelPoll(mSign);
                //开始轮询请求 --->>线程1111
                RxNoHttpUtils.rxNohttpRequest()
                        .post()
                        .url(StaticHttpUrl.POST_URL)
                        .addParameter("userName", "LiQi")
                        .addParameter("userPass", "LiQi.pass")
                        .addParameter("userAge", 20)
                        .addParameter("userSex", "1")
                        // .setDialogGetListener(this)
                        .setSign(mSign[0])
                        //构建轮询请求
                        .builderPoll(String.class, new OnIsRequestListener<String>() {
                            @Override
                            public void onNext(String response) {
                                String trim = content.getText().toString().trim();
                                content.setText(trim + "\n\n轮询名称：线程1111\n网络请求结果：" + response);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        })
                        .setInitialDelay(3 * 1000)
                        .setPeriod(5 * 1000)
//                        .setBooleanFunc1(new Func1<RxInformationModel<String>, Boolean>() {
//                            @Override
//                            public Boolean call(RxInformationModel<String> stringRxInformationModel) {
//                                return null;
//                            }
//                        })
                        .switchPoll()
                        .requestRxNoHttp();

                //开始轮询请求 --->>线程2222
                RxNoHttpUtils.rxNohttpRequest()
                        .post()
                        .url(StaticHttpUrl.POST_URL)
                        .addParameter("userName", "LiQi")
                        .addParameter("userPass", "LiQi.pass")
                        .addParameter("userAge", 20)
                        .addParameter("userSex", "1")
                        // .setDialogGetListener(this)
                        .setSign(mSign[1])
                        //构建轮询请求
                        .builderPoll(String.class, new OnIsRequestListener<String>() {
                            @Override
                            public void onNext(String response) {
                                String trim = content.getText().toString().trim();
                                content.setText(trim + "\n\n轮询名称：线程2222\n网络请求结果：" + response);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        })
                        .setInitialDelay(5 * 1000)
                        .setPeriod(7 * 1000)
//                        .setBooleanFunc1(new Func1<RxInformationModel<String>, Boolean>() {
//                            @Override
//                            public Boolean call(RxInformationModel<String> stringRxInformationModel) {
//                                return null;
//                            }
//                        })
                        .switchPoll()
                        .requestRxNoHttp();


                //开始轮询请求 --->>线程3333
                RxNoHttpUtils.rxNohttpRequest()
                        .post()
                        .url(StaticHttpUrl.POST_URL)
                        .addParameter("userName", "LiQi")
                        .addParameter("userPass", "LiQi.pass")
                        .addParameter("userAge", 20)
                        .addParameter("userSex", "1")
                        //.setDialogGetListener(this)
                        .setSign(mSign[2])
                        //构建轮询请求
                        .builderPoll(String.class, new OnIsRequestListener<String>() {
                            @Override
                            public void onNext(String response) {
                                String trim = content.getText().toString().trim();
                                content.setText(trim + "\n\n轮询名称：线程3333\n网络请求结果：" + response);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        })
                        .setInitialDelay(7 * 1000)
                        .setPeriod(9 * 1000)
//                        .setBooleanFunc1(new Func1<RxInformationModel<String>, Boolean>() {
//                            @Override
//                            public Boolean call(RxInformationModel<String> stringRxInformationModel) {
//                                return null;
//                            }
//                        })
                        .switchPoll()
                        .requestRxNoHttp();
                break;
            //停止第二个NoHttp网络轮询请求
            case R.id.stop_poll_nohttp_three:
                RxNoHttpUtils.cancelPoll(mSign[1]);
                break;
            //开启自定义轮询
            case R.id.start_poll:
                //停止所有轮询
                RxNoHttpUtils.cancelPoll(mSign);
                isDiy = false;
                //开始自定义轮询
                RxNoHttpUtils.rxNohttpRequest()
                        .post()
                        .url(StaticHttpUrl.POST_URL)
                        .addParameter("userName", "LiQi")
                        .addParameter("userPass", "LiQi.pass")
                        .addParameter("userAge", 20)
                        .addParameter("userSex", "1")
                        .setAnUnknownErrorHint("Poll自定义请求未知错误提示")
                        //.setDialogGetListener(this)
                        .setSign(this)
                        //构建轮询请求
                        .builderPoll(String.class, new OnIsRequestListener<String>() {
                            @Override
                            public void onNext(String response) {
                                if (!isDiy) {
                                    isDiy = true;
                                    content.setText("轮询名称：自定义轮询\n框架原生调用轮询结果：" + response);
                                } else {
                                    String trim = content.getText().toString().trim();
                                    content.setText(trim + "\n\n轮询名称：自定义轮询\n框架原生调用轮询结果：" + response);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        })
                        .setInitialDelay(3 * 1000)
                        .setPeriod(5 * 1000)

                        .setOnObserverEventListener(new OnObserverEventListener<RestRequest<String>, RxInformationModel<String>>() {
                            @Override
                            public RxInformationModel<String> onObserverEvent(RestRequest<String> transferValue) {
                                Log.e("外部实现轮询运行","外部实现轮询运行开始>>>");
                                RxInformationModel<String> informationModel=new RxInformationModel<>();
                                informationModel.setData("<<<外部现实轮询>>>");
                                return informationModel;
                            }
                        })

                        .setBooleanFunc1(new Predicate<RxInformationModel<String>>() {
//                            @Override
//                            public Boolean call(RxInformationModel<String> stringRxInformationModel) {
//                                Log.e("外部实现轮询拦截","外部实现轮询拦截>>>拦截状态："+stringRxInformationModel.isStop());
//                                return stringRxInformationModel.isStop();
//                            }

                            @Override
                            public boolean test(RxInformationModel<String> stringRxInformationModel) throws Exception {
                                Log.e("外部实现轮询拦截","外部实现轮询拦截>>>拦截状态："+stringRxInformationModel.isStop());
                                return stringRxInformationModel.isStop();
                            }
                        })

                        .setRxInformationModelAction1(new Consumer<RxInformationModel<String>>() {
//                            @Override
//                            public void call(RxInformationModel<String> stringRxInformationModel) {
//                                String data = stringRxInformationModel.getData();
//                                Log.e("外部实现轮询完毕","外部实现轮询运行完毕>>>运行结果："+data);
//                                if (!isDiy) {
//                                    isDiy = true;
//                                    content.setText("轮询名称：自定义轮询\n外部实现调用轮询结果：" + data);
//                                } else {
//                                    String trim = content.getText().toString().trim();
//                                    content.setText(trim + "\n\n轮询名称：自定义轮询\n外部实现调用轮询结果：" + data);
//                                }
//                            }

                            @Override
                            public void accept(RxInformationModel<String> stringRxInformationModel) throws Exception {
                                String data = stringRxInformationModel.getData();
                                Log.e("外部实现轮询完毕","外部实现轮询运行完毕>>>运行结果："+data);
                                if (!isDiy) {
                                    isDiy = true;
                                    content.setText("轮询名称：自定义轮询\n外部实现调用轮询结果：" + data);
                                } else {
                                    String trim = content.getText().toString().trim();
                                    content.setText(trim + "\n\n轮询名称：自定义轮询\n外部实现调用轮询结果：" + data);
                                }
                            }
                        })
                        .switchPoll()
                        .requestRxNoHttp();
                break;
            //停止自定义轮询
            case R.id.stop_poll:
                //如果重新赋值OnObserverEventListener，手动取消无用
                RxNoHttpUtils.cancelPoll(this);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxNoHttpUtils.cancelPoll(mSign);
    }
}
