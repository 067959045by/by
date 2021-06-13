package com.lianzhihui.minitiktok.ui.system

import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.echofeng.common.ui.base.BaseActivity
import com.lianzhihui.minitiktok.adapter.system.ChatAdapter
import com.lianzhihui.minitiktok.bean.system.ChatBean
import com.lianzhihui.minitiktok.presenter.ChatPresnterImp
import com.lianzhihui.minitiktok.view.ChatView
import com.lianzhihui.onlyleague.R
import kotlinx.android.synthetic.main.activity_im_service.*

class ImServiceActivity : BaseActivity() , ChatView,View.OnClickListener {
    private lateinit var chatPresnterImp: ChatPresnterImp
    private lateinit var chatAdapter: ChatAdapter

    override fun getLayoutId(): Int {
        return R.layout.activity_im_service
    }

    override fun initView() {
        setTitle("客服")
        btSend.setOnClickListener(this)
        chatAdapter = ChatAdapter(ArrayList());
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        mRecyclerView.layoutManager = linearLayoutManager
        mRecyclerView.adapter = chatAdapter
        chatPresnterImp = ChatPresnterImp(this,this)
        chatPresnterImp.getChatList(pageNum)
    }

    override fun setSuccess(o: Any?) {
        etContent.text = null
        chatPresnterImp.getChatList(pageNum)
    }

    override fun setFailure(o: Any?) {
    }

    override fun setChatSuccess(data: MutableList<ChatBean>?) {
        chatAdapter.setNewData(data)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btSend->{
                var content = etContent.text.toString()
                if (!TextUtils.isEmpty(content)) {
                    chatPresnterImp.doSendMessage("1", content)
                }
            }
        }
    }
}