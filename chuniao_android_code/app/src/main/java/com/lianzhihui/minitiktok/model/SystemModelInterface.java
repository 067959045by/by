package com.lianzhihui.minitiktok.model;



import com.lianzhihui.minitiktok.bean.hot.AlbumVideoResponse;
import com.lianzhihui.minitiktok.bean.hot.HotClassResponse;
import com.lianzhihui.minitiktok.bean.hot.VideoResponse;
import com.lianzhihui.minitiktok.bean.system.VersionResponse;
import com.lianzhihui.minitiktok.model.base.BaseInterface;

import java.util.List;

public interface SystemModelInterface extends BaseInterface {
    void onCheckSuccess(VersionResponse data);
}
