package com.echofeng.common.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * Copyright (C), 2020-2021
 * FileName: MyViewPager
 * Date: 2021/2/23 17:35
 * Description:
 * 作者姓名 修改时间 版本号 描述
 */
class MyViewPager @JvmOverloads constructor(context: Context, attrs: AttributeSet?): ViewPager(context, attrs) {
    private var scrollable = false
    fun setScrollable(scrollable: Boolean) {
        this.scrollable = scrollable
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return scrollable
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return scrollable
    }
}