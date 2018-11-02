[![](https://jitpack.io/v/liqinew/nohttprxutils.svg)](https://jitpack.io/#liqinew/nohttprxutils)
[![](https://img.shields.io/badge/%E4%BD%9C%E8%80%85-%E6%9D%8E%E5%A5%87-orange.svg)](https://github.com/LiqiNew)
<br>**---- v.2.0.5版本新增功能 ----**
* **兼容到NoHttp-v.1.1.11**
* **修复BUG**修复因为下载时未传入文件名称而导致下载文件不可用的BUG
* **NohttpDownloadUtils** 文件下载工具对象新增getDownloadRequestsUrl()获取下载请求What值对应的路径方法


### 由于NoHttpRxUtils是通过RxJava对NoHttp网络框架操作进行一系列封装。<br>首先对RxJava和NoHttp网络框架做一个简介
# RxJava框架是什么?
RxJava是响应式程序设计的一种实现。<br>
在响应式程序设计中，当数据到达的时候，消费者做出响应。<br>
响应式编程可以将事件传递给注册了的观察者。<br><br>
[RxJava框架GitHub(ReactiveX)](https://github.com/ReactiveX/RxJava)
# NoHttp框架是什么?
### Nohttp是一个专门针对Android网络通讯的框架
#### Nohttp框架特性
比Retrofit使用更简单、更易用。<br><br>
动态配置底层框架为OkHttp、HttpURLConnection<br><br>
支持异步请求、支持同步请求<br><br>
多文件上传，支持大文件上传，表单提交数据<br><br>
文件下载、上传下载、上传和下载的进度回调、错误回调<br><br>
支持Json、xml、Map、List的提交<br><br>
完美的Http缓存模式，可指定缓存到数据库、SD卡，缓存数据已安全加密<br><br>
自定义Request，直接请求JsonObject、JavaBean等<br><br>
Cookie的自动维持，App重启、关开机后还持续维持<br><br>
http 301 302 303 304 307重定向，支持多层嵌套重定向<br><br>
Https、自签名网站Https的访问、支持双向验证<br><br>
失败重试机制，支持请求优先级<br><br>
GET、POST、PUT、PATCH、HEAD、DELETE、OPTIONS、TRACE等请求协议<br><br>
用队列保存请求，平均分配多线程的资源，支持多个请求并发<br><br>
支持取消某个请求、取消指定多个请求、取消所有请求<br><br>
支持断点续传和断点处继续下载<br><br>
[NoHttp框架GitHub(严振杰)](https://github.com/yanzhenjie/NoHttp)
##### 欢迎加入NoHttp作者QQ技术交流群：46523908
<a target="_blank" href="//shang.qq.com/wpa/qunwpa?idkey=28760218432b83451cf4849d585ccfe282f3ef136c44446f55720b6de4f98546"><img border="0" src="http://pub.idqqimg.com/wpa/images/group.png" alt="Android 愚公移山1群" title="Android 愚公移山1群"></a>（点击图标，可以直接加入)
# NoHttpRxUtils框架是什么?
NoHttpRxUtils主要是通过RxJava框架对NoHttp网络框架操作进行再次封装。<br>
减轻使用者繁琐的调用，让使用者更专注于项目业务，而非客户端与服务器之间的网络通讯。<br><br>
由于NoHttpRxUtils网络请求方面是采用NoHttp同步请求，所以请求队列不是NoHttp队列算法而是NoHttpRxUtils队列算法。<br>
暂时不支持队列请求优先级设置。如果要想让一个请求优先,可以使用RxJava线程单一请求。<br>
由于Android系统对SD卡写入权限的约束，所以所有的缓存数据路径都指向于数据库。

##### NoHttpRxUtils框架功能
* 框架初始化、网络请求、文件下载调用方式全采用链式。
* 数据请求方面，支持RxJava线程队列请求和Rx线程单一请求。
* RxJava线程请求队列，"RxJava线程队列"是通过算法自定义实现'线程池',然后通过'线程池'对RxJava线程进行管理和操作。支持撤销单个请求、多个请求，指定请求。
* RxJava线程单一请求，直接开启一个RxJava线程，不进行队列优先直接请求。不可撤销。
* 文件下载队列是NoHttp队列算法。把'NoHttp文件下载'捆绑封装进Android-Service中。支持单个下载任务取消(暂停)、多个下载任务取消、单个下载任务恢复(开始)、多个下载任务恢复(开始)。
* 支持自动通过指定对象去得到服务器返回的数据。Json格式数据转换层采用Gson框架转换(转换对象中定义的数据结构必须要符合Json数据结构)
* 支持轮询请求，并支持撤销单个轮询请求、多个轮询请求、指定轮询请求。
* 支持使用其它网络框架去轮询请求

# 为了你使用更加清晰明了,请仔细阅读下面使用教程
##### 框架内部使用RxJava版本是RxJava-1.1.9和RxJava-2.1.5
##### 框架内部使用NoHttp版本是NoHttp-1.1.4
##### 框架内部使用Gson版本是Gson-2.8.0

如何远程依赖
-----
#### Gradle
**1：在项目根目录build.gradle**	<br>

```gradle allprojects {
repositories {
  　　//依赖仓库
　　　maven { url 'https://jitpack.io' }
　　}
}
```

**2：项目目录build.gradle中依赖NoHttpRxUtils框架**<br>
* 使用RxJava-1的项目请依赖
```gradle
compile 'com.github.liqinew:nohttprxutils:v.2.0.5'
```

* 使用RxJava-2的项目请依赖
```gradle
compile 'com.github.liqinew:nohttprxutils:v.2.0.6'
```

NoHttpRxUtils使用简介
-----
### 链式初始化NoHttp，建议放到Application中onCreate生命周期方法里面
```java
//初始化NoHttp（在此处其实可以调用setDialogGetListener设置全局请求加载框）
 RxNoHttpUtils.rxNoHttpInit(getApplicationContext())
 
              //是否维护Cookie
              .setCookieEnable(false)
              
              //是否缓存进数据库DBCacheStore
              .setDbEnable(true)
              
              //是否开启debug调试
              .isDebug(true)
              
              //设置debug打印Name
              .setDebugName("LiQi-NoHttpUtils")
              
              //设置全局连接超时时间。单位秒，默认30s。
              .setConnectTimeout(40)
              
              //设置全局服务器响应超时时间，单位秒，默认30s。
              .setReadTimeout(40)
              
              //设置下载线程池并发数量(默认并发数量是3)
              .setThreadPoolSize(3)
              
              //设置网络请求队列并发数量(默认并发数量是3)
              .setRunRequestSize(4)
              
              //设置全局默认加载对话框
              //注：传入的context必须是栈顶的context。你可以通过方法registerActivityLifecycleCallbacks()去获取。
              //注：不允许在此方法里面创建dialog对象。
              .setDialogGetListener("全局加载框获取接口")
              
              //设置底层用那种方式去请求
              .setRxRequestUtilsWhy(NoHttpInit.OKHTTP)
              
              //设置全局带证书安全协议请求（如果在请求调用的时候切换了安全协议方式，允许覆盖此设置）
              .setInputStreamSSL(new InputStream())
              
              //设置全局无证书安全协议请求（如果在请求调用的时候切换了安全协议方式，允许覆盖此设置）
              .setInputStreamSSL()
              
              //添加全局请求头
              .addHeader("app>>head","app_head_global")
              
              //添加全局请求参数-只支持String类型
              .addParam("app_param","app_param_global")
              
              //设置Cookie管理监听。
              .setCookieStoreListener(new DBCookieStore.CookieStoreListener())
              
              //设置全局主机验证（如果在请求调用的时候切换了无证书安全协议方式，此设置失效）
              .setHostnameVerifier(new HostnameVerifier())
              
              //设置全局重试次数，配置后每个请求失败都会重试设置的次数。
              //.setRetry(5)

              //设置全局请求网络出现未知错误提示语
              .setAnUnknownErrorHint("全局未知错误提示语")

              //开始初始化NoHttp
              .startInit();
```

### 如何使用NoHttpRxUtils去数据下载?
#### * 下载开启(链式调用)

```java
//获取下载请求构建器
NoHttpDownloadUtils.getNoHttpDownloadBuild()

                    //添加下载文件参数
                   .addDownloadParameter(DOWNLOAD_URL, "Download_Name.apk")
                   
                   //设置是否断点续传下载
                   .setRange(true)
                   
                   //设置下载进度监听接口
                   .setDownloadListener(new DownloadListener())
                   
                   //设置在指定的文件夹发现同名的文件是否删除后重新下载
                   .setDeleteOld(false)
                   
                   //设置下载文件存储文件路径
                   .setFileFolder(FILEPATH)
                   
                   //单个请求设置读取时间(单位秒，默认以全局读取超时时间。)
                   .setReadTimeout(40)
                   
                   //单个请求设置链接超时时间(单位秒，默认以全局链接超时时间。)
                   .setConnectTimeout(40)
                   
                   //单个请求设置请求失败重试次数,默认以全局请求失败重试次数。
                   .setRetryCount(3)
                   
                   //开启下载
                   .satart(new Activity());
```

#### * 下载暂停(取消)

```java
//暂停全部正在下载任务
NoHttpDownloadUtils.cancelAll();

//暂停指定下载任务
NoHttpDownloadUtils.cancel(downloadUrl);
```

#### * 下载恢复

```java
//恢复指定下载
NoHttpDownloadUtils.startRequest(downloadUrl);

//恢复全部下载
NoHttpDownloadUtils.startAllRequest();
```

#### * 下载Url对应的what值操作方法

```java
//获取下载URL对应的'What'值
NoHttpDownloadUtils.getDownloadRequestsWhat(downloadUrl);

//移除下载URL对应的'What'值
NoHttpDownloadUtils.removeWhatData(downloadUrl);

//移除全部下载What
NoHttpDownloadUtils.removeWhatAll();
```

#### * 手动清空下载请求，并关闭下载服务

```java
//清空当前下载请求对象,并停止服务
NoHttpDownloadUtils.clearAll();
```

##### 注：如果上一次下载任务没有完成或者没有清空，那么下一次点击任何下载都会继续执行上一次没有完成的任务继续下载
### 如何使用NoHttpRxUtils去数据请求?
#### * NoHttpRxUtils网络请求，采用链式调用
```java
//获取请求对象
RxNoHttpUtils.rxNoHttpRequest()

             //get请求方式（除了get和post请求，还支持put，delete，head，patch，options，trace）
             .get()
             //post请求方式（除了get和post请求，还支持put，delete，head，patch，options，trace）
             .post()
             
             //设置Url
             .url("url")
             
             //添加请求参数
             //当传入的参数类型不属于内部设定类型时，默认调用Object的toString()转换为String类型参数
             .addParameter()
             
             //添加请求头
             .addHeader()
             
             //设置请求bodyEntity为StringEntity，并传请求类型。
             .requestStringEntity(Content-Type)
             
             //为StringEntity添加body中String值
             .addStringEntityParameter("请求的String")
             
             //从bodyEntity切换到请求配置对象
             .transitionToRequest()
             
             //设置请求bodyEntity为JsonObjectEntity.json格式：{"xx":"xxx","yy":"yyy"}
             .requestJsonObjectEntity()
             
             //给JsonObjectEntity添加参数和值
             .addEntityParameter("key","Valu")
             
             //从bodyEntity切换到请求配置对象
             .transitionToRequest()
             
             //设置请求bodyEntity为JsonListEntity.json格式：[{"xx":"xxx"},{"yy":"yyy"}]
             .requestJsonListEntity()
             
             //给JsonList创造对象，并传键值参数
             .addObjectEntityParameter("key","Valu")
             
             //在创造的对象上添加键值参数
             .addEntityParameter("key","Valu")
             
             //把创造对象刷进进JsonList里面
             //注：如果需要在集合中创建多个对象，可以在此方法后继续调用addObjectEntityParameter()去创建。
             //流程跟上面一致。
             .objectBrushIntoList()
             
             //从bodyEntity切换到请求配置对象
             .transitionToRequest()
             
             //设置请求bodyEntity为InputStreamEntity
             .requestInputStreamEntity(Content-Type)
             
             //给InputStreamEntity添加输入流
             .addEntityInputStreamParameter(InputStream)
             
             //从bodyEntity切换到请求配置对象
             .transitionToRequest()
             
             //单个请求设置读取时间(单位秒，默认以全局读取超时时间。)
             .setReadTimeout(40)
             
             //单个请求设置链接超时时间(单位秒，默认以全局链接超时时间。)
             .setConnectTimeout(30)
             
             //单个请求设置请求失败重试计数。默认值是0,也就是说,失败后不会再次发起请求。
             .setRetryCount(3)
             
             //单个请求设置缓存key
             .setCacheKey("get请求Key")
             
             //单个请求设置缓存模式(跟原生NoHttp五种缓存模式一致)
             .setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE)
             
             //设置当前请求是否添加进Rx"线程池"队列中，默认是添加rx"线程池"中.
             //!!如果设置false,请求线程不经过Rx"线程池"队列直接请求.只针对数据请求,不针对轮询请求。
             .setQueue(false)
             
             //设置Rx"线程池"队列标识.
             //(标识设置请保证唯一.!!如果setQueue(false)设置为false,setSign(标识对象)设置无任何作用)
             .setSign(new Object())
             
             //添加HTTPS协议无证书参数
             .addHttpsIsCertificate()
             
             //添加HTTPS协议有证书参数
             .addHttpsIsCertificate(new InputStream)
             
             //设置请求图片的最大宽高
             .setBitmapMaxWH(500,500)
             
             //设置请求位图的配置和比例
             .setBitmapConfigType(Bitmap.Config, ImageView.ScaleType)
             
             //设置请求加载框。
             //注：如果此处没有设置加载框，那么就默认使用全局设置的加载框。
             //如果全局设置也没有设置加载框，那么就不显示加载框和内置提示语。
             //注：不允许在此方法里面创建dialog对象。
             .setDialogGetListener(new Dialog())

             //设置请求网络出现未知错误提示语
             .setAnUnknownErrorHint("未知错误提示语")

             //创建请求对象指定响应数据转换类型，然后在设置请求成功或者失败回调接口
             .builder(Objects.class,new OnIsRequestListener<T>)
             
             //开始请求
             .requestRxNoHttp();
```

#### * 手动取消Rx"线程池"中队列请求(注：setQueue(false)如果设置为false，手动取消将失去作用)

```java
//单个取消Sign对应的请求
RxNoHttpUtils.cancel(Sign));

//取消批量Sign对应的请求
RxNoHttpUtils.cancel(Sign[]);

//取消RX"线程池"中所有的请求
// RxNoHttpUtils.cancelAll();
```

#### * NoHttpRxUtils轮询请求，采用链式调用

```java
//获取请求对象
RxNoHttpUtils.rxNoHttpRequest()

             //NoHttp网络请求设置参数跟上面一样设置
             ...
             
             //设置当前轮询请求Sign
             .setSign(new Object())
             
             //创建轮询请求对象，并指定响应数据转换类型和请求成功或者失败回调接口
             .builderPoll(Objects.class,new OnIsRequestListener<T>)
             
             //设置第一次加载延迟
             .setInitialDelay(3 * 1000)
             
             //设置轮询间隔时间-默认3秒
             .setPeriod(5 * 1000)
             
             //设置被观察者产生的行为事件监听器
             //(如果此处实现被观察者产生的行为事件监听器，
             //那么框架内部就不去维护此轮询请求，必须实现轮询拦截器接口去维护此轮询什么时候停止。
             //RxNoHttpUtils.cancelPoll()取消轮询将无效，设置内部加载框将无效)
             .setOnObserverEventListener(new OnObserverEventListener<RestRequest<T>, RxInformationModel<T>>(){
                     @Override
                  public RxInformationModel<T> onObserverEvent(RestRequest<T> transferValue) {

                    // RxInformationModel<T>对象方法介绍
                    //getData()=获取请求数据
                    //setData(T data)=赋值请求数据
                    //setException(boolean exception)=赋值是否是异常状态
                    //isException()=获取是否异常状态
                    //setThrowable(Throwable throwable)=赋值异常类
                    //getThrowable()=获取异常类
                    //setStop(boolean stop)=赋值是否停止轮询状态
                    //isStop()=获取是否轮询状态
                    //在此方法里面RxInformationModel<T>对象需要new出来.

                    //在此方法中可以换成自己钟意的网络框架去请求。
                    //如果上面设置网络请求参数，除了body其它的都能从RestRequest<Objects>里面取得。

                   return informationModel;
                  }
             })
             
             // 设置设置数据拦截监听对象
             .setBooleanFunc1(new Func1<RxInformationModel<T>, Boolean>() {
                      @Override
                   public Boolean call(RxInformationModel<T> stringRxInformationModel) {

                  //在此方法里面可以根据RxInformationModel<T>.getData()获取请求的数据，
                  //然后根据请求的数据来决定是否停止轮询

                    return stringRxInformationModel.isStop();
                   }
             })
             
             //设置观察者根据被观察产生的行为做出相应处理监听器
             //如果实现了此接口，那么builderPoll中实现的OnIsRequestListener将无效。
            .setRxInformationModelAction1(new Action1<RxInformationModel<T>>() {
                @Override
               public void call(RxInformationModel<T> stringRxInformationModel) {

                //在此方法里面根据RxInformationModel<T>中的数据做出相应动作

               }
             })
             
             //转换成轮询请求类
             .switchPoll()
             
             //开始请求
             .requestRxNoHttp();
```

#### * 手动取消轮询请求

```java
//单个取消Sign对应的轮询请求
RxNoHttpUtils.cancelPoll(Sign));

//取消批量Sign对应的轮询请求
RxNoHttpUtils.cancelPoll(Sign[]);

//取消所有的轮询请求
RxNoHttpUtils.cancelPollAll();
```

#### * 手动清除缓存

```java
//清除对应的key的缓存数据
RxNoHttpUtils.removeKeyCacheData("Cachekey");

//清除所有缓存数据
RxNoHttpUtils.removeAllCacheData();
```

##### 如果觉得不错，请star给我动力。<br> <br> 非常感谢。
### NoHttpRxUtils数据请求方面，请查看我的博客文档。
[NoHttpRxUtils网络通讯框架](http://www.jianshu.com/p/61d3eaecc7ca)
### NoHttpRxUtils轮询请求方面，请查看我的博客文档。
[基于RxJava-1源码扩展的轮询器](http://www.jianshu.com/p/2aa5855425c8)
### 基于RxJava-1源码扩展的轮询器GitHub开源项目。
[RxJavaExpandPoll-GitHub开源项目](https://github.com/LiqiNew/RxJavaExpandPoll)
### 由于NoHttpRxUtils请求中Rx"线程池"是自己写的队列算法,要了解的请看我写的Rx"线程池"项目。
[RxThreadPool-RxJava线程池案例](https://github.com/LiqiNew/RxThreadPool)

# License

    Copyright 2016 Liqi

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
