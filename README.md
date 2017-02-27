# NohttpRxUtils
NohttpRxUtils数据请求方面：采用Rxjava对Nohttp网络请求进行MVP模式封装。<br>
NohttpRxUtils数据下载方面：针对Nohttp数据下载进行Service捆绑封装。
### NohttpRxUtils数据下载方面
                       ```
                       //获取下载请求构建器 <br>
                       NohttpDownloadUtils.getNohttpDownloadBuild()
                                .addDownloadParameter(DOWNLOAD_FILE01, "Liqi_single_test.apk")
                                .setRange(true)
                                .setDownloadListener(this)
                                .setDeleteOld(false)
                                .setThreadPoolSize(3)
                                .setFileFolder(FILEPATH)
                                .satart(this);
                       ```
### NohttpRxUtils数据请求方面，请查看我的博客文档
[我的博客](http://www.jianshu.com/p/61d3eaecc7ca) 
