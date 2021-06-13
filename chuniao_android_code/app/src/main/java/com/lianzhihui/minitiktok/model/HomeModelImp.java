package com.lianzhihui.minitiktok.model;

import android.content.Context;

import com.echofeng.common.net.APIConstant;
import com.echofeng.common.net.HttpManager;
import com.echofeng.common.net.HttpManagerCallback;
import com.echofeng.common.net.ResultData;
import com.echofeng.common.utils.GsonUtil;
import com.echofeng.common.utils.MyToast;
import com.lianzhihui.minitiktok.bean.hot.AlbumVideoResponse;
import com.lianzhihui.minitiktok.bean.hot.HotClassResponse;
import com.lianzhihui.minitiktok.bean.hot.VideoResponse;
import com.net.core.exception.ApiException;
import com.net.core.model.HttpParams;

import java.util.List;

//网络数据请求实现
public class HomeModelImp {
    private Context context;
    HomeModelInterface homeModelInterface;
    public HomeModelImp(Context context, HomeModelInterface homeModelInterface){
        this.context = context;
        this.homeModelInterface = homeModelInterface;
    }

    public void getRecommendVideo(int page) {
        HttpParams params = new HttpParams();
        params.put("page",page+"");
        params.put("page_size","10");
        new HttpManager(context, new HttpManagerCallback() {
            @Override
            public void onSuccess(ResultData o) {
                List<VideoResponse> data = GsonUtil.GsonToList(o.getDataDecryption(),VideoResponse.class);
                homeModelInterface.onHomeVideoRecommendSuccess(data);
            }

            @Override
            public void onError(ApiException e) {
                homeModelInterface.onFailure(e);
            }
        }).post(APIConstant.GET_HOME_VIDEO_RECOMMEND,params);
    }
    public void getFollowVideo(int page) {
        HttpParams params = new HttpParams();
        params.put("page",page+"");
        params.put("page_size","10");
        new HttpManager(context, new HttpManagerCallback() {
            @Override
            public void onSuccess(ResultData o) {
                List<VideoResponse> data = GsonUtil.GsonToList(o.getDataDecryption(),VideoResponse.class);
                homeModelInterface.onHomeVideoRecommendSuccess(data);
            }

            @Override
            public void onError(ApiException e) {
                homeModelInterface.onFailure(e);
            }
        }).post(APIConstant.GET_HOME_VIDEO_FOLLOW,params);
    }
    public void getHotClass() {
        HttpParams params = new HttpParams();
        new HttpManager(context, new HttpManagerCallback() {
            @Override
            public void onSuccess(ResultData o) {
                List<HotClassResponse> data = GsonUtil.GsonToList(o.getDataDecryption(),HotClassResponse.class);
                homeModelInterface.onHotClassSuccess(data);
            }

            @Override
            public void onError(ApiException e) {
                homeModelInterface.onFailure(e);
            }
        }).post(APIConstant.GET_HOME_HOT_CLASS,params);
    }
//    type_id	number分类ID 分类列表传参数
//    is_free	integer是否免费 1是0否 金币列表 传参数
//    orderBy	string hot 最热 new 最新
//    is_new	integer 是否最新 1是0否 进入最新页面 传参
//    keywords	string关键字  搜索时传参
    public void getHotGoldVideo(int page,String orderBy) {
        HttpParams params = new HttpParams();
        params.put("page",page+"");
        params.put("page_size","20");
        params.put("is_free","0");
        params.put("orderBy",orderBy);
        params.put("is_new","0");
        new HttpManager(context, new HttpManagerCallback() {
            @Override
            public void onSuccess(ResultData o) {
                List<VideoResponse> data = GsonUtil.GsonToList(o.getDataDecryption(),VideoResponse.class);
                homeModelInterface.onSearchVideoSuccess(data);
            }

            @Override
            public void onError(ApiException e) {
                homeModelInterface.onFailure(e);
            }
        }).post(APIConstant.GET_HOME_VIDEO_SEARCH,params);
    }
    //    type_id	number分类ID 分类列表传参数
//    is_free	integer是否免费 1是0否 金币列表 传参数
//    orderBy	string hot 最热 new 最新
//    is_new	integer 是否最新 1是0否 进入最新页面 传参
//    keywords	string关键字  搜索时传参
    public void getHotNewVideo(int page) {
        HttpParams params = new HttpParams();
        params.put("page",page+"");
        params.put("page_size","10");
        params.put("is_new","1");
        new HttpManager(context, new HttpManagerCallback() {
            @Override
            public void onSuccess(ResultData o) {
                List<VideoResponse> data = GsonUtil.GsonToList(o.getDataDecryption(),VideoResponse.class);
                homeModelInterface.onSearchVideoSuccess(data);
            }

            @Override
            public void onError(ApiException e) {
                homeModelInterface.onFailure(e);
            }
        }).post(APIConstant.GET_HOME_VIDEO_SEARCH,params);
    }
    public void getClassVideo(int page, String type_id) {
        HttpParams params = new HttpParams();
        params.put("page",page+"");
        params.put("page_size","20");
        params.put("is_free","1");
        params.put("orderBy","hot");
        params.put("is_new","0");
        params.put("type_id",type_id);
        new HttpManager(context, new HttpManagerCallback() {
            @Override
            public void onSuccess(ResultData o) {
                List<VideoResponse> data = GsonUtil.GsonToList(o.getDataDecryption(),VideoResponse.class);
                homeModelInterface.onSearchVideoSuccess(data);
            }

            @Override
            public void onError(ApiException e) {
                homeModelInterface.onFailure(e);
            }
        }).post(APIConstant.GET_HOME_VIDEO_SEARCH,params);
    }

