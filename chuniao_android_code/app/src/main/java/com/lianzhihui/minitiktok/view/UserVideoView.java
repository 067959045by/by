package com.lianzhihui.minitiktok.view;


import com.lianzhihui.minitiktok.bean.hot.VideoResponse;
import com.lianzhihui.minitiktok.view.base.BaseView;

import java.util.List;

public interface UserVideoView extends BaseView {
    void setMineVideoSuccess(List<VideoResponse> data);
    void setLikeVideoSuccess(List<VideoResponse> data);
    void setBuyVideoSuccess(List<VideoResponse> data);
}
