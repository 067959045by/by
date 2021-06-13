package com.lianzhihui.minitiktok.view;


import com.lianzhihui.minitiktok.bean.hot.AlbumVideoResponse;
import com.lianzhihui.minitiktok.bean.hot.HotClassResponse;
import com.lianzhihui.minitiktok.bean.hot.VideoResponse;
import com.lianzhihui.minitiktok.bean.system.VersionResponse;
import com.lianzhihui.minitiktok.view.base.BaseView;

import java.util.List;

public interface SystemView extends BaseView {
    void setCheckUpdateSuccess(VersionResponse data);
}
