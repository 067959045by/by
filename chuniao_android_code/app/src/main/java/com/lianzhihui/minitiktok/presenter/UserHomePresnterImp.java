package com.lianzhihui.minitiktok.presenter;

import android.content.Context;

import com.lianzhihui.minitiktok.bean.hot.VideoResponse;
import com.lianzhihui.minitiktok.bean.system.UploadResponse;
import com.lianzhihui.minitiktok.bean.user.UserInfo;
import com.lianzhihui.minitiktok.model.UserHomeModelImp;
import com.lianzhihui.minitiktok.model.UserHomeModelInterface;
import com.lianzhihui.minitiktok.model.UserVideoModelImp;
import com.lianzhihui.minitiktok.model.UserVideoModelInterface;
import com.lianzhihui.minitiktok.view.UserHomeView;
import com.lianzhihui.minitiktok.view.UserVideoView;
import com.net.core.body.UIProgressResponseCallBack;

import java.io.File;
import java.util.List;

//逻辑实现
public class UserHomePresnterImp implements UserHomeModelInterface {

    private final UserHomeView userHomeView;
    private final UserHomeModelImp userHomeModelImp;

    public UserHomePresnterImp(Context context, UserHomeView userHomeView) {
        this.userHomeView = userHomeView;
        userHomeModelImp = new UserHomeModelImp(context, this);
    }
    public void getMineInfo(){
        userHomeModelImp.getMineInfo();
    }
    public void getUserInfo(String code) {
        userHomeModelImp.getUserInfo(code);
    }
    public void doUploadHead(File file, UIProgressResponseCallBack listener) {
        userHomeModelImp.doUploadHead(file,listener);
    }
    //    nick	string 昵称
//    intro	string简介
//    gender	integer性别 1 男 2 女 3 保密
//    birth	string生日 Y-m-d
//    avatar	string 头像
//    city	string城市
    public void updateUserInfo(String nick,String intro,String gender,String birth,String avatar,String city) {
        userHomeModelImp.updateUserInfo(nick,intro,gender,birth,avatar,city);
    }
    @Override
    public void onSuccess(Object data) {
        userHomeView.setSuccess(data);
    }

    @Override
    public void onFailure(Object data) {
        userHomeView.setFailure(data);
    }

    @Override
    public void onMineInfoSuccess(UserInfo data) {
        userHomeView.setMineInfoSuccess(data);
    }

    @Override
    public void onUserInfoSuccess(UserInfo data) {
        userHomeView.setUserInfoSuccess(data);
    }

    @Override
    public void onUploadHeadSuccess(UploadResponse data) {
        userHomeView.setUploadHeadSuccess(data);
    }


}
