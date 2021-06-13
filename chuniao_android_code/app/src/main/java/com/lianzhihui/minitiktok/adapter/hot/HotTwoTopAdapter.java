package com.lianzhihui.minitiktok.adapter.hot;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.echofeng.common.utils.AESFileCrypt;
import com.echofeng.common.utils.FileUtils;
import com.lianzhihui.minitiktok.bean.hot.VideoResponse;
import com.lianzhihui.minitiktok.ui.main.one.PlayListActivity;
import com.lianzhihui.onlyleague.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * create by libo
 * create on 2020-05-20
 * description
 */
public class HotTwoTopAdapter extends BaseQuickAdapter<VideoResponse, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {

    public HotTwoTopAdapter(List<VideoResponse> data) {
        super(R.layout.item_hot_two_top);
        setOnItemClickListener(this::onItemClick);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoResponse item) {
        SimpleTarget<File> simpleTarget = new SimpleTarget<File>() {
            @Override
            public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                byte[] decryptBytes = AESFileCrypt.decryptData(resource);
                Bitmap bitmap = BitmapFactory.decodeByteArray(decryptBytes, 0, decryptBytes.length);
                helper.setImageBitmap(R.id.ivCover,bitmap);
            }
        };
        Glide.with(mContext).asFile().load(item.getCover()).error(R.mipmap.ic_launcher).into(simpleTarget);
        switch (helper.getAdapterPosition()){
            case 0:
                helper.setImageResource(R.id.ivTop,R.mipmap.icon_top1);
                break;
            case 1:
                helper.setImageResource(R.id.ivTop,R.mipmap.icon_top2);
                break;
            case 2:
                helper.setImageResource(R.id.ivTop,R.mipmap.icon_top3);
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        VideoResponse videoResponse = (VideoResponse) adapter.getItem(position);
        ArrayList<VideoResponse> videoList = new ArrayList<>();
        videoList.add(videoResponse);
        Intent intent = new Intent(mContext, PlayListActivity.class);
        intent.putExtra("currentPosition",position);
        intent.putExtra("videoList",videoList);
        mContext.startActivity(intent);
    }
}
