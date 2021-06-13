package com.lianzhihui.minitiktok.widget.view

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Handler
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.bytedance.tiktok.utils.NumUtils
import com.bytedance.tiktok.utils.OnVideoControllerListener
import com.echofeng.common.ui.widget.view.ClickableSpanNoUnderline
import com.echofeng.common.ui.widget.view.Tag
import com.echofeng.common.ui.widget.view.TagTextView
import com.echofeng.common.utils.AESFileCrypt
import com.echofeng.common.utils.MyToast
import com.echofeng.common.utils.SystemUtils
import com.lianzhihui.minitiktok.bean.hot.HotClassResponse
import com.lianzhihui.minitiktok.bean.hot.VideoResponse
import com.lianzhihui.minitiktok.ui.main.two.ColumnActivity
import com.lianzhihui.onlyleague.R
import com.qmuiteam.qmui.span.QMUITouchableSpan
import kotlinx.android.synthetic.main.view_controller.view.*
import java.io.File


/**
 * create by libo
 * create on 2020-05-20
 * description
 */
class ControllerView(context: Context?, attrs: AttributeSet?) : RelativeLayout(context, attrs), View.OnClickListener {
    private var listener: OnVideoControllerListener? = null
    private var videoData: VideoResponse? = null

    private fun init() {
        val rootView = LayoutInflater.from(context).inflate(R.layout.view_controller, this)
        ButterKnife.bind(this, rootView)
        ivHead!!.setOnClickListener(this)
        ivComment!!.setOnClickListener(this)
        ivShare!!.setOnClickListener(this)
        rlLike!!.setOnClickListener(this)
        ivFocus!!.setOnClickListener(this)
        rlRecord!!.setOnClickListener(this)
        btFullVideo!!.setOnClickListener(this)
//        setRotateAnim()
    }

    fun setVideoData(videoData: VideoResponse) {
        this.videoData = videoData
        val simpleTarget: SimpleTarget<File?> = object : SimpleTarget<File?>() {
            override fun onResourceReady(resource: File, transition: Transition<in File?>?) {
                val decryptBytes = AESFileCrypt.decryptData(resource)
                val bitmap = BitmapFactory.decodeByteArray(decryptBytes, 0, decryptBytes.size)
                ivHead.setImageBitmap(bitmap)
            }
        }
        Glide.with(context).asFile().load(videoData.user!!.avatar).override(100,100).error(R.mipmap.ic_launcher).into(simpleTarget)
        tvNickname!!.text = "@" + videoData.user!!.nick
        tvContent!!.text = videoData.title
        tvLikecount!!.text = NumUtils.numberFilter(videoData.like)
        tvCommentcount!!.text = NumUtils.numberFilter(videoData.comment)
        tvSharecount!!.text = NumUtils.numberFilter(videoData.share)
        animationView!!.setAnimation("like.json")
        var tagList = ArrayList<Tag>()
        for (i in 0 until videoData.category.size) {
            tagList.add(Tag(videoData.category[i].type_id,videoData.category[i].title))
        }
        autoLinkTextView.setText(autoLinkTextView.addTagClickableSpan(tagList, "", object : ClickableSpanNoUnderline.OnClickListener<TagTextView.TagClickableSpan>{
            override fun onClick(widget: View?, span: TagTextView.TagClickableSpan?) {
                var intent = Intent(context, ColumnActivity::class.java)
                var classItem = HotClassResponse()
                classItem.id = span?.id
                classItem.title = span?.content
                intent.putExtra("classItem", classItem)
                context.startActivity(intent)
            }
        }))
        autoLinkTextView.setMovementMethod(LinkMovementMethod.getInstance());
        autoLinkTextView.setHighlightColor(ContextCompat.getColor(context,R.color.white));
        //点赞状态
        if (videoData.is_like==1) {
            ivLike!!.setTextColor(resources.getColor(R.color.red))
        } else {
            ivLike!!.setTextColor(resources.getColor(R.color.white))
        }
        //是否有合集 1=有；0=否
        if (videoData.is_album==0){
            ivRecord.visibility = GONE
        }else{
            ivRecord.visibility = VISIBLE
        }
        //关注状态
        if (videoData.user.is_follow==1) {
            ivFocus!!.visibility = GONE
        } else {
            ivFocus!!.visibility = VISIBLE
        }
        showAdView(videoData);
    }
    fun getVideoData(): VideoResponse? {
        return videoData
    }
    private fun showAdView(data: VideoResponse){
        if (data.ad!=null) {
            val simpleTarget: SimpleTarget<File?> = object : SimpleTarget<File?>() {
                override fun onResourceReady(resource: File, transition: Transition<in File?>?) {
                    val decryptBytes = AESFileCrypt.decryptData(resource)
                    val bitmap = BitmapFactory.decodeByteArray(decryptBytes, 0, decryptBytes.size)
                    ivAdIcon.setImageBitmap(bitmap)
                }
            }
            Glide.with(context).asFile().load(data.ad!!.product_logo).error(R.mipmap.ic_launcher).into(simpleTarget)
            tvAdTitle.setText(data.ad.title)
            tvAdContent.setText(data.ad.sub_title)
            btAdDownLoad.setOnClickListener(this)
        }
        if (data.is_ad==1) {
            view_action.visibility = GONE
            Handler().postDelayed(Runnable {
                view_ad.visibility = VISIBLE
                view_conent.visibility = GONE
            }, 1000)
        }else{
            view_action.visibility = VISIBLE
            view_ad.visibility = GONE
            view_conent.visibility = VISIBLE
        }
    }
    fun setListener(listener: OnVideoControllerListener?) {
        this.listener = listener
    }

    override fun onClick(v: View) {
        if (listener == null) {
            return
        }
        when (v.id) {
            R.id.ivHead -> listener!!.onHeadClick()
            R.id.rlLike -> {
                listener!!.onLikeClick()
                like()
            }
            R.id.ivComment -> listener!!.onCommentClick()
            R.id.ivShare -> listener!!.onShareClick()
            R.id.rlRecord -> listener!!.onBoxClick()
            R.id.ivFocus -> if (videoData!!.user.is_follow == 0) {
                videoData!!.is_like = 1
                ivFocus!!.visibility = GONE
                listener!!.onFocusClick()
            }
            R.id.btFullVideo -> {
                listener!!.onFullVideoClick()
            }
            R.id.btAdDownLoad -> {
                var link =videoData?.ad?.link
                if (link!!.contains("url://")) {
                    val realLink = link.replace("url://", "")
                    SystemUtils.openBrowser(context, realLink)
                }
            }
        }
    }

    /**
     * 点赞动作
     */
    fun like() {
        if (videoData!!.is_like==0) {
            //点赞
            animationView!!.visibility = VISIBLE
            animationView!!.playAnimation()
            ivLike!!.setTextColor(resources.getColor(R.color.red))
            videoData!!.is_like = 1
        } else {
            //取消点赞
            animationView!!.visibility = INVISIBLE
            ivLike!!.setTextColor(resources.getColor(R.color.white))
            videoData!!.is_like = 0
        }

    }
    init {
        init()
    }
}