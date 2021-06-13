package com.echofeng.common.config;

import com.echofeng.common.utils.AESCBCCrypt2;
import com.echofeng.common.utils.GsonUtil;
import com.echofeng.common.utils.LogUtil;
import com.echofeng.common.utils.MD5Utils;
import com.echofeng.common.utils.PreferenceUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.security.GeneralSecurityException;

public class DataManager {
    private static final String CHARACTER = "UTF-8";
    public static void setAuthorization(String token) {
        PreferenceUtils.applyString(AppConstants.Share.AUTHORIZATION, token);
    }

    public static String getAuthorization() {
        return PreferenceUtils.getString(AppConstants.Share.AUTHORIZATION, "");
    }
    public static void cleanAuthorization() {
        PreferenceUtils.commitString(AppConstants.Share.AUTHORIZATION, "");
    }

    public static String singPostBody(JSONObject jsonObject){
        String jsonBody = jsonObject.toString();
        String aesBody = null;
        try {
            aesBody = AESCBCCrypt2.aesEncrypt(jsonBody);
            LogUtil.debug("singPostBody:",aesBody);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aesBody;
    }
    public static String singFileBody(String timestamp){
//        String souseData = "A"+timestamp+"C8pvY0YQWqtfFLHb";
        String souseData = "A"+timestamp+"C8pvY0YQWqtfFLHb";
        String md5Data = MD5Utils.md5(souseData).toUpperCase();
        return md5Data;
    }

    public static void exitAccount() {
        PreferenceUtils.clear();
    }

    public static void setLoginResponse(JsonElement dataDecryption) {
        String jsonString = new Gson().toJson(dataDecryption);
        PreferenceUtils.applyString(AppConstants.Share.LOGIN_DATA,jsonString);
    }
    public static JsonElement getLoginJsonElement(){
        String jsonString = PreferenceUtils.getString(AppConstants.Share.LOGIN_DATA,"{}");
        JsonElement jsonElement = new JsonParser().parse(jsonString);
        return jsonElement;
    }
}
