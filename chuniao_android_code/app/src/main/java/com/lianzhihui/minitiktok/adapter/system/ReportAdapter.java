package com.lianzhihui.minitiktok.adapter.system;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lianzhihui.minitiktok.bean.system.ReportBean;
import com.lianzhihui.onlyleague.R;

import java.util.List;

public class ReportAdapter extends BaseQuickAdapter<ReportBean, BaseViewHolder> {
    public ReportAdapter(@Nullable List<ReportBean> data) {
        super(R.layout.item_report);
    }
    public void setChooseItem(int pos){
        for (int i = 0; i < getData().size(); i++) {
            getItem(i).setSelected(false);
        }
        getItem(pos).setSelected(true);
        notifyDataSetChanged();
    }
    @Override
    protected void convert(BaseViewHolder helper, ReportBean item) {
        helper.setText(R.id.bt_tag,item.getContent());
        if (item.isSelected()){
            helper.setBackgroundRes(R.id.bt_tag,R.drawable.shape_round_red_8dp);
        }else {
            helper.setBackgroundRes(R.id.bt_tag,R.drawable.shape_round_white_8dp);
        }
    }
}
