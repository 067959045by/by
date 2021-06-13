package com.lianzhihui.minitiktok.ui.main.two

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.echofeng.common.ui.base.BaseFragment
import com.lianzhihui.minitiktok.adapter.hot.GoldOneAdapter
import com.lianzhihui.minitiktok.bean.hot.AlbumVideoResponse
import com.lianzhihui.minitiktok.bean.hot.HotClassResponse
import com.lianzhihui.minitiktok.bean.hot.VideoResponse
import com.lianzhihui.minitiktok.presenter.HomePresnterImp
import com.lianzhihui.minitiktok.ui.main.one.PlayListActivity
import com.lianzhihui.minitiktok.ui.main.one.UserHomeVideoFragment
import com.lianzhihui.minitiktok.view.HomeView
import com.lianzhihui.onlyleague.R
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.fragment_gold_one.*
import kotlinx.android.synthetic.main.fragment_gold_one.mRecyclerView
import kotlinx.android.synthetic.main.fragment_user_home_video.*
private const val ARG_PARAM1 = "param1"
/**
 * A simple [Fragment] subclass.
 * Use the [GoldOneFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GoldOneFragment : BaseFragment() ,HomeView,OnRefreshLoadMoreListener,BaseQuickAdapter.OnItemClickListener{

    private lateinit var orderBy: String
    private var videoList: ArrayList<VideoResponse>? = null
    private lateinit var homePresnterImp: HomePresnterImp
    private lateinit var adapter: GoldOneAdapter
    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
                GoldOneFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                    }
                }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            orderBy = it.getString(ARG_PARAM1).toString()
        }
    }
    override fun getLayoutId(): Int {
        return R.layout.fragment_gold_one
    }

    override fun initView() {
        adapter = GoldOneAdapter(ArrayList())
        val gridLayoutManager = GridLayoutManager(context,2)
        val decorationH = DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL)
        val decorationV = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        mRecyclerView.addItemDecoration(decorationH)
        mRecyclerView.addItemDecoration(decorationV)
        mRecyclerView.setLayoutManager(gridLayoutManager)
        mRecyclerView.setAdapter(adapter)
        mSmartRefreshLayout.setOnRefreshLoadMoreListener(this)
        adapter.setOnItemClickListener(this)
    }

    override fun initData() {
        homePresnterImp = HomePresnterImp(context,this);
    }

    override fun onResume() {
        super.onResume()
        homePresnterImp.getHotGoldVideo(pageNum,orderBy)
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
        pageNum = 1
        homePresnterImp.getHotGoldVideo(pageNum,orderBy)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        pageNum+=1
        homePresnterImp.getHotGoldVideo(pageNum,orderBy)
    }
    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        var intent = Intent(activity, PlayListActivity::class.java)
        intent.putExtra("currentPosition",position)
        intent.putExtra("videoList",videoList)
        activity?.startActivity(intent)
    }

}