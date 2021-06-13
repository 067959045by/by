package com.lianzhihui.minitiktok.ui.main.two

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.echofeng.common.ui.base.BaseActivity
import com.google.android.material.tabs.TabLayout
import com.lianzhihui.minitiktok.adapter.MyFragmentAdapter
import com.lianzhihui.minitiktok.adapter.hot.GoldOneAdapter
import com.lianzhihui.minitiktok.bean.hot.AlbumVideoResponse
import com.lianzhihui.minitiktok.bean.hot.HotClassResponse
import com.lianzhihui.minitiktok.bean.hot.VideoResponse
import com.lianzhihui.minitiktok.presenter.HomePresnterImp
import com.lianzhihui.minitiktok.ui.main.one.PlayListActivity
import com.lianzhihui.minitiktok.view.HomeView
import com.lianzhihui.onlyleague.R
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.activity_column.*
import kotlinx.android.synthetic.main.activity_column.mRecyclerView
import kotlinx.android.synthetic.main.activity_column.mSmartRefreshLayout
import kotlinx.android.synthetic.main.fragment_gold_one.*
import java.util.ArrayList

class ColumnActivity : BaseActivity() , HomeView,OnRefreshLoadMoreListener,BaseQuickAdapter.OnItemClickListener{
    private lateinit var classItem: HotClassResponse
    private var videoList: ArrayList<VideoResponse>? = null
    private lateinit var homePresnterImp: HomePresnterImp
    private lateinit var adapter: GoldOneAdapter

    override fun getLayoutId(): Int {
        return R.layout.activity_column
    }

    override fun initView() {
        classItem = intent.getSerializableExtra("classItem") as HotClassResponse
        setTitle(classItem.title)
        mSmartRefreshLayout.setOnRefreshLoadMoreListener(this)
        adapter = GoldOneAdapter(ArrayList())
        val gridLayoutManager = GridLayoutManager(context,2)
        mRecyclerView.setLayoutManager(gridLayoutManager)
        mRecyclerView.setAdapter(adapter)
        mSmartRefreshLayout.setOnRefreshLoadMoreListener(this)
        adapter.setOnItemClickListener(this)
        homePresnterImp = HomePresnterImp(context,this);
        homePresnterImp.getClassVideo(pageNum,classItem.id)
    }


    override fun setSuccess(o: Any?) {
    }

    override fun setFailure(o: Any?) {
        if (mSmartRefreshLayout!=null) {
            mSmartRefreshLayout.finishRefresh()
            mSmartRefreshLayout.finishLoadMore()
        }
    }

    override fun setVideoRecommendSuccess(data: MutableList<VideoResponse>?) {
    }

    override fun setSearchVideoSuccess(data: MutableList<VideoResponse>?) {
        if (mSmartRefreshLayout!=null) {
            mSmartRefreshLayout.finishRefresh()
            mSmartRefreshLayout.finishLoadMore()
        }
        if (pageNum==1) {
            videoList = data as ArrayList<VideoResponse>?
            adapter.setNewData(data)
        }else{
            adapter.addData(data as List<VideoResponse>)
        }
    }

    override fun setBoxVideoSuccess(data: AlbumVideoResponse?) {
    }

    override fun setHotClassSuccess(data: MutableList<HotClassResponse>?) {
    }

    override fun setCheckVideoSuccess(data: VideoResponse?) {
    }

    override fun setBuyVideoSuccess(data: Any?) {
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        pageNum=1
        homePresnterImp.getClassVideo(pageNum,classItem.id)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        pageNum+=1
        homePresnterImp.getClassVideo(pageNum,classItem.id)
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        var intent = Intent(context, PlayListActivity::class.java)
        intent.putExtra("currentPosition",position)
        intent.putExtra("videoList",videoList)
        startActivity(intent)
    }
}