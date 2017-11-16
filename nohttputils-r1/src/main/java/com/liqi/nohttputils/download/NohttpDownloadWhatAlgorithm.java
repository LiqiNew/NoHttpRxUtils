package com.liqi.nohttputils.download;

import java.util.Hashtable;

/**
 * 生产下载地址对应唯一what算法对象
 *
 * @author Liqi
 */
public class NohttpDownloadWhatAlgorithm {
    private static NohttpDownloadWhatAlgorithm mBaseHandlerIdObj;
    /**
     * what值初始值
     */
    private int mWhatInit = 0x1;

    private NohttpDownloadWhatAlgorithm() {
    }

    public synchronized static NohttpDownloadWhatAlgorithm getNohttpDownloadKeyFactory() {
        return mBaseHandlerIdObj = null == mBaseHandlerIdObj ? new NohttpDownloadWhatAlgorithm()
                : mBaseHandlerIdObj;
    }

    public void setWhatInit(int whatInit) {
        mWhatInit = whatInit;
    }

    /**
     * 获取下载地址对应的唯一What值
     *
     * @param whats       存储What值集合
     * @param downloadUrl 下载地址
     * @return
     */
    public int productionWhat(Hashtable<String, Integer> whats, String downloadUrl) {
        if (whats.containsKey(downloadUrl)) {
            return whats.get(downloadUrl);
        } else {
            return addClassID(whats, downloadUrl);
        }
    }


    /**
     * 把对应的下載請求的What值存储进容器里面
     *
     * @param downloadUrl
     * @return
     */
    private synchronized int addClassID(Hashtable<String, Integer> whats, String downloadUrl) {
        int values = getValues(whats);
        synchronized (this) {
            whats.put(downloadUrl, values);
        }
        return values;
    }

    /**
     * 保证容器里面的What值唯一性
     */
    private synchronized int getValues(Hashtable<String, Integer> whats) {
        if (whats.containsValue(++mWhatInit)) {
            getValues(whats);
        }
        return mWhatInit;
    }
}
