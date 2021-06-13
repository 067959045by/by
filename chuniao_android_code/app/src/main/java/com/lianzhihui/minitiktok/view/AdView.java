package com.lianzhihui.minitiktok.view;


import com.lianzhihui.minitiktok.bean.AdResponse;
import com.lianzhihui.minitiktok.bean.user.LoginResponse;
import com.lianzhihui.minitiktok.view.base.BaseView;

import java.util.List;

public interface AdView extends BaseView {
    void setAdSuccess(List<AdResponse> data);
}
