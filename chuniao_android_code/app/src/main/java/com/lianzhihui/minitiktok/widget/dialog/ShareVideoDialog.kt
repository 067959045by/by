package com.lianzhihui.minitiktok.widget.dialog

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.echofeng.common.MyApplication
import com.echofeng.common.config.DataManager
import com.echofeng.common.ui.base.BaseDialog
import com.echofeng.common.utils.AESFileCrypt
import com.echofeng.common.utils.GsonUtil
import com.echofeng.common.utils.ImageUtil
import com.lianzhihui.minitiktok.bean.hot.VideoResponse
import com.lianzhihui.minitiktok.bean.user.LoginResponse
import com.lianzhihui.minitiktok.ui.system.ReportVideoActivity
import com.lianzhihui.onlyleague.R
import kotlinx.android.synthetic.main.dialog_share_video.*
import kotlinx.android.synthetic.main.view_controller.view.*
import java.io.File
import java.util.ArrayList


class ShareVideoDialog(context: Context): BaseDialog(context) ,View.OnClickListener{
    private var videoId: String? = null
    override fun provideViewId(): Int {
        return R.layout.dialog_share_video
    }

    override fun initView() {
        //生成二维码
        bottom()
        fullWidth()
        rl_cancel.setOnClickListener(this)
        btCopy.setOnClickListener(this)
        btSave.setOnClickListener(this)
        tv_report.setOnClickListener(this)
    }
    fun setVideoData(data: VideoResponse){
        videoId = data.id
        val simpleTarget: SimpleTarget<File?> = object : SimpleTarget<File?>() {
            override fun onResourceReady(resource: File, transition: Transition<in File?>?) {
                val decryptBytes = AESFileCrypt.decryptData(resource)
                val bitmap = BitmapFactory.decodeByteArray(decryptBytes, 0, decryptBytes.size)
                ivCover.setImageBitmap(bitmap)
            }
        }
        var loginResponse = GsonUtil.GsonToBean(DataManager.getLoginJsonElement(),LoginResponse::class.java);
        Glide.with(context).asFile().load(data.cover).error(R.mipmap.ic_launcher).into(simpleTarget)
        val bitmap: Bitmap = QRCodeEncoder.syncEncodeQRCode(loginResponse.config.sys.share_url, 400)
        ivQrCode.setImageBitmap(bitmap)
        btCopy.setTag(loginResponse.config.sys.share_text)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.rl_cancel -> dismiss()
            R.id.btSave -> {
                ImageUtil.saveBmp2Gallery(context,rlPoster,"lvban_poster.jpg")
            }
            R.id.btCopy -> {
                copyString(v.getTag().toString())
            }
            R.id.tv_report -> {
                var intent = Intent(context, ReportVideoActivity::class.java)
                intent.putExtra("reportType","video")
                intent.putExtra("reportId",videoId)
                context.startActivity(intent)
                dismiss()
            }
        }
    }
}