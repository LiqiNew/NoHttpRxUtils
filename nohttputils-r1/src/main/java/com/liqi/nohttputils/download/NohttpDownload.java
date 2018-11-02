package com.liqi.nohttputils.download;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.liqi.nohttputils.download.service.NohttpDownloadService;
import com.yanzhenjie.nohttp.Logger;

/**
 * 下载服务操作对象
 * Created by LiQi on 2017/2/23.
 */

class NohttpDownload implements NohttpDownloadService.DownloadServiceFinishListener {
    private static NohttpDownload mNohttpDownload;
    private final String SERVICE = "com.liqi.nohttputils.download.service.NohttpDownloadService";
    private NohttpDownloadService mBindService;
    private NohttpDownloadConfig mNohttpDownloadConfig;
    private Context mContext;
    /**
     * 服务绑定回调接口
     */
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        // 出现意外的解绑服务回调此方法
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Logger.e("下载服务解绑");
        }

        // 绑定成功的回调方法
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBindService = ((NohttpDownloadService.NohttpDownloadBinderService) service).getBindService();
            mBindService.setDownloadServiceFinishListener(NohttpDownload.this);
            mBindService.start(mNohttpDownloadConfig);
        }
    };

    private NohttpDownload() {
    }

    static synchronized NohttpDownload getNohttpDownload() {
        return mNohttpDownload = null == mNohttpDownload ? new NohttpDownload() : mNohttpDownload;
    }

    NohttpDownload init(NohttpDownloadConfig nohttpDownloadConfig, Context context) {
        mNohttpDownloadConfig = nohttpDownloadConfig;
        mContext = context;
        startService();
        return mNohttpDownload;
    }

    /**
     * 开启服务
     */
    private void startService() {
        if (isServiceRunning()) {
            if (null == mBindService) {
                mContext.bindService(getService(), mServiceConnection, Context.BIND_AUTO_CREATE);
            } else
                mBindService.start(mNohttpDownloadConfig);
        } else {
            mContext.startService(getService());
            mContext.bindService(getService(), mServiceConnection, Context.BIND_AUTO_CREATE);
        }
    }

    /**
     * 获取服务
     *
     * @return
     */
    private Intent getService() {
        return new Intent(mContext, NohttpDownloadService.class);
    }

    /**
     * 判断下载服务是否已经开启
     *
     * @return
     */
    private boolean isServiceRunning() {
        ActivityManager manager = (ActivityManager) mContext.getSystemService(mContext.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (SERVICE.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 暂停当前正在下载的任务
     */
    void cancelAll() {
        if (null != mBindService)
            mBindService.cancelAll();
    }

    /**
     * 暂停当前指定下载的任务
     *
     * @param downloadUrl 下载地址
     */
    void cancel(String downloadUrl) {
        if (null != mBindService)
            mBindService.cancel(downloadUrl);
    }

    /**
     * 开启当前所有下载任务
     */
    void startAllRequest() {
        if (null != mBindService)
            mBindService.startAllRequest();
    }

    /**
     * 开启当前指定下载任务
     *
     * @param downloadUrl 下载地址
     */
    void startRequest(String downloadUrl) {
        if (null != mBindService)
            mBindService.startRequest(downloadUrl);
    }

    /**
     * 清空当前下载请求对象,并停止服务
     */
    void clearAll() {
        if (null != mBindService) {
            mBindService.clearAll();
            mContext.unbindService(mServiceConnection);
            mContext.stopService(getService());
            mBindService = null;
        }
    }

    /**
     * 获取下载请求对应的what值
     *
     * @param downloadUrl 下载地址
     * @return
     */
    int getWhat(String downloadUrl) {
        int what = -1;
        if (null != mBindService)
            what = mBindService.getWhat(downloadUrl);

        return what;
    }

    /**
     * 获取下载请求What值对应的路径
     * <p>
     * 如果有重复的值，那么获取数据源最后一位路径值。
     * </p>
     *
     * @param what What值
     * @return 下载请求What值对应的路径值
     */
    String getDownloadRequestsUrl(int what) {
        String downloadRequestsUrl = "";
        if (null != mBindService)
            downloadRequestsUrl = mBindService.getDownloadRequestsUrl(what);

        return downloadRequestsUrl;
    }

    /**
     * 把指定的What从容器里面移除
     *
     * @param downloadUrl 下载地址
     */
    void removeWhatData(String downloadUrl) {
        if (null != mBindService)
            mBindService.removeWhatData(downloadUrl);
    }

    /**
     * 清空What存储容器
     */
    void removeWhatAll() {
        if (null != mBindService)
            mBindService.removeWhatAll();
    }

    @Override
    public void onFinish() {
        mBindService.cancelAll();
        mContext.unbindService(mServiceConnection);
        mBindService = null;
        mContext = null;
        mNohttpDownloadConfig = null;
    }
}
