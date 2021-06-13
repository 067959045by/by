package com.echofeng.common.ui.widget.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.echofeng.common.R;
import com.echofeng.common.ui.widget.view.viewpagerlayoutmanager.ViewPagerLayoutManager;

/**
 * Copyright (C), 2019-2020, 成都链智慧科技有限公司
 * FileName: ZFEmptyView
 * Author: echo
 * Date: 2020/9/14 14:52
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class ZFEmptyRecyclerView extends RelativeLayout {
    private ImageView iconView;
    private TextView titleView;
    private View emptyView;
    private RecyclerView mRecyclerView;

    public ZFEmptyRecyclerView(Context context) {
        super(context);
        initView(context);
    }

    public ZFEmptyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ZFEmptyRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public ZFEmptyRecyclerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    public RecyclerView getmRecyclerView() {
        return mRecyclerView;
    }

    private void initView(Context context) {
        View root = LayoutInflater.from(context).inflate(R.layout.layout_empty_view, this, true);
        iconView = root.findViewById(R.id.iv_icon);
        titleView = (TextView) root.findViewById(R.id.tv_content);
        emptyView = (View) root.findViewById(R.id.emptyView);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.zfRecyclerView);
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        if (mRecyclerView!=null) {
            mRecyclerView.setLayoutManager(layoutManager);
        }
    }
    public void setLayoutManager(ViewPagerLayoutManager layoutManager) {
        if (mRecyclerView!=null) {
            mRecyclerView.setLayoutManager(layoutManager);
        }
    }
    public void scrollToPosition(int position) {
        if (mRecyclerView!=null) {
            mRecyclerView.scrollToPosition(position);
        }
    }
    public void addOnChildAttachStateChangeListener(
            @NonNull RecyclerView.OnChildAttachStateChangeListener listener) {
        if (mRecyclerView!=null) {
            mRecyclerView.addOnChildAttachStateChangeListener(listener);
        }

    }
    public void setAdapter(BaseQuickAdapter adapter) {
        if (mRecyclerView!=null) {
            mRecyclerView.setAdapter(adapter);
        }
    }

    public void setOpenState(boolean isOpen){
        if (!isOpen) {
            emptyView.setVisibility(VISIBLE);
            mRecyclerView.setVisibility(GONE);
//            iconView.setImageResource(R.mipmap.icon_not_open);
//            titleView.setText(getContext().getText(R.string.text_not_open));
        } else {
            emptyView.setVisibility(GONE);
            mRecyclerView.setVisibility(VISIBLE);
        }
    }
    /**
     * @param dataSize 列表数据长度
     * @param pageNum  当前页码
     */
    public void showEmptyView(@NonNull int dataSize, @NonNull int pageNum) {

        if (pageNum == 1) {
            if (dataSize == 0) {
                emptyView.setVisibility(VISIBLE);
                mRecyclerView.setVisibility(GONE);
            } else {
                emptyView.setVisibility(GONE);
                mRecyclerView.setVisibility(VISIBLE);
            }
        } else {
            emptyView.setVisibility(GONE);
            mRecyclerView.setVisibility(VISIBLE);
        }
    }

    /**
     * @param dataSize 列表数据长度
     * @param pageNum  当前页码
     * @param content  内容
     */
    public void showEmptyView(@NonNull int dataSize, @NonNull int pageNum, String content) {
        titleView.setText(content);
        if (pageNum == 1) {
            if (dataSize == 0) {
                emptyView.setVisibility(VISIBLE);
                mRecyclerView.setVisibility(GONE);
            } else {
                emptyView.setVisibility(GONE);
                mRecyclerView.setVisibility(VISIBLE);
            }
        } else {
            emptyView.setVisibility(GONE);
            mRecyclerView.setVisibility(VISIBLE);
        }
    }

    /**
     * @param dataSize 列表数据长度
     * @param pageNum  当前页码
     * @param icon     logo
     * @param content  内容
     */
    public void showEmptyView(@NonNull int dataSize, @NonNull int pageNum, int icon, String content) {
        iconView.setImageResource(icon);
        titleView.setText(content);
        if (pageNum == 1) {
            if (dataSize == 0) {
                emptyView.setVisibility(VISIBLE);
                mRecyclerView.setVisibility(GONE);
            } else {
                emptyView.setVisibility(GONE);
                mRecyclerView.setVisibility(VISIBLE);
            }
        } else {
            emptyView.setVisibility(GONE);
            mRecyclerView.setVisibility(VISIBLE);
        }
    }
}
