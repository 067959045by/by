package com.lianzhihui.minitiktok.model;



import com.lianzhihui.minitiktok.bean.user.LoginResponse;
import com.lianzhihui.minitiktok.model.base.BaseInterface;

public interface LoginModelInterface extends BaseInterface {
    //获取数据状态回调的接口
    void onLoginSuccess(LoginResponse data);
    void onBindPhoneSuccess(Object data);
}
