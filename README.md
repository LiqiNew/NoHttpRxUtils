[![](https://jitpack.io/v/liqinew/nohttprxutils.svg)](https://jitpack.io/#liqinew/nohttprxutils)
### 由于NoHttpRxUtils是通过RxJava-1对NoHttp网络框架操作进行一系列封装。<br>首先对RxJava和NoHttp网络框架做一个简介
# RxJava框架是什么?
RxJava是响应式程序设计的一种实现。<br>
在响应式程序设计中，当数据到达的时候，消费者做出响应。<br>
响应式编程可以将事件传递给注册了的observer。<br><br>
[RxJava框架GitHub(ReactiveX)](https://github.com/ReactiveX/RxJava)
# NoHttp框架是什么?
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
[NoHttp框架GitHub(严振杰)](https://github.com/yanzhenjie/NoHttp)
##### 欢迎加入NoHttp作者QQ技术交流群：46523908
# NoHttpRxUtils框架是什么?
NoHttpRxUtils主要是通过RxJava-1框架对NoHttp网络框架操作进行再次封装。<br>
减轻使用者繁琐的调用，让使用者更专注于项目业务，而非客户端与服务器之间的网络通讯。<br><br>
由于NoHttpRxUtils网络请求方面是采用NoHttp同步请求，所以请求队列不是NoHttp队列而是NoHttpRxUtils队列。<br>
暂时不支持队列请求优先级设置。<br>
由于Android系统对SD卡写入权限的约束，所以所有的缓存数据路径都指向于数据库。

##### NoHttpRxUtils框架功能
* 框架初始化、网络请求、文件下载调用方式全采用链式。
* 数据请求方面，支持RxJava线程队列请求和Rx线程单一请求。
* RxJava线程队列请求=>通过算法实现线程池对RxJava-1框架线程进行管理和操作,支持撤销单个请求、多个请求，指定请求。
* Rx线程单一请求=>直接开启一个RxJava线程，不进行队列优先直接请求。
* 文件下载，针对NoHttp数据下载进行Android-Service捆绑封装，支持单个下载任务取消(暂停)、多个下载任务取消、单个下载任务恢复(开始)、多个下载任务恢复(开始)。
* 支持自动通过指定对象去得到服务器数据。Json格式数据转换层采用Gson框架转换(转换对象中定义的数据结构必须要符合Json数据结构)
* 支持轮询请求,支持撤销单个轮询请求、多个轮询请求、指定轮询请求。
* 支持使用其它网络框架去轮询请求

# 为了你使用更加清晰明了,请仔细阅读下面使用教程
##### 框架内部使用RxJava版本是RxJava-1.1.9
##### 框架内部使用NoHttp版本是NoHttp-1.1.4
##### 框架内部使用Gson版本是Gson-2.8.0

如何远程依赖
-----
#### Gradle
**1：在项目根目录build.gradley**	<br>
allprojects {<br>
　　repositories { <br>
  　　//依赖仓库	<br>		
　　　maven { url 'https://jitpack.io' }<br>
　　}<br>
}<br>
**2：依赖NohttpRxUtils框架**<br>
compile 'com.github.liqinew:nohttprxutils:v.1.3'<br>

NoHttpRxUtils使用简介
-----
### 链式初始化NoHttp，建议放到Application中onCreate生命周期方法里面
//初始化NoHttp（在此处其实可以调用setDialogGetListener设置全局请求加载框）<br>
 RxNoHttpUtils.rxNoHttpInit(getApplicationContext())<br><br>
//是否维护Cookie<br>
.setCookieEnable(false)<br><br>
//是否缓存进数据库DBCacheStore<br>
.setDbEnable(true)<br><br>
//是否开启debug调试<br>
.isDebug(true)<br><br>
//设置debug打印Name<br>
.setDebugName("LiQi-NoHttpUtils")<br><br>
//设置全局连接超时时间。单位秒，默认30s。<br>
//.setConnectTimeout(40)<br><br>
//设置全局服务器响应超时时间，单位秒，默认30s。<br>
//.setReadTimeout(40)<br><br>
//设置下载线程池并发数量(默认并发数量是3)<br>
.setThreadPoolSize(3)<br><br>
//设置网络请求队列并发数量(默认并发数量是3)<br>
.setRunRequestSize(4)<br><br>
//设置全局默认加载对话框<br>
//.setDialogGetListener("全局加载框获取接口")<br><br>
//设置底层用那种方式去请求<br>
.setRxRequestUtilsWhy(NoHttpInit.OKHTTP)<br><br>
//设置全局带证书安全协议请求（如果在请求调用的时候切换了安全协议方式，允许覆盖此设置）<br>
//.setInputStreamSSL(new InputStream())<br><br>
//设置全局无证书安全协议请求（如果在请求调用的时候切换了安全协议方式，允许覆盖此设置）<br>
//.setInputStreamSSL()<br><br>
//添加全局请求头<br>
//.addHeader("app>>head","app_head_global")<br><br>
//添加全局请求参数-只支持String类型<br>
//.addParam("app_param","app_param_global")<br><br>
//设置Cookie管理监听。<br>
//.setCookieStoreListener(new DBCookieStore.CookieStoreListener())<br><br>
//设置全局主机验证（如果在请求调用的时候切换了无证书安全协议方式，此设置失效）<br>
//.setHostnameVerifier(new HostnameVerifier())<br><br>
//设置全局重试次数，配置后每个请求失败都会重试设置的次数。<br>
//.setRetry(5)<br><br>
//开始初始化NoHttp<br>
.startInit();
### 如何使用NoHttpRxUtils去数据下载?
#### * 下载开启(链式调用)
//获取下载请求构建器<br>
NoHttpDownloadUtils.getNoHttpDownloadBuild()<br><br>
//添加下载文件参数<br>
.addDownloadParameter(DOWNLOAD_FILE01, "Liqi_single_test.apk")<br><br>
//设置是否断点续传下载<br>
.setRange(true)<br><br>
//设置下载进度监听接口<br>
.setDownloadListener(this)<br><br>
//设置在指定的文件夹发现同名的文件是否删除后重新下载<br>
.setDeleteOld(false)<br><br>
//设置下载文件存储文件路径<br>
.setFileFolder(FILEPATH)<br><br>
//单个请求设置读取时间(单位秒，默认以全局读取超时时间。)<br>
.setReadTimeout(40)<br><br>
//单个请求设置链接超时时间(单位秒，默认以全局链接超时时间。)<br>
.setConnectTimeout(40)<br><br>
//单个请求设置请求失败重试计数。默认值是0,也就是说,失败后不会再次发起请求。<br>
.setRetryCount(3)<br><br>
//开启下载<br>
.satart(this);
#### * 下载暂停(取消)
//暂停全部正在下载任务<br>
NoHttpDownloadUtils.cancelAll();<br><br>
//暂停指定下载任务<br>
NoHttpDownloadUtils.cancel(downloadUrl);
#### * 下载恢复
//恢复指定下载<br>
  NoHttpDownloadUtils.startRequest(downloadUrl);<br><br>
  //恢复全部下载<br>
NoHttpDownloadUtils.startAllRequest();
#### * 下载Url对应的what值操作方法
//获取下载URL对象的What值<br>
NoHttpDownloadUtils.getDownloadRequestsWhat(downloadUrl);<br><br>
//移除下载地址对应的What<br>
NoHttpDownloadUtils.removeWhatData(downloadUrl);<br><br>
//移除全部下载What<br>
NoHttpDownloadUtils.removeWhatAll();
#### * 手动清空下载请求，并关闭下载服务
//清空当前下载请求对象,并停止服务<br>
NoHttpDownloadUtils.clearAll();
##### 注：如果上一次下载任务没有完成或者没有清空，那么下一次点击任何下载都会继续执行上一次没有完成的任务继续下载
### 如何使用NoHttpRxUtils去数据请求?
#### * NoHttpRxUtils网络请求，采用链式调用
//获取请求对象<br>
RxNoHttpUtils.rxNoHttpRequest()<br><br>
//get请求方式（除了get和post请求，还支持put，delete，head，patch，options，trace）<br>
.get()<br><br>
//post请求方式（除了get和post请求，还支持put，delete，head，patch，options，trace）<br>
.post()<br><br>
//设置Url<br>
.url("url")<br><br>
//添加请求参数<br>
//当传入的参数类型不属于内部设定类型时，默认调用Object的toString()转换为String类型参数<br>
.addParameter()<br><br>
//添加请求头<br>
.addHeader()<br><br>
//设置请求bodyEntity为StringEntity，并传请求类型。<br>
.requestStringEntity(Content-Type)<br><br>
//为StringEntity添加body中String值<br>
.addStringEntityParameter("请求的String")<br><br>
//从bodyEntity切换到请求配置对象<br>
.transitionToRequest()<br><br>
//设置请求bodyEntity为JsonObjectEntity.json格式：{"xx":"xxx","yy":"yyy"}<br>
.requestJsonObjectEntity()<br><br>
//给JsonObjectEntity添加参数和值<br>
.addEntityParameter("key","Valu")<br><br>
//从bodyEntity切换到请求配置对象<br>
.transitionToRequest()<br><br>
//设置请求bodyEntity为JsonListEntity.json格式：[{"xx":"xxx"},{"yy":"yyy"}]<br>
.requestJsonListEntity()<br><br>
//给JsonList创造对象，并传键值参数<br>
.addObjectEntityParameter("key","Valu")<br><br>
//在创造对象的上添加键值参数<br>
.addEntityParameter("key","Valu")<br><br>
//把创造对象刷进进JsonList里面<br>
.objectBrushIntoList()<br><br>
//从bodyEntity切换到请求配置对象<br>
.transitionToRequest()<br><br>
//设置请求bodyEntity为InputStreamEntity<br>
.requestInputStreamEntity(Content-Type)<br><br>
//给InputStreamEntity添加输入流<br>
.addEntityInputStreamParameter(InputStream)<br><br>
//从bodyEntity切换到请求配置对象<br>
.transitionToRequest()<br><br>
//单个请求设置读取时间(单位秒，默认以全局读取超时时间。)<br>
.setReadTimeout(40)<br><br>
//单个请求设置链接超时时间(单位秒，默认以全局链接超时时间。)<br>
.setConnectTimeout(30)<br><br>
//单个请求设置请求失败重试计数。默认值是0,也就是说,失败后不会再次发起请求。<br>
.setRetryCount(3)<br><br>
//单个请求设置缓存key<br>
.setCacheKey("get请求Key")<br><br>
//单个请求设置缓存模式(跟原生NoHttp五种缓存模式一致)<br>
.setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE)<br><br>
//设置当前请求是否添加进Rx"线程池"队列中(默认是添加rx"线程池"中.!!如果设置false,请求线程不经过Rx"线程池"直接请求)<br>
.setQueue(false)<br><br>
//设置Rx"线程池"队列标识.(标识设置请保证唯一.!!如果setQueue(false)设置为false,setSign(标识对象)设置无任何作用)<br>
.setSign(new Object())<br><br>
//添加HTTPS协议无证书参数<br>
.addHttpsIsCertificate()<br><br>
//添加HTTPS协议有证书参数<br>
.addHttpsIsCertificate(InputStream)<br><br>
//设置请求图片的最大宽高<br>
.setBitmapMaxWH(500,500)<br><br>
//设置请求位图的配置和比例<br>
.setBitmapConfigType(Bitmap.Config, ImageView.ScaleType)<br><br>
//设置请求加载框(如果此处没有设置加载框，那么就默认使用初始化设置的加载框)<br>
.setDialogGetListener(this)<br><br>
//创建请求对象，并指定响应转换类型和请求成功或者失败回调接口<br>
.builder(Objects.class,new OnIsRequestListener<T>)<br><br>
//开始请求<br>
.requestRxNoHttp();
  
#### * 手动取消Rx"线程池"中队列请求(注：setQueue(false)如果设置为false，手动取消将失去作用)
//单个取消Sign对应的请求<br>
RxNoHttpUtils.cancel(Sign));<br><br>
//取消批量Sign对应的请求<br>
RxNoHttpUtils.cancel(Sign[]);<br><br>
//取消RX"线程池"中所有的请求<br>
// RxNoHttpUtils.cancelAll();
  
#### * NoHttpRxUtils轮询请求，采用链式调用
//获取请求对象<br>
RxNoHttpUtils.rxNoHttpRequest()<br><br>
//NoHttp网络请求设置参数跟上面一样设置<br>
...<br><br>
//设置当前轮询请求Sign<br>
.setSign(new Object())<br><br>
//创建轮询请求对象，并指定响应转换类型和请求成功或者失败回调接口<br>
.builderPoll(Objects.class,new OnIsRequestListener<T>)<br><br>
//设置初始化加载延迟<br>
.setInitialDelay(3 * 1000)<br><br>
//设置轮询间隔时间-默认3秒<br>
.setPeriod(5 * 1000)<br><br>
//设置被观察者产生的行为事件监听器-<br>
//(如果此处实现被观察者产生的行为事件监听器，那么框架内部就不去维护此轮询请求，必须实现轮询拦截器接口去维护此轮询什么时候停止。)<br>
.setOnObserverEventListener(new OnObserverEventListener<RestRequest<T>, RxInformationModel<T>>(){<br>
　　　　 @Override<br>
　　public RxInformationModel<T> onObserverEvent(RestRequest<T> transferValue) {<br>
　　　　// RxInformationModel<T>对象方法介绍<br>
　　　　//getData()=获取请求数据<br>
　　　　//setData(T data)=赋值请求数据<br>
　　　　//setException(boolean exception)=赋值是否是异常状态<br>
　　　　//isException()=获取是否异常状态<br>
　　　　//setThrowable(Throwable throwable)=赋值异常类<br>
　　　　//getThrowable()=获取异常类<br>
　　　　//setStop(boolean stop)=赋值是否停止轮询状态<br>
　　　　//isStop()=获取是否轮询状态<br>
　　　　//RxInformationModel<T> 此对象需要new 出来.<br>
　　　　//在此方法中可以换成自己钟意的网络框架去请求，如果上面设置网络请求参数，除了body其它的都能从RestRequest<Objects>里面取得。<br>
　　　return informationModel;<br>
　　}<br>
})<br><br>
// 设置设置数据拦截监听对象<br>
.setBooleanFunc1(new Func1<RxInformationModel<T>, Boolean>() {<br>
　　　　@Override <br>
　　　public Boolean call(RxInformationModel<T> stringRxInformationModel) {<br>
　　//在此方法里面可以根据RxInformationModel<T>.getData()获取请求的数据，然后根据请求的数据来决定是否停止轮询<br>
　　　　return stringRxInformationModel.isStop();<br>
　　　}
})<br><br>
//设置观察者根据被观察产生的行为做出相应处理监听器<br>
//如果实现了此接口，那么builderPoll重实现的OnIsRequestListener将无效。<br>
.setRxInformationModelAction1(new Action1<RxInformationModel<T>>() {<br>
　　　　@Override <br>
　　　public void call(RxInformationModel<T> stringRxInformationModel) {<br>
　　　　//在此方法里面根据RxInformationModel<T>中的数据做出相应动作<br>
　　　}<br>
})<br><br>
//转换成轮询请求类<br>
.switchPoll()<br><br>
//开始请求<br>
.requestRxNoHttp();
  
#### * 手动取消轮询请求
//单个取消Sign对应的轮询请求<br>
RxNoHttpUtils.cancelPoll(Sign));<br><br>
//取消批量Sign对应的轮询请求<br>
RxNoHttpUtils.cancelPoll(Sign[]);<br><br>
//取消所有的轮询请求<br>
// RxNoHttpUtils.cancelPollAll();
  
#### * 手动清除缓存
//清除对应的key的缓存数据<br>
RxNoHttpUtils.removeKeyCacheData("Cachekey");<br><br>
//清除所有缓存数据<br>
RxNoHttpUtils.removeAllCacheData();
##### 如果觉得不错,请star给我动力.
### NoHttpRxUtils数据请求方面，请查看我的博客文档(博客文档还未更新)
[我的博客](http://www.jianshu.com/p/61d3eaecc7ca) 
### 由于NoHttpRxUtils请求中Rx"线程池"是自己写的队列算法,要了解的请看我写的Rx"线程池"案例
[Rx线程池案例](https://github.com/LiqiNew/RxThreadPool) 
