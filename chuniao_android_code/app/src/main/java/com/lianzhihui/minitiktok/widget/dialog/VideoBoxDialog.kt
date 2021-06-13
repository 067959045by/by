package com.lianzhihui.minitiktok.widget.dialog

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.View
import android.widget.VideoView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.echofeng.common.ui.base.BaseDialog
import com.lianzhihui.minitiktok.adapter.hot.VideoBoxAdapter
import com.lianzhihui.minitiktok.adapter.user.UserHomeVideoAdapter
import com.lianzhihui.minitiktok.bean.hot.AlbumVideoResponse
import com.lianzhihui.minitiktok.bean.hot.HotClassResponse
import com.lianzhihui.minitiktok.bean.hot.VideoResponse
import com.lianzhihui.minitiktok.model.HomeModelImp
import com.lianzhihui.minitiktok.presenter.HomePresnterImp
import com.lianzhihui.minitiktok.ui.main.one.PlayListActivity
import com.lianzhihui.minitiktok.view.HomeView
import com.lianzhihui.onlyleague.R
import kotlinx.android.synthetic.main.dialog_video_box.*
import kotlinx.android.synthetic.main.dialog_video_box.mRecyclerView
import kotlinx.android.synthetic.main.fragment_user_home_video.*

class VideoBoxDialog(context: Context) : BaseDialog(context) ,HomeView,View.OnClickListener,BaseQuickAdapter.OnItemClickListener{
    private var videoList: ArrayList<VideoResponse>? = null
    private lateinit var homePresnterImp: HomePresnterImp
    private lateinit var adapter: VideoBoxAdapter

    override fun provideViewId(): Int {
        return R.layout.dialog_video_box
    }

    override fun initView() {
        iv_close.setOnClickListener(this)
        fullWidth()
        bottom()
        adapter = VideoBoxAdapter(ArrayList())
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = RecyclerView.VERTICAL
        mRecyclerView.setLayoutManager(layoutManager)
        mRecyclerView.setAdapter(adapter)
        adapter.setOnItemClickListener(this)
        homePresnterImp = HomePresnterImp(context,this)
    }

    fun setId(id:String){
        homePresnterImp.getBoxVideo(id)
    }

    override fun setSuccess(o: Any?) {
    }

    override fun setFailure(o: Any?) {
    }

    override fun setVideoRecommendSuccess(data: MutableList<VideoResponse>?) {
    }

    override fun setSearchVideoSuccess(data: MutableList<VideoResponse>?) {
    }

    override fun setBoxVideoSuccess(data: AlbumVideoResponse?) {
        videoList = data!!.video_list as ArrayList<VideoResponse>
        if (!TextUtils.isEmpty(data!!.album_info.album_name)){
            tvTitle.setText(data!!.album_info.album_name)
        }
        adapter.setNewData(data!!.video_list)
    }

    override fun setHotClassSuccess(data: MutableList<HotClassResponse>?) {
    }

    override fun setCheckVideoSuccess(data: VideoResponse?) {
    }

    override fun setBuyVideoSuccess(data: Any?) {
    }

    override fun onClick(p0: View?) {
        dismiss()
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        var intent = Intent(context, PlayListActivity::class.java)
        intent.putExtra("currentPosition",position)
        intent.putExtra("videoList",videoList)
        context.startActivity(intent)
        dismiss()
    }
}