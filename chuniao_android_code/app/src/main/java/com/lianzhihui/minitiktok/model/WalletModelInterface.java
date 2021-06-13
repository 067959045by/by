package com.lianzhihui.minitiktok.model;



import com.lianzhihui.minitiktok.bean.AdResponse;
import com.lianzhihui.minitiktok.bean.wallet.PayInfoResponse;
import com.lianzhihui.minitiktok.bean.wallet.WalletMainResponse;
import com.lianzhihui.minitiktok.model.base.BaseInterface;

import org.web3j.crypto.Wallet;

import java.util.List;

public interface WalletModelInterface extends BaseInterface {
    void onWalletMainSuccess(WalletMainResponse data);

    void onPayLinkSuccess(PayInfoResponse data);
}
