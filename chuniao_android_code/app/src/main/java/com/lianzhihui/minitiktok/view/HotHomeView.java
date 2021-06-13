package com.lianzhihui.minitiktok.view;


import com.lianzhihui.minitiktok.bean.AdResponse;
import com.lianzhihui.minitiktok.bean.hot.MostHotResponse;
import com.lianzhihui.minitiktok.view.base.BaseView;

import java.util.List;

public interface HotHomeView extends BaseView {
    void setHotHomeSuccess(MostHotResponse data);
}
