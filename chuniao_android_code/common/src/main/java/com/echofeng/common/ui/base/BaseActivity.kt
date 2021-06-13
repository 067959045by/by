package com.echofeng.common.ui.base

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Process
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.echofeng.common.R
import com.echofeng.common.config.AppConstants
import com.echofeng.common.config.AppManager
import com.echofeng.common.config.AppStatusManager
import com.echofeng.common.config.MessageEvent
import com.echofeng.common.ui.widget.dialog.LoadingDialog
import com.echofeng.common.utils.LogUtil
import com.echofeng.common.utils.MyToast
import com.echofeng.common.utils.NetUtil
import com.echofeng.common.utils.PreferenceUtils
import com.echofeng.common.utils.helper.LocaleManager
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.title_bar_common.*
import kotlinx.android.synthetic.main.title_common_style_white_weixin.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*
import java.util.concurrent.TimeUnit

abstract class BaseActivity : AppCompatActivity() {
    protected var TAG = this.javaClass.simpleName
    protected var mActivity: BaseActivity? = null
    protected var rxPermissions: RxPermissions? = null
    protected var pageNum = 1
    protected var pageNum2 = 1
    protected lateinit var activity: Activity
    var isFirstIn = true

    //这里用来判断重复频繁点击问题
    protected var clickable = true
    protected var context: Context? = null
    protected var barView: View? = null

