package com.liqi.nohttputils.download;

import android.app.Activity;
import android.content.Context;


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
    private int mThreadPoolSize;
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

    private NohttpDownloadConfig() {
        mDownloadUrlEntities = null == mDownloadUrlEntities ? new ArrayList<DownloadUrlEntity>() : mDownloadUrlEntities;
    }

    public int getThreadPoolSize() {
        return mThreadPoolSize;
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
         * 设置线程池并发数量
         *
         * @param threadPoolSize 线程池并发数量
         * @return
         */
        public Build setThreadPoolSize(int threadPoolSize) {
            mNohttpDownloadConfig.mThreadPoolSize = threadPoolSize;
            return this;
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

        public void satart(Activity activity) {
            NohttpDownload.getNohttpDownload().init(mNohttpDownloadConfig, activity.getApplicationContext());
        }
    }

}
