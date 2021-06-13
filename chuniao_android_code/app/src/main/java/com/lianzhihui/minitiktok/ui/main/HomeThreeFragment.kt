package com.lianzhihui.minitiktok.ui.main

import android.content.Intent
import android.view.View
import com.echofeng.common.ui.base.BaseFragment
import com.echofeng.common.utils.SystemUtils
import com.lianzhihui.minitiktok.adapter.BannerImageAdapter
import com.lianzhihui.minitiktok.bean.AdResponse
import com.lianzhihui.minitiktok.presenter.AdPresnterImp
import com.lianzhihui.minitiktok.ui.system.ImServiceActivity
import com.lianzhihui.minitiktok.view.AdView
import com.lianzhihui.onlyleague.R
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.fragment_home_three.*
import kotlinx.android.synthetic.main.fragment_home_three.mBanner
import kotlinx.android.synthetic.main.fragment_hot_two.*

class HomeThreeFragment : BaseFragment(),AdView,View.OnClickListener {
    private lateinit var adPresnterImp: AdPresnterImp

    override fun getLayoutId(): Int {
        return R.layout.fragment_home_three
    }

    override fun initView() {
        rlService.setOnClickListener(this)
    }

    override fun initData() {
        adPresnterImp = AdPresnterImp(context, this)
        adPresnterImp.getAdList("meg_home")
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.rlService->{
                startActivity(Intent(context,ImServiceActivity::class.java))
            }
        }
    }

    override fun setSuccess(o: Any?) {
    }

    override fun setFailure(o: Any?) {
    }

    override fun setAdSuccess(data: MutableList<AdResponse>?) {
        if (data!!.size==0){
            return
        }
        mBanner.addBannerLifecycleObserver(this)//添加生命周期观察者
                .setAdapter(BannerImageAdapter(context,data))
                .setIndicator(CircleIndicator(context));
    }
}