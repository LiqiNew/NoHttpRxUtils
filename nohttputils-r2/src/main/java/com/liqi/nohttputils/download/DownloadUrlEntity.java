package com.liqi.nohttputils.download;


import com.yanzhenjie.nohttp.Logger;

/**
 * 文件下载路径和存储文件名对象
 * Created by LiQi on 2017/2/23.
 */

public class DownloadUrlEntity {
    private String mDownloadUrL;
    private String mFileName;

    DownloadUrlEntity() {
        super();
    }

    public String getDownloadUrL() {
        return mDownloadUrL;
    }

    public void setDownloadUrL(String downloadUrL) {
        mDownloadUrL = downloadUrL;
    }

    public String getFileName() {
        return null == mFileName || "".equals(mFileName) ? urlGetName() : mFileName;
    }

    public void setFileName(String fileName) {
        mFileName = fileName;
    }

    /**
     * 切割URl最尾部的内容，按“/”切割
     *
     * @return
     */
    private String urlGetName() {
        String[] split = mDownloadUrL.split("/");
        if (split.length > 0) {
            return split[split.length - 1];
        } else {
            Logger.e("下载地址按“/”切割错误,无法获取存储文件名");
            return "ErrorDownloading";
        }
    }
}
