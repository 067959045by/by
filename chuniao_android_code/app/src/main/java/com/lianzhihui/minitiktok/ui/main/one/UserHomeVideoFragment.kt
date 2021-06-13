package com.lianzhihui.minitiktok.ui.main.one

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.echofeng.common.ui.base.BaseFragment
import com.echofeng.common.ui.widget.layout.ZFEmptyRecyclerView
import com.lianzhihui.minitiktok.adapter.user.UserHomeVideoAdapter
import com.lianzhihui.minitiktok.bean.hot.VideoResponse
import com.lianzhihui.minitiktok.presenter.UserVideoPresnterImp
import com.lianzhihui.minitiktok.view.UserVideoView
import com.lianzhihui.onlyleague.R
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.fragment_gold_one.*
import kotlinx.android.synthetic.main.fragment_user_home_video.mRecyclerView
import kotlinx.android.synthetic.main.fragment_video_user.*
import kotlinx.android.synthetic.main.fragment_video_user.mSmartRefreshLayout

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
/**
 * A simple [Fragment] subclass.
 * Use the [UserHomeVideoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserHomeVideoFragment : BaseFragment() ,UserVideoView,BaseQuickAdapter.OnItemClickListener,OnRefreshLoadMoreListener{
    private var videoList: ArrayList<VideoResponse>? = null
    private lateinit var userVideoPresnterImp: UserVideoPresnterImp
    private lateinit var adapter: UserHomeVideoAdapter
    private var colomNum: Int = 1
    private lateinit var userId: String
    companion object {
        @JvmStatic
        fun newInstance(param1: Int,param2: String) =
                UserHomeVideoFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            colomNum = it.getInt(ARG_PARAM1)
            userId = it.getString(ARG_PARAM2).toString()
        }
    }
    override fun getLayoutId(): Int {
        return R.layout.fragment_video_user
    }

    override fun initView() {
        mSmartRefreshLayout.setOnRefreshLoadMoreListener(this)
        adapter = UserHomeVideoAdapter(ArrayList())
        val gridLayoutManager = GridLayoutManager(context, 3)
        val decorationH = DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL)
        val decorationV = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        mRecyclerView.addItemDecoration(decorationH)
        mRecyclerView.addItemDecoration(decorationV)
        mRecyclerView.setLayoutManager(gridLayoutManager)
        mRecyclerView.setAdapter(adapter)
        adapter.setOnItemClickListener(this)
        adapter.emptyView = ZFEmptyRecyclerView(context)
    }
    override fun initData() {
        userVideoPresnterImp = UserVideoPresnterImp(context,this);
        if(colomNum==1) {
            userVideoPresnterImp.getOwnVideo(userId,pageNum)
        }
    }

    override fun onFragmentVisible() {
        super.onFragmentVisible()
        if (!loadFinish){
            loadData()
            loadFinish=true
        }
    }
    private fun loadData(){
        when(colomNum){
            1->{     userVideoPresnterImp.getOwnVideo(userId,pageNum)}
            2->{     userVideoPresnterImp.getLikeVideo(userId,pageNum)}
            3->{     userVideoPresnterImp.getBuyVideo(pageNum,userId)}
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

    override fun setMineVideoSuccess(data: MutableList<VideoResponse>?) {
        if (mSmartRefreshLayout!=null) {
            mSmartRefreshLayout.finishRefresh()
            mSmartRefreshLayout.finishLoadMore()
        }
        if (pageNum==1) {
            videoList = data as ArrayList<VideoResponse>?
            adapter.setNewData(data)
        }else{
            if (data != null) {
                adapter.addData(data)
            }
        }
    }

    override fun setLikeVideoSuccess(data: MutableList<VideoResponse>?) {
        if (mSmartRefreshLayout!=null) {
            mSmartRefreshLayout.finishRefresh()
            mSmartRefreshLayout.finishLoadMore()
        }
        if (pageNum==1) {
            videoList = data as ArrayList<VideoResponse>?
            adapter.setNewData(data)
        }else{
            if (data != null) {
                adapter.addData(data)
            }
        }
    }

    override fun setBuyVideoSuccess(data: MutableList<VideoResponse>?) {
        if (mSmartRefreshLayout!=null) {
            mSmartRefreshLayout.finishRefresh()
            mSmartRefreshLayout.finishLoadMore()
        }
        if (pageNum==1) {
            videoList = data as ArrayList<VideoResponse>?
            adapter.setNewData(data)
        }else{
            if (data != null) {
                adapter.addData(data)
            }
        }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        var intent = Intent(context,PlayListActivity::class.java)
        intent.putExtra("currentPosition",position)
        intent.putExtra("videoList",videoList)
        startActivity(intent)
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        pageNum==1
        loadData()
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        pageNum+=1
        loadData()
    }

}