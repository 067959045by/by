package com.lianzhihui.minitiktok.presenter;

import android.content.Context;

import com.lianzhihui.minitiktok.bean.AdResponse;
import com.lianzhihui.minitiktok.bean.hot.MostHotResponse;
import com.lianzhihui.minitiktok.model.AdModelImp;
import com.lianzhihui.minitiktok.model.AdModelInterface;
import com.lianzhihui.minitiktok.model.HotHomeModelImp;
import com.lianzhihui.minitiktok.model.HotHomeModelInterface;
import com.lianzhihui.minitiktok.view.AdView;
import com.lianzhihui.minitiktok.view.HotHomeView;

import java.util.List;

//逻辑实现
public class HotHomePresnterImp implements HotHomeModelInterface {

    private final HotHomeView hotHomeView;
    private final HotHomeModelImp hotHomeModelImp;

    public HotHomePresnterImp(Context context, HotHomeView hotHomeView) {
        this.hotHomeView = hotHomeView;
        hotHomeModelImp = new HotHomeModelImp(context, this);
    }
    public void getMostHotHome() {
        hotHomeModelImp.getMostHotHome();
    }

    @Override
    public void onSuccess(Object data) {
        hotHomeView.setSuccess(data);
    }

    @Override
    public void onFailure(Object data) {
        hotHomeView.setFailure(data);
    }

    @Override
    public void onHotHomeSuccess(MostHotResponse data) {
        hotHomeView.setHotHomeSuccess(data);
    }
}
