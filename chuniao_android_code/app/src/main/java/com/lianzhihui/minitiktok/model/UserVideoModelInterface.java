package com.lianzhihui.minitiktok.model;



import com.lianzhihui.minitiktok.bean.hot.VideoResponse;
import com.lianzhihui.minitiktok.bean.user.LoginResponse;
import com.lianzhihui.minitiktok.model.base.BaseInterface;

import java.util.List;

public interface UserVideoModelInterface extends BaseInterface {
    //获取数据状态回调的接口
    void onMineVideoSuccess(List<VideoResponse> data);
    void onLikeVideoSuccess(List<VideoResponse> data);
    void onBuyVideoSuccess(List<VideoResponse> data);
}
