package com.lianzhihui.minitiktok.widget.dialog;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.CheckBox;
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

/**
 * 余额选择
 */

public class DialogCoinAdapter extends BaseQuickAdapter<WalletMainResponse.Payment, BaseViewHolder>  {

    public DialogCoinAdapter(@Nullable List<WalletMainResponse.Payment> data) {
        super(R.layout.item_dialog_choose_balance, data);
    }

    public WalletMainResponse.Payment getItemSelected(){
        WalletMainResponse.Payment recordBean = null;
        for (WalletMainResponse.Payment d:getData()) {
            if (d.isSelected()){
                return d;
            }
        }
        return recordBean;
    }

    private void onReloadList(int posation,boolean selected){
        for (int i = 0; i < getData().size(); i++) {
            getData().get(i).setSelected(false);
        }
        if (selected){
            getData().get(posation).setSelected(true);
        }else {
            getData().get(posation).setSelected(false);
        }
        notifyDataSetChanged();
    }
    @Override
    protected void convert(@NonNull BaseViewHolder helper, WalletMainResponse.Payment item) {
        helper.setText(R.id.tvCoinName,item.getName());
        ImageView ivIcon = helper.getView(R.id.ivIcon);
        SimpleTarget<File> simpleTarget = new SimpleTarget<File>() {
            @Override
            public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                byte[] decryptBytes = AESFileCrypt.decryptData(resource);
                Bitmap bitmap = BitmapFactory.decodeByteArray(decryptBytes, 0, decryptBytes.length);
                ivIcon.setImageBitmap(bitmap);
            }
        };
        Glide.with(mContext).asFile().load(item.getImg()).error(R.mipmap.icon_pay_ali).into(simpleTarget);

        CheckBox checkBox = (CheckBox)helper.getView(R.id.ckChoose);
        checkBox.setTag(helper.getAdapterPosition());
        if (item.isSelected()){
            checkBox.setChecked(true);
        }else {
            checkBox.setChecked(false);
        }
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int posation = (int) checkBox.getTag();
                if (checkBox.isClickable()) {
                    if (checkBox.isSelected()) {
                        onReloadList(posation, false);
                    } else {
                        onReloadList(posation, true);
                    }
                }
            }
        });
    }

}
