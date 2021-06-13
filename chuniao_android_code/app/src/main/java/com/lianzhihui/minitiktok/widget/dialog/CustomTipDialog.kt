package com.lianzhihui.minitiktok.widget.dialog

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import com.echofeng.common.inter.CustomCallback
import com.echofeng.common.net.HttpManager
import com.echofeng.common.ui.base.BaseDialog
import com.echofeng.common.utils.SystemUtils
import com.lianzhihui.onlyleague.R
import com.net.core.callback.DownloadProgressCallBack
import com.net.core.exception.ApiException
import com.net.core.utils.HttpLog
import com.qmuiteam.qmui.util.QMUIDeviceHelper
import kotlinx.android.synthetic.main.dialog_custom_confirm.*
import kotlinx.android.synthetic.main.dialog_custom_confirm.dialog_tv_confirm
import kotlinx.android.synthetic.main.dialog_custom_confirm.tv_dialog_message
import kotlinx.android.synthetic.main.dialog_custom_confirm.tv_dialog_title
import kotlinx.android.synthetic.main.dialog_custom_tip.*


/**
 * Description:
 * Author: RAMON
 * CreateDate: 2020/10/27 2:59 PM
 * UpdateUser:
 * UpdateDate:
 * UpdateRemark:
 * Version:
 */
class CustomTipDialog : BaseDialog,View.OnClickListener{
    private var downLoadUrl: String = ""
    private var isProgress: Boolean = false
    private var isFinishDownLoad: Boolean = false
    private var customCallback: CustomCallback? = null

    constructor(context: Context) : super(context) {}
    constructor(context: Context, customCallback: CustomCallback?,isProgress:Boolean) : super(context) {
        this.customCallback = customCallback
        this.isProgress=isProgress
    }
    constructor(context: Context, downLoadUrl: String?,isProgress:Boolean) : super(context) {
        this.customCallback = customCallback
        this.downLoadUrl= downLoadUrl!!
        this.isProgress=isProgress
    }

    override fun provideViewId(): Int {
        return R.layout.dialog_custom_tip
    }

    fun setText(title: String?, content: String?,  btnConfirm: String?) {
        tv_dialog_title.setText(title)
        tv_dialog_message.setText(content)
        dialog_tv_confirm.setText(btnConfirm)
    }
    override fun initView() {
        dialog_tv_confirm.setOnClickListener(this)
//        setCanceledOnTouchOutside(false)
    }

    fun startDownLoad(){
        downloadFileByBrowser(context,downLoadUrl);
//        HttpManager.downloadFile(downLoadUrl, object : DownloadProgressCallBack<String?>() {
//            override fun update(bytesRead: Long, contentLength: Long, done: Boolean) {
//                val progress = (bytesRead * 100 / contentLength).toInt()
//                tv_dialog_message.visibility = View.GONE
//                qmuiProgressBar.visibility = View.VISIBLE
//                qmuiProgressBar.setProgress(progress)
//                if (done) { //下载完成
//                    SystemUtils.installApk(context,"/sdcard/lvban/update/lvban_release_v1.apk")
//                }
//            }
//            override fun onComplete(path: String) {
//                dialog_tv_confirm.isEnabled = true
//                isFinishDownLoad = true
//                dialog_tv_confirm.setText("立即安装")
//                SystemUtils.installApk(context,"/sdcard/lvban/update/lvban_release_v1.apk")
//            }
//            override fun onStart() {
//                dialog_tv_confirm.isEnabled = false
//            }
//            override fun onError(e: ApiException) {}
//        })
    }
    /**
     * 使用这种方法下载完全把工作交给了系统应用，自己的应用中不需要申请任何权限，方便简单快捷。但如此我们也不能知道
     * 下载文件的大小，不能监听下载进度和下载结果。
     *
     * @param context 上下文
     * @param url     下载 url
     */
    fun downloadFileByBrowser(context: Context, url: String) {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.addCategory(Intent.CATEGORY_BROWSABLE)
        intent.data = Uri.parse(url)
        context.startActivity(intent)
    }
    override fun onClick(v: View?) {
        if (v != null) {
            when(v.id){
                R.id.dialog_tv_confirm->{
                    if (isProgress){
                        if (isFinishDownLoad){
                            SystemUtils.installApk(context,"/sdcard/lvban/update/lvban_release_v1.apk")
                        }else {
                            startDownLoad()
                        }
                    }else {
                        customCallback?.onCompare(true)
                    }
                }
            }
        }
    }
}