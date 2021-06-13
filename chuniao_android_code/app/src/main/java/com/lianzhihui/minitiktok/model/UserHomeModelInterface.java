package com.lianzhihui.minitiktok.model;



import com.lianzhihui.minitiktok.bean.hot.VideoResponse;
import com.lianzhihui.minitiktok.bean.system.UploadResponse;
import com.lianzhihui.minitiktok.bean.user.UserInfo;
import com.lianzhihui.minitiktok.model.base.BaseInterface;

import java.util.List;

public interface UserHomeModelInterface extends BaseInterface {
    //获取数据状态回调的接口
    void onMineInfoSuccess(UserInfo data);

    void onUserInfoSuccess(UserInfo data);

    void onUploadHeadSuccess(UploadResponse data);
}
