package com.echofeng.common.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import static android.content.ContentValues.TAG;

public class FileUtils {
    public static File getFileFromUri(Uri uri, Context context) {
    if (uri == null) {
        return null;
    }
    switch (uri.getScheme()) {
        case "content":
            return getFileFromContentUri(uri, context);
        case "file":
            return new File(uri.getPath());
        default:
            return null;
    }
}

    /**
     通过内容解析中查询uri中的文件路径
     */
    private static File getFileFromContentUri(Uri contentUri, Context context) {
        if (contentUri == null) {
            return null;
        }
        File file = null;
        String filePath;
        String[] filePathColumn = {MediaStore.MediaColumns.DATA};
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(contentUri, filePathColumn, null,
                null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            filePath = cursor.getString(cursor.getColumnIndex(filePathColumn[0]));
            cursor.close();

            if (!TextUtils.isEmpty(filePath)) {
                file = new File(filePath);
            }
        }
        return file;
    }

    public static byte[] getFileByte(File resource) {
        RandomAccessFile rf = null;
        byte[] data = null;
        try {
            rf = new RandomAccessFile(resource, "r");
            data = new byte[(int) rf.length()];
            rf.readFully(data);
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            closeQuietly(rf);
        }
        return data;
    }

    //关闭读取file
    public static void closeQuietly(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        }
//        **
//                * 文件转字节
//     */
    public static byte[] file2Bytes(File file) {
        byte[] buffer = null;
        try {
            if (!file.exists()) {
                Log.w(TAG,  " file is not exist!");
                return null;
            }
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * 字节转文件
     */
    public static File bytes2File(byte[] bfile, String filePath) {
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        File file = null;
        try {
            file = new File(filePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if(!file.exists()){
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        Log.i(TAG, "file : " + file);
        return file;
    }

    /**
     * 大文件转字节（推荐，性能较好）
     *
     * @param filePath 文件的绝对路径
     * @return 转换后的字节数组
     */
    public static byte[] bigFile2Bytes(String filePath) {
        byte[] result = null;
        FileChannel fc = null;
        try {
            fc = new RandomAccessFile(filePath, "rw").getChannel();
            MappedByteBuffer byteBuffer = fc.map(FileChannel.MapMode.READ_ONLY, 0,
                    fc.size()).load();
            Log.i(TAG, "byteBuffer isLoaded :" + byteBuffer.isLoaded());
            result = new byte[(int) fc.size()];
            if (byteBuffer.remaining() > 0) {
                byteBuffer.get(result, 0, byteBuffer.remaining());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fc != null)
                    fc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.i(TAG, "bigFile2Bytes result: " + result);
        return result;
    }

    /**
     * Bitmap保存到文件
     *
     * @param bitmap   位图
     * @param filePath 保存到的绝对路径
     * @return 是否保存成功
     */
    public static boolean saveBitmap2File(Bitmap bitmap, String filePath) {
        boolean state = false;
        if (null == bitmap) {
            Log.e(TAG, " bitmap is null !");
            return state;
        }
        if (TextUtils.isEmpty(filePath)) {
            Log.e(TAG, " filePath is null !");
            return state;
        }
        File file = new File(filePath);
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if(!file.exists()){
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            Log.i(TAG, "file has save: " + filePath);
            state = true;
        } catch (Exception e) {
            e.printStackTrace();
            state = false;
        }
        return state;
    }
}
