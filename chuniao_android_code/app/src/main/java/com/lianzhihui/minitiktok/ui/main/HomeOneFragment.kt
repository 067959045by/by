package com.lianzhihui.minitiktok.ui.main

import androidx.fragment.app.Fragment
import com.echofeng.common.config.AppConstants
import com.echofeng.common.config.MessageEvent
import com.echofeng.common.ui.base.BaseFragment
import com.google.android.material.tabs.TabLayout
import com.lianzhihui.onlyleague.R
import com.lianzhihui.minitiktok.adapter.MyFragmentAdapter
import com.lianzhihui.minitiktok.ui.main.one.VideoPageFragment
import kotlinx.android.synthetic.main.fragment_home_one.*
import org.greenrobot.eventbus.EventBus
import java.util.ArrayList

class HomeOneFragment : BaseFragment() ,TabLayout.OnTabSelectedListener{
    companion object {
        /** 默认显示第一页推荐页  */
        var curPage = 1
    }
    override fun getLayoutId(): Int {
        return R.layout.fragment_home_one
    }

    override fun initData() {
//        EventBus.getDefault().post(MessageEvent(11,""))
    }

    override fun initView() {
        val fragmentList = ArrayList<Fragment>()
        fragmentList.add(VideoPageFragment.newInstance(false))
        fragmentList.add(VideoPageFragment.newInstance(true))
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
                    EventBus.getDefault().post(MessageEvent(AppConstants.Event.REFRESH_HOME_ATINTION, true))
                }
            }
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }
}