    public void doVideoLike(String id) {
        HttpParams params = new HttpParams();
        params.put("video_id",id);
        new HttpManager(context, new HttpManagerCallback() {
            @Override
            public void onSuccess(ResultData o) {
                homeModelInterface.onSuccess(o.getDataDecryption());
            }

            @Override
            public void onError(ApiException e) {
                homeModelInterface.onFailure(e);
            }
        }).post(APIConstant.DO_VIDEO_LIKE,params);
    }

    public void getBoxVideo(String id) {
        HttpParams params = new HttpParams();
        params.put("id",id);
        new HttpManager(context, new HttpManagerCallback() {
            @Override
            public void onSuccess(ResultData o) {
                AlbumVideoResponse data = GsonUtil.GsonToBean(o.getDataDecryption(),AlbumVideoResponse.class);
                homeModelInterface.onBoxVideoSuccess(data);
            }

            @Override
            public void onError(ApiException e) {
                homeModelInterface.onFailure(e);
            }
        }).post(APIConstant.GET_VIDEO_BOX,params);
    }

    public void doAttintion(String code) {
        HttpParams params = new HttpParams();
        params.put("to_user_id",code);
        new HttpManager(context, new HttpManagerCallback() {
            @Override
            public void onSuccess(ResultData o) {
                MyToast.showToast("已关注");
                homeModelInterface.onSuccess(o.getDataDecryption());
            }

            @Override
            public void onError(ApiException e) {
                homeModelInterface.onFailure(e);
            }
        }).post(APIConstant.DO_USER_ATTINTION,params);
    }

    public void doVideoCheck(String video_id) {
        HttpParams params = new HttpParams();
        params.put("video_id",video_id);
        new HttpManager(context, new HttpManagerCallback() {
            @Override
            public void onSuccess(ResultData o) {
                VideoResponse data = GsonUtil.GsonToBean(o.getDataDecryption(),VideoResponse.class);
                homeModelInterface.onCheckVideoSuccess(data);
            }

            @Override
            public void onError(ApiException e) {
                homeModelInterface.onFailure(e);
            }
        }).post(APIConstant.DO_VIDEO_CHECK,params);
    }
    public void doBuyVideo(String video_id) {
        HttpParams params = new HttpParams();
        params.put("video_id",video_id);
        new HttpManager(context, new HttpManagerCallback() {
            @Override
            public void onSuccess(ResultData o) {
                VideoResponse data = GsonUtil.GsonToBean(o.getDataDecryption(),VideoResponse.class);
                homeModelInterface.onBuyVideoSuccess(data);
            }

            @Override
            public void onError(ApiException e) {
                MyToast.showToast(e.getMessage());
                homeModelInterface.onFailure(e);
            }
        }).post(APIConstant.DO_VIDEO_BUY,params);
    }
}
