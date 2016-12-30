package com.liqi.nohttputils.nohttp;

import android.graphics.Bitmap;
import android.util.Log;

import com.yolanda.nohttp.BasicBinary;
import com.yolanda.nohttp.Binary;
import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.tools.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * 上传文件对象
 * Created by LiQi on 2016/12/9.
 */
 class BinaryAllObj extends BasicBinary {

    protected InputStream inputStream;

    /*==========inputStream形式传入==========*/

    /**
     * An input stream {@link Binary}.
     *
     * @param inputStream must be {@link FileInputStream}, {@link ByteArrayInputStream}.
     * @param fileName    file name. Had better pass this value, unless the server tube don't care about the file name.
     */
     BinaryAllObj(InputStream inputStream, String fileName) {
        this(inputStream, fileName, null);
    }

    /**
     * An input stream {@link Binary}.
     *
     * @param inputStream must be {@link FileInputStream}, {@link ByteArrayInputStream}.
     * @param fileName    file name. Had better pass this value, unless the server tube don't care about the file name.
     * @param mimeType    content type.
     */
     BinaryAllObj(InputStream inputStream, String fileName, String mimeType) {
        super(fileName, mimeType);
        this.inputStream = inputStream;
        if (!(inputStream instanceof FileInputStream) && !(inputStream instanceof ByteArrayInputStream)) {
            Log.e("Binary", "Binary was cancelled, because the InputStream must be FileInputStream or ByteArrayInputStream.");
            super.cancel();
        }
    }


    /*==========File形式传入==========*/

    /**
     * File binary.
     *
     * @param file a file.
     */
     BinaryAllObj(File file) {
        this(file, file.getName(), null);
    }

    /**
     * File binary.
     *
     * @param file     a file.
     * @param fileName file name.
     */
     BinaryAllObj(File file, String fileName) {
        this(file, fileName, null);
    }

    /**
     * File binary.
     *
     * @param file     a file.
     * @param fileName file name.
     * @param mimeType content type.
     */
     BinaryAllObj(File file, String fileName, String mimeType) {
        super(fileName, mimeType);
        try {
            this.inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            Log.e("Binary", "Binary was cancelled, because the file does not exist.");
            super.cancel();
        }
    }


   /* ==========bitmap形式传入==========*/

    /**
     * An input stream {@link Binary}.
     *
     * @param bitmap   image.
     * @param fileName file name. Had better pass this value, unless the server tube don't care about the file name.
     */
     BinaryAllObj(Bitmap bitmap, String fileName) {
        this(bitmap, fileName, null);
    }

    /**
     * An input stream {@link Binary}.
     *
     * @param bitmap   image.
     * @param fileName file name. Had better pass this value, unless the server tube don't care about the file name.
     * @param mimeType such as: image/png.
     */
     BinaryAllObj(Bitmap bitmap, String fileName, String mimeType) {
        super(fileName, mimeType);

        if (bitmap != null) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            IOUtils.closeQuietly(outputStream);
            inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        } else {
            Log.e("Binary", "Binary was cancelled, because the Bitmap is null or bitmap is recycled.");
            super.cancel();
        }
    }

    @Override
    public void cancel() {
        IOUtils.closeQuietly(inputStream);
        super.cancel();
    }

    @Override
    public long getBinaryLength() {
        try {
            return inputStream == null ? 0 : inputStream.available();
        } catch (IOException e) {
            Logger.e(e);
        }
        return 0;
    }

    @Override
    protected InputStream getInputStream() throws IOException {
        return inputStream;
    }
}
