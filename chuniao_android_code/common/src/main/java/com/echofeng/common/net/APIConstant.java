package com.echofeng.common.net;


import com.echofeng.common.MyApplication;
import com.echofeng.common.config.AppConstants;
import com.echofeng.common.utils.PreferenceUtils;

public class APIConstant {
    public static String URL1 = "";
    public static String URL2 = "";

    //URL
    public static String getBASE_URL() {
        String URL = getRandomUrl();
        return URL;
    }

    private static String getRandomUrl() {
        switch (MyApplication.getEnvironment()) {
            case AppConstants.AppStatus.TEST:
//                URL1 = "http://134.122.170.89:9501";
//                URL2 = "http://134.122.170.28:9501";
                URL1 = "http://api.chuniao.me";
                URL2 = "http://api.chuniao.site";
                break;
            case AppConstants.AppStatus.RELEASE:
//                URL1 = "http://134.122.170.89:9501";
//                URL2 = "http://134.122.170.28:9501";
                URL1 = "http://api.chuniao.me";
                URL2 = "http://api.chuniao.site";
                break;
        }
        String url = PreferenceUtils.getString("baseUrl", URL1);
        return url;
    }

    public static String getBASE_H5_URL(String api) {
        String URL = getBASE_URL() + api;
        return URL;
    }

    //PING
    public static final String DO_PING = "/ping?device=A";
    //获取手机验证码
    public static final String GET_VERIFY_PHONE_CODE = "/app/api/sendmsg";
    //设备登录并获取token
    public static final String DO_LOGIN = "/app/api/auth/login/device";
    //绑定手机号
    public static final String DO_BIND_PHONE = "/app/api/user/bindphone";
    //首页推荐视频
    public static final String GET_HOME_VIDEO_RECOMMEND = "/app/api/video/home/recommend";
    // 获取关注的视频
    public static final String GET_HOME_VIDEO_FOLLOW = "/app/api/video/home/follow";
    // 购买的视频
    public static final String GET_USER_VIDEO_BUY = "/app/api/video/list/user/buy";
    // 喜欢的视频
    public static final String GET_USER_VIDEO_LIKE = "/app/api/video/like/list";
    // 视频分类列表
    public static final String GET_HOME_HOT_CLASS = "/app/api/video/type/list";
    // 分类视频列表
    public static final String GET_HOME_VIDEO_CLASS = "/app/api/video/list/type";
    // 搜索视频列表
    public static final String GET_HOME_VIDEO_SEARCH = "/app/api/search/video/list";
    // 作品的视频
    public static final String GET_USER_VIDEO_OWN = "/app/api/video/user/list";
    // 点赞视频
    public static final String DO_VIDEO_LIKE = "/app/api/video/like";
    // 获取我的资料
    public static final String GET_MINE_INFO = "/app/api/user/info";
    // 获取用户的资料
    public static final String GET_USER_INFO = "/app/api/user/data";
    // 编辑用户的资料
    public static final String UPDATE_USER_INFO = "/app/api/user/update";
    // 单个合集视频列表
    public static final String GET_VIDEO_BOX = "/app/api/album/video/lists";
    // 获取广告列表
    public static final String GET_AD_LIST = "/app/api/ad/list";
    // 关注取消关注
    public static final String DO_USER_ATTINTION = "/app/api/relation/do";
    // 最热接口
    public static final String GET_MOST_HOT_HOME = "/app/api/video/list/hot/perfect";
    // 举报
    public static final String DO_REPORT = "/app/api/user/video/report";
    // 检查更新
    public static final String CHECK_UPDATE = "/app/api/site/version";
    // 金币列表/VIP套餐页面
    public static final String WALLET_COIN_LIST = "/app/api/orders/user/purchase/list";
    // 金币列表/VIP套餐页面
    public static final String DO_AY_LINK = "/app/api/orders/user/purchase";
    // IM聊天消息
    public static final String GET_CHAT_LIST = "/app/api/feedback/list";
    // 发送IM聊天消息
    public static final String DO_SEND_MESSAGE = "/app/api/feedback/create";
    // 视频检查
    public static final String DO_VIDEO_CHECK = "/app/api/video/info";
    //    购买视频
    public static final String DO_VIDEO_BUY = "/app/api/video/buy";
    //    兑换
    public static final String DO_EXCHANGE = "/app/api/code/exchange";
    //    绑定邀请码
    public static final String BIND_INVATION = "/app/api/user/bindcode";
}
