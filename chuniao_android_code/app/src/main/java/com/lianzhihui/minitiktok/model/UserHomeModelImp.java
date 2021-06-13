package com.lianzhihui.minitiktok.model;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;

import com.echofeng.common.config.AppConfig;
import com.echofeng.common.config.DataManager;
import com.echofeng.common.inter.CustomCallback;
import com.echofeng.common.net.APIConstant;
import com.echofeng.common.net.HttpManager;
import com.echofeng.common.net.HttpManagerCallback;
import com.echofeng.common.net.ResultData;
import com.echofeng.common.net.ResultDataNoAes;
import com.echofeng.common.utils.GsonUtil;
import com.echofeng.common.utils.LogUtil;
import com.echofeng.common.utils.MyToast;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.lianzhihui.minitiktok.bean.hot.VideoResponse;
import com.lianzhihui.minitiktok.bean.system.UploadResponse;
import com.lianzhihui.minitiktok.bean.user.UserInfo;
import com.net.core.EasyHttp;
import com.net.core.body.UIProgressResponseCallBack;
import com.net.core.callback.ProgressDialogCallBack;
import com.net.core.exception.ApiException;
import com.net.core.model.HttpParams;
import com.net.core.subsciber.IProgressDialog;

import java.io.File;
import java.util.List;

//网络数据请求实现
public class UserHomeModelImp {
    private Context context;
    UserHomeModelInterface userHomeModelInterface;
    public UserHomeModelImp(Context context, UserHomeModelInterface userHomeModelInterface){
        this.context = context;
        this.userHomeModelInterface = userHomeModelInterface;
    }

    public void getMineInfo() {
        HttpParams params = new HttpParams();
        new HttpManager(context, new HttpManagerCallback() {
            @Override
            public void onSuccess(ResultData o) {
                UserInfo data = GsonUtil.GsonToBean(o.getDataDecryption(), UserInfo.class);
                userHomeModelInterface.onMineInfoSuccess(data);
            }

            @Override
            public void onError(ApiException e) {
                userHomeModelInterface.onFailure(e);
            }
        }).post(APIConstant.GET_MINE_INFO,params);
    }

    public void getUserInfo(String code) {
        HttpParams params = new HttpParams();
        params.put("code",code);
        new HttpManager(context, new HttpManagerCallback() {
            @Override
            public void onSuccess(ResultData o) {
                UserInfo data = GsonUtil.GsonToBean(o.getDataDecryption(), UserInfo.class);
                userHomeModelInterface.onUserInfoSuccess(data);
            }

            @Override
            public void onError(ApiException e) {
                userHomeModelInterface.onFailure(e);
            }
        }).post(APIConstant.GET_USER_INFO,params);
    }

    public void updateUserInfo(String nick, String intro, String gender, String birth, String avatar, String city) {
        HttpParams params = new HttpParams();
        params.put("nick",nick);
        params.put("intro",intro);
        params.put("gender",gender);
        params.put("birth",birth);
        if (!TextUtils.isEmpty(avatar)) {
            params.put("avatar", avatar);
        }
        params.put("city",city);
        new HttpManager(context, new HttpManagerCallback() {
            @Override
            public void onSuccess(ResultData o) {
                userHomeModelInterface.onSuccess(o.getDataDecryption());
            }

            @Override
            public void onError(ApiException e) {
                userHomeModelInterface.onFailure(e);
            }
        }).post(APIConstant.UPDATE_USER_INFO,params);
    }

    public void doUploadHead(File file, UIProgressResponseCallBack listener) {
        IProgressDialog mProgressDialog = new IProgressDialog() {
            @Override
            public Dialog getDialog() {
                ProgressDialog dialog = new ProgressDialog(context);
                dialog.setMessage("请稍候...");
                return dialog;
            }
        };
        String timeStamp = System.currentTimeMillis()/1000+"";
        String singPostBody = DataManager.singFileBody(timeStamp+"");
//        EasyHttp.post("http://134.122.170.89:8001/upload?"+"platform=A&timestamp="+timeStamp+"&dir=user&sign="+singPostBody)
        EasyHttp.post(AppConfig.upload_url+"/upload?" +"platform=A&timestamp="+timeStamp+"&dir=user&sign="+singPostBody)
                //方式一：文件上传
                //如果有文件名字可以不用再传Type,会自动解析到是image/*
                .headers(HttpManager.getHeaders())
                .params("file", file, file.getName(), listener)
                .accessToken(true)
                .timeStamp(true)
                .execute(new ProgressDialogCallBack<String>(mProgressDialog, true, true) {
                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        MyToast.showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(String response) {
                        ResultDataNoAes resultData = GsonUtil.GsonToBean(response, ResultDataNoAes.class);
                        UploadResponse data = GsonUtil.GsonToBean(resultData.getData(),UploadResponse.class);
                        userHomeModelInterface.onUploadHeadSuccess(data);
                    }
                });
    }
}
