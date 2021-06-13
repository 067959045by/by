package com.lianzhihui.minitiktok.widget.dialog

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.echofeng.common.inter.CustomCallback
import com.echofeng.common.ui.base.BaseDialog
import com.echofeng.common.utils.MyToast
import com.echofeng.common.utils.SystemUtils
import com.lianzhihui.minitiktok.bean.wallet.PayInfoResponse
import com.lianzhihui.minitiktok.bean.wallet.WalletMainResponse
import com.lianzhihui.minitiktok.presenter.WalletPresnterImp
import com.lianzhihui.minitiktok.view.WalletView
import com.lianzhihui.onlyleague.R
import kotlinx.android.synthetic.main.dialog_pay_way.*

class PayWayDialog:BaseDialog,View.OnClickListener ,WalletView {
    private var isVipPay: Boolean = false
    private lateinit var vipItem: WalletMainResponse.VipItem
    private lateinit var walletPresnterImp: WalletPresnterImp
    private lateinit var adapter: DialogCoinAdapter
    private var customCallback: CustomCallback? = null
    constructor(context: Context) : super(context) {}
    constructor(context: Context, customCallback: CustomCallback) : super(context) {
        this.customCallback = customCallback
    }
    override fun provideViewId(): Int {
        return R.layout.dialog_pay_way
    }

    override fun initView() {
        bottom()
        fullWidth()
        iv_close.setOnClickListener(this)
        btConfirm.setOnClickListener(this)

        adapter = DialogCoinAdapter(ArrayList())
        var layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = RecyclerView.VERTICAL
        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.adapter = adapter
        walletPresnterImp = WalletPresnterImp(context,this)
    }


    fun setData(data:WalletMainResponse.VipItem,isVip :Boolean){
        isVipPay = isVip
        vipItem = data
        adapter.setNewData(data.payment)
        tvPayBlance.setText(data.price+"元")
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.iv_close->{
                dismiss()
            }
            R.id.btConfirm->{
                var payment = adapter.itemSelected
                if (payment==null){
                    MyToast.showToast("请选择支付方式")
                    return
                }
                showLoading("")
                if (isVipPay) {
                    walletPresnterImp.doPayLink(null, payment.id, vipItem.id)
                }else{
                    walletPresnterImp.doPayLink(vipItem.id, payment.id, null)
                }
                customCallback?.onCompare(payment)
            }
        }
    }

    override fun setSuccess(o: Any?) {
        dismissLoading()
    }

    override fun setFailure(o: Any?) {
        dismissLoading()
    }

    override fun setWalletMainSuccess(data: WalletMainResponse?) {
    }

    override fun setPayLinkSuccess(data: PayInfoResponse?) {
        dismissLoading()
        SystemUtils.openBrowser(context, data?.link)
    }

}