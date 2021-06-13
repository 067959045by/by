package com.lianzhihui.minitiktok.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.echofeng.common.ui.widget.view.LikeView;
import com.echofeng.common.ui.widget.view.jzplayer.JzvdStdTikTok;
import com.echofeng.common.utils.AESFileCrypt;
import com.lianzhihui.minitiktok.bean.hot.VideoResponse;
import com.lianzhihui.minitiktok.widget.view.ControllerView;
import com.lianzhihui.onlyleague.R;

import java.io.File;
import java.util.List;

import cn.jzvd.JZDataSource;
import cn.jzvd.Jzvd;

/**
 * create by libo
 * create on 2020-05-20
 * description
 */
public class MainVideoAdapter extends BaseQuickAdapter<VideoResponse, BaseViewHolder> {

    public MainVideoAdapter(List<VideoResponse> data) {
        super(R.layout.item_video);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoResponse item) {
        ControllerView mControllerView = helper.getView(R.id.mControllerView);
        mControllerView.setVideoData(item);
        LikeView mLikeView = helper.getView(R.id.mLikeView);
        mLikeView.setOnLikeListener(() -> {
            if (item.getIs_like()==1) {  //1是0否
                mControllerView.like();
            }
        });
        JzvdStdTikTok mVideoPlayer = helper.getView(R.id.videoPlayer);
        ImageView ivHead = helper.getView(R.id.ivHead);
        SimpleTarget<File> simpleTarget = new SimpleTarget<File>() {
            @Override
            public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                byte[] decryptBytes = AESFileCrypt.decryptData(resource);
                Bitmap bitmap = BitmapFactory.decodeByteArray(decryptBytes, 0, decryptBytes.length);
                mVideoPlayer.posterImageView.setImageBitmap(bitmap);
            }
        };
        JZDataSource jzDataSource = null;
        String coverUrl = "";
        coverUrl = item.getCover();
        jzDataSource = new JZDataSource(item.getSmu(),item.getTitle());
        if (item.getAd()!=null){
            if (!TextUtils.isEmpty(item.getAd().getPlay())&&item.getAd().getType()==2){
                jzDataSource = new JZDataSource(item.getAd().getPlay(),item.getAd().getTitle());
                Glide.with(mVideoPlayer.getContext()).asFile().load(item.getAd().getCover()).error(R.mipmap.ic_launcher).into(simpleTarget);
                coverUrl = item.getAd().getCover();
            }
        }
        Glide.with(mVideoPlayer.getContext()).asFile().load(coverUrl).error(R.mipmap.ic_launcher).into(simpleTarget);
        jzDataSource.looping = true;
        mVideoPlayer.setUp(jzDataSource, Jzvd.SCREEN_NORMAL);
        mVideoPlayer.startPreloading(); //开始预加载，加载完等待播放
    }
}
