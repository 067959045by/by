package com.lianzhihui.minitiktok.ui.main.one

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.VideoView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView.OnChildAttachStateChangeListener
import cn.jzvd.Jzvd
import com.bytedance.tiktok.utils.OnVideoControllerListener
import com.echofeng.common.config.AppConstants
import com.echofeng.common.config.MessageEvent
import com.echofeng.common.inter.CustomCallback
import com.echofeng.common.ui.base.BaseFragment
import com.echofeng.common.ui.widget.view.LikeView
import com.echofeng.common.ui.widget.view.jzplayer.JzvdStdTikTok
import com.echofeng.common.ui.widget.view.viewpagerlayoutmanager.OnViewPagerListener
import com.echofeng.common.ui.widget.view.viewpagerlayoutmanager.ViewPagerLayoutManager
import com.lianzhihui.minitiktok.adapter.MainVideoAdapter
import com.lianzhihui.minitiktok.adapter.VideoAdapter
import com.lianzhihui.minitiktok.bean.hot.AlbumVideoResponse
import com.lianzhihui.minitiktok.bean.hot.HotClassResponse
import com.lianzhihui.minitiktok.bean.hot.VideoResponse
import com.lianzhihui.minitiktok.presenter.HomePresnterImp
import com.lianzhihui.minitiktok.ui.main.four.WalletActivity
import com.lianzhihui.minitiktok.view.HomeView
import com.lianzhihui.minitiktok.widget.dialog.*
import com.lianzhihui.minitiktok.widget.view.ControllerView
import com.lianzhihui.onlyleague.R
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.fragment_gold_one.*
import kotlinx.android.synthetic.main.fragment_video_page.*
import kotlinx.android.synthetic.main.fragment_video_page.mSmartRefreshLayout
import kotlinx.android.synthetic.main.item_hot_two_pay_nice.view.*
import kotlinx.android.synthetic.main.view_controller.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 * Use the [VideoPageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class VideoPageFragment : BaseFragment(),HomeView,OnViewPagerListener,OnRefreshLoadMoreListener{

    /** 将要播放视频位置  */
    private var mCheckPosition: Int = 0
    private val initPos: Int = 0
    private var player: JzvdStdTikTok? = null
    private lateinit var homePresnterImp: HomePresnterImp
    private var adapter: MainVideoAdapter? = null
    private var checkVideoDialog: CustomConfirmDialog? = null
    private var viewPagerLayoutManager: ViewPagerLayoutManager? = null


    // TODO: Rename and change types of parameters
    private var isHot: Boolean = false
    companion object {
        @JvmStatic
        fun newInstance(param1: Boolean) =
                VideoPageFragment().apply {
                    arguments = Bundle().apply {
                        putBoolean(ARG_PARAM1, param1)
                    }
                }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            isHot = it.getBoolean(ARG_PARAM1)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_video_page
    }

    override fun initView() {
        mSmartRefreshLayout!!.setOnRefreshLoadMoreListener(this)
        adapter = MainVideoAdapter(ArrayList())
        viewPagerLayoutManager = ViewPagerLayoutManager(activity)
        recyclerView!!.setAdapter(adapter)
        recyclerView!!.setLayoutManager(viewPagerLayoutManager)
        recyclerView!!.scrollToPosition(initPos)
        viewPagerLayoutManager!!.setOnViewPagerListener(this)
        recyclerView.addOnChildAttachStateChangeListener(object : OnChildAttachStateChangeListener {
            override fun onChildViewAttachedToWindow(view: View) {}
            override fun onChildViewDetachedFromWindow(view: View) {
                val jzvd: Jzvd = view.findViewById(R.id.videoPlayer)
                if (jzvd != null && Jzvd.CURRENT_JZVD != null && jzvd.jzDataSource != null &&
                        jzvd.jzDataSource.containsTheUrl(Jzvd.CURRENT_JZVD.jzDataSource.currentUrl)) {
                    if (Jzvd.CURRENT_JZVD != null && Jzvd.CURRENT_JZVD.screen != Jzvd.SCREEN_FULLSCREEN) {
                        Jzvd.releaseAllVideos()
                    }
                }
            }
        })
    }

    override fun initData() {
        homePresnterImp = HomePresnterImp(context, this)
        if (isHot) {
            homePresnterImp.getRecommendVideo(pageNum)
        }
    }
    override fun onFragmentVisible() {
        super.onFragmentVisible()
        if (!loadFinish){
            if (isHot) {
                homePresnterImp.getRecommendVideo(pageNum)
            }
            loadFinish = true;
        }
    }
    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos()
    }
    override fun onInitComplete() {
        //自动播放第一条
        var videoData = adapter?.getItem(0)
        if (videoData != null) {
            autoPlayVideo(0,videoData.is_ad)
        }
        homePresnterImp.doVideoCheck(videoData?.id)
    }

    override fun onPageRelease(isNext: Boolean, position: Int) {
        if (0 == position) {
            Jzvd.releaseAllVideos()
        }
    }

    override fun onPageSelected(position: Int, isBottom: Boolean) {
        checkVideoDialog?.dismiss()
        mCheckPosition = position
        var data = adapter?.getItem(mCheckPosition)
        if (data?.is_ad==0) {
            showLoading("")
            homePresnterImp.doVideoCheck(adapter?.getItem(mCheckPosition)?.id)
        }
        if (data != null) {
            autoPlayVideo(mCheckPosition, data.is_ad)
        }
    }
    private fun autoPlayVideo(position: Int,isAd:Int) {
        if (recyclerView == null || recyclerView.getChildAt(0) == null) {
            return
        }
        val itemView = viewPagerLayoutManager!!.findViewByPosition(position) ?: return
        val rootView = itemView.findViewById<ViewGroup>(R.id.rl_container)
        val likeView: LikeView = rootView.findViewById(R.id.mLikeView)
        val controllerView: ControllerView = rootView.findViewById(R.id.mControllerView)
        player = recyclerView.getChildAt(0).findViewById(R.id.videoPlayer)
        if (isAd==1){
            if (player != null) {
                player!!.startVideoAfterPreloading()
            }
        }
        //播放暂停事件
        likeView.setOnPlayPauseListener(object : LikeView.OnPlayPauseListener {
            override fun onPlayOrPause() {
                if (player!!.state == Jzvd.STATE_PLAYING) {
                    player!!.state = Jzvd.STATE_PAUSE
                    player!!.mediaInterface.pause()
                    player!!.updateStartImage()
                } else {
                    var currentVideo = adapter?.getItem(mCheckPosition)
                    if (currentVideo?.isCanPlay == true){
                        player!!.state = Jzvd.STATE_PLAYING
                        player!!.mediaInterface.start()
                        player!!.updateStartImage()
                    }else{
                        if (currentVideo?.is_ad==0) {
                            homePresnterImp.doVideoCheck(adapter?.getItem(mCheckPosition)?.id)
                        }else{
                            player!!.state = Jzvd.STATE_PLAYING
                            player!!.mediaInterface.start()
                            player!!.updateStartImage()
                        }
                    }
                }
            }
        })
        //评论点赞事件
        likeShareEvent(controllerView)
    }
    /**
     * 用户操作事件
     */
    private fun likeShareEvent(controllerView: ControllerView) {
        controllerView.setListener(object : OnVideoControllerListener {
            override fun onHeadClick() {
                var videoData = controllerView.getVideoData()
                var intent = Intent(context, UserCenterActivity::class.java)
                intent.putExtra("userId", videoData!!.user.code)
                startActivity(intent)
            }

            override fun onLikeClick() {
                var videoData = controllerView.getVideoData()
                if (videoData?.is_like==1) {
                    var likeCount = (videoData?.like).toString()
                    controllerView.tvLikecount.setText(likeCount)
                }else{
                    var likeCount = (videoData?.like?.plus(1)!!).toString()
                    controllerView.tvLikecount.setText(likeCount)
                }
                homePresnterImp.doVideoLike(videoData!!.id)
            }

            override fun onCommentClick() {
                CommentDialog(context!!).show()
            }

            override fun onShareClick() {
                var videoData = controllerView.getVideoData()
                var shareVideoDialog = ShareVideoDialog(context!!)
                shareVideoDialog.show()
                shareVideoDialog.setVideoData(videoData!!)
            }

            override fun onBoxClick() {
                var videoData = controllerView.getVideoData()
                var videoBoxDialog = VideoBoxDialog(context!!)
                videoBoxDialog.show()
                videoBoxDialog.setId(videoData!!.album_id)
            }

            override fun onFocusClick() {
                var videoData = controllerView.getVideoData()
                homePresnterImp.doAttintion(videoData!!.user.code);
            }

            override fun onFullVideoClick() {
                var videoList = ArrayList<VideoResponse>()
                var videoData = adapter?.getItem(mCheckPosition)
                videoList.add(videoData!!)
                var fullUrl = videoData?.mu
                if (!TextUtils.isEmpty(fullUrl)){
                    var intent = Intent(context, PlayListActivity::class.java)
                    intent.putExtra("currentPosition",0)
                    intent.putExtra("videoList",videoList)
                    context?.startActivity(intent)
                }else{
                    dialogNeedVip();
                }
            }
        })
    }

    private fun dialogNeedVip(){
        var dialog = context?.let { CustomConfirmDialog(it,object : CustomCallback{
            override fun onCompare(o: Any?) {
                startActivity(Intent(context,WalletActivity::class.java))
            }

            override fun onCancel() {
            }
        }) }
        dialog?.setText("温馨提示","完整版只针对VIP会员开放","取消","去开通VIP")
        dialog?.show()
    }
    override fun setSuccess(o: Any?) {
    }

    override fun setFailure(o: Any?) {
        dismissLoading()
        if (mSmartRefreshLayout!=null) {
            mSmartRefreshLayout.finishRefresh()
            mSmartRefreshLayout.finishLoadMore()
        }
    }

    override fun setVideoRecommendSuccess(data: MutableList<VideoResponse>?) {
        if (mSmartRefreshLayout!=null) {
            mSmartRefreshLayout.finishRefresh()
            mSmartRefreshLayout.finishLoadMore()
        }
        if (pageNum==1) {
            adapter!!.setNewData(data)
        }else{
            adapter!!.addData(data as ArrayList)
            if (data.size==0){
                pageNum-=1
            }
        }
    }

    override fun setSearchVideoSuccess(data: MutableList<VideoResponse>?) {
    }

    override fun setBoxVideoSuccess(data: AlbumVideoResponse?) {
    }

    override fun setHotClassSuccess(data: MutableList<HotClassResponse>?) {
    }

    override fun setCheckVideoSuccess(data: VideoResponse?) {
        dismissLoading()
//        1001=次数已用完，开通VIP享不限次观,
//        1002=金币视频查看完整版需支付金币,
//        1003=粉丝视频仅限粉丝观看
        if (data?.auth_error==null) {
            adapter?.getItem(mCheckPosition)?.mu = data?.mu
            adapter?.getItem(mCheckPosition)?.isCanPlay = true
            if (player != null) {
                player!!.startVideoAfterPreloading()
            }
        }else{
            adapter?.getItem(mCheckPosition)?.isCanPlay = false
            when(data.auth_error.key){
                1001->{
                    checkVideoDialog = context?.let { CustomConfirmDialog(it,object : CustomCallback{
                        override fun onCompare(o: Any?) {
                            checkVideoDialog?.dismiss()
                            startActivity(Intent(context,WalletActivity::class.java))
                        }

                        override fun onCancel() {
                            var shareVideoDialog = ShareVideoDialog(context!!)
                            shareVideoDialog.show()
                            shareVideoDialog.setVideoData(data!!)
                        }
                    }) }
                    checkVideoDialog?.setText("温馨提示",data.auth_error.info,"分享推广","充值vip")
                    checkVideoDialog?.show()
                }
                1002->{
                    checkVideoDialog = context?.let { CustomConfirmDialog(it,object : CustomCallback{
                        override fun onCompare(o: Any?) {
                            checkVideoDialog?.dismiss()
                            startActivity(Intent(context,WalletActivity::class.java))
                        }

                        override fun onCancel() {
                            checkVideoDialog?.dismiss()
                            showLoading("正在购买视频")
                            homePresnterImp.doBuyVideo(data.id)
                        }
                    }) }
                    checkVideoDialog?.setText("温馨提示",data.auth_error.info,"购买影片","立即充值")
                    checkVideoDialog?.show()
                }
                1003->{
                    var customTip = context?.let { CustomTipDialog(it) }
                    customTip?.setText("温馨提示",data.auth_error.info,"我知道了")
                    customTip?.show()
                }
            }
        }
    }

    override fun setBuyVideoSuccess(data: Any?) {
        dismissLoading()
        checkVideoDialog?.dismiss()
        checkVideoDialog==null
        if (player != null) {
            player!!.startVideoAfterPreloading()
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        pageNum=1
        if (isHot) {
            homePresnterImp.getRecommendVideo(pageNum)
        }else{
            homePresnterImp.getFollowVideo(pageNum)
        }
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        pageNum+=1
        if (isHot) {
            homePresnterImp.getRecommendVideo(pageNum)
        }else{
            homePresnterImp.getFollowVideo(pageNum)
        }
    }

    override fun onMessageEvent(event: MessageEvent?) {
        super.onMessageEvent(event)
        when(event!!.code){
            AppConstants.Event.UPDATE_STATE_PLAY_ONE->{
                if (player!=null&& adapter?.getItem(mCheckPosition)?.isCanPlay == true) {
                    player!!.state = Jzvd.STATE_PLAYING
                    player!!.startVideoAfterPreloading()
                    player!!.updateStartImage()
                }
            }
            AppConstants.Event.UPDATE_STATE_PURSE_ONE->{
                if (player!=null) {
                    player!!.state = Jzvd.STATE_PAUSE
                    player!!.mediaInterface.pause()
                    player!!.updateStartImage()
                }
            }
            AppConstants.Event.REFRESH_HOME_ATINTION->{
                mSmartRefreshLayout.autoRefresh()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (player!=null&& adapter?.getItem(mCheckPosition)?.isCanPlay == true) {
            if (isVisible) {
                player!!.state = Jzvd.STATE_PLAYING
                player!!.startVideoAfterPreloading()
                player!!.updateStartImage()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        if (player!=null) {
            player!!.state = Jzvd.STATE_PAUSE
            try {
                player!!.mediaInterface.pause()
                player!!.updateStartImage()
            }catch (e:Exception){
                return
            }

        }
    }

}
