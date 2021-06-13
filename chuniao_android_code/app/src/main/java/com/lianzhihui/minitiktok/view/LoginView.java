package com.lianzhihui.minitiktok.view;


import com.lianzhihui.minitiktok.bean.user.LoginResponse;
import com.lianzhihui.minitiktok.view.base.BaseView;

public interface LoginView extends BaseView {
    void setLoginSuccess(LoginResponse data);
    void setBindPhoneSuccess(Object data);
}
