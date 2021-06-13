package com.echofeng.common.utils;

/**
 * Copyright (C), 2019-2019, wase_five
 * FileName: VerificationCountDownTimer
 * Author: echo
 * Date: 2019/11/20 16:26
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
import android.os.CountDownTimer;

public class VerificationCountDownTimer extends CountDownTimer {

    public static long curMillis =0;
    public static boolean FLAG_FIRST_IN =true;

    /**
     * 类中的构造函数
     * @param millisInFuture
     * @param countDownInterval
     */
    public VerificationCountDownTimer(long millisInFuture, long countDownInterval){
        super(millisInFuture, countDownInterval);
    }

    /**
     * 当前任务每完成一次倒计时间隔时间时回调
     * @param millisUntilFinished
     */
    @Override
    public void onTick(long millisUntilFinished) {

    }


    /**
     * 当前任务完成的时候回调
     */
    @Override
    public void onFinish() {

    }


    public void timerStart(boolean onClick){

        if(onClick) {
            VerificationCountDownTimer.curMillis= System.currentTimeMillis();
        }
        VerificationCountDownTimer.FLAG_FIRST_IN=false;
        this.start();
    }
}
