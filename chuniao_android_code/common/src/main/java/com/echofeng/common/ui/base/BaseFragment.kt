package com.echofeng.common.ui.base

import android.app.Activity
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.echofeng.common.R
import com.echofeng.common.config.MessageEvent
import com.echofeng.common.ui.widget.dialog.LoadingDialog
import com.echofeng.common.utils.MyToast
import com.echofeng.common.utils.NetUtil
import com.tbruyelle.rxpermissions2.RxPermissions
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class BaseFragment : Fragment() {
    protected var TAG = this.javaClass.simpleName
    protected var rootView: View? = null
    private var isFirstVisible = true //是否第一次可见
    protected var loadFinish = false //是否已加载数据
    protected var rxPermissions: RxPermissions? = null
    private var lastCallTime: Long = 0
    protected var pageNum = 1

    //这里用来判断重复频繁点击问题
    protected var clickable = true
    private var loadingDialog: LoadingDialog? = null
    protected var activity: Activity? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        EventBus.getDefault().register(this)
        rxPermissions = RxPermissions(this) //
        activity = getActivity()
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutId(), null)
        }
        return rootView
    }

    fun showFullScreen() {
        val window = activity!!.window
        val decorView = window.decorView
        val option = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
        decorView.systemUiVisibility = option
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    fun getStringValues(resId: Int): String {
        return resources.getString(resId)
    }

    fun copyString(string: String?) {
        val cm = activity!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        cm.text = string
        MyToast.showToast(resources.getString(R.string.toast_copy_success))
    }

    fun setTextUnderLine(targetView: TextView) {
        targetView.paint.flags = Paint.UNDERLINE_TEXT_FLAG //下划线
        targetView.paint.isAntiAlias = true //抗锯齿
    }

    //定义视图抽象方法
    protected abstract fun getLayoutId(): Int
    protected abstract fun initView()
    protected abstract fun initData()

    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onMessageEvent(event: MessageEvent?) { /* Do something */
    }

    /**
     * 在fragment首次可见时回调，可在这里进行加载数据，保证只在第一次打开Fragment时才会加载数据，
     * 这样就可以防止每次进入都重复加载数据
     */
    protected fun onFragmentFirstVisible() {}

    /**
     * fragment 每次可见回调
     */
    protected open fun onFragmentVisible() {}

    /**
     * fragment每次不可见回调
     */
    protected fun onFragmentUnVisible() {}
    protected fun onFragmentVisible(isVisible: Boolean) {}
    override fun onResume() {
        super.onResume()
        onFragmentVisible(isVisible)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (rootView == null) {
            return
        }
        if (isVisibleToUser) {
            onFragmentVisible()
            if (isFirstVisible) {
                onFragmentFirstVisible()
                isFirstVisible = false
            }
        } else {
            onFragmentUnVisible()
        }
        callOnVisibleChange(isVisibleToUser)
    }

    /**
     * 解决重复调用多次问题
     *
     * @param isVisible
     */
    fun callOnVisibleChange(isVisible: Boolean) {
        val time = System.currentTimeMillis() - lastCallTime
        if (this.isVisible == isVisible && time < 100) {
            return
        }
        lastCallTime = System.currentTimeMillis()
        onVisibleChange(isVisible)
    }

    fun onVisibleChange(isVisible: Boolean) {}
    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    fun hasNet(): Boolean {
        val state = NetUtil.getNetWorkState(activity)
        return if (state == NetUtil.NETWORK_NONE) {
            MyToast.showToast(resources.getString(R.string.toast_no_net))
            false
        } else {
            true
        }
    }

    fun showNetTopView(view: View) {
        if (hasNet()) {
            view.visibility = View.GONE
        } else {
            view.visibility = View.VISIBLE
        }
    }

    protected fun finish() {
        if (activity != null) {
            activity!!.finish()
        }
    }

    protected fun showLoading(title: String?) {
        try {
            if (loadingDialog == null) {
                loadingDialog = LoadingDialog(activity!!)
                loadingDialog!!.setTitle(title)
            }
            loadingDialog!!.setOnDismissListener { clickable = true }
            loadingDialog!!.show()
        } catch (e: Exception) {
        }
    }

    protected fun dismissLoading() {
        if (loadingDialog != null) {
            loadingDialog!!.dismiss()
        }
    }
}