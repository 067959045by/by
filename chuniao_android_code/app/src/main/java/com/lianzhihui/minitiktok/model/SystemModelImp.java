package com.lianzhihui.minitiktok.model;

import android.content.Context;

import com.echofeng.common.net.APIConstant;
import com.echofeng.common.net.HttpManager;
import com.echofeng.common.net.HttpManagerCallback;
import com.echofeng.common.net.ResultData;
import com.echofeng.common.utils.GsonUtil;
import com.echofeng.common.utils.MyToast;
import com.echofeng.common.utils.SystemUtils;
import com.lianzhihui.minitiktok.bean.hot.AlbumVideoResponse;
import com.lianzhihui.minitiktok.bean.hot.HotClassResponse;
import com.lianzhihui.minitiktok.bean.hot.VideoResponse;
import com.lianzhihui.minitiktok.bean.system.VersionResponse;
import com.lianzhihui.minitiktok.bean.user.LoginResponse;
import com.net.core.exception.ApiException;
import com.net.core.model.HttpParams;

import java.util.List;

//网络数据请求实现
public class SystemModelImp {
    private Context context;
    SystemModelInterface homeModelInterface;
    public SystemModelImp(Context context, SystemModelInterface homeModelInterface){
        this.context = context;
        this.homeModelInterface = homeModelInterface;
    }

    public void doVideoReport(String vid,String content) {
        HttpParams params = new HttpParams();
        params.put("vid",vid);
        params.put("content",content);
        new HttpManager(context, new HttpManagerCallback() {
            @Override
            public void onSuccess(ResultData o) {
                MyToast.showToast(o.getMsg());
                homeModelInterface.onSuccess(o.getMsg());
            }

            @Override
            public void onError(ApiException e) {
                MyToast.showToast(e.getMessage());
                homeModelInterface.onFailure(e);
            }
        }).post(APIConstant.DO_REPORT,params);
    }
    public void doUserReport(String to_user_id,String content) {
        HttpParams params = new HttpParams();
        params.put("to_user_id",to_user_id);
        params.put("content",content);
        new HttpManager(context, new HttpManagerCallback() {
            @Override
            public void onSuccess(ResultData o) {
                MyToast.showToast(o.getMsg());
                homeModelInterface.onSuccess(o.getMsg());
            }

            @Override
            public void onError(ApiException e) {
                MyToast.showToast(e.getMessage());
                homeModelInterface.onFailure(e);
            }
        }).post(APIConstant.DO_REPORT,params);
    }
    public void checkUpdate() {
        HttpParams params = new HttpParams();
        params.put("device_type","A");
        params.put("version_code", SystemUtils.getAppVersionName(context));
        new HttpManager(context, new HttpManagerCallback() {
            @Override
            public void onSuccess(ResultData o) {
                VersionResponse data = GsonUtil.GsonToBean(o.getDataDecryption(), VersionResponse.class);
                homeModelInterface.onCheckSuccess(data);
            }

            @Override
            public void onError(ApiException e) {
                MyToast.showToast(e.getMessage());
                homeModelInterface.onFailure(e);
            }
        }).get(APIConstant.CHECK_UPDATE,params);
    }

    public void doBindInvationCode(String code) {
        HttpParams params = new HttpParams();
        params.put("code",code);
        new HttpManager(context, new HttpManagerCallback() {
            @Override
            public void onSuccess(ResultData o) {
                MyToast.showToast("绑定成功");
                homeModelInterface.onSuccess(o.getDataDecryption());
            }

            @Override
            public void onError(ApiException e) {
                MyToast.showToast(e.getMessage());
                homeModelInterface.onFailure(e);
            }
        }).post(APIConstant.BIND_INVATION,params);
    }
}
