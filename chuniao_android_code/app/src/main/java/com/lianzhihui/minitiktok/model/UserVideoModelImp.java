package com.lianzhihui.minitiktok.model;

import android.content.Context;
import android.text.TextUtils;

import com.echofeng.common.net.APIConstant;
import com.echofeng.common.net.HttpManager;
import com.echofeng.common.net.HttpManagerCallback;
import com.echofeng.common.net.ResultData;
import com.echofeng.common.utils.GsonUtil;
import com.lianzhihui.minitiktok.bean.hot.VideoResponse;
import com.net.core.exception.ApiException;
import com.net.core.model.HttpParams;

import java.util.List;

//网络数据请求实现
public class UserVideoModelImp {
    private Context context;
    UserVideoModelInterface userVideoModelInterface;
    public UserVideoModelImp(Context context, UserVideoModelInterface userVideoModelInterface){
        this.context = context;
        this.userVideoModelInterface = userVideoModelInterface;
    }

    public void getLikeVideo(String userId,int page) {
        HttpParams params = new HttpParams();
        params.put("page",page+"");
        params.put("page_size","20");
        if (!TextUtils.isEmpty(userId)) {
            params.put("user_id", userId);
        }
        new HttpManager(context, new HttpManagerCallback() {
            @Override
            public void onSuccess(ResultData o) {
                List<VideoResponse> data = GsonUtil.GsonToList(o.getDataDecryption(),VideoResponse.class);
                userVideoModelInterface.onLikeVideoSuccess(data);
            }

            @Override
            public void onError(ApiException e) {
                userVideoModelInterface.onFailure(e);
            }
        }).post(APIConstant.GET_USER_VIDEO_LIKE,params);
    }

    public void getOwnVideo(String userId,int page) {
        HttpParams params = new HttpParams();
        params.put("page",page+"");
        params.put("page_size","20");
        if (!TextUtils.isEmpty(userId)) {
            params.put("user_id", userId);
        }
        new HttpManager(context, new HttpManagerCallback() {
            @Override
            public void onSuccess(ResultData o) {
                List<VideoResponse> data = GsonUtil.GsonToList(o.getDataDecryption(),VideoResponse.class);
                userVideoModelInterface.onMineVideoSuccess(data);
            }

            @Override
            public void onError(ApiException e) {
                userVideoModelInterface.onFailure(e);
            }
        }).post(APIConstant.GET_USER_VIDEO_OWN,params);
    }

    public void getBuyVideo(int page,String user_id) {
        HttpParams params = new HttpParams();
        params.put("page",page+"");
        params.put("page_size","20");
        if (!TextUtils.isEmpty(user_id)) {
            params.put("user_id", user_id);
        }
        new HttpManager(context, new HttpManagerCallback() {
            @Override
            public void onSuccess(ResultData o) {
                List<VideoResponse> data = GsonUtil.GsonToList(o.getDataDecryption(),VideoResponse.class);
                userVideoModelInterface.onBuyVideoSuccess(data);
            }

            @Override
            public void onError(ApiException e) {
                userVideoModelInterface.onFailure(e);
            }
        }).post(APIConstant.GET_USER_VIDEO_BUY,params);
    }
}
