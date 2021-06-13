package com.echofeng.common.ui.widget.dialog

import android.content.Context
import android.os.Handler
import android.text.TextUtils
import android.view.View
import com.echofeng.common.R
import com.echofeng.common.ui.base.BaseDialog
import com.echofeng.common.utils.LogUtil
import kotlinx.android.synthetic.main.dialog_loading.*

class LoadingDialog(context: Context) : BaseDialog(context) {
    override fun provideViewId(): Int {
        return R.layout.dialog_loading
    }

    override fun initView() {
        setCanceledOnTouchOutside(false)
        setCancelable(false)
    }
    fun setTitle(title: String?) {
        if (TextUtils.isEmpty(title)) {
            tv_title.setVisibility(View.GONE)
        } else {
            tv_title.setVisibility(View.VISIBLE)
        }
        tv_title.setText(title)
    }

    var handler: Handler? = null
    var disRb: Runnable? = null

    fun showDialog() {
        try {
            handler = Handler()
            disRb = Runnable {
                if (context != null) {
                    dismiss()
                }
            }
            handler!!.postDelayed(disRb, 10 * 1000.toLong())
        } catch (e: Exception) {
            LogUtil.error("loadingDialog:", e.message)
        }
        show()
    }
    override fun dismiss() {
        super.dismiss()
    }

    override fun show() {
        super.show()
    }
}