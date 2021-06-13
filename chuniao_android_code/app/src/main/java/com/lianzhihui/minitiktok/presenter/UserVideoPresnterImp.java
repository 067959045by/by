package com.lianzhihui.minitiktok.presenter;

import android.content.Context;

import com.lianzhihui.minitiktok.bean.hot.VideoResponse;
import com.lianzhihui.minitiktok.model.HomeModelImp;
import com.lianzhihui.minitiktok.model.HomeModelInterface;
import com.lianzhihui.minitiktok.model.UserVideoModelImp;
import com.lianzhihui.minitiktok.model.UserVideoModelInterface;
import com.lianzhihui.minitiktok.view.HomeView;
import com.lianzhihui.minitiktok.view.UserVideoView;

import java.util.List;

//逻辑实现
public class UserVideoPresnterImp implements UserVideoModelInterface {

    private final UserVideoView userVideoView;
    private final UserVideoModelImp userVideoModelImp;

    public UserVideoPresnterImp(Context context, UserVideoView userVideoView) {
        this.userVideoView = userVideoView;
        userVideoModelImp = new UserVideoModelImp(context, this);
    }
    public void getLikeVideo(String userId,int page){
        userVideoModelImp.getLikeVideo(userId,page);
    }
    public void getOwnVideo(String userId,int page){
        userVideoModelImp.getOwnVideo(userId,page);
    }
    public void getBuyVideo(int page,String user_id){
        userVideoModelImp.getBuyVideo(page,user_id);
    }

    @Override
    public void onSuccess(Object data) {
        userVideoView.setSuccess(data);
    }

    @Override
    public void onFailure(Object data) {
        userVideoView.setFailure(data);
    }

    @Override
    public void onMineVideoSuccess(List<VideoResponse> data) {
        userVideoView.setMineVideoSuccess(data);
    }

    @Override
    public void onLikeVideoSuccess(List<VideoResponse> data) {
        userVideoView.setLikeVideoSuccess(data);
    }

    @Override
    public void onBuyVideoSuccess(List<VideoResponse> data) {
        userVideoView.setBuyVideoSuccess(data);
    }
}
