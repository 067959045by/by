package com.echofeng.common.net;

import android.util.Log;

import com.echofeng.common.utils.DateUtil;
import com.net.core.model.HttpParams;

/**
 * Description: 包含过滤日期信息 请求体
 * Author: RAMON
 * CreateDate: 2020/9/27 10:11 AM
 * UpdateUser:
 * UpdateDate:
 * UpdateRemark:
 * Version:
 */
public class DataHttpParams extends HttpParams {

    public DataHttpParams(int type) {

        long startTime = 0L;
        long endTime = DateUtil.timeStampLong();

        switch (type) {
            case 0:
                startTime = DateUtil.getDataZero(DateUtil.getAnyDayAgo(7));
                break;
            case 1:
                startTime = DateUtil.getToday();
                break;
            case 2:
                startTime = DateUtil.timeStampYesterday();
                endTime = startTime + 86399;
                break;
            case 3:
                startTime = DateUtil.getDataZero(DateUtil.getAnyDayAgo(3));
                break;
            case 4:
//                startTime = DateUtil.getDataZero(DateUtil.getFewMouthAgo(1));
                startTime = DateUtil.getDataZero(DateUtil.getAnyDayAgo(30));
                break;
            case 5:
//                startTime = DateUtil.getDataZero(DateUtil.getFewMouthAgo(3));
                startTime = DateUtil.getDataZero(DateUtil.getAnyDayAgo(90));
                break;
            default:
                break;
        }

        put("startTime", startTime + "");
        put("endTime", endTime + "");
        Log.e("ramon", "startTime: " + startTime);
    }
}
