package com.lianzhihui.minitiktok.view;


import com.lianzhihui.minitiktok.bean.AdResponse;
import com.lianzhihui.minitiktok.bean.system.ChatBean;
import com.lianzhihui.minitiktok.view.base.BaseView;

import java.util.List;

public interface ChatView extends BaseView {
    void setChatSuccess(List<ChatBean> data);
}
