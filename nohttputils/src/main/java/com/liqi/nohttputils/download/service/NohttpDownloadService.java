package com.liqi.nohttputils.download.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.SparseArray;

import com.liqi.nohttputils.download.DownloadUrlEntity;
import com.liqi.nohttputils.download.NohttpDownloadConfig;
import com.liqi.nohttputils.download.NohttpDownloadWhatAlgorithm;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.download.DownloadListener;
import com.yanzhenjie.nohttp.download.DownloadQueue;
import com.yanzhenjie.nohttp.download.DownloadRequest;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * 下载服务
 *
 * 个人QQ:543945827
 * NoHttp作者群号：46523908
 * Created by LiQi on 2017/2/23.
 */

public class NohttpDownloadService extends Service implements DownloadListener {
    private NohttpDownloadBinderService mNohttpDownloadBinderService;
    /**
     * 指定线程池并发数量
     */
    private int threadPoolSize;
    /**
     * 下载任务列表。
     */
    private SparseArray<DownloadRequest> mDownloadRequests;
    /**
     * 下载请求对应的what值容器
     */
    private Hashtable<String, Integer> mWhats;
    private DownloadListener mDownloadListener;
    private DownloadServiceFinishListener mDownloadServiceFinishListener;

    public void setDownloadServiceFinishListener(DownloadServiceFinishListener downloadServiceFinishListener) {
        mDownloadServiceFinishListener = downloadServiceFinishListener;
    }

    /**
     * 开启下载请求
     *
     * @param nohttpDownloadConfig 配置文件
     */
    public void start(NohttpDownloadConfig nohttpDownloadConfig) {
        mDownloadListener = nohttpDownloadConfig.getDownloadListener();
        threadPoolSize = nohttpDownloadConfig.getThreadPoolSize();
        addDownloadRequest(nohttpDownloadConfig);
    }

    /**
     * 添加进下载请求集合中
     *
     * @param nohttpDownloadConfig 配置对象
     */
    private void addDownloadRequest(NohttpDownloadConfig nohttpDownloadConfig) {
        List<DownloadUrlEntity> downloadUrlEntities = nohttpDownloadConfig.getDownloadUrlEntities();
        if (null != downloadUrlEntities && !downloadUrlEntities.isEmpty()) {
            mDownloadRequests = null == mDownloadRequests ? new SparseArray<DownloadRequest>() : mDownloadRequests;
            mWhats = null == mWhats ? new Hashtable<String, Integer>() : mWhats;
            NohttpDownloadWhatAlgorithm.getNohttpDownloadKeyFactory().setWhatInit(getMapWhatInit());

            for (int i = 0; i < downloadUrlEntities.size(); i++) {
                DownloadUrlEntity downloadUrlEntity = downloadUrlEntities.get(i);
                String downloadUrL = downloadUrlEntity.getDownloadUrL();
                //如果mWhats里面存在对应的what值就取出，如果没有就生产一个新的唯一what值
                int key = NohttpDownloadWhatAlgorithm.getNohttpDownloadKeyFactory().productionWhat(mWhats, downloadUrL);
                DownloadRequest downloadRequest = mDownloadRequests.get(key);
                //对应的what值有下载请求进入
                if (null != downloadRequest) {
                    //如果下载地址不一致，那么移除掉对应的下载请求
                    if (!downloadUrL.equals(downloadRequest.url())) {
                        mDownloadRequests.get(key).cancel();
                        mDownloadRequests.remove(key);
                    }
                    continue;
                }
                mDownloadRequests.put(key, NoHttp.createDownloadRequest(
                        downloadUrL, nohttpDownloadConfig.getFileFolder(),
                        downloadUrlEntity.getFileName(), nohttpDownloadConfig.isRange(),
                        nohttpDownloadConfig.isDeleteOld()));
            }

            startAllRequest();
        }
    }

    /**
     * 开启全部下载请求队列
     */
    public void startAllRequest() {
        if (mDownloadRequests.size() > 0) {
            for (int i = 0; i < mDownloadRequests.size(); i++) {
                int what = mDownloadRequests.keyAt(i);
                DownloadRequest downloadRequest = mDownloadRequests.get(what);

                if (downloadRequest.isCanceled()) {
                    downloadRequest = NoHttp.createDownloadRequest(
                            downloadRequest.url(), downloadRequest.getFileDir(),
                            downloadRequest.getFileName(), downloadRequest.isRange(),
                            downloadRequest.isDeleteOld());
                    mDownloadRequests.put(what, downloadRequest);
                }
                if (!downloadRequest.isStarted())
                    getDownloadQueueInstance().add(what, downloadRequest, this);
            }
        }
    }

