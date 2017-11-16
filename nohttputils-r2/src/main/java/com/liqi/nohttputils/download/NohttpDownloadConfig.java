package com.liqi.nohttputils.download;

import android.app.Activity;

import com.liqi.nohttputils.nohttp.RxUtilsConfig;
import com.yanzhenjie.nohttp.download.DownloadListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件下载配置对象
 * Created by LiQi on 2017/2/23.
 */

public class NohttpDownloadConfig {
    /**
     * 线程池数量,并发任务的数量
     */
    private final int THREADPOOLSIZE = RxUtilsConfig.ConfigBuilder.getConfigBuilder().getRxUtilsConfig().getThreadPoolSize();
    /**
     * 是否断点续传下载，默认断点续传
     */
    private boolean isRange = true;
    /**
     * 在指定的文件夹发现同名的文件是否删除后重新下载，true则删除重新下载，false则直接通知下载成功。
     */
    private boolean isDeleteOld = false;
    /**
     * 下载地址和存储文件名字集合
     */
    private List<DownloadUrlEntity> mDownloadUrlEntities;
    /**
     * 下载文件存储文件夹
     */
    private String mFileFolder;
    /**
     * 下载进度监听接口
     */
    private DownloadListener mDownloadListener;
    /**
     * 下载链接超时时间（默认以全局链接超时时间）
     */
    private int mConnectTimeout = -1;
    /**
     * 读取超时时间（默认以全局读取超时时间）
     */
    private int mReadTimeout = -1;
    /**
     * 请求失败重试计数
     */
    private int mRetryCount=-1;

    private NohttpDownloadConfig() {
        mDownloadUrlEntities = null == mDownloadUrlEntities ? new ArrayList<DownloadUrlEntity>() : mDownloadUrlEntities;
    }

    public int getTHREADPOOLSIZE() {
        return THREADPOOLSIZE;
    }

    public int getRetryCount() {
        return mRetryCount;
    }

    public int getConnectTimeout() {
        if (mConnectTimeout > 0) {
            return mConnectTimeout * 1000;
        } else {
            return mConnectTimeout;
        }
    }

    public int getReadTimeout() {
        if (mReadTimeout > 0) {
            return mReadTimeout * 1000;
        } else {
            return mReadTimeout;
        }
    }

    public boolean isRange() {
        return isRange;
    }

    public boolean isDeleteOld() {
        return isDeleteOld;
    }

    public List<DownloadUrlEntity> getDownloadUrlEntities() {
        return mDownloadUrlEntities;
    }

    public String getFileFolder() {
        return mFileFolder;
    }

    public DownloadListener getDownloadListener() {
        return mDownloadListener;
    }

    /**
     * 下载文件构建器对象
     */
    public static class Build {
        private NohttpDownloadConfig mNohttpDownloadConfig;

        Build() {
            mNohttpDownloadConfig = new NohttpDownloadConfig();
        }

        /**
         * 设置是否断点续传下载
         *
         * @param isRange 是否断点续传下载
         * @return
         */
        public Build setRange(boolean isRange) {
            mNohttpDownloadConfig.isRange = isRange;
            return this;
        }

        /**
         * 设置在指定的文件夹发现同名的文件是否删除后重新下载
         *
         * @param isDeleteOld 是否删除后重新下载
         * @return
         */
        public Build setDeleteOld(boolean isDeleteOld) {
            mNohttpDownloadConfig.isDeleteOld = isDeleteOld;
            return this;
        }

        /**
         * 添加下载文件参数
         *
         * @param downloadUrl 下载地址
         * @param fileName    存储文件名字(如果为空，文件名字就从URL中截取)
         * @return
         */
        public Build addDownloadParameter(String downloadUrl, String fileName) {
            if (null != downloadUrl && !"".equals(downloadUrl)) {
                DownloadUrlEntity downloadUrlEntity = new DownloadUrlEntity();
                downloadUrlEntity.setDownloadUrL(downloadUrl);
                downloadUrlEntity.setFileName(fileName);
                mNohttpDownloadConfig.mDownloadUrlEntities.add(downloadUrlEntity);
            }
            return this;
        }

        /**
         * 设置下载进度监听接口
         *
         * @param downloadListener 下载进度监听接口
         * @return
         */
        public Build setDownloadListener(DownloadListener downloadListener) {
            mNohttpDownloadConfig.mDownloadListener = downloadListener;
            return this;
        }

        /**
         * 设置下载文件存储文件路径
         *
         * @param fileFolder 下载文件存储文件路径
         * @return
         */
        public Build setFileFolder(String fileFolder) {
            mNohttpDownloadConfig.mFileFolder = fileFolder;
            return this;
        }

        /**
         * 设置链接超时时间
         *
         * @param connectTimeout 时间，单位秒
         * @return
         */
        public Build setConnectTimeout(int connectTimeout) {
            mNohttpDownloadConfig.mConnectTimeout = connectTimeout;
            return this;
        }

        /**
         * 设置读取时间
         *
         * @param readTimeout 时间，单位秒
         * @return
         */
        public Build setReadTimeout(int readTimeout) {
            mNohttpDownloadConfig.mReadTimeout = readTimeout;
            return this;
        }

        /**
         * 设置请求失败重试计数。默认值是0,也就是说,失败后不会再次发起请求。
         *
         * @param retryCount 重试计数
         * @return
         */
        public Build setRetryCount(int retryCount) {
            mNohttpDownloadConfig.mRetryCount = retryCount;
            return this;
        }

        public void satart(Activity activity) {
            NohttpDownload.getNohttpDownload().init(mNohttpDownloadConfig, activity.getApplicationContext());
        }
    }

}
