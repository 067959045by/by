package com.lianzhihui.minitiktok.model;

import android.content.Context;

import com.echofeng.common.net.APIConstant;
import com.echofeng.common.net.HttpManager;
import com.echofeng.common.net.HttpManagerCallback;
import com.echofeng.common.net.ResultData;
import com.echofeng.common.utils.GsonUtil;
import com.echofeng.common.utils.MyToast;
import com.lianzhihui.minitiktok.bean.AdResponse;
import com.lianzhihui.minitiktok.bean.system.ChatBean;
import com.net.core.exception.ApiException;
import com.net.core.model.HttpParams;

import java.util.List;

//网络数据请求实现
public class ChatModelImp {
    private Context context;
    ChatModelInterface adModelInterface;
    public ChatModelImp(Context context, ChatModelInterface adModelInterface){
        this.context = context;
        this.adModelInterface = adModelInterface;
    }

    public void getChatList(int page) {
        HttpParams params = new HttpParams();
        params.put("page", page+"");
        params.put("page_size", "20");
        new HttpManager(context, new HttpManagerCallback() {
            @Override
            public void onSuccess(ResultData o) {
                List<ChatBean> data = GsonUtil.GsonToList(o.getDataDecryption(), ChatBean.class);
                adModelInterface.onChatListSuccess(data);
            }

            @Override
            public void onError(ApiException e) {
                MyToast.showToast(e.getMessage());
                adModelInterface.onFailure(e);
            }
        }).post(APIConstant.GET_CHAT_LIST,params);
    }
//    1是文字2是图片
    public void doSendMessage(String type,String content) {
        HttpParams params = new HttpParams();
        params.put("type", type);
        params.put("content", content);
        new HttpManager(context, new HttpManagerCallback() {
            @Override
            public void onSuccess(ResultData o) {
                adModelInterface.onSuccess(o.getDataDecryption());
            }

            @Override
            public void onError(ApiException e) {
                MyToast.showToast(e.getMessage());
                adModelInterface.onFailure(e);
            }
        }).post(APIConstant.DO_SEND_MESSAGE,params);
    }
}
