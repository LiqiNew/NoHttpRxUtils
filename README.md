# NohttpRxUtils
NohttpRxUtils数据请求方面：采用Rxjava对Nohttp网络请求进行"Builder模式"链式调用封装。<br>
NohttpRxUtils数据下载方面：针对Nohttp数据下载进行Service捆绑封装。
### NohttpRxUtils数据下载方面
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
### NohttpRxUtils数据请求方面
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
### NohttpRxUtils数据请求方面，请查看我的博客文档
[我的博客](http://www.jianshu.com/p/61d3eaecc7ca) 
