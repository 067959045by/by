package com.echofeng.common.net;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.echofeng.common.MyApplication;
import com.echofeng.common.config.AppManager;
import com.echofeng.common.config.DataManager;
import com.echofeng.common.inter.CustomCallback;
import com.echofeng.common.utils.AESCBCCrypt2;
import com.echofeng.common.utils.GsonUtil;
import com.echofeng.common.utils.LogUtil;
import com.echofeng.common.utils.MyToast;
import com.echofeng.common.utils.NetUtil;
import com.echofeng.common.utils.SystemUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.net.core.EasyHttp;
import com.net.core.body.UIProgressResponseCallBack;
import com.net.core.callback.CallBack;
import com.net.core.callback.DownloadProgressCallBack;
import com.net.core.callback.ProgressDialogCallBack;
import com.net.core.exception.ApiException;
import com.net.core.model.HttpHeaders;
import com.net.core.model.HttpParams;
import com.net.core.request.DownloadRequest;
import com.net.core.subsciber.IProgressDialog;

import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.Proxy;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;
import static com.echofeng.common.utils.MyToast.showToast;
import static com.echofeng.common.utils.NetUtil.NETWORK_NONE;

public class HttpManager {
    private static final String LOG_TAG = "HttpManager";
    private static String IMEI = "";
    private String baseUrl;
    private final HttpManagerCallback httpManagerCallback;
    private Context context;

    public HttpManager(HttpManagerCallback httpManagerCallback) {
        this.httpManagerCallback = httpManagerCallback;
    }

    public HttpManager(Context context, HttpManagerCallback httpManagerCallback) {
        this.context = context;
        this.httpManagerCallback = httpManagerCallback;
    }

    /************************************
     * @描述 初始化网络配置
     * @日期 2019/7/17 14:48
     ************************************/

    public static void initBaseConfig() {
        //设置请求参数
        HttpParams params = new HttpParams();
        EasyHttp.getInstance()
                .debug(LOG_TAG, MyApplication.debug)
                .setReadTimeOut(10 * 1000)
                .setWriteTimeOut(10 * 1000)
                .setConnectTimeout(10 * 1000)
                .setRetryCount(0)//默认网络不好自动重试2次
                .setRetryDelay(5000)//每次延时500ms重试
                .setRetryIncreaseDelay(5000)//每次延时叠加500ms
                .setBaseUrl(APIConstant.getBASE_URL())
                .setCertificates()//信任所有证书
//                .setOkproxy(Proxy.NO_PROXY)
//                .addCommonHeaders(headers)//设置全局公共头
                .addCommonParams(params)//设置全局公共参数
                .addInterceptor(new CustomSignInterceptor());//添加参数签名拦截器
    }

    public static HttpHeaders getHeaders() {
        //设置请求头
        HttpHeaders headers = new HttpHeaders();
//        headers.put("userAddress", DataManager.getAuthorization());getAuthorization
        headers.put("device_type", "A");
        headers.put("device_no", SystemUtils.getAndroidId(MyApplication.mContext));
        headers.put("version", SystemUtils.getAppVersionName(MyApplication.mContext));
        headers.put("token", DataManager.getAuthorization());
        String singHeader = DataManager.singPostBody(new JSONObject(headers.headersMap));
        headers.clear();
        headers.put("X-TOKEN",singHeader);
        return headers;
    }

