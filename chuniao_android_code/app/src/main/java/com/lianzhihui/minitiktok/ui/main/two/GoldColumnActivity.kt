package com.lianzhihui.minitiktok.ui.main.two

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.echofeng.common.ui.base.BaseActivity
import com.google.android.material.tabs.TabLayout
import com.lianzhihui.minitiktok.adapter.MyFragmentAdapter
import com.lianzhihui.onlyleague.R
import kotlinx.android.synthetic.main.activity_gold_column.*
import kotlinx.android.synthetic.main.fragment_home_two.*
import kotlinx.android.synthetic.main.fragment_home_two.mViewPager
import kotlinx.android.synthetic.main.fragment_home_two.tabLayout
import java.util.ArrayList

class GoldColumnActivity : BaseActivity() , TabLayout.OnTabSelectedListener,View.OnClickListener{
    override fun getLayoutId(): Int {
        return R.layout.activity_gold_column
    }

    override fun initView() {
        include_top_iv_back.setOnClickListener(this)
        val fragmentList = ArrayList<Fragment>()
        fragmentList.add(GoldOneFragment.newInstance("hot"))
        fragmentList.add(GoldOneFragment.newInstance("new"))
        val fragmentManager = supportFragmentManager
        val adapter = MyFragmentAdapter(fragmentManager, fragmentList)
        mViewPager.setAdapter(adapter)
        mViewPager.setScrollable(false)
        mViewPager.setOffscreenPageLimit(fragmentList.size)
        tabLayout.addOnTabSelectedListener(this)
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
            R.id.include_top_iv_back->{
                finish()
            }
        }
    }

}