package com.lianzhihui.minitiktok.widget.dialog

import android.content.Context
import android.view.View
import com.echofeng.common.inter.CustomCallback
import com.echofeng.common.ui.base.BaseDialog
import com.lianzhihui.onlyleague.R
import kotlinx.android.synthetic.main.dialog_custom_confirm.*

/**
 * Description:
 * Author: RAMON
 * CreateDate: 2020/10/27 2:59 PM
 * UpdateUser:
 * UpdateDate:
 * UpdateRemark:
 * Version:
 */
class CustomConfirmDialog : BaseDialog,View.OnClickListener{
    private var customCallback: CustomCallback? = null

    constructor(context: Context) : super(context) {}
    constructor(context: Context, customCallback: CustomCallback?) : super(context) {
        this.customCallback = customCallback
    }

    override fun provideViewId(): Int {
        return R.layout.dialog_custom_confirm
    }

    fun setText(title: String?, content: String?, btnLeft: String?, btnRight: String?) {
        tv_dialog_title.setText(title)
        tv_dialog_title2.setText(content)
        dialog_tv_cancel.setText(btnLeft)
        dialog_tv_confirm.setText(btnRight)
    }
    override fun initView() {
        dialog_tv_cancel.setOnClickListener(this)
        dialog_tv_confirm.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when(v.id){
                R.id.dialog_tv_cancel->{
                    dismiss()
                    customCallback?.onCancel()
                }
                R.id.dialog_tv_confirm->{
                    dismiss()
                    customCallback?.onCompare(true)
                }
            }
        }
    }
}