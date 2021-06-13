package com.lianzhihui.minitiktok.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Handler
import android.text.TextUtils
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.echofeng.common.config.AppConfig
import com.echofeng.common.config.AppStatusManager
import com.echofeng.common.ui.base.BaseNoRestartActivity
import com.echofeng.common.utils.AESFileCrypt
import com.echofeng.common.utils.LogUtil
import com.echofeng.common.utils.SystemUtils
import com.echofeng.common.utils.VerificationCountDownTimer
import com.lianzhihui.minitiktok.bean.user.LoginResponse
import com.lianzhihui.minitiktok.config.AppConstants
import com.lianzhihui.minitiktok.presenter.LoginPresnterImp
import com.lianzhihui.minitiktok.view.LoginView
import com.lianzhihui.onlyleague.R
import kotlinx.android.synthetic.main.activity_splash.*
import java.io.File

class SplashActivity : BaseNoRestartActivity(),LoginView ,View.OnClickListener{
    private var pastChannel: String = ""
    private var pastCode: String = ""
    private lateinit var verificationCountDownTimer: VerificationCountDownTimer
    private lateinit var loginPresnterImp: LoginPresnterImp

    override fun getLayoutId(): Int {
        return R.layout.activity_splash;
    }

    override fun initView() {
        tvJump.setOnClickListener(this)
        ivCover.setOnClickListener(this)
        loginPresnterImp = LoginPresnterImp(this,this)
        showLoading("登录中...")
        loginPresnterImp.doCheckIp()
        var thread = Thread()
        thread.run {
            initPast()
        }
        thread.start()
    }
    override fun initData() {
        verificationCountDownTimer = object : VerificationCountDownTimer(6 * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                super.onTick(millisUntilFinished)
                runOnUiThread {
                    tvJump.setText((millisUntilFinished / 1000).toString() + "S")
                    tvJump.setEnabled(false)
                }
            }

            override fun onFinish() {
                super.onFinish()
                runOnUiThread {
                    tvJump.setText("跳过")
                    tvJump.setEnabled(true)
                }
            }
        }
    }

    private fun initPast(){
        var clipboard = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        //无数据时直接返回
        if (!clipboard.hasPrimaryClip()) {
            return;
        }
        var clipData = clipboard.getPrimaryClip() as ClipData
        if (clipData != null && clipData.getItemCount() > 0) {
            var text = clipData.getItemAt(0).getText().toString()
            if (text.contains("code=")&&text.contains("channel=")){
                var splitStr = text.split("code=")
                pastCode = splitStr[1].split("&")[0]
                pastChannel = splitStr[1].split("&channel=")[1]
            }
        }
    }

    override fun setSuccess(o: Any?) {
        dismissLoading()
        if (TextUtils.isEmpty(pastChannel)){
            pastChannel = SystemUtils.getChannelName()
        }
        LogUtil.debug("TIKTOK_CHANNEL", pastCode+"当前的渠道为:"+pastChannel)
        loginPresnterImp.doLogin(pastCode,pastChannel)
    }

    override fun setFailure(o: Any?) {
        dismissLoading()
    }

    override fun setLoginSuccess(data: LoginResponse) {
        dismissLoading()
        ivCover.setTag(data.startup.link)
        AppConfig.upload_url = data.config.sys.upload_url

        val simpleTarget: SimpleTarget<File?> = object : SimpleTarget<File?>() {
            override fun onResourceReady(resource: File, transition: Transition<in File?>?) {
                val decryptBytes = AESFileCrypt.decryptData(resource)
                val bitmap = BitmapFactory.decodeByteArray(decryptBytes, 0, decryptBytes.size)
                ivCover.setImageBitmap(bitmap)
                tvJump.visibility = View.VISIBLE
            }
        }
        Handler().postDelayed({
            Glide.with(context!!).asFile().load(data!!.startup.cover).into(simpleTarget)
            verificationCountDownTimer.start()
        }, 2000)
    }

    override fun setBindPhoneSuccess(data: Any?) {
    }

    override fun onClick(v: View?) {
        when(v){
            tvJump->{
                AppStatusManager.getInstance().setAppStatus(AppConstants.AppStatus.STATUS_NORMAL)
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            ivCover->{
                var linkUrl = ivCover.getTag().toString()
                if (linkUrl.contains("url://")) {
                    val realLink: String = linkUrl.replace("url://", "")
                    SystemUtils.openBrowser(this, realLink)
                }
            }
        }
    }

}