package com.lianzhihui.minitiktok.model;



import com.lianzhihui.minitiktok.bean.hot.AlbumVideoResponse;
import com.lianzhihui.minitiktok.bean.hot.HotClassResponse;
import com.lianzhihui.minitiktok.bean.hot.VideoResponse;
import com.lianzhihui.minitiktok.model.base.BaseInterface;

import java.util.List;

public interface HomeModelInterface extends BaseInterface {
    //获取数据状态回调的接口
    void onHomeVideoRecommendSuccess(List<VideoResponse> data);
    void onSearchVideoSuccess(List<VideoResponse> data);

    void onHotClassSuccess(List<HotClassResponse> data);

    void onBoxVideoSuccess(AlbumVideoResponse data);

    void onCheckVideoSuccess(VideoResponse data);
    void onBuyVideoSuccess(Object data);
}
