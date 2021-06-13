package com.lianzhihui.minitiktok.ui.system

import android.view.View
import com.echofeng.common.ui.base.BaseActivity
import com.lianzhihui.onlyleague.R
import com.qmuiteam.qmui.kotlin.onClick
import kotlinx.android.synthetic.main.title_bar_common.*

class AboutActivity : BaseActivity() ,View.OnClickListener{
    override fun getLayoutId(): Int {
        return R.layout.activity_about
    }

    override fun initView() {
        include_top_iv_back.onClick { this }
    }

    override fun onClick(p0: View?) {
    }

}