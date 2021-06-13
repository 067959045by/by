package com.lianzhihui.minitiktok.presenter;

import android.content.Context;

import com.lianzhihui.minitiktok.bean.hot.AlbumVideoResponse;
import com.lianzhihui.minitiktok.bean.hot.HotClassResponse;
import com.lianzhihui.minitiktok.bean.hot.VideoResponse;
import com.lianzhihui.minitiktok.model.HomeModelImp;
import com.lianzhihui.minitiktok.model.HomeModelInterface;
import com.lianzhihui.minitiktok.model.LoginModelImp;
import com.lianzhihui.minitiktok.model.LoginModelInterface;
import com.lianzhihui.minitiktok.view.HomeView;
import com.lianzhihui.minitiktok.view.LoginView;

import org.jetbrains.annotations.Nullable;

import java.util.List;

//逻辑实现
public class HomePresnterImp implements HomeModelInterface {

    private final HomeView homeView;
    private final HomeModelImp homeModelImp;

    public HomePresnterImp(Context context, HomeView homeView) {
        this.homeView = homeView;
        homeModelImp = new HomeModelImp(context, this);
    }
    public void getRecommendVideo(int page){
        homeModelImp.getRecommendVideo(page);
    }
    public void getFollowVideo(int page){
        homeModelImp.getFollowVideo(page);
    }
    public void getBoxVideo(String id) {
        homeModelImp.getBoxVideo(id);
    }
    public void getHotClass(){
        homeModelImp.getHotClass();
    }
    public void getHotGoldVideo(int page,String orderBy) {
        homeModelImp.getHotGoldVideo(page,orderBy);
    }
    public void getHotNewVideo(int page) {
        homeModelImp.getHotNewVideo(page);
    }
    public void getClassVideo(int page,String type_id) {
        homeModelImp.getClassVideo(page,type_id);
    }
    public void doVideoLike(@Nullable String id) {
        homeModelImp.doVideoLike(id);
    }
    public void doVideoCheck(@Nullable String video_id) {
        homeModelImp.doVideoCheck(video_id);
    }
    public void doBuyVideo(@Nullable String video_id) {
        homeModelImp.doBuyVideo(video_id);
    }
    @Override
    public void onHomeVideoRecommendSuccess(List<VideoResponse> data) {
        homeView.setVideoRecommendSuccess(data);
    }

    @Override
    public void onSearchVideoSuccess(List<VideoResponse> data) {
        homeView.setSearchVideoSuccess(data);
    }

    @Override
    public void onHotClassSuccess(List<HotClassResponse> data) {
        homeView.setHotClassSuccess(data);
    }

    @Override
    public void onBoxVideoSuccess(AlbumVideoResponse data) {
        homeView.setBoxVideoSuccess(data);
    }

    @Override
    public void onCheckVideoSuccess(VideoResponse data) {
        homeView.setCheckVideoSuccess(data);
    }

    @Override
    public void onBuyVideoSuccess(Object data) {
        homeView.setBuyVideoSuccess(data);
    }

    @Override
    public void onSuccess(Object data) {
        homeView.setSuccess(data);
    }

    @Override
    public void onFailure(Object data) {
        homeView.setFailure(data);
    }

    public void doAttintion(@Nullable String code) {
        homeModelImp.doAttintion(code);
    }
}
