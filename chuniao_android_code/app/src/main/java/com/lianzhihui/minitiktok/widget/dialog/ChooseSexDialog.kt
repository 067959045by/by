package com.lianzhihui.minitiktok.widget.dialog

import android.content.Context
import android.view.View
import com.echofeng.common.inter.CustomCallback
import com.echofeng.common.ui.base.BaseDialog
import com.lianzhihui.onlyleague.R
import kotlinx.android.synthetic.main.dialog_choose_sex.*

/**
 * Description:
 * Author: RAMON
 * CreateDate: 2020/10/27 2:59 PM
 * UpdateUser:
 * UpdateDate:
 * UpdateRemark:
 * Version:
 */
class ChooseSexDialog : BaseDialog,View.OnClickListener {
    private var customCallback: CustomCallback? = null
    constructor(context: Context) : super(context) {}
    constructor(context: Context, customCallback: CustomCallback?) : super(context) {
        this.customCallback = customCallback
    }
    override fun provideViewId(): Int {
        return R.layout.dialog_choose_sex
    }
    override fun initView() {
        bottom()
        fullWidth()
        rl_boy.setOnClickListener(this)
        rl_girl.setOnClickListener(this)
        rl_cancel.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when(v.id){
                R.id.rl_boy -> {
                    customCallback?.onCompare(1)
                    dismiss()}
                R.id.rl_girl -> {
                    customCallback?.onCompare(2)
                    dismiss()}
                R.id.rl_cancel -> dismiss()
            }
        }
    }
}