package com.lianzhihui.minitiktok.presenter;

import android.content.Context;


import com.lianzhihui.minitiktok.bean.user.LoginResponse;
import com.lianzhihui.minitiktok.view.LoginView;
import com.lianzhihui.minitiktok.model.LoginModelImp;
import com.lianzhihui.minitiktok.model.LoginModelInterface;

//逻辑实现
public class LoginPresnterImp implements LoginModelInterface {

    private final LoginView loginView;
    private final LoginModelImp loginModelImp;

    public LoginPresnterImp(Context context, LoginView loginView) {
        this.loginView = loginView;
        loginModelImp = new LoginModelImp(context, this);
    }
    public void doCheckIp() {
        loginModelImp.doCheckIp();
    }
    public void doLogin(String code,String channel) {
        loginModelImp.doLogin(code,channel);
    }
    public void getPhoneVerifyCode(String phone){
        loginModelImp.getPhoneVerifyCode(phone);
    }
    public void doPhoneBind(String phone,String code){
        loginModelImp.doPhoneBind(phone,code);
    }
    @Override
    public void onLoginSuccess(LoginResponse data) {
        loginView.setLoginSuccess(data);
    }

    @Override
    public void onBindPhoneSuccess(Object data) {
        loginView.setBindPhoneSuccess(data);
    }

    @Override
    public void onSuccess(Object data) {
        loginView.setSuccess(data);
    }

    @Override
    public void onFailure(Object data) {
        loginView.setFailure(data);
    }


}
