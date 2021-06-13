package com.lianzhihui.minitiktok.presenter;

import android.content.Context;

import com.lianzhihui.minitiktok.bean.AdResponse;
import com.lianzhihui.minitiktok.bean.wallet.PayInfoResponse;
import com.lianzhihui.minitiktok.bean.wallet.WalletMainResponse;
import com.lianzhihui.minitiktok.model.AdModelImp;
import com.lianzhihui.minitiktok.model.AdModelInterface;
import com.lianzhihui.minitiktok.model.WalletModelImp;
import com.lianzhihui.minitiktok.model.WalletModelInterface;
import com.lianzhihui.minitiktok.view.AdView;
import com.lianzhihui.minitiktok.view.WalletView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

//逻辑实现
public class WalletPresnterImp implements WalletModelInterface {

    private final WalletView walletView;
    private final WalletModelImp walletModelImp;

    public WalletPresnterImp(Context context, WalletView walletView) {
        this.walletView = walletView;
        walletModelImp = new WalletModelImp(context, this);
    }
    public void getWalletMain() {
        walletModelImp.getWalletMain();
    }
    //    coin_id	integer金币ID 购买金币传参
//    payment_id	string支付方式ID
//    vip_id	integervipID 购买VIP 传参
    public void doPayLink(String coin_id,String payment_id,String vip_id) {
        walletModelImp.doPayLink(coin_id,payment_id,vip_id);
    }

    @Override
    public void onSuccess(Object data) {
        walletView.setSuccess(data);
    }

    @Override
    public void onFailure(Object data) {
        walletView.setFailure(data);
    }

    @Override
    public void onWalletMainSuccess(WalletMainResponse data) {
        walletView.setWalletMainSuccess(data);
    }

    @Override
    public void onPayLinkSuccess(PayInfoResponse data) {
        walletView.setPayLinkSuccess(data);
    }

    public void doExchange(@NotNull String code) {
        walletModelImp.doExchange(code);
    }
}
