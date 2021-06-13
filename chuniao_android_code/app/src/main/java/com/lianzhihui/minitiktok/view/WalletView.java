package com.lianzhihui.minitiktok.view;


import com.lianzhihui.minitiktok.bean.AdResponse;
import com.lianzhihui.minitiktok.bean.wallet.PayInfoResponse;
import com.lianzhihui.minitiktok.bean.wallet.WalletMainResponse;
import com.lianzhihui.minitiktok.view.base.BaseView;

import java.util.List;

public interface WalletView extends BaseView {
    void setWalletMainSuccess(WalletMainResponse data);
    void setPayLinkSuccess(PayInfoResponse data);
}
