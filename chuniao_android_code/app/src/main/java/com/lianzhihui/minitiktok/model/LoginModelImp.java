package com.lianzhihui.minitiktok.model;

import android.content.Context;

import com.echofeng.common.MyApplication;
import com.echofeng.common.config.DataManager;
import com.echofeng.common.net.APIConstant;
import com.echofeng.common.net.HttpManager;
import com.echofeng.common.net.HttpManagerCallback;
import com.echofeng.common.net.ResultData;
import com.echofeng.common.utils.GsonUtil;
import com.echofeng.common.utils.MyToast;
import com.echofeng.common.utils.PreferenceUtils;
import com.echofeng.common.utils.SystemUtils;
import com.lianzhihui.minitiktok.bean.user.LoginResponse;
import com.net.core.exception.ApiException;
import com.net.core.model.HttpParams;

//网络数据请求实现
public class LoginModelImp {
    private Context context;
    LoginModelInterface loginModelInterface;
    public LoginModelImp(Context context,LoginModelInterface loginModelInterface){
        this.context = context;
        this.loginModelInterface = loginModelInterface;
    }

    public void getPhoneVerifyCode(String phone) {
        HttpParams params = new HttpParams();
        params.put("phone",phone);
        new HttpManager(context, new HttpManagerCallback() {
            @Override
            public void onSuccess(ResultData o) {
                loginModelInterface.onSuccess(o);
            }

            @Override
            public void onError(ApiException e) {
                loginModelInterface.onFailure(e);
            }
        }).post(APIConstant.GET_VERIFY_PHONE_CODE,params);
    }

    public void doLogin(String code,String channel) {
        HttpParams params = new HttpParams();
        params.put("device_type", "A");
        params.put("device_no", SystemUtils.getAndroidId(MyApplication.mContext));
        params.put("version", SystemUtils.getAppVersionName(MyApplication.mContext));
        params.put("token", DataManager.getAuthorization());
        params.put("code", code);
        params.put("channel", channel);
        new HttpManager(context, new HttpManagerCallback() {
            @Override
            public void onSuccess(ResultData o) {
                DataManager.setLoginResponse(o.getDataDecryption());
                LoginResponse data = GsonUtil.GsonToBean(o.getDataDecryption(), LoginResponse.class);
                DataManager.setAuthorization(data.getAuth().getToken());
                loginModelInterface.onLoginSuccess(data);
            }

            @Override
            public void onError(ApiException e) {
                DataManager.cleanAuthorization();
                MyToast.showToast(e.getMessage());
                loginModelInterface.onFailure(e);
            }
        }).post(APIConstant.DO_LOGIN,params);
    }

    public void doPhoneBind(String phone,String code) {
        HttpParams params = new HttpParams();
        params.put("phone",phone);
        params.put("code",code);
        new HttpManager(context, new HttpManagerCallback() {
            @Override
            public void onSuccess(ResultData o) {
                MyToast.showToast(o.getMsg());
                loginModelInterface.onBindPhoneSuccess(o.getDataDecryption());
            }

            @Override
            public void onError(ApiException e) {
                MyToast.showToast(e.getMessage());
                loginModelInterface.onFailure(e);
            }
        }).post(APIConstant.DO_BIND_PHONE,params);
    }

    public void doCheckIp() {
        HttpParams params = new HttpParams();
        new HttpManager(context, new HttpManagerCallback() {
            @Override
            public void onSuccess(ResultData o) {
                loginModelInterface.onSuccess(o.getData());
            }

            @Override
            public void onError(ApiException e) {
                PreferenceUtils.applyString("baseUrl",APIConstant.URL2);
                MyToast.showToast(e.getMessage());
                loginModelInterface.onFailure(e);
            }
        }).get(APIConstant.DO_PING,params);
    }
}
