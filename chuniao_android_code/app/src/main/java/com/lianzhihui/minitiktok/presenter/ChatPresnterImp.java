package com.lianzhihui.minitiktok.presenter;

import android.content.Context;

import com.lianzhihui.minitiktok.bean.AdResponse;
import com.lianzhihui.minitiktok.bean.system.ChatBean;
import com.lianzhihui.minitiktok.model.AdModelImp;
import com.lianzhihui.minitiktok.model.AdModelInterface;
import com.lianzhihui.minitiktok.model.ChatModelImp;
import com.lianzhihui.minitiktok.model.ChatModelInterface;
import com.lianzhihui.minitiktok.view.AdView;
import com.lianzhihui.minitiktok.view.ChatView;

import java.util.List;

//逻辑实现
public class ChatPresnterImp implements ChatModelInterface {

    private final ChatView adView;
    private final ChatModelImp adModelImp;

    public ChatPresnterImp(Context context, ChatView adView) {
        this.adView = adView;
        adModelImp = new ChatModelImp(context, this);
    }
    public void doSendMessage(String type,String content) {
        adModelImp.doSendMessage(type,content);
    }
    public void getChatList(int page) {
        adModelImp.getChatList(page);
    }

    @Override
    public void onSuccess(Object data) {
        adView.setSuccess(data);
    }

    @Override
    public void onFailure(Object data) {
        adView.setFailure(data);
    }

    @Override
    public void onChatListSuccess(List<ChatBean> data) {
        adView.setChatSuccess(data);
    }
}
