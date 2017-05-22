[![](https://jitpack.io/v/liqinew/nohttprxutils.svg)](https://jitpack.io/#liqinew/nohttprxutils)
### 由于NohttpRxUtils是针对Nohttp网络框架的请求方法进行封装，<br>首先对Nohttp网络框架做一个简介
# Nohttp框架是什么?
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
支持取消某个请求、取消指定多个请求、取消所有请求
##### 欢迎加入Nohttp作者QQ技术交流群：46523908
# 为了你使用更加清晰明了,请仔细阅读下面使用教程
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
compile 'com.github.liqinew:nohttprxutils:v.1.2'<br>

NohttpRxUtils使用简介
-----
NohttpRxUtils数据请求方面：通过算法实现线程池对Rx线程进行队列管理和操作,支持Rx线程队列请求和Rx线程单一请求。<br>
NohttpRxUtils数据下载方面：针对Nohttp数据下载进行Service捆绑封装。
### 链式初始化nohttp，建议放到Application中onCreate生命周期方法里面
//初始化nohttp（在此处其实可以调用setDialogGetListener设置全局请求加载框）<br>
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
//开始初始化Nohttp<br>
.startInit();
### 如何使用NohttpRxUtils去数据下载?
##### 下载开启(链式调用)
//获取下载请求构建器<br>
NohttpDownloadUtils.getNohttpDownloadBuild()<br><br>
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
##### 下载暂停(取消)
//暂停全部正在下载任务<br>
NohttpDownloadUtils.cancelAll();<br><br>
//暂停指定下载任务<br>
NohttpDownloadUtils.cancel(downloadUrl);
##### 下载恢复
//恢复指定下载<br>
  NohttpDownloadUtils.startRequest(downloadUrl);<br><br>
  //恢复全部下载<br>
NohttpDownloadUtils.startAllRequest();
##### 下载Url对应的what值操作方法
//获取下载URL对象的What值<br>
NohttpDownloadUtils.getDownloadRequestsWhat(downloadUrl);<br><br>
//移除下载地址对应的What<br>
NohttpDownloadUtils.removeWhatData(downloadUrl);<br><br>
//移除全部下载What<br>
NohttpDownloadUtils.removeWhatAll();
##### 手动清空下载请求，并关闭下载服务
//清空当前下载请求对象,并停止服务<br>
NohttpDownloadUtils.clearAll();
##### 注：如果上一次下载任务没有完成或者没有清空，那么下一次点击任何下载都会继续执行上一次没有完成的任务继续下载
### 如何使用NohttpRxUtils去数据请求?
##### NohttpRxUtils网络请求，采用链式调用
//获取请求对象<br>
RxNoHttpUtils.rxNohttpRequest()<br><br>
//get请求方式（除了get和post请求，还支持put，delete，head，patch，options，trace）<br>
.get()<br><br>
//post请求方式（除了get和post请求，还支持put，delete，head，patch，options，trace）<br>
.post()<br><br>
//设置Url<br>
.url("url")<br><br>
//添加请求参数<br>
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
//单个请求设置缓存模式(跟原生Nohttp五种缓存模式一致)<br>
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
##### 手动取消Rx"线程池"中队列请求(注：setQueue(false)如果设置为false，手动取消将失去作用)
//单个取消Sign对应的请求<br>
RxNoHttpUtils.cancel(Sign));<br><br>
//取消批量Sign对应的请求<br>
RxNoHttpUtils.cancel(Sign[]);<br><br>
//取消RX"线程池"中所有的请求<br>
// RxNoHttpUtils.cancelAll();
##### 手动清除缓存
//清除对应的key的缓存数据<br>
RxNoHttpUtils.removeKeyCacheData("Cachekey");<br><br>
//清除所有缓存数据<br>
RxNoHttpUtils.removeAllCacheData();
##### 如果觉得不错,请star给我动力.
### NohttpRxUtils数据请求方面，请查看我的博客文档(博客文档还未更新)
[我的博客](http://www.jianshu.com/p/61d3eaecc7ca) 
### 由于NohttpRxUtils请求中Rx"线程池"是自己写的队列算法,要了解的请看我写的Rx"线程池"案例
[Rx线程池案例](https://github.com/LiqiNew/RxThreadPool) 