    /**
     * 开启指定下载请求队列
     */
    public void startRequest(String downloadUrl) {
        if (null != mDownloadRequests && mDownloadRequests.size() > 0) {
            if(mWhats.containsKey(downloadUrl)) {
                int what = mWhats.get(downloadUrl);
                DownloadRequest downloadRequest = mDownloadRequests.get(what);
                if (null!=downloadRequest) {
                    if (downloadRequest.isCanceled()) {
                        downloadRequest = NoHttp.createDownloadRequest(
                                downloadRequest.url(), downloadRequest.getFileDir(),
                                downloadRequest.getFileName(), downloadRequest.isRange(),
                                downloadRequest.isDeleteOld());
                        mDownloadRequests.put(what, downloadRequest);
                    }
                    if (!downloadRequest.isStarted())
                        getDownloadQueueInstance().add(what, downloadRequest, this);
                }
            }
        }
    }


    /**
     * 取消当前所有正在下载的任务
     */
    public void cancel() {
        if (null != mDownloadRequests && mDownloadRequests.size() > 0) {
            for (int i = 0; i < mDownloadRequests.size(); i++) {
                DownloadRequest downloadRequest = mDownloadRequests.valueAt(i);
                if (null != downloadRequest && !downloadRequest.isCanceled())
                    downloadRequest.cancel();
            }
        }
    }

    /**
     * 取消指定下载的任务
     */
    public void cancel(String downloadUrl) {
        if (null != mDownloadRequests && mDownloadRequests.size() > 0) {
            if (mWhats.containsKey(downloadUrl)) {
                int what = mWhats.get(downloadUrl);
                DownloadRequest downloadRequest = mDownloadRequests.get(what);
                if (null != downloadRequest && !downloadRequest.isCanceled())
                    downloadRequest.cancel();
            }
        }
    }

    /**
     * 清空当前下载请求对象
     */
    public void clearAll() {
        if (null != mDownloadRequests && mDownloadRequests.size() > 0) {
            for (int i = 0; i < mDownloadRequests.size(); i++) {
                DownloadRequest downloadRequest = mDownloadRequests.valueAt(i);
                if (null != downloadRequest && !downloadRequest.isCanceled())
                    downloadRequest.cancel();
            }
            mDownloadRequests.clear();
        }
        removeWhatAll();
    }

    /**
     * 获取下载队列
     *
     * @return
     */
    private DownloadQueue getDownloadQueueInstance() {
        threadPoolSize = threadPoolSize > 0 ? threadPoolSize : mDownloadRequests.size();
        DownloadQueue mDownloadQueue = NoHttp.newDownloadQueue(threadPoolSize);
        return mDownloadQueue;
    }

    /**
     * 获取下载请求对应的what值
     *
     * @param downloadUrl 下载地址
     * @return
     */
    public int getWhat(String downloadUrl) {
        if (null != mWhats && !mWhats.isEmpty()) {
            if (mWhats.containsKey(downloadUrl))
                return mWhats.get(downloadUrl);
        }
        return -1;
    }

    /**
     * 把指定的What从容器里面移除
     *
     * @param downloadUrl 下载地址
     */
    public void removeWhatData(String downloadUrl) {
        if (null != mWhats && !mWhats.isEmpty()) {
            if (mWhats.containsKey(downloadUrl))
                mWhats.remove(downloadUrl);
        }
    }

    /**
     * 清空What存储容器
     */
    public void removeWhatAll() {
        if (null != mWhats && !mWhats.isEmpty())
            mWhats.clear();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mNohttpDownloadBinderService = null == mNohttpDownloadBinderService ? new NohttpDownloadBinderService() : mNohttpDownloadBinderService;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDownloadError(int what, Exception exception) {
        if (mDownloadListener != null) mDownloadListener.onDownloadError(what, exception);
    }

    @Override
    public void onStart(int what, boolean isResume, long rangeSize, Headers responseHeaders, long allCount) {
        if (mDownloadListener != null)
            mDownloadListener.onStart(what, isResume, rangeSize, responseHeaders, allCount);
    }

    @Override
    public void onProgress(int what, int progress, long fileCount, long speed) {
        if (mDownloadListener != null)
            mDownloadListener.onProgress(what, progress, fileCount, speed);
    }

    @Override
    public void onFinish(int what, String filePath) {
        mDownloadRequests.remove(what);
        if (mDownloadListener != null) mDownloadListener.onFinish(what, filePath);
        if (mDownloadServiceFinishListener != null) {
            if (mDownloadRequests.size() <= 0) {
                mDownloadRequests = null;
                mDownloadServiceFinishListener.onFinish();
            }
        }
    }

    /**
     * 获取what初始化值
     *
     * @return
     */
    private int getMapWhatInit() {
        int whatInit = 0x1;
        if (!mWhats.isEmpty()) {
            for (Map.Entry<String, Integer> entry : mWhats.entrySet()) {
                whatInit = entry.getValue();
            }
        }
        return whatInit;
    }

    @Override
    public void onCancel(int what) {
        if (mDownloadListener != null) mDownloadListener.onCancel(what);
    }

    /**
     * 下载请求已经全部下载完毕接口
     */
    public interface DownloadServiceFinishListener {
        void onFinish();
    }

    /**
     * 获取NohttpDownloadService对象Binder类
     */
    public class NohttpDownloadBinderService extends Binder {
        public NohttpDownloadService getBindService() {
            return NohttpDownloadService.this;
        }
    }

}
