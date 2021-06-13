package com.lianzhihui.minitiktok.ui.account.ui.login

import android.content.Intent
import android.text.TextUtils
import android.view.View
import com.echofeng.common.ui.base.BaseActivity
import com.echofeng.common.utils.MyToast
import com.echofeng.common.utils.VerificationCountDownTimer
import com.lianzhihui.minitiktok.bean.user.LoginResponse
import com.lianzhihui.minitiktok.presenter.LoginPresnterImp
import com.lianzhihui.minitiktok.ui.MainActivity
import com.lianzhihui.minitiktok.view.LoginView
import com.lianzhihui.onlyleague.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() ,LoginView,View.OnClickListener{
    private lateinit var loginPresnterImp: LoginPresnterImp
    private lateinit var verificationCountDownTimer: VerificationCountDownTimer

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initView() {
        setTitle("绑定手机号")
        loginPresnterImp = LoginPresnterImp(this,this)
        bt_login.setOnClickListener(this)
        tv_get_code.setOnClickListener(this)
        verificationCountDownTimer = object : VerificationCountDownTimer(60 * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                super.onTick(millisUntilFinished)
                runOnUiThread {
                    if (tv_get_code != null) {
                        tv_get_code.setText((millisUntilFinished / 1000).toString() + "S")
                        tv_get_code.setEnabled(false)
                    }
                }
            }

            override fun onFinish() {
                super.onFinish()
                runOnUiThread {
                    if (tv_get_code != null) {
                        tv_get_code.setText(getStringValues(R.string.text_bind2))
                        tv_get_code.setEnabled(true)
                    }
                }
            }
        }
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when(v.id){
                R.id.bt_login -> {
                    var phoneNum = et_phone.text.toString()
                    var phoneCode = et_code.text.toString()
                    if (TextUtils.isEmpty(phoneNum)){
                        MyToast.showToast("请填写手机号")
                        return
                    }
                    if (TextUtils.isEmpty(phoneCode)){
                        MyToast.showToast("请填写验证码")
                        return
                    }
                    loginPresnterImp.doPhoneBind(phoneNum,phoneCode)
                }
                R.id.tv_get_code -> {
                    var phoneNum = et_phone.text.toString()
                    if (TextUtils.isEmpty(phoneNum)){
                        MyToast.showToast("请填写手机号")
                        return
                    }
                    loginPresnterImp.getPhoneVerifyCode(phoneNum)
                }

            }
        }
    }

    override fun setSuccess(o: Any?) {
        verificationCountDownTimer.start()
    }

    override fun setFailure(o: Any?) {
    }

    override fun setLoginSuccess(data: LoginResponse?) {
    }

    override fun setBindPhoneSuccess(data: Any?) {
        finish()
    }

}