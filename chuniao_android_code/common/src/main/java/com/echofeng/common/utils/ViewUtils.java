package com.echofeng.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

public class ViewUtils {
    /**
     *  @作者: Administrator  by:jp_canlada@foxmail.com
     *  @描述: 设置TextView 属性
     *  @时间: 2018/11/2 0002  17:34
     */
    public static void setDrawableLeft(Context context, TextView view, String text, int icon){
        view.setText(text);
        if (icon!=0){
            Drawable drawable = ContextCompat.getDrawable(context,icon);
            drawable.setBounds( 0, 0, drawable.getMinimumWidth(),drawable.getMinimumHeight());
            view.setCompoundDrawables(drawable,null,null,null);
        }
    }
    /**
     * view转bitmap
     */
    public static Bitmap viewConversionBitmap(View v) {
        int w = v.getWidth();
        int h = v.getHeight();

        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);

        c.drawColor(Color.WHITE);
        /** 如果不设置canvas画布为白色，则生成透明 */

        v.layout((int) v.getX(), (int) v.getY(), w+(int) v.getX(), h+(int) v.getY());
        v.draw(c);

        return bmp;
    }
}
