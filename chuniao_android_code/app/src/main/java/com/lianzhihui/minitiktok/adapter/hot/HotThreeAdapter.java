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
import com.echofeng.common.utils.FileUtils;
import com.lianzhihui.minitiktok.bean.hot.HotClassResponse;
import com.lianzhihui.onlyleague.R;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.List;

/**
 * create by libo
 * create on 2020-05-20
 * description
 */
public class HotThreeAdapter extends BaseQuickAdapter<HotClassResponse, BaseViewHolder> {

    public HotThreeAdapter(List<HotClassResponse> data) {
        super(R.layout.item_hot_three);
    }

    @Override
    protected void convert(BaseViewHolder helper, HotClassResponse item) {
        helper.setText(R.id.tvTitle,item.getTitle());
        SimpleTarget<File> simpleTarget = new SimpleTarget<File>() {
            @Override
            public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                byte[] decryptBytes = AESFileCrypt.decryptData(resource);
                Bitmap bitmap = BitmapFactory.decodeByteArray(decryptBytes, 0, decryptBytes.length);
                helper.setImageBitmap(R.id.ivCover,bitmap);
            }
        };
        Glide.with(mContext).asFile().load(item.getCover()).error(R.mipmap.ic_launcher).into(simpleTarget);
    }
}
