package com.echofeng.common.config;


import org.jetbrains.annotations.NotNull;

/**
 * Copyright (C), 2019-2019, wase_five
 * FileName: Constants
 * Author: echo
 * Date: 2019/11/13 14:49
 * Description: 这里配置常量
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class AppConstants {

    public static final String LANGUAGE_KEY = "language";
    public static final String APP_ANDROID_ID = "APPID_MOCK";//模拟应用标识
    //友盟
    public static final String YOUMENG_APPKEY = "5f4a0fb012981d3ca30a6ac7";
    public static final String YOUMENG_CANCEL = "okToken_android";
    //Bugly
    public static final String BUGLY_APP_ID = "1506f2865f";



    public static class AppStatus {
        public static final int STATUS_RECYCLE = -1; //被回收
        public static final int STATUS_NORMAL = 1;    //正常
        public static final int TEST = 1001;
        public static final int CHECK = 1002;
        public static final int RELEASE = 1003;
        public static final long DELAY_MILLIS = 1 * 1000;
    }


    /**
     * Preference key配置
     */
    public static class Share {
        public static final String AUTHORIZATION = "AUTHORIZATION";
        public static final String LANGUAGE_KEY = "LANGUAGE";
        public static final String CHECK_PERMISSIONS = "CHECK_PERMISSIONS";
        public static final String LOGIN_DATA = "LOGIN_DATA";
    }


    /**
     * EventBus key配置
     */
    public static class Event {
        public static final int UPDATE_TOKEN = 10001;
        public static final int UPDATE_STATE_PLAY_ONE = 10002;
        public static final int UPDATE_STATE_PLAY_TWO = 10003;
        public static final int UPDATE_STATE_PURSE_ONE = 10004;
        public static final int UPDATE_STATE_PURSE_TWO = 10005;
        public static final int UPDATE_USER_HOME = 10006;
        public static final int REFRESH_HOME_ATINTION = 10007;
    }

    /**
     * Preference key配置
     */
    public static class Net {
    }

    public static class Intent {
    }

    public static class System {
    }

}
