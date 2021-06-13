package com.lianzhihui.minitiktok.ui.main.four

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.echofeng.common.ui.base.BaseActivity
import com.lianzhihui.onlyleague.R

class TransRecordActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_trans_record
    }

    override fun initView() {
        setTitle("交易记录")
    }
}