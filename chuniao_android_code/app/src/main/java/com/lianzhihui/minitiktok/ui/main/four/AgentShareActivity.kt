package com.lianzhihui.minitiktok.ui.main.four

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder
import com.bumptech.glide.Glide
import com.echofeng.common.config.DataManager
import com.echofeng.common.ui.base.BaseActivity
import com.echofeng.common.utils.GsonUtil
import com.echofeng.common.utils.ImageUtil
import com.lianzhihui.minitiktok.bean.user.LoginResponse
import com.lianzhihui.onlyleague.R
import kotlinx.android.synthetic.main.activity_agent_share.*
import kotlinx.android.synthetic.main.activity_agent_share.btCopy
import kotlinx.android.synthetic.main.activity_agent_share.btSave
import kotlinx.android.synthetic.main.activity_agent_share.ivQrCode
import kotlinx.android.synthetic.main.dialog_share_video.*

class AgentShareActivity : BaseActivity() ,View.OnClickListener{
    override fun getLayoutId(): Int {
        return R.layout.activity_agent_share
    }

    override fun initView() {
        setTitle("全民代理")
        btCopy.setOnClickListener(this)
        btSave.setOnClickListener(this)
        var loginResponse = GsonUtil.GsonToBean(DataManager.getLoginJsonElement(), LoginResponse::class.java);
        btCopy.setTag(loginResponse.config.sys.share_text)
        val bitmap: Bitmap = QRCodeEncoder.syncEncodeQRCode(loginResponse.config.sys.share_url, 400)
        ivQrCode.setImageBitmap(bitmap)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btCopy->{
                copyString(v.getTag().toString())
            }
            R.id.btSave->{
                ImageUtil.saveBmp2Gallery(context,ivQrCode,"lvban_invite_code.jpg")
            }
        }
    }
}