package com.liqi.nohttputils.download;

/**
 * 文件下载工具代理对象
 *
 * 个人QQ:543945827
 * NoHttp作者群号：46523908
 * Created by LiQi on 2017/2/24.
 */

public class NohttpDownloadUtils {
    /**
     * 获取下载请求构建器
     *
     * @return
     */
    public static NohttpDownloadConfig.Build getNohttpDownloadBuild() {
        return new NohttpDownloadConfig.Build();
    }

    /**
     * 暂停全部下载
     */
    public static void cancelAll() {
        NohttpDownload.getNohttpDownload().cancelAll();
    }

    /**
     * 暂停指定下载
     *
     * @param downloadUrl 下载地址
     */
    public static void cancel(String downloadUrl) {
        NohttpDownload.getNohttpDownload().cancel(downloadUrl);
    }

    /**
     * 恢复全部下载
     */
    public static void startAllRequest() {
        NohttpDownload.getNohttpDownload().startAllRequest();
    }

    /**
     * 恢复指定下载
     *
     * @param downloadUrl 下载地址
     */
    public static void startRequest(String downloadUrl) {
        NohttpDownload.getNohttpDownload().startRequest(downloadUrl);
    }

    /**
     * 清空当前下载请求对象,并停止服务
     */
    public static void clearAll() {
        NohttpDownload.getNohttpDownload().clearAll();
    }

    /**
     * 获取下载URL对象的What值
     *
     * @param downloadUrl 下载地址
     * @return
     */
    public static int getDownloadRequestsWhat(String downloadUrl) {
        return NohttpDownload.getNohttpDownload().getWhat(downloadUrl);
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
    public static String getDownloadRequestsUrl(int what) {
        return NohttpDownload.getNohttpDownload().getDownloadRequestsUrl(what);
    }

    /**
     * 移除下载地址对应的What
     *
     * @param downloadUrl 下载地址
     */
    public static void removeWhatData(String downloadUrl) {
        NohttpDownload.getNohttpDownload().removeWhatData(downloadUrl);
    }

    /**
     * 移除全部下载What
     *
     */
    public static void removeWhatAll() {
        NohttpDownload.getNohttpDownload().removeWhatAll();
    }
}
