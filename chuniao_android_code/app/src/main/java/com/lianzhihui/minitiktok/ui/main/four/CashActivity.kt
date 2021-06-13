package com.lianzhihui.minitiktok.ui.main.four

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.echofeng.common.ui.base.BaseActivity
import com.echofeng.common.utils.NumberUtils
import com.google.android.material.tabs.TabLayout
import com.lianzhihui.onlyleague.R
import kotlinx.android.synthetic.main.activity_cash.*
import kotlinx.android.synthetic.main.activity_wallet.tabLayout

class CashActivity : BaseActivity() ,View.OnClickListener, TabLayout.OnTabSelectedListener{

    override fun getLayoutId(): Int {
        return R.layout.activity_cash
    }

    override fun initView() {
        setTitle("提现")
        var stoneNum = intent.getStringExtra("stoneNum")
        tabLayout.addOnTabSelectedListener(this)
        itemSourseOne.setOnClickListener(this)
        itemSourseTwo.setOnClickListener(this)
        tvStonBalance.setText(stoneNum)
        NumberUtils.iniSetBankNumberEtText(etBankNum)
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        when(tab?.position){
            0->{
                contentChashOne.visibility = View.VISIBLE
                contentChashTwo.visibility = View.GONE
            }
            1->{
                contentChashOne.visibility = View.GONE
                contentChashTwo.visibility = View.VISIBLE
            }
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.itemSourseOne->{
                itemSourseOne.setBackgroundResource(R.mipmap.icon_cash_item_choose)
                itemSourseTwo.setBackgroundResource(R.mipmap.icon_cash_item_unchoose)
                tvItemOne1.setTextColor(ContextCompat.getColor(this,R.color.white))
                tvItemOne2.setTextColor(ContextCompat.getColor(this,R.color.white))
                tvItemTwo1.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary))
                tvItemTwo2.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary))
            }
            R.id.itemSourseTwo->{
                itemSourseOne.setBackgroundResource(R.mipmap.icon_cash_item_unchoose)
                itemSourseTwo.setBackgroundResource(R.mipmap.icon_cash_item_choose)
                tvItemOne1.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary))
                tvItemOne2.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary))
                tvItemTwo1.setTextColor(ContextCompat.getColor(this,R.color.white))
                tvItemTwo2.setTextColor(ContextCompat.getColor(this,R.color.white))
            }
        }
    }
}