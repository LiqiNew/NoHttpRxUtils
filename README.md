[中文版文档](https://github.com/LiqiNew/NoHttpRxUtils/blob/master/README_CHINESE.md) <br><br>
[![](https://jitpack.io/v/liqinew/nohttprxutils.svg)](https://jitpack.io/#liqinew/nohttprxutils)
[![](https://img.shields.io/badge/%E4%BD%9C%E8%80%85-%E6%9D%8E%E5%A5%87-orange.svg)](https://github.com/LiqiNew)
<br>**---- v.2.0.5 New in version ----**
* **Compatible to NoHttp-v.1.1.11**
* **Repair BUG**Fix bug that download file is not available because the file name was not passed in at the time of download
* **NohttpDownloadUtils** File download tool object added (getDownloadRequestsUrl() Get the path method corresponding to the download request What value)

### Because NoHttpRxUtils through the RxJava face NoHttp network framework for a series of bundle.First of all against RxJava and NoHttp network framework to do a brief introduction
# What is the RxJava framework?
RxJava is a responsive programming design frame.<br>
In a responsive programming, when the data arrives, the consumer responds.<br>
Responsive programming can pass events to registered observers.<br><br>
[RxJava Frame GitHub(ReactiveX)](https://github.com/ReactiveX/RxJava)

# What is the NoHttp framework?
### NoHttp is a framework specifically for Android network communications
#### NoHttp framework characteristic
Easier to use than 'Retrofit' is easier to use.<br><br>
Dynamic configuration of the underlying framework is OkHttp、HttpURLConnection<br><br>
Support asynchronous requests, And supports synchronization requests<br><br>
Support multi-file upload, support large file upload, support the form to submit data<br><br>
Support file download and upload, and provide progress callback.<br><br>
Support Json, xml, Map, List format submission<br><br>
Support Http cache mode. Cache memory address can specify the database or SD card. The cached data has been encrypted securely.<br><br>
Support for custom Request, you can directly request JsonObject or JavaBean and so on.<br><br>
Support automatic maintenance of cookies. Project restart or system restart can continue to maintain.<br><br>
Support Http 301, 302,303,304,307 redirect. Supports multi-layer nested redirection.<br><br>
Support Https access, support two-way authentication.<br><br>
Support failure retry mechanism to support request priority.<br><br>
Support GET, POST, PUT, PATCH, HEAD, DELETE, OPTIONS, TRACE and other request agreement<br><br>
Support queue request, support multiple requests concurrent.<br><br>
Support the cancellation of a request, cancel the specified multiple requests, cancel all requests.<br><br>
Support breakpoint upload and breakpoint download.<br><br>
[NoHttp Frame GitHub(Zhenjie Yan)](https://github.com/yanzhenjie/NoHttp)

# What is the NoHttpRxUtils framework?
NoHttpRxUtils is mainly through the RxJava framework for 'NoHttp' network framework operation re-encapsulation.<br>
Reduce the user's cumbersome calls, so that users are more focused on the project business, rather than the client and the server network communication between.<br>
Since the 'NoHttpRxUtils' network request is a 'NoHttp' synchronization request, the request queue is not a 'NoHttp' queue algorithm but a 'NoHttpRxUtils' queue algorithm.<br>
Sorry, the queue request priority setting is not supported at this time. If you want to make a request priority, you can use the RxJava thread single request.<br>
Sorry, because the Android system to write permissions on the SD card constraints, so all the cache data path points to the database.

##### NoHttpRxUtils framework characteristic
* Frame initialization, network request, file download, all use chain response call.
* Data request aspect, support RxJava thread queue request and RxJava thread single request.
* RxJava thread request queue, "RxJava thread queue" is through the algorithm to customize the implementation of 'thread pool', and then through the 'thread pool' on the RxJava thread management and operation. Support for revoking individual requests, multiple requests, and specifying requests.
* RxJava thread single request, directly open a RxJava thread, do not queue priority direct request. Irrevocable.
* The file download queue is a NoHttp queue algorithm. The 'NoHttp file download' bundle into the Android-Service. Support a single download task to cancel (pause), Support multiple download tasks to cancel, Support single download task recovery (start),Support multiple download tasks to resume (start).
* Support automatically through the specified object to get the server to return the data. Json format data conversion layer using Gson framework conversion (conversion object defined in the data structure must meet the Json data structure)
* Supports polling requests,And supports undoing a single polling request or multiple polling requests or specifying a polling request.
* Support the use of other network framework to polling requests.

# For your use more clarity, please read the following tutorials carefully.
##### RxJava in the internal use of the version is RxJava-1.1.9 and RxJava-2.1.5
##### NoHttp in the internal use of the version is NoHttp-1.1.4
##### Gson in the internal use of the version is Gson-2.8.0

How to rely on remotely?
-----
#### Gradle
**1：In the project root directory build.gradle**	<br>

```gradle allprojects {
repositories { 
  　　//Rely on the warehouse
　　　maven { url 'https://jitpack.io' }
　　}
}
```

**2：The project directory build.gradle relies on the NoHttpRxUtils framework**<br>
* RxJava-1 project depends on
```gradle
compile 'com.github.liqinew:nohttprxutils:v.2.0.5'
```

* RxJava-2 project depends on
```gradle
compile 'com.github.liqinew:nohttprxutils:v.2.0.6'
```
NoHttpRxUtils use method
-----
### Chained mode call initialization NoHttp, suggested to the Application onCreate life cycle method inside.
```java
//Initialize NoHttp
// hint:in fact, you can call setDialogGetListener here to set the global load box.
 RxNoHttpUtils.rxNoHttpInit(getApplicationContext())
 
              //Whether to maintain cookies,(If true maintain,else not maintained.)
              .setCookieEnable(false)
              
              //Whether the cache into the database.(If true yes,else not.)
              .setDbEnable(true)
              
              //Whether to open debug.(If true yes,else not.)
              .isDebug(true)
              
              //Set debug to print Name
              .setDebugName("LiQi-NoHttpUtils")
              
              //Set the global connection timeout. Unit seconds, default 30s.
              .setConnectTimeout(40)
              
              //Set the global server response timeout. Unit seconds, the default 30s.
              .setReadTimeout(40)
              
              //Set the number of concurrent downloads, the default number of concurrent 3.
              .setThreadPoolSize(3)
              
              //Set the number of concurrent requests for network requests, the default number of concurrent 3.
              .setRunRequestSize(4)
              
              //Set the global default load dialog box
              //Note:The incoming context must be the top of the stack.
              //You can get by using the method 'registerActivityLifecycleCallbacks()' to get.
              //Note:This method is not allowed to create a dialog object inside.
              .setDialogGetListener(new Dialog())
              
              //Set the bottom layer in that way to request.NoHttpInit.OKHTTP or NoHttpInit.URLCONNECTION two ways.
              .setRxRequestUtilsWhy(NoHttpInit.OKHTTP)
              
              //Set the global security protocol request with certificate.
              //Note: If the security protocol mode is toggled while the request is being called, the setting is overwritten.
              .setInputStreamSSL(new InputStream())
              
              //Set the global certificateless security protocol request
              //Note: If the security protocol mode is toggled while the request is being called, the setting is overwritten.
              .setInputStreamSSL()
              
              //Add a global request header
              .addHeader("app_head_key","app_head_global")
              
              //Add global request parameters. Only the String type is supported.
              .addParam("app_param","app_param_global")
              
              //Set up cookie management to listen.
              .setCookieStoreListener(new DBCookieStore.CookieStoreListener())
              
              //Set up global host authentication.
              //Note: This setting is not valid if the certificateless security protocol is switched when the request is called.
              .setHostnameVerifier(new HostnameVerifier())
              
              //Sets the number of global request failed retries.
              //.setRetry(5)

              //Set global request network appears unknown error hint message
              .setAnUnknownErrorHint("Global unknown error hint message")

              //Start initialization.
              .startInit();
```

### How do I use 'NoHttpRxUtils' to download data?
#### * Start download(Using chain call)

```java
//Get the download request builder
NoHttpDownloadUtils.getNoHttpDownloadBuild()

                    //Add download file parameters
                   .addDownloadParameter(DOWNLOAD_URL, "Download_Name.apk")
                   
                   //Set whether to continue downloading at breakpoint.If true yes,else not.
                   .setRange(true)
                   
                   //Set the download progress monitoring interface
                   .setDownloadListener(new DownloadListener())
                   
                   //Set whether to delete the same file name file, and then re-download.If true yes,else not.
                   .setDeleteOld(false)
                   
                   //Set the download file to store the file path
                   .setFileFolder(FILEPATH)
                   
                   //Single request to set the read time. Unit seconds, default to global read timeout.
                   .setReadTimeout(40)
                   
                   //Single request to set the link timeout. Unit seconds, default to global link timeout.
                   .setConnectTimeout(40)
                   
                   //Single request to set the number of failed request retries, default number of global link retries.
                   .setRetryCount(3)
                   
                   //Open the download
                   .satart(new Activity());
```

#### * Download Pause (Cancel)

```java
//Pause all Downloading tasks
NoHttpDownloadUtils.cancelAll();

//Pause the specified download task
NoHttpDownloadUtils.cancel(downloadUrl);
```

#### * Download recovery

```java
//Restore the specified download
NoHttpDownloadUtils.startRequest(downloadUrl);

//Restore all downloads
NoHttpDownloadUtils.startAllRequest();
```

#### * Download the 'what' value of the 'URL'

```java
//Get the 'What' value for the download URL
NoHttpDownloadUtils.getDownloadRequestsWhat(downloadUrl);

//Remove the 'What' value for the download URL
NoHttpDownloadUtils.removeWhatData(downloadUrl);

//Remove all downloads 'What' values
NoHttpDownloadUtils.removeWhatAll();
```

#### * Manually clear the download request and close the download service.

```java
//Empty the current download request object and stop the service.
NoHttpDownloadUtils.clearAll();
```

##### Note: If the last download task is not completed or is not empty, the next click on any download will continue to perform the last failed task to continue to download
### How do I use 'NoHttpRxUtils' to request data?
#### * NoHttpRxUtils network request, Using chain call.
```java
//Get the request object
RxNoHttpUtils.rxNoHttpRequest()

             //get request mode. In addition to get and post requests, but also support put, delete, head, patch, options, trace,
             .get() 
             //post request mode. In addition to get and post requests, but also support put, delete, head, patch, options, trace,
             .post()
             
             //Set the request Url
             .url("url")
             
             //Add the request parameters.
             //Note: When the incoming parameter type does not belong to the internal setting type,
             //the default call toString () of Object is converted to a String type parameter.
             .addParameter()
             
             //Add the request header.
             .addHeader()
             
             //Set the request bodyEntity to StringEntity and pass the request type.
             .requestStringEntity(Content-Type)
             
             //Add a String value for the body for StringEntity.
             .addStringEntityParameter("bodyString")
             
             //Switch from bodyEntity to request configuration object
             .transitionToRequest()
             
             //Set the request bodyEntity to JsonObjectEntity.Json format: {"xx": "xxx", "yy": "yyy"}
             .requestJsonObjectEntity()
             
             //Add parameters and values to JsonObjectEntity
             .addEntityParameter("key","Valu")
             
             //Switch from bodyEntity to request configuration object
             .transitionToRequest()
             
             //Set the request bodyEntity to JsonListEntity.json format: [{"xx": "xxx"}, {"yy": "yyy"}]
             .requestJsonListEntity()
             
             //Create an object for JsonList and pass key value parameters
             .addObjectEntityParameter("key","Valu")
             
             //Add key-value parameters to the created object
             .addEntityParameter("key","Valu")
             
             //Brush the creation object into JsonList
             //Note: If you need to create multiple objects in the collection, 
             //you can continue to call addObjectEntityParameter () after this method to create.
             //The process is consistent with the above.
             .objectBrushIntoList()
             
             //Switch from bodyEntity to request configuration object
             .transitionToRequest()
             
             //Set the request bodyEntity to InputStreamEntity
             .requestInputStreamEntity(Content-Type)
             
             //Add an input stream to InputStreamEntity
             .addEntityInputStreamParameter(new InputStream())
             
             //Switch from bodyEntity to request configuration object
             .transitionToRequest()
             
             //Single request to set the read time. Unit seconds, default to global read timeout.
             .setReadTimeout(40)
             
             //Single request to set the link timeout. Unit seconds, default to global link timeout.
             .setConnectTimeout(30)
             
             //Single request to set the number of failed request retries, default number of global link retries.
             .setRetryCount(3)
             
             //Set a single request to set the cache key.
             .setCacheKey("setRequestCacheKey")
             
             //Single request to set the cache mode. With the original NoHttp five cache mode consistent.
             .setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE)
             
             //Sets whether the current request is added to the Rx "thread pool" queue. default is to add RxJava 'thread pool'.
             //Note: If false is set, the request thread does not request it directly through the RxJava "thread pool" queue.
             //Only for data requests, not for polling requests.
             .setQueue(false)
             
             //Set the RxJava "thread pool" queue identifier,Please make sure to be unique.
             //Note: If setQueue (false) is set to false, the setSign (Sign) setting has no effect.
             .setSign(new Object())
             
             //Add HTTPS protocol without certificate parameters.
             .addHttpsIsCertificate()
             
             //Add the HTTPS protocol with certificate parameters.
             .addHttpsIsCertificate(new InputStream())
             
             //Set the maximum width of the requested image.
             .setBitmapMaxWH(500,500)
             
             //Set the configuration and scale of the requested bitmap.
             .setBitmapConfigType(Bitmap.Config, ImageView.ScaleType)
             
             //Set the load box.
             //Note: If you do not set the load box here, then the default use of the global settings of the load box.
             //If the global setting does not set the load box, then the load box and built-in prompts are not displayed.
             //Note:This method is not allowed to create a dialog object inside.
             .setDialogGetListener(new Dialog())

             //Set request network appears unknown error hint message
             .setAnUnknownErrorHint("Unknown error hint message")

             //Create the request object to specify the response data conversion type,
             //and then set the request to succeed or fail the callback interface.
             .builder(Objects.class,new OnIsRequestListener<T>)
             
             //Start the request.
             .requestRxNoHttp();
```
  
#### * Manually cancel the queue request in the RxJava "thread pool". Note: setQueue (false) If set to false, manual cancellation will be disabled.

```java
//Single cancellation of the corresponding request for 'Sign'.
RxNoHttpUtils.cancel(Sign));

//Cancel the request for bulk 'Sign'
RxNoHttpUtils.cancel(Sign[]);

//Cancel all requests in the RxJava 'thread pool'.
// RxNoHttpUtils.cancelAll();
```

#### * NoHttpRxUtils poll request, Using chain call.

```java
//Get the request object.
RxNoHttpUtils.rxNoHttpRequest()

             //NoHttp The network request settings are set as described above
             ...
             
             //Set the current polling request 'Sign'
             .setSign(new Object())
             
             //Create a polling request object, 
             //and then specify the response data conversion type and setup request success and failure callback interface
             .builderPoll(Objects.class,new OnIsRequestListener<T>)
             
             //Set the initialization load delay
             .setInitialDelay(3 * 1000)
             
             //Set the polling interval, default 3s.
             .setPeriod(5 * 1000)
             
             //Set the behavior event listener generated by the observer
             //Note: If the behavioral event listener is implemented here,
             //then the polling request is not maintained inside the framework,
             //and the polling interceptor interface must be implemented to maintain the polling stop.
             //RxNoHttpUtils.cancelPoll () cancel polling will be invalid, set the internal loading box will be invalid.
             .setOnObserverEventListener(new OnObserverEventListener<RestRequest<T>, RxInformationModel<T>>(){
                     @Override
                  public RxInformationModel<T> onObserverEvent(RestRequest<T> transferValue) {
                  
                    // RxInformationModel<T>Object method introduction.
                    //getData() -> Get request data
                    //setData(T data) -> Assignment request data
                    //setException(boolean exception) -> Assignment is abnormal state
                    //isException() -> Get the exception status
                    //setThrowable(Throwable throwable) -> Assignment exception class
                    //getThrowable() -> Get exception class
                    //setStop(boolean stop) -> Assign whether to stop the polling state
                    //isStop() -> Get the polling status

                    //In this method inside the RxInformationModel <T> object needs new out.
                    
                    //In this method can be replaced by their own framework to the network request.
                    //If you set the network request parameters above, 
                    //in addition to the body, you can get from the RestRequest <Objects> object.
                    
                   return informationModel;
                  }
             })
             
             // Set the data to intercept the listening object
             .setBooleanFunc1(new Func1<RxInformationModel<T>, Boolean>() {
                      @Override
                   public Boolean call(RxInformationModel<T> stringRxInformationModel) {
                   
                  //In this method, you can get the requested data by RxInformationModel <T> .getData (),
                  //and then decide whether to stop the polling according to the requested data.
                  
                    return stringRxInformationModel.isStop();
                   }
             })
             
             //Set the observer to make the corresponding handler based on the observed behavior
             //Note: If this interface is implemented, the implementation of the OnIsRequestListener in the builderPoll will be invalid.
            .setRxInformationModelAction1(new Action1<RxInformationModel<T>>() {
                @Override
               public void call(RxInformationModel<T> stringRxInformationModel) {
               
                //In this method, the corresponding action is made according to the data in RxInformationModel <T>.
                
               }
             })
             
             //Convert to polling request class
             .switchPoll()
             
             //Start the request
             .requestRxNoHttp();
```

#### * Cancel the polling request manually.

```java
//Single Cancel the poll request corresponding to 'Sign'
RxNoHttpUtils.cancelPoll(Sign));

//Cancel the batch request corresponding to 'Sign'
RxNoHttpUtils.cancelPoll(Sign[]);

//Cancel all polling requests
RxNoHttpUtils.cancelPollAll();
```

#### * Manually clear the cache

```java
//Clear the cache data corresponding to 'Key'
RxNoHttpUtils.removeKeyCacheData("Cachekey");

//Clear all cached data
RxNoHttpUtils.removeAllCacheData();
```

##### If you feel good, please give me the star. <br><br>Thank you very much.
### NoHttpRxUtils data request, please check my blog documentation.(Chinese blog)
[NoHttpRxUtils Network communication framework ](http://www.jianshu.com/p/61d3eaecc7ca)
### NoHttpRxUtils polling request, please check my blog documentation.(Chinese blog)
[Extended poller interprets blog](http://www.jianshu.com/p/2aa5855425c8)
### Based on the RxJava-1 source extension of the poller GitHub open source project.
[RxJavaExpandPoll](https://github.com/LiqiNew/RxJavaExpandPoll)
### Because NoHttpRxUtils request in the RxJava "thread pool" is written by the queue algorithm, I would like to see the I see the RxJava "thread pool" project.
[RxThreadPool](https://github.com/LiqiNew/RxThreadPool)

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
