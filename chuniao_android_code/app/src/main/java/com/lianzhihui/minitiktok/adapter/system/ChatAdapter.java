package com.lianzhihui.minitiktok.adapter.system;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.echofeng.common.utils.AESFileCrypt;
import com.lianzhihui.minitiktok.bean.system.ChatBean;
import com.lianzhihui.minitiktok.bean.system.ReportBean;
import com.lianzhihui.onlyleague.R;

import java.io.File;
import java.util.List;

public class ChatAdapter extends BaseMultiItemQuickAdapter<ChatBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ChatAdapter(List<ChatBean> data) {
        super(data);
        addItemType(ChatBean.TYPE_ME,R.layout.item_chat_me);
        addItemType(ChatBean.TYPE_HE,R.layout.item_chat_he);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChatBean item) {
        helper.setText(R.id.tvContent,item.getContent());
        SimpleTarget<File> simpleTarget = new SimpleTarget<File>() {
            @Override
            public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                byte[] decryptBytes = AESFileCrypt.decryptData(resource);
                Bitmap bitmap = BitmapFactory.decodeByteArray(decryptBytes, 0, decryptBytes.length);
                helper.setImageBitmap(R.id.ivHead,bitmap);
            }
        };
        Glide.with(mContext).asFile().load(item.getAvatar()).override(100,100).error(R.mipmap.ic_launcher).into(simpleTarget);

    }
}
