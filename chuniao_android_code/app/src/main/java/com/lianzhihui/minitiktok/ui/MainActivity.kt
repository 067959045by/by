package com.lianzhihui.minitiktok.ui

import androidx.fragment.app.Fragment
import com.chaychan.library.BottomBarItem
import com.chaychan.library.BottomBarLayout
import com.echofeng.common.config.AppConstants
import com.echofeng.common.config.MessageEvent
import com.echofeng.common.inter.CustomCallback
import com.echofeng.common.net.HttpManager
import com.lianzhihui.onlyleague.R
import com.lianzhihui.minitiktok.adapter.MyFragmentAdapter
import com.lianzhihui.minitiktok.ui.main.HomeFourFragment
import com.lianzhihui.minitiktok.ui.main.HomeOneFragment
import com.lianzhihui.minitiktok.ui.main.HomeThreeFragment
import com.lianzhihui.minitiktok.ui.main.HomeTwoFragment
import com.echofeng.common.ui.base.BaseActivity
import com.echofeng.common.utils.MyToast
import com.lianzhihui.minitiktok.bean.system.VersionResponse
import com.lianzhihui.minitiktok.presenter.SystemPresnterImp
import com.lianzhihui.minitiktok.view.SystemView
import com.lianzhihui.minitiktok.widget.dialog.CustomTipDialog
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import java.util.*

class MainActivity : BaseActivity(), SystemView,BottomBarLayout.OnItemSelectedListener {
    private lateinit var systemPresnterImp: SystemPresnterImp

    companion object {
        var curMainPage = 0
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main;
    }

    override fun initView() {
        val fragmentList = ArrayList<Fragment>()
        fragmentList.add(HomeOneFragment())
        fragmentList.add(HomeTwoFragment())
        fragmentList.add(HomeThreeFragment())
        fragmentList.add(HomeFourFragment())
        val fragmentManager = supportFragmentManager
        val adapter = MyFragmentAdapter(fragmentManager, fragmentList)
        mViewPager.setAdapter(adapter)
        mViewPager.setScrollable(false)
        mViewPager.setOffscreenPageLimit(4)
        mBottomBarLayout.setOnItemSelectedListener(this)
        systemPresnterImp = SystemPresnterImp(this,this);
        systemPresnterImp.checkUpdate()
        checkStoragePermission()
    }

    override fun onItemSelected(p0: BottomBarItem?, p1: Int, p2: Int) {
        when(p2){
            0 ->{
                mViewPager.setCurrentItem(p2, false)
                EventBus.getDefault().post(MessageEvent(AppConstants.Event.UPDATE_STATE_PLAY_ONE, true))
                EventBus.getDefault().post(MessageEvent(AppConstants.Event.UPDATE_STATE_PURSE_TWO, true))
            }
            1 ->{
                mViewPager.setCurrentItem(p2, false)
                EventBus.getDefault().post(MessageEvent(AppConstants.Event.UPDATE_STATE_PLAY_TWO, true))
                EventBus.getDefault().post(MessageEvent(AppConstants.Event.UPDATE_STATE_PURSE_ONE, true))
            }
            2 ->{
                MyToast.showToast("暂未开放")
            }
            3,4->{
                mViewPager.setCurrentItem(p2-1, false)
                EventBus.getDefault().post(MessageEvent(AppConstants.Event.UPDATE_STATE_PURSE_ONE, true))
                EventBus.getDefault().post(MessageEvent(AppConstants.Event.UPDATE_STATE_PURSE_TWO, true))
                EventBus.getDefault().post(MessageEvent(AppConstants.Event.UPDATE_USER_HOME, true))
            }
        }
    }

    override fun setSuccess(o: Any?) {
    }

    override fun setFailure(o: Any?) {
    }

    override fun setCheckUpdateSuccess(data: VersionResponse?) {
        var versionDialog = CustomTipDialog(this, data!!.package_path,true)
        versionDialog.show()
        versionDialog.setText(data!!.title, data!!.remark,"立即下载")
    }

}