package com.lianzhihui.minitiktok.adapter.hot;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.echofeng.common.utils.AESFileCrypt;
import com.lianzhihui.minitiktok.bean.hot.MostHotResponse;
import com.lianzhihui.minitiktok.bean.hot.VideoResponse;
import com.lianzhihui.onlyleague.R;

import java.io.File;
import java.util.List;

/**
 * create by libo
 * create on 2020-05-20
 * description
 */
public class HotTwoNiceVideoAdapter extends BaseQuickAdapter<VideoResponse, BaseViewHolder> {

    public HotTwoNiceVideoAdapter(List<VideoResponse> data) {
        super(R.layout.item_hot_two_pay_nice);
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
        helper.setText(R.id.tvTitle,item.getTitle());
        helper.setText(R.id.tvSee,item.getPlay_total());
        helper.setText(R.id.tvLike,item.getLike()+"");
    }
}
