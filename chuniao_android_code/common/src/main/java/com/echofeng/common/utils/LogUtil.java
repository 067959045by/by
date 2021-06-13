package com.echofeng.common.utils;

import android.util.Log;

public class LogUtil {
    protected static boolean isDebug=false;
    public static void init(boolean isDebugOpen){
        isDebug=isDebugOpen;
    }

    public static void info(String tagName, String info){
        if(isDebug){
            Log.i(tagName,info);
        }
    }

    public static void error(String tagName, String error){
        if(isDebug){
            Log.e(tagName,error);
        }
    }

    public static void debug(String tagName, String debug){
        if(isDebug){
            Log.d(tagName,debug);
        }
    }
}