    /**
     * 报错则重启应用
     *
     * @param context
     */
    public void restartApplication(Context context) {
        AppManager.getAppManager().finishAllActivity();
        final Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * 自定义header post请求
     *
     * @param url
     * @param params
     * @param httpHeaders
     */
    public synchronized void post(final String url, HttpParams params, HttpHeaders httpHeaders) {
        final CustomPostRequest customPostRequest = new CustomPostRequest(url);
        String singPostBody = DataManager.singPostBody(new JSONObject(params.urlParamsMap));
        params.clear();
        params.put("data",singPostBody);
        JSONObject jsonObject = new JSONObject(params.urlParamsMap);
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        customPostRequest.headers(httpHeaders);
        customPostRequest.requestBody(requestBody);
        LogUtil.debug("singPostBody:",singPostBody);
        if (baseUrl != null) {
            customPostRequest.baseUrl(baseUrl);
        } else {
            customPostRequest.baseUrl(APIConstant.getBASE_URL());
        }
        //此框架类型被擦除
        customPostRequest.execute(new CallBack<String>() {

            @Override
            public void onStart() {
                httpManagerCallback.onStart();
            }

            @Override
            public void onCompleted() {
                httpManagerCallback.onCompleted();
            }

            @Override
            public void onError(ApiException e) {
                httpManagerCallback.onResp();
                LogUtil.info("wase", e.getMessage() + "<<ApiException");
                if (context != null) {
//                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                httpManagerCallback.onError(e);
            }

            @Override
            public void onSuccess(String str) {
                LogUtil.error(LOG_TAG, "onSuccess");
                httpManagerCallback.onResp();
                if (str == null || "".equals(str)) {
                    ApiException apiException = new ApiException(new Throwable("返回数据为null"), -100);
                    httpManagerCallback.onError(apiException);
                    return;
                }
                ResultData baseData = GsonUtil.GsonToBean(str, ResultData.class);
                if (baseData.getData() == null || "null".equals(baseData.getData().toString())) {
                    resultCode(baseData);
                    return;
                }
                resultCode(baseData);
            }
        });
    }

    /**
     * POST请求
     *
     * @param url
     * @param params
     */
    public synchronized void post(final String url, HttpParams params) {
        final CustomPostRequest customPostRequest = new CustomPostRequest(url);
        customPostRequest.headers(getHeaders());
        String singPostBody = DataManager.singPostBody(new JSONObject(params.urlParamsMap));
        params.clear();
//        params.put("handshake","v20210501");
        params.put("handshake","v20210601");
        params.put("data",singPostBody);
        JSONObject jsonObject = new JSONObject(params.urlParamsMap);
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        customPostRequest.requestBody(requestBody);
        if (baseUrl != null) {
            customPostRequest.baseUrl(baseUrl);
        } else {
            customPostRequest.baseUrl(APIConstant.getBASE_URL());
        }
        //此框架类型被擦除
        customPostRequest.execute(new CallBack<String>() {

            @Override
            public void onStart() {
                httpManagerCallback.onStart();
            }

            @Override
            public void onCompleted() {
                httpManagerCallback.onCompleted();
                httpManagerCallback.onResp();
            }

            @Override
            public void onError(ApiException e) {
                httpManagerCallback.onResp();
                LogUtil.info("wase", e.getMessage() + "<<ApiException");
                if (context != null) {
//                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                int netWorkState = NetUtil.getNetWorkState(MyApplication.mContext);
                if (NETWORK_NONE == netWorkState) {
//                    MyToast.showToast("网络异常");
                }
                httpManagerCallback.onError(e);
            }

            @Override
            public void onSuccess(String str) {
                LogUtil.error(LOG_TAG, "onSuccess");
                httpManagerCallback.onResp();
                if (str == null || "".equals(str)) {
                    ApiException apiException = new ApiException(new Throwable("返回数据为null"), -100);
                    httpManagerCallback.onError(apiException);
                    return;
                }
                ResultData baseData = GsonUtil.GsonToBean(str, ResultData.class);
                if (baseData.getData() == null || "null".equals(baseData.getData().toString())) {
                    resultCode(baseData);
                    return;
                }
                resultCode(baseData);
            }
        });
    }

    /**
     * GET请求
     *
     * @param url
     * @param params
     */
    public void get(final String url, HttpParams params) {
        final CustomGetRequest customGetRequest = new CustomGetRequest(url);
        if (baseUrl != null) {
            customGetRequest.baseUrl(baseUrl);
        } else {
            customGetRequest.baseUrl(APIConstant.getBASE_URL());
        }
        customGetRequest.params(params);
        //此框架类型被擦除
        customGetRequest.execute(new CallBack<String>() {

            @Override
            public void onStart() {
                httpManagerCallback.onStart();
            }

            @Override
            public void onCompleted() {
                httpManagerCallback.onCompleted();
            }

            @Override
            public void onError(ApiException e) {
                if (context != null) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                httpManagerCallback.onError(e);
            }

            @Override
            public void onSuccess(String str) {
                if (str == null || "".equals(str)) {
                    ApiException apiException = new ApiException(new Throwable("返回数据为null"), -100);
                    showToast(  apiException.getMessage());
                    httpManagerCallback.onError(apiException);
                    return;
                }
                ResultDataNoAes baseData = GsonUtil.GsonToBean(str, ResultDataNoAes.class);
                if (baseData.getData() == null || "null".equals(baseData.getData().toString())) {
                    ApiException apiException = new ApiException(new Throwable(baseData.getMsg()), baseData.getCode());
//                    MyToast.showToast(baseData.getMsg());
                    httpManagerCallback.onError(apiException);
                    return;
                }
                ResultData resultData = new ResultData();
                resultData.setDataDecryption(baseData.getData());
                switch (baseData.getCode()) {
                    case 200:
                        httpManagerCallback.onSuccess(resultData);
                        break;
                    case 0:
                        httpManagerCallback.onSuccess(resultData);
                        break;
                }
            }
        });
    }
    private void resultCode(ResultData baseData){
        ApiException apiException = new ApiException(new Throwable(baseData.getMsg()), baseData.getCode());
        switch (baseData.getCode()) {
            case 200:
                JsonParser jsonParser = new JsonParser();
                String decryptJson = AESCBCCrypt2.aesDecrypt(baseData.getData());
                LogUtil.debug(LOG_TAG,"dataDecryption:"+decryptJson);
                JsonElement jsonElement = jsonParser.parse(decryptJson);
                baseData.setDataDecryption(jsonElement);
                httpManagerCallback.onSuccess(baseData);
                break;
            case 4999://token过期
                DataManager.setAuthorization("");
                break;
            case 70014://设备交易状态异常
                httpManagerCallback.onError(apiException);
                break;
            default:
                httpManagerCallback.onError(apiException);
                break;
        }
        return;
    }

    public static void downloadFile(String downloadUrl,DownloadProgressCallBack callBack){
        EasyHttp.downLoad(downloadUrl)
                .savePath("/sdcard/lvban/update")
                .saveName("lvban_release_v1.apk")//不设置默认名字是时间戳生成的
                .execute(callBack);
    }

}