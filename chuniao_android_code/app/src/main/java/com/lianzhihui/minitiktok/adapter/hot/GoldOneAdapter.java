package com.lianzhihui.minitiktok.adapter.hot;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

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
public class GoldOneAdapter extends BaseQuickAdapter<VideoResponse, BaseViewHolder> {

    public GoldOneAdapter(List<VideoResponse> data) {
        super(R.layout.item_gold_column);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoResponse item) {
        helper.setText(R.id.tvNickName,item.getUser().getNick());
        helper.setText(R.id.tvLocation,item.getCity());
        helper.setText(R.id.tvCostCoin,item.getCoins()+"");
        helper.setGone(R.id.tvCostCoin,item.getCoins()==0?false:true);
        SimpleTarget<File> simpleTarget = new SimpleTarget<File>() {
            @Override
            public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                byte[] decryptBytes = AESFileCrypt.decryptData(resource);
                Bitmap bitmap = BitmapFactory.decodeByteArray(decryptBytes, 0, decryptBytes.length);
                helper.setImageBitmap(R.id.ivCover,bitmap);
            }
        };
        Glide.with(mContext).asFile().load(item.getCover()).override(480,800).error(R.mipmap.ic_launcher).into(simpleTarget);
        SimpleTarget<File> simpleTargetHead = new SimpleTarget<File>() {
            @Override
            public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                byte[] decryptBytes = AESFileCrypt.decryptData(resource);
                Bitmap bitmap = BitmapFactory.decodeByteArray(decryptBytes, 0, decryptBytes.length);
                helper.setImageBitmap(R.id.ivHead,bitmap);
            }
        };
        Glide.with(mContext).asFile().load(item.getUser().getAvatar()).override(100,100).error(R.mipmap.ic_launcher).into(simpleTargetHead);
    }
}
