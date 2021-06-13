package com.echofeng.common.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.echofeng.common.MyApplication;
import com.echofeng.common.R;
import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperToast;

/**
 * Copyright (C), 2019-2019, wase_five
 * FileName: MyToast
 * Author: echo
 * Date: 2019/11/13 15:26
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class MyToast {
    /**
     * 吐司
     * @param content
     */
    public static void showToast(String content){
        if(content!=null&&content!="") {
            Toast toast = Toast.makeText(MyApplication.mContext, content, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setText(content);
            toast.show();
        }else {
        }
    }


    /**
     * 吐司
     * @param content
     */
    public static void showToastLong(String content){
        if(content!=null&&content!="") {
            Toast toast = Toast.makeText(MyApplication.mContext, content, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setText(content);
            toast.show();
        }else {
        }
    }

    /**
     * 防止多次吐司
     * @param content
     */
    public static void singleToast(String content){

        if(isFastClick()) {
            if(content!=null&&content!="") {
                Toast toast = Toast.makeText(MyApplication.mContext, content, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.setText(content);
                toast.show();
            }else {
            }

        }

    }
    /**
     * 吐司showCustomToast
     * @param content
     */
    public static void showCustomToast(String content){
        if(content!=null&&content!="") {
            SuperToast superToast = SuperToast.create(MyApplication.mContext,content, Style.DURATION_SHORT);
            superToast.getView().setBackgroundColor(ContextCompat.getColor(MyApplication.mContext, R.color.colorAccent));
            superToast.setTextColor(ContextCompat.getColor(MyApplication.mContext, R.color.white));
            superToast.show();
        }else {
        }
    }

    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }


    /**
     * 防止多次吐司
     * @param content
     */
    public static void singleToast(Context context, Object content){
        Toast toast;
        if(isFastClick()) {
            if(content!=null) {
                if(content instanceof Integer){
                     toast = Toast.makeText(context, (int)content, Toast.LENGTH_SHORT);
                }else{
                    toast = Toast.makeText(context, content.toString(), Toast.LENGTH_SHORT);
                }
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
    }
}
