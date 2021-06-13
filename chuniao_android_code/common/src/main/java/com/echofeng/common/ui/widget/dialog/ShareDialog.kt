package com.echofeng.common.ui.widget.dialog

import android.content.Context
import com.echofeng.common.R
import com.echofeng.common.ui.base.BaseDialog

class ShareDialog(context: Context) : BaseDialog(context) {
    override fun provideViewId(): Int {
        return R.layout.dialog_share
    }

    override fun initView() {
    }
}