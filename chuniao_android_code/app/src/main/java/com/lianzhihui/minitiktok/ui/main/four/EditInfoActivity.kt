package com.lianzhihui.minitiktok.ui.main.four

import android.Manifest
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.provider.MediaStore
import android.view.View
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.echofeng.common.config.AppConstants
import com.echofeng.common.config.MessageEvent
import com.echofeng.common.inter.CustomCallback
import com.echofeng.common.net.HttpManager
import com.echofeng.common.ui.base.BaseActivity
import com.echofeng.common.utils.*
import com.google.gson.JsonElement
import com.lianzhihui.minitiktok.bean.system.UploadResponse
import com.lianzhihui.minitiktok.bean.user.UserInfo
import com.lianzhihui.minitiktok.presenter.UserHomePresnterImp
import com.lianzhihui.minitiktok.view.UserHomeView
import com.lianzhihui.minitiktok.widget.dialog.ChooseSexDialog
import com.lianzhihui.onlyleague.R
import com.net.core.body.UIProgressResponseCallBack
import com.tencent.bugly.proguard.T
import com.zaaach.citypicker.CityPicker
import com.zaaach.citypicker.adapter.OnPickListener
import com.zaaach.citypicker.model.City
import kotlinx.android.synthetic.main.activity_edit_info.*
import kotlinx.android.synthetic.main.activity_edit_info.ivHead
import kotlinx.android.synthetic.main.activity_edit_info.tvId
import kotlinx.android.synthetic.main.personal_my_home_header.*
import org.greenrobot.eventbus.EventBus
import java.io.File
import java.io.FileInputStream
import java.util.*


class EditInfoActivity : BaseActivity(),UserHomeView,View.OnClickListener,DatePickerDialog.OnDateSetListener {
    private var userInfo: UserInfo? = null
    private lateinit var userHomePresnterImp: UserHomePresnterImp

    override fun getLayoutId(): Int {
        return R.layout.activity_edit_info
    }

    override fun initView() {
        checkStoragePermission()
        setTitle("编辑资料")
        setOption("保存", this);
        ivHead.setOnClickListener(this)
        item_setting1.setOnClickListener(this)
        item_setting3.setOnClickListener(this)
        item_setting4.setOnClickListener(this)
        item_setting5.setOnClickListener(this)
        userInfo = intent.getSerializableExtra("userInfo") as UserInfo?
        tvNickName.setText(userInfo!!.nick)
        tvId.setText(userInfo!!.code)
        tvBirthday.setText(userInfo!!.birth)
        etSing.setText(userInfo!!.intro)
        if (userInfo!!.gender==1){
            tvGender.setText("男")
        }else{
            tvGender.setText("女")
        }
        tvCity.setText(userInfo!!.city)
        val simpleTarget: SimpleTarget<File?> = object : SimpleTarget<File?>() {
            override fun onResourceReady(resource: File, transition: Transition<in File?>?) {
                val decryptBytes = AESFileCrypt.decryptData(resource)
                val bitmap = BitmapFactory.decodeByteArray(decryptBytes, 0, decryptBytes.size)
                ivHead.setImageBitmap(bitmap)
            }
        }
        Glide.with(context!!).asFile().load(userInfo!!.avatar).error(R.mipmap.ic_launcher).into(simpleTarget)
        userHomePresnterImp = UserHomePresnterImp(this, this);
    }
    /**
     * 接收拍照结果和显示图片 从Uri获取图片
     */
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 200 && data != null){
            if (data.data!=null) {
                var images = data.data
                if (images != null) {
                    val uri = images
                    val file = FileUtils.getFileFromUri(uri, mActivity)
                    if (file != null) {
                        val stream = FileInputStream(file)
                        val bitmap: Bitmap = BitmapFactory.decodeStream(stream)
                        ivHead.setImageBitmap(bitmap)
                        startUpload(file)
                    }
                }
            }
        }
    }

    fun startUpload(file: File){
        userHomePresnterImp.doUploadHead(file,object : UIProgressResponseCallBack() {
            override fun onUIResponseProgress(bytesRead: Long, contentLength: Long, done: Boolean) {
                if (done) {
                    MyToast.showToast("上传成功")
                }
            }
        })
    }
    private fun openCityPage(){
        CityPicker.from(this) //activity或者fragment
                .enableAnimation(true) //启用动画效果，默认无
                .setLocatedCity(null) //APP自身已定位的城市，传null会自动定位（默认）
                .setOnPickListener(object : OnPickListener {
                    override fun onPick(position: Int, data: City) {
                        tvCity.setText(data.name)
                    }

                    override fun onLocate() {}
                    override fun onCancel() {}
                }).show()
    }

    /**
     * 调起系统相册
     */
    private fun selectImage() {
        checkStoragePermission()
        Thread(Runnable {
            runOnUiThread {
                val intent = Intent()
                intent.setType("image/*")
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
                intent.setAction(Intent.ACTION_PICK)
                intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                startActivityForResult(Intent.createChooser(intent, "选择头像"), 200)
            }
        }).start()
    }
    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        val desc = String.format("%d-%d-%d", p1, p2 + 1, p3)
        tvBirthday.setText(desc)
        userInfo!!.years = desc
    }

    override fun setSuccess(o: Any?) {
        dismissLoading()
        finish()
    }

    override fun setFailure(o: Any?) {
        dismissLoading()
    }

    override fun setMineInfoSuccess(data: UserInfo?) {
    }

    override fun setUserInfoSuccess(data: UserInfo?) {
    }

    override fun setUploadHeadSuccess(data: UploadResponse?) {
        userInfo!!.avatar= data!!.file_path
        userHomePresnterImp.updateUserInfo(tvNickName.text.toString(), etSing.text.toString(), userInfo!!.gender.toString(), tvBirthday.text.toString(), userInfo!!.avatar, tvCity.text.toString());
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().post(MessageEvent(AppConstants.Event.UPDATE_USER_HOME, true))
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when(v.id){
                R.id.ivHead -> {
                    selectImage()
                }
                R.id.include_top_tv_right -> {
                    showLoading("")
                    userHomePresnterImp.updateUserInfo(tvNickName.text.toString(), etSing.text.toString(), userInfo!!.gender.toString(), tvBirthday.text.toString(), userInfo!!.avatar, tvCity.text.toString());
                }
                R.id.item_setting1 -> {
                }
                R.id.item_setting3 -> {
                    ChooseSexDialog(this, object : CustomCallback {
                        override fun onCompare(o: Any) {
                            var ageId = o as Int
                            userInfo?.gender = ageId
                            if (ageId==1) {
                                tvGender.setText("男")
                            }else{
                                tvGender.setText("女")
                            }
                        }
                        override fun onCancel() {}
                    }).show()
                }
                R.id.item_setting4 -> {
                    val calendar: Calendar = Calendar.getInstance()
                    val dialog = DatePickerDialog(this, this,
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH))
                    dialog.show()
                }
                R.id.item_setting5 -> {
                    openCityPage()
                }
            }
        }
    }

}