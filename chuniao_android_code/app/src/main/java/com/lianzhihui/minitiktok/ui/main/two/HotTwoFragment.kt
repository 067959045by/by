package com.lianzhihui.minitiktok.ui.main.two

import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.echofeng.common.ui.base.BaseFragment
import com.echofeng.common.utils.SystemUtils
import com.lianzhihui.minitiktok.adapter.BannerImageAdapter
import com.lianzhihui.minitiktok.adapter.hot.HotTwoNiceItemAdapter
import com.lianzhihui.minitiktok.adapter.hot.HotTwoTopAdapter
import com.lianzhihui.minitiktok.bean.AdResponse
import com.lianzhihui.minitiktok.bean.hot.MostHotResponse
import com.lianzhihui.minitiktok.presenter.AdPresnterImp
import com.lianzhihui.minitiktok.presenter.HotHomePresnterImp
import com.lianzhihui.minitiktok.view.AdView
import com.lianzhihui.minitiktok.view.HotHomeView
import com.lianzhihui.onlyleague.R
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.listener.OnBannerListener
import kotlinx.android.synthetic.main.fragment_gold_one.*
import kotlinx.android.synthetic.main.fragment_home_three.*
import kotlinx.android.synthetic.main.fragment_hot_three.*
import kotlinx.android.synthetic.main.fragment_hot_three.mRecyclerView
import kotlinx.android.synthetic.main.fragment_hot_two.*
import kotlinx.android.synthetic.main.fragment_hot_two.mBanner
import kotlinx.android.synthetic.main.fragment_hot_two.mSmartRefreshLayout

/**
 * A simple [Fragment] subclass.
 * Use the [HotTwoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HotTwoFragment : BaseFragment() , AdView, HotHomeView,BaseQuickAdapter.OnItemClickListener,OnRefreshListener {

    private lateinit var adPresnterImp: AdPresnterImp
    private lateinit var hotHomePresnterImp: HotHomePresnterImp
    private lateinit var adapterTop: HotTwoTopAdapter
    private lateinit var adapterNice: HotTwoNiceItemAdapter

    override fun getLayoutId(): Int {
        return R.layout.fragment_hot_two
    }

    override fun initView() {
        mSmartRefreshLayout.setOnRefreshListener(this)
        adapterTop = HotTwoTopAdapter(ArrayList())
        adapterNice = HotTwoNiceItemAdapter(context,ArrayList())
        val gridLayoutManager = GridLayoutManager(context, 3)
        val decoration = DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL)
        mRecyclerView1.addItemDecoration(decoration)
        mRecyclerView1.setLayoutManager(gridLayoutManager)
        mRecyclerView1.setAdapter(adapterTop)

        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        mRecyclerView2.setLayoutManager(linearLayoutManager)
        mRecyclerView2.setAdapter(adapterNice)
        adapterNice.setOnItemClickListener(this)
        mBanner.setOnBannerListener { any, position ->
            var adBean = any as AdResponse
            SystemUtils.openBrowser(context,adBean?.link)
        }
//        mBanner.setOnBannerListener(OnBannerListener<Any?> { data, position ->
//
//        } as Nothing)
    }

    override fun initData() {
        adPresnterImp = AdPresnterImp(context, this)
        hotHomePresnterImp = HotHomePresnterImp(context, this)
    }
    override fun onFragmentVisible() {
        super.onFragmentVisible()
        if (!loadFinish){
            adPresnterImp.getAdList("hot_top")
            hotHomePresnterImp.getMostHotHome()
            loadFinish = true
        }
    }
    override fun onRefresh(refreshLayout: RefreshLayout) {
        hotHomePresnterImp.getMostHotHome()
    }

    override fun setSuccess(o: Any?) {
    }

    override fun setFailure(o: Any?) {
        if (mSmartRefreshLayout!=null) {
            mSmartRefreshLayout.finishRefresh()
            mSmartRefreshLayout.finishLoadMore()
        }
    }

    override fun setHotHomeSuccess(data: MostHotResponse?) {
        if (mSmartRefreshLayout!=null) {
            mSmartRefreshLayout.finishRefresh()
            mSmartRefreshLayout.finishLoadMore()
        }
        adapterTop.setNewData(data!!.hot_video)
        adapterNice.setNewData(data.perfect_list)
    }

    override fun setAdSuccess(data: List<AdResponse>?) {
        if (data!!.size==0){
            return
        }
        mBanner.addBannerLifecycleObserver(this)//添加生命周期观察者
                .setAdapter(BannerImageAdapter(context,data))
                .setIndicator(CircleIndicator(context));
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
    }
}