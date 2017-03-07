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
### NohttpRxUtils数据请求方面，请查看我的博客文档
[我的博客](http://www.jianshu.com/p/61d3eaecc7ca) 
