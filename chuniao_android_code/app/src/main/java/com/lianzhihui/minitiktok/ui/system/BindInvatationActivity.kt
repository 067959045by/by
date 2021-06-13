package com.lianzhihui.minitiktok.ui.system

import android.text.TextUtils
import android.view.View
import com.echofeng.common.ui.base.BaseActivity
import com.echofeng.common.utils.MyToast
import com.lianzhihui.minitiktok.bean.system.VersionResponse
import com.lianzhihui.minitiktok.presenter.SystemPresnterImp
import com.lianzhihui.minitiktok.view.SystemView
import com.lianzhihui.onlyleague.R
import kotlinx.android.synthetic.main.activity_bind_invatation.*

class BindInvatationActivity : BaseActivity() ,SystemView,View.OnClickListener{

    private lateinit var systemPresnterImp: SystemPresnterImp

    override fun getLayoutId(): Int {
        return R.layout.activity_bind_invatation
    }

    override fun initView() {
        setTitle("绑定邀请码")
        btConfirm.setOnClickListener(this)
        systemPresnterImp = SystemPresnterImp(this,this)
    }

    override fun setSuccess(o: Any?) {
        dismissLoading()
        finish()
    }

    override fun setFailure(o: Any?) {
        dismissLoading()
    }

    override fun setCheckUpdateSuccess(data: VersionResponse?) {
        TODO("Not yet implemented")
    }

    override fun onClick(v: View?) {
        when(v){
            btConfirm->{
                var code = etInvatation.text.toString()
                if (TextUtils.isEmpty(code)){
                    MyToast.showToast("请填写邀请码")
                    return
                }
                showLoading("")
                systemPresnterImp.doBindInvationCode(code)
            }
        }
    }
}