    //toolbar（如果存在）设置方法
    protected var titleTv: TextView? = null
    protected var optionTv: TextView? = null
    var backIv: ImageView? = null
    protected var weixinTitle: TextView? = null
    var weixinStyleBackIv: ImageView? = null
    protected var loadingDialog: LoadingDialog? = null
    var langType: String? = null
    protected val WhiteColor = 1
    protected val BlackColor = 2
    private var initDisposable: Disposable? = null

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = applicationContext
        activity = this
        langType = PreferenceUtils.getString(
                AppConstants.Share.LANGUAGE_KEY,
                Locale.SIMPLIFIED_CHINESE.language
        )
        //判断app状态
        if (AppStatusManager.getInstance().getAppStatus() == AppConstants.AppStatus.STATUS_RECYCLE) {
            //被回收，跳转到启动页面
            restartApplication(this);
            finish();
            return;
        }
        //向恶势力低头 activity管理
        EventBus.getDefault().register(this)
        //主题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            val window: Window = this.getWindow()
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary))
        }
        //关键代码
        setContentView(getLayoutId())
        mActivity = this
        rxPermissions = RxPermissions(this)
        initBar()
        initWeiXinBar()
        initView()
        lazyInitView()
        initView(savedInstanceState)
    }

    //定义视图抽象方法
    protected abstract fun getLayoutId(): Int
    protected fun setActionBarTextColorFlag(): Boolean {
        return false
    }

    //定义初始化抽象方法
    protected abstract fun initView()
    protected fun initLazyView() {}
    protected fun initView(savedInstanceState: Bundle?) {
        LocaleManager.setNewLocale(this, langType)
    }

    /**
     * @ProjectName: NBN
     * @ClassName: BaseActivity
     * @Description: 设置沉浸式窗口时调用
     * @Author: 赵峰（zhaofengcto@foxmail.com）
     * @CreateDate: 2021/1/13 11:53
     * @UpdateUser: 更新者
     * @UpdateDate: 2021/1/13 11:53
     * @UpdateRemark: 更新说明
     * @Version: 1.0
     * @boolean colorType 1:浅色文字  2：深色文字
     */
    fun setNoActionBar(colorType: Int) {
        if (colorType == 1) {
            //状态栏图标和文字颜色为浅色
            this.window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            this.window.decorView.findViewById<View>(android.R.id.content).setPadding(0, 0, 0, 0)
        } else {
            //状态栏图标和文字颜色为暗色
            this.window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            this.window.decorView.findViewById<View>(android.R.id.content).setPadding(0, 0, 0, 0)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            this.window.statusBarColor = Color.TRANSPARENT
        }
    }

    /**
     * 报错则重启应用
     */
    fun restartApplication(context: Context) {
        Handler().postDelayed({
            AppManager.getAppManager().finishAllActivity()
            val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
            intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            Process.killProcess(Process.myPid())
        }, 200)
    }

    fun getStringValues(resId: Int): String {
        return getString(resId)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleManager.setLocale(base))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    val emptyView: View
        get() {
            val emptyView = layoutInflater.inflate(R.layout.layout_empty_view, null)
            emptyView.findViewById<View>(R.id.emptyRoot).visibility = View.VISIBLE
            return emptyView
        }

    fun getEmptyView(id: Int, at: String?): View {
        val emptyView = layoutInflater.inflate(R.layout.layout_empty_view, null)
        val iv_icon = emptyView.findViewById<ImageView>(R.id.iv_icon)
        iv_icon.setImageResource(id)
        val tv_content = emptyView.findViewById<TextView>(R.id.tv_content)
        tv_content.text = at
        emptyView.findViewById<View>(R.id.emptyRoot).visibility = View.VISIBLE
        return emptyView
    }

    fun getEmptyView(id: Int, at: Int): View {
        val emptyView = layoutInflater.inflate(R.layout.layout_empty_view, null)
        val iv_icon = emptyView.findViewById<ImageView>(R.id.iv_icon)
        iv_icon.setImageResource(id)
        val tv_content = emptyView.findViewById<TextView>(R.id.tv_content)
        tv_content.text = resources.getText(at)
        emptyView.findViewById<View>(R.id.emptyRoot).visibility = View.VISIBLE
        return emptyView
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

    fun copyString(string: String?) {
        val cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        cm.text = string
        MyToast.showToast(resources.getString(R.string.toast_copy_success))
    }

    protected fun toast(str: String) {
        MyToast.showToast(str)
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
    // 申请相机权限的requestCode
    private val mRequestCode = 100
    internal var permissions = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.INTERNET)
    internal var mPermissionList: MutableList<String> = ArrayList()
    open fun checkStoragePermission(){
        if (Build.VERSION.SDK_INT >= 23){
            mPermissionList.clear()
            for (i in permissions.indices) {
                if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                    mPermissionList.add(permissions[i])
                }
            }
            if (mPermissionList.size > 0) {
                ActivityCompat.requestPermissions(this, permissions, mRequestCode)
            }
        }
    }
    open fun isGetPermission(permissions: Array<String?>): Boolean {
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

    open fun checkPermission(permissions: Array<String?>): Boolean {
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
        dismissLoading()
        //取消订阅
        if (initDisposable != null && !initDisposable!!.isDisposed) {
            initDisposable!!.dispose()
        }
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
    }

    public override fun onPause() {
        super.onPause()
        dismissLoading()
    }

    fun finishActivity() {
        AppManager.getAppManager().finishActivity(this)
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
        if (findViewById<View?>(R.id.app_top_bar) != null) {
            barView = findViewById(R.id.app_top_bar)
        }
        if (barView != null) {
            backIv = barView!!.findViewById(R.id.include_top_iv_back)
            include_top_iv_back.setOnClickListener {
                finish()
            }
            titleTv = barView!!.findViewById(R.id.include_top_tv_title)
            optionTv = barView!!.findViewById(R.id.include_top_tv_right)
        }
    }

    /**
     * 初始化bar
     */
    private fun initWeiXinBar() {
        if (barView == null) {
            barView = findViewById(R.id.dapp_top_bar)
            if (barView != null) {
                weixinTitle = barView!!.findViewById(R.id.title)
                weixinStyleBackIv = barView!!.findViewById(R.id.left_icon)
                left_icon.setOnClickListener {
                    finish()
                }
            }
        }
    }

    private fun onClick(v: View) {
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
     * 设置title
     *
     * @param title
     */
    protected fun setWeixinTitle(title: String?) {
        if (weixinTitle != null && weixinTitle != null) {
            weixinTitle!!.setTextColor(resources.getColor(R.color.black))
            weixinTitle!!.text = title
        }
    }

    protected fun setWeixinTitleColor(color: Int) {
        if (weixinTitle != null && weixinTitle != null) {
            weixinTitle!!.setTextColor(resources.getColor(color))
        }
    }

    /**
     * @ProjectName: NBN
     * @ClassName: BaseFragment
     * @Description: 设置dapp bar主题
     * @Author: 赵峰（zhaofengcto@foxmail.com）
     * @CreateDate: 2021/1/7 15:26
     * @UpdateUser: 更新者
     * @UpdateDate: 2021/1/7 15:26
     * @UpdateRemark: 更新说明
     * @Version: 1.0
     */
    protected fun setWeixinTheme(isLight: Boolean) {
        if (barView == null) {
            return
        }
        val toobarRight = barView!!.findViewById<RelativeLayout>(R.id.close_root)
        val toobarMore = barView!!.findViewById<ImageView>(R.id.iv_setting)
        val toobarClose = barView!!.findViewById<ImageView>(R.id.iv_close)
        if (toobarMore == null) {
            return
        }
        if (toobarClose == null) {
            return
        }
        if (toobarRight == null) {
            return
        }
        toobarRight.setBackgroundResource(if (isLight) R.drawable.select_shape_black_20dp else R.drawable.select_shape_while_20dp)
        toobarMore.setImageResource(if (isLight) R.drawable.radio_btn_circle_white else R.drawable.radio_btn_circle_black)
        toobarClose.setImageResource(if (isLight) R.drawable.radio_btn_circle_white_close else R.drawable.radio_btn_circle_black_close)
        weixinTitle!!.setTextColor(
                if (isLight) ContextCompat.getColor(
                        this,
                        R.color.white
                ) else ContextCompat.getColor(this, R.color.black)
        )
    }

    /**
     * 设置title
     *
     * @param
     */
    protected fun setWeixinTitleIvBack(resId: Int) {
        if (weixinStyleBackIv != null && weixinStyleBackIv != null) {
            weixinStyleBackIv!!.setImageResource(resId)
        }
    }

    /**
     * 设置option
     *
     * @param title
     */
    protected fun setOption(title: String?, listener: View.OnClickListener?) {
        if (optionTv != null && title != null) {
            optionTv!!.text = title
            optionTv!!.setTextColor(resources.getColor(R.color.white))
            optionTv!!.setOnClickListener(listener)
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
                loadingDialog!!.showDialog()
            } else {
                loadingDialog!!.setTitle(title)
                loadingDialog!!.showDialog()
            }
        } catch (e: Exception) {
            LogUtil.error("xx", e.message.toString())
        }
    }

    protected fun showLoading(title: String?, color: Int) {
        try {
            if (loadingDialog == null) {
                loadingDialog = LoadingDialog(this)
                loadingDialog!!.setTitle(title)
                loadingDialog!!.setOnDismissListener { clickable = true }
                loadingDialog!!.showDialog()
            } else {
                loadingDialog!!.setTitle(title)
                loadingDialog!!.showDialog()
            }
        } catch (e: Exception) {
            LogUtil.error("xx", e.message.toString())
        }
    }

    protected fun dismissLoading() {
        if (loadingDialog != null && !this.isFinishing && !this.isDestroyed) {
            try {
                loadingDialog!!.dismiss()
            } catch (e: Exception) {
                LogUtil.error("ramon", e.toString())
            }
        }
    }

    /**
     * 权限申请
     */
    @SuppressLint("CheckResult")
    protected fun checkPermissions() {
        PreferenceUtils.applyBoolean(AppConstants.Share.CHECK_PERMISSIONS, false)
        rxPermissions!!.request(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_CALENDAR,
                Manifest.permission.READ_CALENDAR,
                Manifest.permission.ACCESS_NOTIFICATION_POLICY
        )
                .subscribe { granted: Boolean ->
                    if (granted) {
                        // Always true pre-M
                        // I can control the camera now
                    } else {
                        // Oups permission denied
                        // finish();
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
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT)
    }

    /**
     * 隐藏键盘
     */
    protected fun hideInput() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val v = window.peekDecorView()
        if (null != v) {
            imm.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }

    public override fun onStop() {
//        SmartRefreshLayout refresher = findViewById(R.id.refresher);
//        if (refresher != null) {
//            if (refresher.getState() == RefreshState.Refreshing) {
//                refresher.finishRefresh();
//            }
//            if (refresher.getState() == RefreshState.Loading) {
//                refresher.finishLoadMore();
//            }
//        }
        dismissLoading()
        super.onStop()
    }

    fun ocStartActivity(intent: Intent) {
        if (!hasNet()) {
            return
        }
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        super.startActivity(intent)
    }

    private fun lazyInitView() {
        Observable.timer(200, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Long> {
                    override fun onSubscribe(disposable: Disposable) {
                        initDisposable = disposable
                    }

                    override fun onNext(number: Long) {
                        initLazyView()
                    }

                    override fun onError(e: Throwable) {
                        //取消订阅
                        if (initDisposable != null && !initDisposable!!.isDisposed) {
                            initDisposable!!.dispose()
                        }
                    }

                    override fun onComplete() {
                        //取消订阅
                        if (initDisposable != null && !initDisposable!!.isDisposed) {
                            initDisposable!!.dispose()
                        }
                    }
                })
    }
}