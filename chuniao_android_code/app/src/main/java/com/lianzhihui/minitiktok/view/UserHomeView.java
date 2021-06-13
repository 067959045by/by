package com.lianzhihui.minitiktok.view;


import com.lianzhihui.minitiktok.bean.hot.VideoResponse;
import com.lianzhihui.minitiktok.bean.system.UploadResponse;
import com.lianzhihui.minitiktok.bean.user.UserInfo;
import com.lianzhihui.minitiktok.view.base.BaseView;

import java.util.List;

public interface UserHomeView extends BaseView {
    void setMineInfoSuccess(UserInfo data);
    void setUserInfoSuccess(UserInfo data);
    void setUploadHeadSuccess(UploadResponse data);
}
