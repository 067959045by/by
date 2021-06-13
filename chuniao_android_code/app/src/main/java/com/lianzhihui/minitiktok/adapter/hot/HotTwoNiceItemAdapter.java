package com.lianzhihui.minitiktok.adapter.hot;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lianzhihui.minitiktok.bean.hot.MostHotResponse;
import com.lianzhihui.minitiktok.bean.hot.VideoResponse;
import com.lianzhihui.minitiktok.ui.main.one.PlayListActivity;
import com.lianzhihui.onlyleague.R;

import java.util.ArrayList;
import java.util.List;

import jnr.ffi.annotations.In;

/**
 * create by libo
 * create on 2020-05-20
 * description
 */
public class HotTwoNiceItemAdapter extends BaseQuickAdapter<MostHotResponse.Perfect, BaseViewHolder> implements BaseQuickAdapter.OnItemChildClickListener {

    public HotTwoNiceItemAdapter(Context context, List<MostHotResponse.Perfect> data) {
        super(R.layout.item_hot_two_item_nice);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MostHotResponse.Perfect item) {
        helper.setText(R.id.tvTitle,item.getTitle());
        HotTwoNiceVideoAdapter adapterNice = new HotTwoNiceVideoAdapter(new ArrayList<>());
        RecyclerView mRecyclerView = helper.getView(R.id.mRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(adapterNice);
        adapterNice.setNewData(item.getGet_perfect_video());
        adapterNice.setOnItemClickListener(this::onItemChildClick);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (getData().size()==position){
            position-=1;
        }
        ArrayList<VideoResponse> videoList = (ArrayList<VideoResponse>) getItem(position).getGet_perfect_video();
        Intent intent = new Intent(mContext, PlayListActivity.class);
        intent.putExtra("currentPosition",position);
        intent.putExtra("videoList",videoList);
        mContext.startActivity(intent);
    }
}
