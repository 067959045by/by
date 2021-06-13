package com.lianzhihui.minitiktok.ui.system

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.echofeng.common.ui.base.BaseActivity
import com.lianzhihui.minitiktok.adapter.system.ReportAdapter
import com.lianzhihui.minitiktok.bean.system.ReportBean
import com.lianzhihui.minitiktok.bean.system.VersionResponse
import com.lianzhihui.minitiktok.presenter.SystemPresnterImp
import com.lianzhihui.minitiktok.view.SystemView
import com.lianzhihui.onlyleague.R
import kotlinx.android.synthetic.main.activity_report_video.*
import kotlinx.android.synthetic.main.activity_report_video.mRecyclerView
import kotlinx.android.synthetic.main.fragment_hot_three.*

class ReportVideoActivity : BaseActivity() ,SystemView,BaseQuickAdapter.OnItemClickListener,View.OnClickListener{
    private var reportType: String? = null
    private var reportId: String? = null
    private lateinit var systemPresnterImp: SystemPresnterImp
    private lateinit var adapter: ReportAdapter
    var list = ArrayList<ReportBean>()
    private lateinit var content:String
    override fun getLayoutId(): Int {
        return R.layout.activity_report_video
    }

    override fun initView() {
        setTitle("举报视频")
        bt_submit.setOnClickListener(this)
        reportType = intent.getStringExtra("reportType")
        reportId = intent.getStringExtra("reportId")
        systemPresnterImp = SystemPresnterImp(this,this)
        list.add(ReportBean("内容质量差"))
        list.add(ReportBean("不实信息"))
        list.add(ReportBean("违法犯罪"))
        list.add(ReportBean("盗用视频"))
        list.add(ReportBean("非原创"))
        list.add(ReportBean("广告"))
        list.add(ReportBean("其他"))
        adapter = ReportAdapter(ArrayList())
        adapter.setOnItemClickListener(this)
        val gridLayoutManager = GridLayoutManager(context,3)
        mRecyclerView.setLayoutManager(gridLayoutManager)
        mRecyclerView.adapter = adapter
        adapter.setNewData(list)
    }

    override fun onItemClick(adp: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        content = list.get(position).content
        adapter.setChooseItem(position)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.bt_submit->{
                if (reportType.equals("video")) {
                    systemPresnterImp.doReportVideo(reportId,content)
                }else{
                    systemPresnterImp.doUserReport(reportId,content)
                }
            }
        }
    }

    override fun setSuccess(o: Any?) {
        finish()
    }

    override fun setFailure(o: Any?) {
    }

    override fun setCheckUpdateSuccess(data: VersionResponse?) {
    }

}