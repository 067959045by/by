package com.lianzhihui.minitiktok.model;

import android.content.Context;
import android.text.TextUtils;

import com.echofeng.common.net.APIConstant;
import com.echofeng.common.net.HttpManager;
import com.echofeng.common.net.HttpManagerCallback;
import com.echofeng.common.net.ResultData;
import com.echofeng.common.utils.GsonUtil;
import com.echofeng.common.utils.MyToast;
import com.lianzhihui.minitiktok.bean.AdResponse;
import com.lianzhihui.minitiktok.bean.wallet.PayInfoResponse;
import com.lianzhihui.minitiktok.bean.wallet.WalletMainResponse;
import com.net.core.exception.ApiException;
import com.net.core.model.HttpParams;

import java.util.List;

//网络数据请求实现
public class WalletModelImp {
    private Context context;
    WalletModelInterface walletModelInterface;
    public WalletModelImp(Context context, WalletModelInterface walletModelInterface){
        this.context = context;
        this.walletModelInterface = walletModelInterface;
    }

//    position	是
//    position=video_hot
//    hot_top热门头部位；creater_index创造者中心页面；meg_home消息页面；
    public void getWalletMain() {
        HttpParams params = new HttpParams();
        new HttpManager(context, new HttpManagerCallback() {
            @Override
            public void onSuccess(ResultData o) {
                WalletMainResponse data = GsonUtil.GsonToBean(o.getDataDecryption(), WalletMainResponse.class);
                walletModelInterface.onWalletMainSuccess(data);
            }

            @Override
            public void onError(ApiException e) {
                MyToast.showToast(e.getMessage());
                walletModelInterface.onFailure(e);
            }
        }).post(APIConstant.WALLET_COIN_LIST,params);
    }
//    coin_id	integer金币ID 购买金币传参
//    payment_id	string支付方式ID
//    vip_id	integervipID 购买VIP 传参

    public void doPayLink(String coin_id,String payment_id,String vip_id) {
        HttpParams params = new HttpParams();
        if (!TextUtils.isEmpty(coin_id)){
            params.put("coin_id", coin_id);
        }
        if (!TextUtils.isEmpty(vip_id)){
            params.put("vip_id",vip_id);
        }
        params.put("payment_id",payment_id);
        new HttpManager(context, new HttpManagerCallback() {
            @Override
            public void onSuccess(ResultData o) {
                PayInfoResponse data = GsonUtil.GsonToBean(o.getDataDecryption(), PayInfoResponse.class);
                walletModelInterface.onPayLinkSuccess(data);
            }

            @Override
            public void onError(ApiException e) {
                MyToast.showToast(e.getMessage());
                walletModelInterface.onFailure(e);
            }
        }).post(APIConstant.DO_AY_LINK,params);
    }

    public void doExchange(String code) {
        HttpParams params = new HttpParams();
        params.put("code",code);
        new HttpManager(context, new HttpManagerCallback() {
            @Override
            public void onSuccess(ResultData o) {
                MyToast.showToast("兑换成功");
                walletModelInterface.onSuccess(o.getDataDecryption());
            }

            @Override
            public void onError(ApiException e) {
                MyToast.showToast(e.getMessage());
                walletModelInterface.onFailure(e);
            }
        }).post(APIConstant.DO_EXCHANGE,params);
    }
}
