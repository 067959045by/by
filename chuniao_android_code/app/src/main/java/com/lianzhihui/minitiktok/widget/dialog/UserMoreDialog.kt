package com.lianzhihui.minitiktok.widget.dialog

import android.content.Context
import android.view.View
import com.echofeng.common.inter.CustomCallback
import com.echofeng.common.ui.base.BaseDialog
import com.lianzhihui.onlyleague.R
import kotlinx.android.synthetic.main.dialog_user_more.*

/**
 * Description:
 * Author: RAMON
 * CreateDate: 2020/10/27 2:59 PM
 * UpdateUser:
 * UpdateDate:
 * UpdateRemark:
 * Version:
 */
class UserMoreDialog : BaseDialog,View.OnClickListener {
    private var customCallback: CustomCallback? = null
    constructor(context: Context) : super(context) {}
    constructor(context: Context, customCallback: CustomCallback?) : super(context) {
        this.customCallback = customCallback
    }
    override fun provideViewId(): Int {
        return R.layout.dialog_user_more
    }
    override fun initView() {
        bottom()
        fullWidth()
        rl_jubao.setOnClickListener(this)
        rl_lahei.setOnClickListener(this)
        rl_cancel.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when(v.id){
                R.id.rl_jubao -> {
                    customCallback?.onCompare(1)
                    dismiss()}
                R.id.rl_lahei -> {
                    dismiss()}
                R.id.rl_cancel -> dismiss()
            }
        }
    }
}