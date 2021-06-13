package com.echofeng.common.net;

import com.net.core.interceptor.BaseDynamicInterceptor;
import com.net.core.utils.HttpLog;

import org.bouncycastle.util.encoders.UTF8;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.TreeMap;



/**
 * <p>描述：对参数进行签名、添加token、时间戳处理的拦截器</p>
 * 主要功能说明：<br>
 * 因为参数签名没办法统一，签名的规则不一样，签名加密的方式也不同有MD5、BASE64等等，只提供自己能够扩展的能力。<br>
 * 作者： zhouyou<br>
 * 日期： 2017/5/4 15:21 <br>
 * 版本： v1.0<br>
 */
public class CustomSignInterceptor extends BaseDynamicInterceptor<CustomSignInterceptor> {
    @Override
    public TreeMap<String, String> dynamic(TreeMap<String, String> dynamicMap) {
        //dynamicMap:是原有的全局参数+局部参数
        if (isTimeStamp()) {//是否添加时间戳，因为你的字段key可能不是timestamp,这种动态的自己处理
            //dynamicMap.put(Constant.TIMESTAMP, String.valueOf(System.currentTimeMillis()));
        }
        if (isAccessToken()) {//是否添加token
            //String acccess = AppApplication.getSPUtils().getString(Constant.ACCESSTOKEN);
            //dynamicMap.put(Constant.ACCESSTOKEN, acccess);
        }
        if (isSign()) {//是否签名,因为你的字段key可能不是sign，这种动态的自己处理
            //dynamicMap.put(Constant.SIGN, sign(dynamicMap));
        }
        //HttpLog.i("dynamicMap:" + dynamicMap.toString());
        return dynamicMap;//dynamicMap:是原有的全局参数+局部参数+新增的动态参数
    }

    //签名规则：POST+url+参数的拼装+secret
    private String sign(TreeMap<String, String> dynamicMap) {
        String url = getHttpUrl().url().toString();
        ////url = url.replaceAll("%2F", "/");
        StringBuilder sb = new StringBuilder("POST");
        sb.append(url);
        for (Map.Entry<String, String> entry : dynamicMap.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }

        sb.append("");//Constant.TRIP_DES_KEY
        String signStr = sb.toString();
        try {
            signStr = URLDecoder.decode(signStr, "utf-8");
        } catch (UnsupportedEncodingException exception) {
            exception.printStackTrace();
        }
        HttpLog.i(signStr);
        return signStr;
    }
}
