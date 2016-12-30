package com.liqi.nohttputils.nohttp;

import android.graphics.Bitmap;

import com.yolanda.nohttp.BasicBinary;

import java.io.File;
import java.io.InputStream;

/**
 * 文件上传对象生存工厂
 * Created by LiQi on 2016/12/9.
 */
public class BinaryFactory {
    public static BasicBinary getBinary(InputStream inputStream, String fileName) {
        return new BinaryAllObj(inputStream, fileName);
    }

    public static BasicBinary getBinary(InputStream inputStream, String fileName, String mimeType) {
        return new BinaryAllObj(inputStream, fileName, mimeType);
    }

    public static BasicBinary getBinary(File file) {
        return new BinaryAllObj(file);
    }

    public static BasicBinary getBinary(File file, String fileName) {
        return new BinaryAllObj(file, fileName);
    }

    public static BasicBinary getBinary(File file, String fileName, String mimeType) {
        return new BinaryAllObj(file, fileName, mimeType);
    }

    public static BasicBinary getBinary(Bitmap bitmap, String fileName) {
        return new BinaryAllObj(bitmap, fileName);
    }

    public BasicBinary getBinary(Bitmap bitmap, String fileName, String mimeType) {
        return new BinaryAllObj(bitmap, fileName, mimeType);
    }
}
