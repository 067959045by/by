package com.lianzhihui.minitiktok.ui.main

import androidx.fragment.app.Fragment
import com.echofeng.common.config.AppConstants
import com.echofeng.common.config.MessageEvent
import com.echofeng.common.ui.base.BaseFragment
import com.google.android.material.tabs.TabLayout
import com.lianzhihui.onlyleague.R
import com.lianzhihui.minitiktok.adapter.MyFragmentAdapter
import com.lianzhihui.minitiktok.ui.main.two.HotOneFragment
import com.lianzhihui.minitiktok.ui.main.two.HotThreeFragment
import com.lianzhihui.minitiktok.ui.main.two.HotTwoFragment
import kotlinx.android.synthetic.main.fragment_home_one.*
import kotlinx.android.synthetic.main.fragment_home_two.mViewPager
import kotlinx.android.synthetic.main.fragment_home_two.tabLayout
import org.greenrobot.eventbus.EventBus
import java.util.ArrayList

class HomeTwoFragment:BaseFragment() , TabLayout.OnTabSelectedListener{
    override fun getLayoutId(): Int {
        return R.layout.fragment_home_two
    }

    override fun initData() {
    }

    override fun initView() {
        val fragmentList = ArrayList<Fragment>()
        fragmentList.add(HotOneFragment())
        fragmentList.add(HotTwoFragment())
        fragmentList.add(HotThreeFragment())
        val fragmentManager = childFragmentManager
        val adapter = MyFragmentAdapter(fragmentManager, fragmentList)
        mViewPager.setAdapter(adapter)
        mViewPager.setScrollable(false)
        mViewPager.setOffscreenPageLimit(fragmentList.size)
        tabLayout.addOnTabSelectedListener(this)
        tabLayout.getTabAt(1)?.select()
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        if (tab != null) {
            mViewPager.setCurrentItem(tab.position,false)
            when(tab.position){
                0->{
                    EventBus.getDefault().post(MessageEvent(AppConstants.Event.UPDATE_STATE_PLAY_TWO, true))
                }
                1,2->{
                    EventBus.getDefault().post(MessageEvent(AppConstants.Event.UPDATE_STATE_PURSE_TWO, true))
                }
            }
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }
}