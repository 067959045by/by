package com.lianzhihui.minitiktok.ui.main.one

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import cn.jzvd.Jzvd
import com.bytedance.tiktok.utils.OnVideoControllerListener
import com.echofeng.common.inter.CustomCallback
import com.echofeng.common.ui.base.BaseActivity
import com.echofeng.common.ui.widget.view.LikeView
import com.echofeng.common.ui.widget.view.jzplayer.JzvdStdTikTok
import com.echofeng.common.ui.widget.view.viewpagerlayoutmanager.OnViewPagerListener
import com.echofeng.common.ui.widget.view.viewpagerlayoutmanager.ViewPagerLayoutManager
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
import kotlinx.android.synthetic.main.activity_play_list.*
import kotlinx.android.synthetic.main.activity_play_list.recyclerView
import kotlinx.android.synthetic.main.fragment_video_page.*
import kotlinx.android.synthetic.main.view_controller.view.*

class PlayListActivity : BaseActivity(), HomeView,OnViewPagerListener, SwipeRefreshLayout.OnRefreshListener  {
    /** 将要播放视频位置  */
    private var mCheckPosition: Int = 0
    private var currentPosition: Int = 0
    private lateinit var videoList: List<VideoResponse>
    private var player: JzvdStdTikTok? = null
    private var checkVideoDialog: CustomConfirmDialog? = null
    private var adapter: VideoAdapter? = null
    private var viewPagerLayoutManager: ViewPagerLayoutManager? = null
    private lateinit var homePresnterImp: HomePresnterImp
    override fun getLayoutId(): Int {
        return R.layout.activity_play_list
    }

    override fun initView() {
        currentPosition = intent.getIntExtra("currentPosition",0)
        videoList = intent.getSerializableExtra("videoList") as List<VideoResponse>
        refreshLayout!!.setOnRefreshListener(this)
        adapter = VideoAdapter(ArrayList())
        adapter!!.setNewData(videoList)
        recyclerView!!.adapter = adapter
        viewPagerLayoutManager = ViewPagerLayoutManager(this)
        recyclerView!!.layoutManager = viewPagerLayoutManager
        recyclerView!!.scrollToPosition(currentPosition)
        viewPagerLayoutManager!!.setOnViewPagerListener(this)
        recyclerView.addOnChildAttachStateChangeListener(object : RecyclerView.OnChildAttachStateChangeListener {
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
        homePresnterImp = HomePresnterImp(context,this)
    }

    override fun onRefresh() {
        pageNum=1
        object : CountDownTimer(1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                refreshLayout!!.isRefreshing = false
            }
        }.start()
    }
    override fun onInitComplete() {
        //自动播放第一条
        var videoData = adapter?.getItem(currentPosition)
        if (videoData != null) {
            autoPlayVideo(currentPosition,videoData.is_ad)
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
                var intent = Intent(mActivity, UserCenterActivity::class.java)
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
                CommentDialog(mActivity!!).show()
            }

            override fun onShareClick() {
                var videoData = controllerView.getVideoData()
                var shareVideoDialog = ShareVideoDialog(mActivity!!)
                shareVideoDialog.show()
                shareVideoDialog.setVideoData(videoData!!)
            }

            override fun onBoxClick() {
                var videoData = controllerView.getVideoData()
                var videoBoxDialog = VideoBoxDialog(mActivity!!)
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
        var dialog = mActivity?.let {
            CustomConfirmDialog(it,object : CustomCallback{
            override fun onCompare(o: Any?) {
                startActivity(Intent(mActivity,WalletActivity::class.java))
            }

            override fun onCancel() {
            }
        })
        }
        dialog?.setText("温馨提示","完整版只针对VIP会员开放","取消","去开通VIP")
        dialog?.show()
    }
    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos()
    }


    override fun setSuccess(o: Any?) {
    }

    override fun setFailure(o: Any?) {
        dismissLoading()
    }

    override fun setVideoRecommendSuccess(data: MutableList<VideoResponse>?) {
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
                    checkVideoDialog = mActivity?.let { CustomConfirmDialog(it,object : CustomCallback {
                        override fun onCompare(o: Any?) {
                            checkVideoDialog?.dismiss()
                            startActivity(Intent(mActivity, WalletActivity::class.java))
                        }

                        override fun onCancel() {
                            var shareVideoDialog = ShareVideoDialog(mActivity!!)
                            shareVideoDialog.show()
                            shareVideoDialog.setVideoData(data!!)
                        }
                    }) }
                    checkVideoDialog?.setText("温馨提示",data.auth_error.info,"分享推广","充值vip")
                    checkVideoDialog?.show()
                }
                1002->{
                    checkVideoDialog = mActivity?.let { CustomConfirmDialog(it,object : CustomCallback {
                        override fun onCompare(o: Any?) {
                            checkVideoDialog?.dismiss()
                            startActivity(Intent(mActivity, WalletActivity::class.java))
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
    override fun onDestroy() {
        super.onDestroy()
        checkVideoDialog==null
    }

}