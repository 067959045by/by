package com.echofeng.common;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;

import com.echofeng.common.config.AppConstants;
import com.echofeng.common.config.DataManager;
import com.echofeng.common.net.HttpManager;
import com.echofeng.common.utils.LogUtil;
import com.echofeng.common.utils.PreferenceUtils;
import com.net.core.EasyHttp;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Copyright (C), 2019-2020
 * FileName: MyApplication
 * Date: 2020/10/21 16:28
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class MyApplication extends Application {

    public static Context mContext;
    public static boolean debug = true;
    private static int environment;

    public static int getEnvironment() {
        return environment;
    }

    public static void setEnvironment(int environment) {
        MyApplication.environment = environment;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        setEnvironment(AppConstants.AppStatus.TEST);
        LogUtil.init(debug);
        //初始化网络配置
        EasyHttp.init(this);
        HttpManager.initBaseConfig();
        /**
         * 初始化SP
         */
        PreferenceUtils.init(mContext, "ECHO");
    }
    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.transparent, android.R.color.darker_gray);//全局设置主题颜色
                return new MaterialHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }
}
