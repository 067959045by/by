package com.echofeng.common.utils;


import androidx.annotation.NonNull;


import org.bitcoinj.core.DumpedPrivateKey;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.TestNet3Params;

/**
 * Copyright (C), 2019-2019, wase_five
 * FileName: BitcoinjUtils
 * Author: echo
 * Date: 2019/12/3 15:52
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class BitcoinjUtils {
    private static final boolean BTC_TEST_NET = false;

    /** 对消息进行签名
     * @param msg 要签名的信息
     * @param privateKey 私钥
     * @return
     */
    public static String signMsg(@NonNull String msg, @NonNull String privateKey) {
        NetworkParameters networkParameters = null;
        if (!BTC_TEST_NET) {
            networkParameters = MainNetParams.get();
        }
        else {
            networkParameters = TestNet3Params.get();
        }
        if (networkParameters==null){
            return "nothing";
        }
        DumpedPrivateKey priKey = DumpedPrivateKey.fromBase58(networkParameters, privateKey);
        ECKey ecKey = priKey.getKey();
        return ecKey.signMessage(msg);
    }

}
