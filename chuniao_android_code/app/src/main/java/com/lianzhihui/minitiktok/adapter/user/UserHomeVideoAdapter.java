package com.lianzhihui.minitiktok.adapter.user;

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
import com.lianzhihui.minitiktok.bean.hot.VideoResponse;
import com.lianzhihui.onlyleague.R;

import java.io.File;
import java.util.List;

/**
 * create by libo
 * create on 2020-05-20
 * description
 */
public class UserHomeVideoAdapter extends BaseQuickAdapter<VideoResponse, BaseViewHolder> {

    public UserHomeVideoAdapter(List<VideoResponse> data) {
        super(R.layout.item_user_home_video);
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
        Glide.with(mContext).asFile().load(item.getCover()).into(simpleTarget);
    }
}
