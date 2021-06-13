package com.lianzhihui.minitiktok.ui.main

import android.content.Intent
import android.graphics.BitmapFactory
import android.text.TextUtils
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.bytedance.tiktok.utils.NumUtils
import com.echofeng.common.config.AppConstants
import com.echofeng.common.config.MessageEvent
import com.echofeng.common.ui.base.BaseFragment
import com.echofeng.common.utils.AESFileCrypt
import com.echofeng.common.utils.ImageUtil
import com.google.android.material.tabs.TabLayout
import com.lianzhihui.minitiktok.adapter.MyFragmentAdapter
import com.lianzhihui.minitiktok.bean.system.UploadResponse
import com.lianzhihui.minitiktok.bean.user.UserInfo
import com.lianzhihui.minitiktok.presenter.UserHomePresnterImp
import com.lianzhihui.minitiktok.ui.main.four.*
import com.lianzhihui.minitiktok.ui.main.one.UserHomeVideoFragment
import com.lianzhihui.minitiktok.ui.system.ImServiceActivity
import com.lianzhihui.minitiktok.view.UserHomeView
import com.lianzhihui.onlyleague.R
import kotlinx.android.synthetic.main.fragment_home_four.*
import kotlinx.android.synthetic.main.personal_my_home_header.*
import kotlinx.android.synthetic.main.view_controller.view.*
import java.io.File
import java.util.ArrayList

class HomeFourFragment : BaseFragment() ,UserHomeView, TabLayout.OnTabSelectedListener,View.OnClickListener{
    private lateinit var intent: Intent
    private var userInfo: UserInfo? = null
    private lateinit var userHomePresnterImp: UserHomePresnterImp

    override fun getLayoutId(): Int {
        return R.layout.fragment_home_four
    }
    override fun initData() {
        userHomePresnterImp = UserHomePresnterImp(context,this);
        userHomePresnterImp.getMineInfo()
    }

    override fun initView() {
        iv_setting.setOnClickListener(this)
        ivHead.setOnClickListener(this)
        item_home_1.setOnClickListener(this)
        item_home_2.setOnClickListener(this)
        item_home_3.setOnClickListener(this)
        item_home_4.setOnClickListener(this)
        btCash.setOnClickListener(this)
        val fragmentList = ArrayList<Fragment>()
        fragmentList.add(UserHomeVideoFragment.newInstance(1,""))
        fragmentList.add(UserHomeVideoFragment.newInstance(2,""))
        fragmentList.add(UserHomeVideoFragment.newInstance(3,""))
        val fragmentManager = childFragmentManager
        val adapter = MyFragmentAdapter(fragmentManager, fragmentList)
        mViewPager.setAdapter(adapter)
        mViewPager.setScrollable(false)
        mViewPager.setOffscreenPageLimit(fragmentList.size)
        tabLayout.addOnTabSelectedListener(this)
    }

    override fun onMessageEvent(event: MessageEvent?) {
        super.onMessageEvent(event)
        when(event?.code){
            AppConstants.Event.UPDATE_USER_HOME->{
                userHomePresnterImp.getMineInfo()
            }
        }
    }
    override fun setSuccess(o: Any?) {
    }

    override fun setFailure(o: Any?) {
    }

    override fun setMineInfoSuccess(data: UserInfo?) {
        userInfo = data
        val simpleTarget: SimpleTarget<File?> = object : SimpleTarget<File?>() {
            override fun onResourceReady(resource: File, transition: Transition<in File?>?) {
                val decryptBytes = AESFileCrypt.decryptData(resource)
                val bitmap = BitmapFactory.decodeByteArray(decryptBytes, 0, decryptBytes.size)
                ivHead.setImageBitmap(bitmap)
            }
        }
        Glide.with(context!!).asFile().load(data!!.avatar).error(R.mipmap.ic_launcher).into(simpleTarget)
        tvNickname.setText(data.nick)
        tvId.setText(data.code)
        tvAge.setText(data.years)
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
        tvAddress.setText(data.city)
        tvSign.setText(data.intro)
        tvGetLikeCount.setText(NumUtils.numberFilter(data.like))
        tvFocusCount.setText(NumUtils.numberFilter(data.flow))
        tvFansCount.setText(NumUtils.numberFilter(data.fans))
        tvOwnCoin.setText(data.coins)
        tvOwnSton.setText(data.diamonds)
    }

    override fun setUserInfoSuccess(data: UserInfo?) {
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
        if (v != null) {
            when(v.id){
                R.id.iv_setting->{
                    intent = Intent(context,ManageSettingActivity::class.java)
                    startActivity(intent)
                }
                R.id.ivHead->{
                    intent = Intent(context, EditInfoActivity::class.java)
                    intent.putExtra("userInfo",userInfo)
                    startActivity(intent)
                }
                R.id.item_home_1->{
                    intent = Intent(context, AgentShareActivity::class.java)
                    startActivity(intent)
                }
                R.id.item_home_2->{
                    intent = Intent(context, ImServiceActivity::class.java)
                    startActivity(intent)
                }
                R.id.item_home_3->{
                    intent = Intent(context, WalletActivity::class.java)
                    startActivity(intent)
                }
                R.id.item_home_4->{
                }
                R.id.btCash->{
                    intent = Intent(context, CashActivity::class.java)
                    intent.putExtra("stoneNum",userInfo?.diamonds)
                    startActivity(intent)
                }
            }
        }
    }


}