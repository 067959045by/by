package com.lianzhihui.minitiktok.presenter;

import android.content.Context;

import com.lianzhihui.minitiktok.bean.hot.AlbumVideoResponse;
import com.lianzhihui.minitiktok.bean.hot.HotClassResponse;
import com.lianzhihui.minitiktok.bean.hot.VideoResponse;
import com.lianzhihui.minitiktok.bean.system.VersionResponse;
import com.lianzhihui.minitiktok.model.HomeModelImp;
import com.lianzhihui.minitiktok.model.HomeModelInterface;
import com.lianzhihui.minitiktok.model.SystemModelImp;
import com.lianzhihui.minitiktok.model.SystemModelInterface;
import com.lianzhihui.minitiktok.view.HomeView;
import com.lianzhihui.minitiktok.view.SystemView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

//逻辑实现
public class SystemPresnterImp implements SystemModelInterface {

    private final SystemView systemView;
    private final SystemModelImp systemModelImp;

    public SystemPresnterImp(Context context, SystemView systemView) {
        this.systemView = systemView;
        systemModelImp = new SystemModelImp(context, this);
    }
    public void doReportVideo(String vid,String content){
        systemModelImp.doVideoReport(vid,content);
    }
    public void doUserReport(String userId,String content){
        systemModelImp.doUserReport(userId,content);
    }
    public void checkUpdate(){
        systemModelImp.checkUpdate();
    }

    @Override
    public void onSuccess(Object data) {
        systemView.setSuccess(data);
    }

    @Override
    public void onFailure(Object data) {
        systemView.setFailure(data);
    }

    @Override
    public void onCheckSuccess(VersionResponse data) {
        systemView.setCheckUpdateSuccess(data);
    }

    public void doBindInvationCode(@NotNull String code) {
        systemModelImp.doBindInvationCode(code);
    }
}
