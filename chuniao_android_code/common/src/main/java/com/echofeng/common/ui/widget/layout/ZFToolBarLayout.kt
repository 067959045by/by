package com.echofeng.common.ui.widget.layout

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.echofeng.common.R
import kotlinx.android.synthetic.main.title_bar_common.view.*

/**
 * Copyright (C), 2020-2021
 * FileName: ZFToolBarLayout
 * Date: 2021/2/23 16:40
 * Description:
 * 作者姓名 修改时间 版本号 描述
 */
class ZFToolBarLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {
    init {
        LayoutInflater.from(context).inflate(R.layout.title_bar_common, this, true)
    }

    fun setTitle(title : String){
        include_top_tv_title.setText(title)
    }
}