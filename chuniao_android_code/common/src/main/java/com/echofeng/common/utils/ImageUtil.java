package com.echofeng.common.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.echofeng.common.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Copyright (C), 2019-2019, tcoin_obj
 * FileName: ImageUtil
 * Author: ZhaoFeng
 * Date: 2019/11/18 11:50
 * Description: 图片处理工具
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class ImageUtil {
    public static void loadDrawableIntoTextView(Context context,TextView targetView, String src){
        Glide.with(context).asDrawable().addListener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                targetView.setCompoundDrawables(null,null,null,resource);
                return false;
            }
        }).load(src);
    }
    public static void setDrawableLeft(Context context,TextView targetView, int resId){
        Drawable drawable = ContextCompat.getDrawable(context,resId);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        targetView.setCompoundDrawables(drawable,null,null,null);
    }
    public static void setDrawableLeftAndRight(Context context,TextView targetView, int resIdLeft,int resIdRight){
        Drawable drawableLeft = ContextCompat.getDrawable(context,resIdLeft);
        Drawable drawableRight = ContextCompat.getDrawable(context,resIdRight);
        drawableLeft.setBounds(0, 0, drawableLeft.getIntrinsicWidth(), drawableLeft.getIntrinsicHeight());
        drawableRight.setBounds(0, 0, drawableRight.getIntrinsicWidth(), drawableRight.getIntrinsicHeight());
        targetView.setCompoundDrawables(drawableLeft,null,drawableRight,null);
    }

    public static Drawable getDrawable(Context context,int drawableId){
        Drawable drawable = ContextCompat.getDrawable(context,drawableId);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        return drawable;
    }
    public static void setDrawableRight(Context context,TextView targetView, int resId){
        Drawable drawable = ContextCompat.getDrawable(context,resId);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        targetView.setCompoundDrawables(null,null,drawable,null);
    }

    /**
     * 获取系统相册目录
     * @return
     */
    public static String getGalleryPath(){
        String galleryPath= Environment.getExternalStorageDirectory()
                + File.separator + Environment.DIRECTORY_DCIM
                +File.separator+"Camera"+File.separator;
        return galleryPath;
    }
    /**
     * @param rootView 获取的bitmap数据
     * @param picName 自定义的图片名
     */
    public static void saveBmp2Gallery(Context context,View rootView, String picName) {
        Bitmap bitmap = ViewUtils.viewConversionBitmap(rootView);
        if (picName.contains(".jpg")){
            picName.replace(".jpg","");
        }
        //系统相册目录
        String galleryPath= getGalleryPath();
        // 声明文件对象
        File file = null;
        // 声明输出流
        FileOutputStream outStream = null;
        try {
            // 如果有目标文件，直接获得文件对象，否则创建一个以filename为名称的文件
            file = new File(galleryPath, picName);
            // 获得输出流，如果文件中有内容，追加内容
            outStream = new FileOutputStream(picName);
            if (null != outStream) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outStream);
            }

        } catch (Exception e) {
            e.getStackTrace();
        }finally {
            try {
                if (outStream != null) {
                    outStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//通知相册更新
        MediaStore.Images.Media.insertImage(context.getContentResolver(),
                bitmap, picName, null);
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);
        MyToast.showToast( context.getString(R.string.qrcode_save_successfully));

    }
}
