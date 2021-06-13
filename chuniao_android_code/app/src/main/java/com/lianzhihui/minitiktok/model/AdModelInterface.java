package com.lianzhihui.minitiktok.model;



import com.lianzhihui.minitiktok.bean.AdResponse;
import com.lianzhihui.minitiktok.bean.user.LoginResponse;
import com.lianzhihui.minitiktok.model.base.BaseInterface;

import java.util.List;

public interface AdModelInterface extends BaseInterface {
    //获取数据状态回调的接口
    void onAdSuccess(List<AdResponse> data);
}
