package com.lianzhihui.minitiktok.adapter.wallet;

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
import com.lianzhihui.minitiktok.bean.wallet.WalletMainResponse;
import com.lianzhihui.onlyleague.R;

import java.io.File;
import java.util.List;

public class VipCardAdapter extends BaseQuickAdapter<WalletMainResponse.VipItem, BaseViewHolder> {
    public VipCardAdapter(@Nullable List<WalletMainResponse.VipItem> data) {
        super(R.layout.item_vip_card);
    }
    @Override
    protected void convert(BaseViewHolder helper, WalletMainResponse.VipItem item) {
        ImageView ivCover = (ImageView) helper.getView(R.id.ivCover);
        SimpleTarget<File> simpleTarget = new SimpleTarget<File>() {
            @Override
            public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                byte[] decryptBytes = AESFileCrypt.decryptData(resource);
                Bitmap bitmap = BitmapFactory.decodeByteArray(decryptBytes, 0, decryptBytes.length);
                ivCover.setImageBitmap(bitmap);
            }
        };
        Glide.with(mContext).asFile().load(item.getCover()).into(simpleTarget);
    }

    public void setChooseItem(int pos){
        for (int i = 0; i < getData().size(); i++) {
            getItem(i).setSelected(false);
        }
        getItem(pos).setSelected(true);
        notifyDataSetChanged();
    }
}
