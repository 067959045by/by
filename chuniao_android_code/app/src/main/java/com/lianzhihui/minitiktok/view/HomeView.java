package com.lianzhihui.minitiktok.view;


import com.lianzhihui.minitiktok.bean.hot.AlbumVideoResponse;
import com.lianzhihui.minitiktok.bean.hot.HotClassResponse;
import com.lianzhihui.minitiktok.bean.hot.VideoResponse;
import com.lianzhihui.minitiktok.view.base.BaseView;

import java.util.List;

public interface HomeView extends BaseView {
    void setVideoRecommendSuccess(List<VideoResponse> data);
    void setSearchVideoSuccess(List<VideoResponse> data);
    void setBoxVideoSuccess(AlbumVideoResponse data);
    void setHotClassSuccess(List<HotClassResponse> data);
    void setCheckVideoSuccess(VideoResponse data);

    void setBuyVideoSuccess(Object data);
}
