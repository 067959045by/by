package com.lianzhihui.minitiktok.config;


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
    public static final String BUGLY_APP_ID = "3ea42a2549";



    public static class AppStatus {
        public static final int STATUS_RECYCLE = -1; //被回收
        public static final int STATUS_NORMAL = 1;    //正常
        public static final int TEST = 1001;
        public static final int CHECK = 1002;
        public static final int RELEASE = 1003;
        public static final long DELAY_MILLIS = 1 * 3000;
    }


    /**
     * Preference key配置
     */
    public static class Share {
        public static final String AUTHORIZATION = "AUTHORIZATION";
    }


    /**
     * EventBus key配置
     */
    public static class Event {
        public static final int UPDATE_TOKEN = 10001;
        public static final int EXIT_APP = 10002;
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
