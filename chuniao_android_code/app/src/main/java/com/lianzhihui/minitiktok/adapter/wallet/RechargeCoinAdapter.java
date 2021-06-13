package com.lianzhihui.minitiktok.adapter.wallet;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lianzhihui.minitiktok.bean.wallet.WalletMainResponse;
import com.lianzhihui.onlyleague.R;

import java.util.List;

public class RechargeCoinAdapter extends BaseQuickAdapter<WalletMainResponse.VipItem, BaseViewHolder> {
    public RechargeCoinAdapter(@Nullable List<WalletMainResponse.VipItem> data) {
        super(R.layout.item_recharge_coin);
    }
    public void setChooseItem(int pos){
        for (int i = 0; i < getData().size(); i++) {
            getItem(i).setSelected(false);
        }
        getItem(pos).setSelected(true);
        notifyDataSetChanged();
    }
    @Override
    protected void convert(BaseViewHolder helper, WalletMainResponse.VipItem item) {
        helper.setText(R.id.tvNum1,item.getCoins());
        helper.setText(R.id.tvNum2,item.getBuy_notice());
        helper.setText(R.id.tvNum3,"￥ "+item.getPrice());
        if (item.isSelected()){
            helper.setGone(R.id.ivBorder,true);
        }else {
            helper.setGone(R.id.ivBorder,false);
        }
    }
}
