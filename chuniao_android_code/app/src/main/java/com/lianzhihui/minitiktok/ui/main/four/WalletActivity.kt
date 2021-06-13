package com.lianzhihui.minitiktok.ui.main.four

import android.content.Intent
import android.view.View
import android.widget.TabHost
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.echofeng.common.inter.CustomCallback
import com.echofeng.common.ui.base.BaseActivity
import com.google.android.material.tabs.TabLayout
import com.lianzhihui.minitiktok.adapter.hot.HotTwoNiceItemAdapter
import com.lianzhihui.minitiktok.adapter.hot.HotTwoTopAdapter
import com.lianzhihui.minitiktok.adapter.wallet.RechargeCoinAdapter
import com.lianzhihui.minitiktok.adapter.wallet.VipCardAdapter
import com.lianzhihui.minitiktok.bean.wallet.PayInfoResponse
import com.lianzhihui.minitiktok.bean.wallet.WalletMainResponse
import com.lianzhihui.minitiktok.presenter.WalletPresnterImp
import com.lianzhihui.minitiktok.ui.MainActivity
import com.lianzhihui.minitiktok.view.WalletView
import com.lianzhihui.minitiktok.widget.dialog.PayWayDialog
import com.lianzhihui.onlyleague.R
import kotlinx.android.synthetic.main.activity_wallet.*
import kotlinx.android.synthetic.main.activity_wallet.mRecyclerView1
import kotlinx.android.synthetic.main.activity_wallet.mRecyclerView2
import kotlinx.android.synthetic.main.fragment_user_home_video.*

class WalletActivity : BaseActivity() ,WalletView,View.OnClickListener,TabLayout.OnTabSelectedListener,BaseQuickAdapter.OnItemClickListener{

    private var stoneNum: String? = null
    private lateinit var adapteCoin: RechargeCoinAdapter
    private lateinit var adapterVip: VipCardAdapter
    private lateinit var walletPresnterImp: WalletPresnterImp

    override fun getLayoutId(): Int {
        return R.layout.activity_wallet
    }

    override fun initView() {
        setTitle("我的钱包")
        setOption("我的账单",this)
        btCash.setOnClickListener(this)
        tabLayout.addOnTabSelectedListener(this)
        btExchange.setOnClickListener(this)

        adapterVip = VipCardAdapter(ArrayList())
        adapteCoin = RechargeCoinAdapter(ArrayList())
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        mRecyclerView1.setLayoutManager(linearLayoutManager)
        mRecyclerView1.setAdapter(adapterVip)

        val gridLayoutManager = GridLayoutManager(context, 3)
        mRecyclerView2.setLayoutManager(gridLayoutManager)
        mRecyclerView2.setAdapter(adapteCoin)

        adapterVip.setOnItemClickListener(this)
        adapteCoin.setOnItemClickListener(this)

        walletPresnterImp = WalletPresnterImp(this,this)
        walletPresnterImp.getWalletMain()
    }
    fun showPayDiaog(payment:WalletMainResponse.VipItem,isVip:Boolean){
        var dialog = PayWayDialog(this,object:CustomCallback{
            override fun onCompare(o: Any?) {
            }

            override fun onCancel() {
            }
        })
        dialog.show()
        dialog.setData(payment,isVip)
    }
    override fun onTabSelected(tab: TabLayout.Tab?) {
        when(tab?.position){
            0->{mRecyclerView1.visibility = View.VISIBLE
                mRecyclerView2.visibility = View.GONE}
            1->{
                mRecyclerView1.visibility = View.GONE
                mRecyclerView2.visibility = View.VISIBLE}
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }

    override fun setSuccess(o: Any?) {
        dismissLoading()
        etExchange.text.clear()
    }

    override fun setFailure(o: Any?) {
        dismissLoading()
    }

    override fun setWalletMainSuccess(data: WalletMainResponse?) {
        stoneNum = data?.diamonds
        tvCoinBalance.setText(data?.coins)
        tvStonBalance.setText(data?.diamonds)
        adapterVip.setNewData(data?.vip_list)
        adapteCoin.setNewData(data?.coin_list)
    }

    override fun setPayLinkSuccess(data: PayInfoResponse?) {

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btCash->{
                var intent = Intent(this, CashActivity::class.java)
                intent.putExtra("stoneNum",stoneNum)
                startActivity(intent)
            }
            R.id.include_top_tv_right->{
                startActivity(Intent(this, TransRecordActivity::class.java))
            }
            R.id.btExchange->{
                var code = etExchange.text.toString()
                showLoading("正在兑换")
                walletPresnterImp.doExchange(code)
            }
        }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        if (adapter==adapteCoin){
            var paymentInfo = adapteCoin.getItem(position) as WalletMainResponse.VipItem
            adapteCoin.setChooseItem(position)
            showPayDiaog(paymentInfo,false)
        }else{
            var paymentInfo = adapterVip.getItem(position) as WalletMainResponse.VipItem
            adapterVip.setChooseItem(position)
            showPayDiaog(paymentInfo,true)
        }
    }

}