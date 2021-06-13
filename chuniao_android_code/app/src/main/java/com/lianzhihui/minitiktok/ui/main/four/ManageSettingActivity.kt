package com.lianzhihui.minitiktok.ui.main.four

import android.content.Intent
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideContext
import com.echofeng.common.config.DataManager
import com.echofeng.common.ui.base.BaseActivity
import com.echofeng.common.utils.SystemUtils
import com.lianzhihui.minitiktok.bean.system.VersionResponse
import com.lianzhihui.minitiktok.bean.user.UserInfo
import com.lianzhihui.minitiktok.presenter.SystemPresnterImp
import com.lianzhihui.minitiktok.ui.account.ui.login.LoginActivity
import com.lianzhihui.minitiktok.ui.system.BindInvatationActivity
import com.lianzhihui.minitiktok.view.SystemView
import com.lianzhihui.minitiktok.widget.dialog.CustomTipDialog
import com.lianzhihui.onlyleague.R
import kotlinx.android.synthetic.main.activity_manage_setting.*

class ManageSettingActivity : BaseActivity() ,SystemView, View.OnClickListener{
    private lateinit var systemPresnterImp: SystemPresnterImp

    override fun getLayoutId(): Int {
        return R.layout.activity_manage_setting
    }

    override fun initView() {
        setTitle("账号管理")
        item_setting1.setOnClickListener(this)
        itemBindInvavite.setOnClickListener(this)
        item_version.setOnClickListener(this)
        tvVersion.setText(SystemUtils.getAppVersionName(this))
        systemPresnterImp = SystemPresnterImp(this,this);
    }

    override fun setSuccess(o: Any?) {
        dismissLoading()
    }

    override fun setFailure(o: Any?) {
        dismissLoading()
    }

    override fun setCheckUpdateSuccess(data: VersionResponse?) {
        dismissLoading()
        var versionDialog = CustomTipDialog(this, data!!.package_path,true)
        versionDialog.show()
        versionDialog.setText(data!!.title, data!!.remark,"立即下载")
    }
    override fun onClick(v: View?) {
        if (v != null) {
            when(v.id){
                R.id.item_setting1->{
                    var intent = Intent(this,LoginActivity::class.java)
                    startActivity(intent)
                }
                R.id.itemBindInvavite->{
                    var intent = Intent(this,BindInvatationActivity::class.java)
                    startActivity(intent)
                }
                R.id.bt_exit_login->{
                    DataManager.exitAccount();
                }
                R.id.item_version->{
                    showLoading("")
                    systemPresnterImp.checkUpdate()
                }
            }
        }
    }

}