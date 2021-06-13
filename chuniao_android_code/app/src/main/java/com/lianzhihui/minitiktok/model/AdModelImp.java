package com.lianzhihui.minitiktok.model;

import android.content.Context;

import com.echofeng.common.net.APIConstant;
import com.echofeng.common.net.HttpManager;
import com.echofeng.common.net.HttpManagerCallback;
import com.echofeng.common.net.ResultData;
import com.echofeng.common.utils.GsonUtil;
import com.echofeng.common.utils.MyToast;
import com.lianzhihui.minitiktok.bean.AdResponse;
import com.net.core.exception.ApiException;
import com.net.core.model.HttpParams;

import java.util.List;

//网络数据请求实现
public class AdModelImp {
    private Context context;
    AdModelInterface adModelInterface;
    public AdModelImp(Context context, AdModelInterface adModelInterface){
        this.context = context;
        this.adModelInterface = adModelInterface;
    }

//    position	是
//    position=video_hot
//    hot_top热门头部位；creater_index创造者中心页面；meg_home消息页面；
    public void getAdList(String position) {
        HttpParams params = new HttpParams();
        params.put("position", position);
        new HttpManager(context, new HttpManagerCallback() {
            @Override
            public void onSuccess(ResultData o) {
                List<AdResponse> data = GsonUtil.GsonToList(o.getDataDecryption(), AdResponse.class);
                adModelInterface.onAdSuccess(data);
            }

            @Override
            public void onError(ApiException e) {
                MyToast.showToast(e.getMessage());
                adModelInterface.onFailure(e);
            }
        }).post(APIConstant.GET_AD_LIST,params);
    }
}
