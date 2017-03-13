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
### 使用方法
#### Gradle
**1：在项目根目录build.gradley**	<br>
allprojects {<br>
　　repositories { <br>
  　　//依赖仓库	<br>		
　　　maven { url 'https://jitpack.io' }<br>
　　}<br>
}<br>
**2：依赖NohttpRxUtils框架**<br>
compile 'com.github.LiqiNew:NohttpRxUtils:v.1.0'
# NohttpRxUtils简介
NohttpRxUtils数据请求方面：采用Rxjava对Nohttp网络请求进行"Builder模式"链式调用封装。<br>
NohttpRxUtils数据下载方面：针对Nohttp数据下载进行Service捆绑封装。
### 如何使用NohttpRxUtils去数据下载?
##### 下载开启(链式调用)
//获取下载请求构建器<br>
NohttpDownloadUtils.getNohttpDownloadBuild()<br>
//添加下载文件参数<br>
.addDownloadParameter(DOWNLOAD_FILE01, "Liqi_single_test.apk")<br>
//设置是否断点续传下载<br>
.setRange(true)<br>
//设置下载进度监听接口<br>
.setDownloadListener(this)<br>
//设置在指定的文件夹发现同名的文件是否删除后重新下载<br>
.setDeleteOld(false)<br>
//设置线程池并发数量<br>
.setThreadPoolSize(3)<br>
//设置下载文件存储文件路径<br>
.setFileFolder(FILEPATH)<br>
//开启下载<br>
.satart(this);
##### 下载暂停(取消)
//暂停全部正在下载任务<br>
NohttpDownloadUtils.cancelAll();<br>
//暂停指定下载任务<br>
NohttpDownloadUtils.cancel(downloadUrl);
##### 下载恢复
//恢复指定下载<br>
  NohttpDownloadUtils.startRequest(downloadUrl);<br>
  //恢复全部下载<br>
NohttpDownloadUtils.startAllRequest();
##### 下载Url对应的what值操作方法
//获取下载URL对象的What值<br>
NohttpDownloadUtils.getDownloadRequestsWhat(downloadUrl);<br>
//移除下载地址对应的What<br>
NohttpDownloadUtils.removeWhatData(downloadUrl);<br>
//移除全部下载What<br>
NohttpDownloadUtils.removeWhatAll();
##### 手动清空下载请求，并关闭下载服务
//清空当前下载请求对象,并停止服务<br>
NohttpDownloadUtils.clearAll();
##### 注：如果上一次下载任务没有完成或者没有清空，那么下一次点击任何下载都会继续执行上一次没有完成的任务继续下载
### 如何使用NohttpRxUtils去数据请求?
##### 链式初始化nohttp，建议放到Application中onCreate生命周期方法里面
//初始化nohttp（在此处其实可以调用setDialogGetListener设置全局请求加载框）<br>
 RxNoHttpUtils.rxNoHttpInit(getApplicationContext())<br>
//是否维护Cookie<br>
.setCookieEnable(false)<br>
//是否缓存进数据库DBCacheStore<br>
.setDbEnable(true)<br>
//是否开启debug调试<br>
.isDebug(true)<br>
//设置debug打印Name<br>
.setDebugName("LiQi-NoHttpUtils")<br>
//设置全局连接超时时间。单位毫秒，默认30s。<br>
//.setConnectTimeout(100*1000)<br>
//设置全局服务器响应超时时间，单位毫秒，默认30s。<br>
//.setReadTimeout(100*1000)<br>
//设置全局默认加载对话框<br>
//.setDialogGetListener("全局加载框获取接口")<br>
//设置底层用那种方式去请求<br>
.setRxRequestUtilsWhy(NoHttpInit.OKHTTP)<br>
//开始初始化Nohttp<br>
.startInit();
##### NohttpRxUtils网络请求，采用链式调用
RxNoHttpUtils.rxNohttpRequest()<br>
//get请求方式（除了get和post请求，还支持put，delete，head，patch，options，trace）<br>
.get()<br>
//post请求方式（除了get和post请求，还支持put，delete，head，patch，options，trace）<br>
.post()<br>
//设置Url<br>
.url("url")<br>
//添加请求参数<br>
.addParameter()<br>
//添加请求头<br>
.addHeader()<br>
//添加HTTPS协议无证书参数<br>
.addHttpsIsCertificate()<br>
//添加HTTPS协议有证书参数<br>
.addHttpsIsCertificate(InputStream)<br>
//设置请求图片的最大宽高<br>
.setBitmapMaxWH(500,500)<br>
//设置请求位图的配置和比例<br>
.setBitmapConfigType(Bitmap.Config, ImageView.ScaleType)<br>
//设置请求加载框(如果此处没有设置加载框，那么就默认使用初始化设置的加载框)<br>
.setDialogGetListener(this)<br>
//创建请求对象，并指定响应转换类型和请求成功或者失败回调接口<br>
.builder(Objects.class,new OnIsRequestListener<T>)<br>
//开始请求<br>
.requestRxNoHttp();
##### 如果觉得不错,请star给我动力.
### NohttpRxUtils数据请求方面，请查看我的博客文档
[我的博客](http://www.jianshu.com/p/61d3eaecc7ca) 
