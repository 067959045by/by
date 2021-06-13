package com.lianzhihui.minitiktok.model;

import android.content.Context;

import com.echofeng.common.net.APIConstant;
import com.echofeng.common.net.HttpManager;
import com.echofeng.common.net.HttpManagerCallback;
import com.echofeng.common.net.ResultData;
import com.echofeng.common.utils.GsonUtil;
import com.lianzhihui.minitiktok.bean.hot.MostHotResponse;
import com.net.core.exception.ApiException;
import com.net.core.model.HttpParams;

//网络数据请求实现
public class HotHomeModelImp {
    private Context context;
    HotHomeModelInterface homeModelInterface;
    public HotHomeModelImp(Context context, HotHomeModelInterface homeModelInterface){
        this.context = context;
        this.homeModelInterface = homeModelInterface;
    }

    public void getMostHotHome() {
        HttpParams params = new HttpParams();
        new HttpManager(context, new HttpManagerCallback() {
            @Override
            public void onSuccess(ResultData o) {
                MostHotResponse data = GsonUtil.GsonToBean(o.getDataDecryption(),MostHotResponse.class);
                homeModelInterface.onHotHomeSuccess(data);
            }

            @Override
            public void onError(ApiException e) {
                homeModelInterface.onFailure(e);
            }
        }).post(APIConstant.GET_MOST_HOT_HOME,params);
    }
}
