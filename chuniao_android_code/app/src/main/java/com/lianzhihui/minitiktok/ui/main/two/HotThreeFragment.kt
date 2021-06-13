package com.lianzhihui.minitiktok.ui.main.two

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.echofeng.common.ui.base.BaseFragment
import com.lianzhihui.minitiktok.adapter.hot.HotThreeAdapter
import com.lianzhihui.minitiktok.bean.hot.AlbumVideoResponse
import com.lianzhihui.minitiktok.bean.hot.HotClassResponse
import com.lianzhihui.minitiktok.bean.hot.VideoResponse
import com.lianzhihui.minitiktok.presenter.HomePresnterImp
import com.lianzhihui.minitiktok.view.HomeView
import com.lianzhihui.onlyleague.R
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.fragment_gold_one.*
import kotlinx.android.synthetic.main.fragment_hot_three.*
import kotlinx.android.synthetic.main.fragment_hot_three.mRecyclerView
import kotlinx.android.synthetic.main.fragment_hot_three.mSmartRefreshLayout

/**
 * A simple [Fragment] subclass.
 * Use the [HotThreeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HotThreeFragment : BaseFragment() ,HomeView,View.OnClickListener,BaseQuickAdapter.OnItemClickListener,OnRefreshListener{

    private lateinit var homePresnterImp: HomePresnterImp
    private lateinit var adapter: HotThreeAdapter

    override fun getLayoutId(): Int {
        return R.layout.fragment_hot_three
    }

    override fun initView() {
        mSmartRefreshLayout.setOnRefreshListener(this)
        adapter = HotThreeAdapter(ArrayList())
        val gridLayoutManager = GridLayoutManager(context,2)
        mRecyclerView.setLayoutManager(gridLayoutManager)
        mRecyclerView.setAdapter(adapter)

        iv_gold.setOnClickListener(this)
        adapter.setOnItemClickListener(this)
    }

    override fun initData() {
        homePresnterImp = HomePresnterImp(context,this)
    }

    override fun onFragmentVisible() {
        super.onFragmentVisible()
        if (!loadFinish){
            homePresnterImp.getHotClass()
            loadFinish = true;
        }
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
    }

    override fun setBoxVideoSuccess(data: AlbumVideoResponse?) {
    }

    override fun setHotClassSuccess(data: MutableList<HotClassResponse>?) {
        if (mSmartRefreshLayout!=null) {
            mSmartRefreshLayout.finishRefresh()
            mSmartRefreshLayout.finishLoadMore()
        }
        adapter.setNewData(data)
    }

    override fun setCheckVideoSuccess(data: VideoResponse?) {
    }

    override fun setBuyVideoSuccess(data: Any?) {
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        homePresnterImp.getHotClass()
    }
    override fun onClick(v: View?) {
        if (v != null) {
            when(v.id){
                R.id.iv_gold ->{
                    startActivity(Intent(context,GoldColumnActivity::class.java))
                }
            }
        }
    }
    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        var intent = Intent(context,ColumnActivity::class.java)
        var classItem = adapter!!.getItem(position) as HotClassResponse
        intent.putExtra("classItem", classItem)
        startActivity(intent)
    }

}