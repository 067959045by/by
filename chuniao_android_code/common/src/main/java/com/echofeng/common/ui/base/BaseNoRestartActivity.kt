package com.echofeng.common.ui.base

import android.annotation.SuppressLint
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.echofeng.common.R
import com.echofeng.common.config.MessageEvent
import com.echofeng.common.ui.widget.dialog.LoadingDialog
import com.echofeng.common.utils.MyToast
import com.echofeng.common.utils.NetUtil
import com.echofeng.common.utils.helper.LocaleManager
import com.tbruyelle.rxpermissions2.RxPermissions
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class BaseNoRestartActivity : AppCompatActivity() {
    protected var TAG = this.javaClass.simpleName
    protected var mActivity: BaseNoRestartActivity? = null
    protected var rxPermissions: RxPermissions? = null
    protected var pageNum = 1
    protected var pageNum2 = 1

    //这里用来判断重复频繁点击问题
    protected var clickable = true
    protected var context: Context? = null
    protected var barView: View? = null

    //toolbar（如果存在）设置方法
    protected var titleTv: TextView? = null
    protected var optionTv: TextView? = null
    var backIv: ImageView? = null
    private var loadingDialog: LoadingDialog? = null
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = applicationContext
        EventBus.getDefault().register(this)
        //避免5.0修改不到顶部颜色导致看不到顶部的状态
        //主题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            val window: Window = this.getWindow()
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary))
        }
        setContentView(getLayoutId())
        mActivity = this
        rxPermissions = RxPermissions(this)
        initBar()
        initView()
        initData()
    }

    //定义视图抽象方法
    protected abstract fun getLayoutId(): Int
    //定义初始化抽象方法
    protected abstract fun initView()

    //设置数据的抽象方法
    protected abstract fun initData()
    fun getStringValues(resId: Int): String {
        return resources.getString(resId)
    }

    //默认不开启软键盘
    private fun onInputDef() {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE or WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    }

    fun setTextUnderLine(targetView: TextView) {
        targetView.paint.flags = Paint.UNDERLINE_TEXT_FLAG //下划线
        targetView.paint.isAntiAlias = true //抗锯齿
    }

    fun getColorValues(resId: Int): Int {
        return ContextCompat.getColor(this, resId)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleManager.setLocale(base))
    }

    fun copyString(string: String?) {
        val cm = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        cm.text = string
        Toast.makeText(mActivity, resources.getString(R.string.toast_copy_success), Toast.LENGTH_SHORT).show()
    }

    /**
     * 是否有网络
     *
     * @return
     */
    fun hasNet(): Boolean {
        val state = NetUtil.getNetWorkState(this)
        return if (state == NetUtil.NETWORK_NONE) {
            MyToast.showToast(resources.getString(R.string.toast_no_net))
            false
        } else {
            true
        }
    }

    fun isGetPermission(permissions: Array<String?>): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!checkPermission(permissions)) {
//                requestPermissions(permissions, 100);
                ActivityCompat.requestPermissions(this, permissions, 100)
                false
            } else if (checkPermission(permissions)) {
                true
            } else {  // 后续有其余情形可补充
                false
            }
        } else {
            false
        }
    }

    fun checkPermission(permissions: Array<String?>): Boolean {
        var granted = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (permission in permissions) {
                granted = checkSelfPermission(permission!!) == PackageManager.PERMISSION_GRANTED
                if (!granted) {
                    break
                }
            }
        }
        return granted
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent?) { /* Do something */
    }

    override fun onDestroy() {
        super.onDestroy()
        dismissLoading()
        EventBus.getDefault().unregister(this)
    }

    override fun onResume() {
        super.onResume()
    }

    public override fun onPause() {
        super.onPause()
    }

    /**
     * 使用SP 的尺寸不受 系统字体的影响而改变，防止出现字体放大而出现的布局问题
     *
     * @return
     */
    override fun getResources(): Resources { //还原字体大小
        val res = super.getResources()
        //非默认值
        if (res.configuration.fontScale != 1f) {
            val newConfig = Configuration()
            newConfig.setToDefaults() //设置默认
            res.updateConfiguration(newConfig, res.displayMetrics)
        }
        return res
    }

    /**
     * 初始化bar
     */
    private fun initBar() {
        barView = findViewById(R.id.app_top_bar)
        if (barView != null) {
            backIv = barView!!.findViewById(R.id.include_top_iv_back)
            backIv?.setOnClickListener(View.OnClickListener { finish() })
            titleTv = barView!!.findViewById(R.id.include_top_tv_title)
            optionTv = barView!!.findViewById(R.id.include_top_tv_right)
        }
    }

    protected fun hideBar() {
        if (barView != null) {
            barView!!.visibility = View.GONE
        }
    }

    protected fun showBar() {
        if (barView != null) {
            barView!!.visibility = View.VISIBLE
        }
    }

    /**
     * 设置title
     *
     * @param title
     */
    protected fun setTitle(title: String?) {
        if (titleTv != null && title != null) {
            titleTv!!.text = title
        }
    }

    /**
     * 隐藏返回键
     */
    protected fun hideBack() {
        if (backIv != null) {
            backIv!!.visibility = View.INVISIBLE
        }
    }

    protected fun showLoading(title: String?) {
        try {
            if (loadingDialog == null) {
                loadingDialog = LoadingDialog(this)
                loadingDialog!!.setTitle(title)
                loadingDialog!!.setOnDismissListener { clickable = true }
                loadingDialog!!.show()
            } else {
                loadingDialog!!.setTitle(title)
                loadingDialog!!.show()
            }
        } catch (e: Exception) {
        }
    }

    protected fun dismissLoading() {
        if (loadingDialog != null) {
            loadingDialog!!.dismiss()
        }
    }

    /**
     * 权限申请
     */
    @SuppressLint("CheckResult")
    protected fun checkPermissions(permission: String?) {
        rxPermissions!!.request(permission)
                .subscribe { granted: Boolean ->
                    if (granted) {
                        onPermission(permission, true)
                    } else {
                        onPermission(permission, false)
                    }
                }
    }

    protected fun onPermission(permisstion: String?, isGranted: Boolean) {}

    /**
     * 显示键盘
     *
     * @param et 输入焦点
     */
    fun showInput(et: EditText) {
        et.requestFocus()
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT)
    }

    /**
     * 隐藏键盘
     */
    protected fun hideInput() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        val v = window.peekDecorView()
        if (null != v) {
            imm.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }
}