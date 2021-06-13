package com.lianzhihui.minitiktok.presenter;

import android.content.Context;

import com.lianzhihui.minitiktok.bean.AdResponse;
import com.lianzhihui.minitiktok.bean.user.LoginResponse;
import com.lianzhihui.minitiktok.model.AdModelImp;
import com.lianzhihui.minitiktok.model.AdModelInterface;
import com.lianzhihui.minitiktok.model.LoginModelImp;
import com.lianzhihui.minitiktok.model.LoginModelInterface;
import com.lianzhihui.minitiktok.view.AdView;
import com.lianzhihui.minitiktok.view.LoginView;

import java.util.List;

//逻辑实现
public class AdPresnterImp implements AdModelInterface {

    private final AdView adView;
    private final AdModelImp adModelImp;

    public AdPresnterImp(Context context, AdView adView) {
        this.adView = adView;
        adModelImp = new AdModelImp(context, this);
    }
    public void getAdList(String postation) {
        adModelImp.getAdList(postation);
    }

    @Override
    public void onSuccess(Object data) {
        adView.setSuccess(data);
    }

    @Override
    public void onFailure(Object data) {
        adView.setFailure(data);
    }


    @Override
    public void onAdSuccess(List<AdResponse> data) {
        adView.setAdSuccess(data);
    }
}
