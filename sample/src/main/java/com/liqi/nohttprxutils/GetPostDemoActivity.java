package com.liqi.nohttprxutils;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.liqi.nohttprxutils.base.BaseActivity;
import com.liqi.nohttputils.RxNoHttpUtils;

/**
 * GET POST请求演示界面
 * Created by LiQi on 2016/12/30.
 */
public class GetPostDemoActivity extends BaseActivity<String> implements View.OnClickListener {
    private Button mGet, mPost;
    private TextView mContent;

    @Override
    protected void onCreate() {
        setContentView(R.layout.get_post_demo_activity);
        mGet = $(R.id.get);
        mGet.setOnClickListener(this);
        mGet.setAlpha(0.6f);
        mPost = $(R.id.post);
        mPost.setOnClickListener(this);
        mPost.setAlpha(0.6f);
        mContent = $(R.id.content);
        //清除对应的key的缓存数据
        // RxNoHttpUtils.removeKeyCacheData("Cachekey");
        //清除所有缓存数据
        //  RxNoHttpUtils.removeAllCacheData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get:
                //开始请求
                RxNoHttpUtils.rxNohttpRequest()
                        .get()
                        .url(StaticHttpUrl.LOGIN)
                        .addParameter("pageNum",1)
                        .addParameter("pageSize",10)
                        .setOnDialogGetListener(this)
                        .setQueue(false)
                        //单个请求设置读取时间(单位秒，默认以全局读取超时时间。)
                        // .setReadTimeout(40)
                        //单个请求设置链接超时时间(单位秒，默认以全局链接超时时间。)
                        // .setConnectTimeout(30)
                        //单个请求设置请求失败重试计数。默认值是0,也就是说,失败后不会再次发起请求。
                        //.setRetryCount(3)
                        //单个请求设置缓存key
                        //.setCacheKey("get请求Key")
                        //单个请求设置缓存模式
                        // .setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE)
                        //设置请求bodyEntity为StringEntity，并传请求类型。
                        //.requestStringEntity(Content-Type)
                        //为StringEntity添加body中String值
                        //.addStringEntityParameter("请求的String")
                        //从bodyEntity切换到请求配置对象
                        // .transitionToRequest()
                        //设置请求bodyEntity为JsonObjectEntity.json格式：{"xx":"xxx","yy":"yyy"}
                        // .requestJsonObjectEntity()
                        //给JsonObjectEntity添加参数和值
                        //.addEntityParameter("key","Valu")
                        //从bodyEntity切换到请求配置对象
                        // .transitionToRequest()
                        //设置请求bodyEntity为JsonListEntity.json格式：[{"xx":"xxx"},{"yy":"yyy"}]
                        //.requestJsonListEntity()
                        //给JsonList创造对象，并传键值参数
                        //.addObjectEntityParameter("key","Valu")
                        //在创造对象的上添加键值参数
                        //.addEntityParameter("key","Valu")
                        //把创造对象刷进进JsonList里面
                        //.objectBrushIntoList()
                        //从bodyEntity切换到请求配置对象
                        //.transitionToRequest()
                        //设置请求bodyEntity为InputStreamEntity
                        //.requestInputStreamEntity(Content-Type)
                        //给InputStreamEntity添加输入流
                        //.addEntityInputStreamParameter(InputStream)
                        //从bodyEntity切换到请求配置对象
                        //.transitionToRequest()
                        .builder(String.class, this)
                        .requestRxNoHttp();
                break;
            case R.id.post:
                //开始请求
                RxNoHttpUtils.rxNohttpRequest()
                        .post()
                        .url(StaticHttpUrl.LOGIN)
                        .addParameter("pageNum",1)
                        .addParameter("pageSize",10)
                        .setOnDialogGetListener(this)
                        .setSign(this)
                        .setAnUnknownErrorHint("POST未知错误提示")
                        .builder(String.class, this)
                        .requestRxNoHttp();
                //RxNoHttpUtils.cancel(this);
                break;
        }
    }

    @Override
    public void onNext(String response) {
        mContent.setText(response);
    }
}
