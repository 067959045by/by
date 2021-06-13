package com.lianzhihui.minitiktok.model;



import com.lianzhihui.minitiktok.bean.AdResponse;
import com.lianzhihui.minitiktok.bean.system.ChatBean;
import com.lianzhihui.minitiktok.model.base.BaseInterface;

import java.util.List;

public interface ChatModelInterface extends BaseInterface {
    //获取数据状态回调的接口
    void onChatListSuccess(List<ChatBean> data);
}
