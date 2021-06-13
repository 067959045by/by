package com.lianzhihui.minitiktok.bean.test

import com.echofeng.common.bean.VideoBean
import com.lianzhihui.onlyleague.R
import java.util.*

/**
 * create by libo
 * create on 2020-06-03
 * description 本地数据创建，代替接口获取数据
 */
class DataCreate {

    fun initData() {

        val videoBeanOne = VideoBean()
        videoBeanOne.coverRes = "https://jzvd.nathen.cn/snapshot/61c99f9225c04b24a1d0374e9a3f006700004.jpg"
        videoBeanOne.content = "#街坊 #颜值打分 给自己颜值打100分的女生集合"
        videoBeanOne.videoRes = "http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8"
        videoBeanOne.distance = 7.9f
        videoBeanOne.isFocused = false
        videoBeanOne.isLiked = true
        videoBeanOne.likeCount = 226823
        videoBeanOne.commentCount = 3480
        videoBeanOne.shareCount = 4252

        val userBeanOne = VideoBean.UserBean()
        userBeanOne.uid = 1
        userBeanOne.head = R.mipmap.head1
        userBeanOne.nickName = "南京街坊"
        userBeanOne.sign = "你们喜欢的话题，就是我们采访的内容"
        userBeanOne.subCount = 119323
        userBeanOne.focusCount = 482
        userBeanOne.fansCount = 32823
        userBeanOne.workCount = 42
        userBeanOne.dynamicCount = 42
        userBeanOne.likeCount = 821
        userList.add(userBeanOne)
        videoBeanOne.userBean = userBeanOne

        val videoBeanTwo = VideoBean()
        videoBeanTwo.coverRes = "https://jzvd.nathen.cn/snapshot/61c99f9225c04b24a1d0374e9a3f006700004.jpg"
        videoBeanOne.content = "#街坊 #颜值打分 给自己颜值打100分的女生集合"
        videoBeanTwo.content = "400 户摊主开进济南环联夜市，你们要的烟火气终于来了！"
        videoBeanOne.videoRes = "https://jzvd.nathen.cn/video/59aa468b-1767b6d891e-0007-1823-c86-de200.mp4"
        videoBeanTwo.distance = 19.7f
        videoBeanTwo.isFocused = true
        videoBeanTwo.isLiked = false
        videoBeanTwo.likeCount = 1938230
        videoBeanTwo.commentCount = 8923
        videoBeanTwo.shareCount = 5892

        val userBeanTwo = VideoBean.UserBean()
        userBeanTwo.uid = 2
        userBeanTwo.head = R.mipmap.head2
        userBeanTwo.nickName = "民生直通车"
        userBeanTwo.sign = "直通现场新闻，直击社会热点，深入事件背后，探寻事实真相"
        userBeanTwo.subCount = 20323234
        userBeanTwo.focusCount = 244
        userBeanTwo.fansCount = 1938232
        userBeanTwo.workCount = 123
        userBeanTwo.dynamicCount = 123
        userBeanTwo.likeCount = 344
        userList.add(userBeanTwo)
        videoBeanTwo.userBean = userBeanTwo

        datas.add(videoBeanOne)
        datas.add(videoBeanTwo)
    }

    companion object {
        @JvmField
        var datas = ArrayList<VideoBean>()
        @JvmField
        var userList = ArrayList<VideoBean.UserBean>()
    }
}