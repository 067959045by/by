package com.lianzhihui.minitiktok.widget.dialog

import android.content.Context
import android.view.View
import com.echofeng.common.ui.base.BaseDialog
import com.lianzhihui.onlyleague.R
import kotlinx.android.synthetic.main.dialog_comment.*

class CommentDialog(context: Context): BaseDialog(context),View.OnClickListener{
    override fun provideViewId(): Int {
        return R.layout.dialog_comment
    }

    override fun initView() {
        bottom()
        fullWidth()
        iv_close.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.iv_close->{
                dismiss()
            }
        }
    }
}