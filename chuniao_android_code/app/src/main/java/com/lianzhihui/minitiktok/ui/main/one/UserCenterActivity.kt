package com.lianzhihui.minitiktok.ui.main.one

import android.content.Intent
import android.graphics.BitmapFactory
import android.text.TextUtils
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.bytedance.tiktok.utils.NumUtils
import com.echofeng.common.inter.CustomCallback
import com.echofeng.common.ui.base.BaseActivity
import com.echofeng.common.ui.widget.layout.AppBarStateChangeListener
import com.echofeng.common.utils.AESFileCrypt
import com.echofeng.common.utils.ImageUtil
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.lianzhihui.minitiktok.adapter.MyFragmentAdapter
import com.lianzhihui.minitiktok.bean.hot.AlbumVideoResponse
import com.lianzhihui.minitiktok.bean.hot.HotClassResponse
import com.lianzhihui.minitiktok.bean.hot.VideoResponse
import com.lianzhihui.minitiktok.bean.system.UploadResponse
import com.lianzhihui.minitiktok.bean.user.UserInfo
import com.lianzhihui.minitiktok.presenter.HomePresnterImp
import com.lianzhihui.minitiktok.presenter.UserHomePresnterImp
import com.lianzhihui.minitiktok.ui.system.ReportVideoActivity
import com.lianzhihui.minitiktok.view.HomeView
import com.lianzhihui.minitiktok.view.UserHomeView
import com.lianzhihui.minitiktok.widget.dialog.UserMoreDialog
import com.lianzhihui.onlyleague.R
import kotlinx.android.synthetic.main.activity_user_center.*
import kotlinx.android.synthetic.main.personal_home_header.*
import kotlinx.android.synthetic.main.personal_home_header.ivHead
import kotlinx.android.synthetic.main.personal_home_header.tvAddress
import kotlinx.android.synthetic.main.personal_home_header.tvAge
import kotlinx.android.synthetic.main.personal_home_header.tvFansCount
import kotlinx.android.synthetic.main.personal_home_header.tvFocusCount
import kotlinx.android.synthetic.main.personal_home_header.tvGetLikeCount
import kotlinx.android.synthetic.main.personal_home_header.tvId
import kotlinx.android.synthetic.main.personal_home_header.tvNickname
import kotlinx.android.synthetic.main.personal_home_header.tvSign
import kotlinx.android.synthetic.main.personal_my_home_header.*
import java.io.File
import java.util.*

class UserCenterActivity : BaseActivity() , UserHomeView,HomeView,TabLayout.OnTabSelectedListener,View.OnClickListener {
    private var userId: String? = null
    private lateinit var homePresnterImp: HomePresnterImp
    private lateinit var userHomePresnterImp: UserHomePresnterImp

    override fun getLayoutId(): Int {
        return R.layout.activity_user_center
    }

    override fun initView() {
        ivReturn.setOnClickListener(this)
        tvAddfocus.setOnClickListener(this)
        rlDropdown.setOnClickListener(this)
        appbarLayout.addOnOffsetChangedListener(object : AppBarStateChangeListener(){
            override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {
                if( state == State.EXPANDED ) {
                    //展开状态
                    tvTitle.visibility = View.GONE
                }else if(state == State.COLLAPSED){
                    //折叠状态
                    tvTitle.visibility = View.VISIBLE
                }else {
                    //中间状态
                    tvTitle.visibility = View.GONE
                }
            }

        })
        userId = intent.getStringExtra("userId")
        val fragmentList = ArrayList<Fragment>()
        fragmentList.add(UserHomeVideoFragment.newInstance(1,userId!!))
        fragmentList.add(UserHomeVideoFragment.newInstance(2,userId!!))
        val fragmentManager = supportFragmentManager
        val adapter = MyFragmentAdapter(fragmentManager, fragmentList)
        mViewPager.setAdapter(adapter)
        mViewPager.setScrollable(false)
        mViewPager.setOffscreenPageLimit(fragmentList.size)
        tabLayout.addOnTabSelectedListener(this)
        userHomePresnterImp = UserHomePresnterImp(context,this);
        homePresnterImp = HomePresnterImp(context,this);
        userHomePresnterImp.getUserInfo(userId)
    }

    override fun setSuccess(o: Any?) {
        userHomePresnterImp.getUserInfo(userId)
    }

    override fun setFailure(o: Any?) {
    }

    override fun setVideoRecommendSuccess(data: MutableList<VideoResponse>?) {
    }

    override fun setSearchVideoSuccess(data: MutableList<VideoResponse>?) {
    }

    override fun setBoxVideoSuccess(data: AlbumVideoResponse?) {
    }

    override fun setHotClassSuccess(data: MutableList<HotClassResponse>?) {
    }

    override fun setCheckVideoSuccess(data: VideoResponse?) {
    }

    override fun setBuyVideoSuccess(data: Any?) {
    }

    override fun setMineInfoSuccess(data: UserInfo?) {
    }

    override fun setUserInfoSuccess(data: UserInfo?) {
        val simpleTarget: SimpleTarget<File?> = object : SimpleTarget<File?>() {
            override fun onResourceReady(resource: File, transition: Transition<in File?>?) {
                val decryptBytes = AESFileCrypt.decryptData(resource)
                val bitmap = BitmapFactory.decodeByteArray(decryptBytes, 0, decryptBytes.size)
                ivHead.setImageBitmap(bitmap)
            }
        }
        Glide.with(context!!).asFile().load(data!!.avatar).error(R.mipmap.ic_launcher).into(simpleTarget)
        tvTitle.setText(data.nick)
        tvNickname.setText(data.nick)
        tvId.setText("ID:"+data.code)
        tvAge.setText(data.years)
        tvAddress.setText(data.city)
        if (data.gender==1) {
            ImageUtil.setDrawableLeft(context, tvAge, R.mipmap.icon_boy)
        }else{
            ImageUtil.setDrawableLeft(context, tvAge, R.mipmap.icon_girl)
        }
        if (TextUtils.isEmpty(data.city)){
            tvAddress.visibility = View.GONE
        }else{
            tvAddress.visibility = View.VISIBLE
        }
        tvSign.setText(data.intro)
        tvGetLikeCount.setText(NumUtils.numberFilter(data.like))
        tvFocusCount.setText(NumUtils.numberFilter(data.flow))
        tvFansCount.setText(NumUtils.numberFilter(data.fans))
        if (data.is_follow==1){
            tvAddfocus.visibility = View.GONE
        }else{
            tvAddfocus.visibility = View.VISIBLE
        }
    }

    override fun setUploadHeadSuccess(data: UploadResponse?) {
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        if (tab != null) {
            mViewPager.setCurrentItem(tab.position,false)
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.rlDropdown->{
                UserMoreDialog(this,object:CustomCallback{
                    override fun onCompare(o: Any?) {
                        var intent = Intent(context, ReportVideoActivity::class.java)
                        intent.putExtra("reportType","user")
                        intent.putExtra("reportId",userId)
                        startActivity(intent)
                    }

                    override fun onCancel() {
                    }

                }).show()
            }
            R.id.ivReturn->{
                finish()
            }
            R.id.tvAddfocus->{
                homePresnterImp.doAttintion(userId)
            }
        }
    }
}