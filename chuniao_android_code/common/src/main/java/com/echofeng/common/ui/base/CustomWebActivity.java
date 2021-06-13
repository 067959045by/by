package com.echofeng.common.ui.base;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.echofeng.common.R;
import com.echofeng.common.utils.LogUtil;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;

/**
 * @ProjectName: NBN
 * @ClassName: CustomWebActivity
 * @Description: 通用WebActivity
 * @CreateDate: 2020/9/17 16:51
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/9/17 16:51
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class CustomWebActivity extends BaseActivity implements View.OnClickListener {
    private View app_top_bar;
    private TextView toolbar_title;
    private FrameLayout flWeb;
    private BridgeWebView mWebView;
    private AgentWeb agentWeb;
    private String url;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_custom_web;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initView() {
        app_top_bar = findViewById(R.id.app_top_bar);
        toolbar_title = findViewById(R.id.include_top_tv_title);
        flWeb = findViewById(R.id.fl_web_container);
        Intent intent = getIntent();
        if (intent != null) {
            url = intent.getStringExtra("url");
            Log.e("ramon", "url:" + url);
            String title = intent.getStringExtra("title");
            boolean isHT = intent.getBooleanExtra("isHideTitle", false);
            if (isHT) {
                hideBar();
            }
            setTitle(title);
        }
        mWebView = new BridgeWebView(this);
//        mWebView.clearFocus();
//        mWebView.setFocusable(false);//解决未加载完成滑动webview，加载完成后webview又自动回到顶部的问题
        WebSettings settings = mWebView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setDomStorageEnabled(false);
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setAllowContentAccess(true);
        settings.setAllowFileAccess(false);
        settings.setAllowFileAccessFromFileURLs(false);
        settings.setAllowUniversalAccessFromFileURLs(false);
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setAppCacheEnabled(false);
        mWebView.addJavascriptInterface(new JS2AndroidUtil(), "android");
        mWebView.setLayerType(View.LAYER_TYPE_NONE, null);//加上此方法解决webview无法确定高度导致崩溃问题

        agentWeb = AgentWeb.with(this)//
                .setAgentWebParent(flWeb, new FrameLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setWebChromeClient(chromeClient)
                .setWebViewClient(mWebViewClient)
                .createAgentWeb()
                .ready()
                .go(url);
    }

    private WebViewClient mWebViewClient = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            LogUtil.error("ramon",request.getUrl().toString());
            if (request.getUrl().toString().contains("finishPage")) {
                finish();
                return true;
            }
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //do you  work
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            String title = view.getTitle(); //网页标题
            if (!TextUtils.isEmpty(title) && !title.contains("http")) {
                toolbar_title.setText(title);
            }
        }
    };

    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;

    class chromeBrigeClient extends BridgeWebViewClient {
        public chromeBrigeClient(BridgeWebView webView) {
            super(webView);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            String title = view.getTitle(); //网页标题
            if (!TextUtils.isEmpty(title) && !title.contains("http")) {
                toolbar_title.setText(title);
            }
        }
    }


    WebChromeClient chromeClient = new WebChromeClient() {
        // For Android < 3.0
        public void openFileChooser(ValueCallback<Uri> valueCallback) {
            mUploadMessage = valueCallback;
            openImageChooserActivity();
        }

        // For Android  >= 3.0
        public void openFileChooser(ValueCallback valueCallback, String acceptType) {
            mUploadMessage = valueCallback;
            openImageChooserActivity();
        }

        //For Android  >= 4.1
        public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {
            mUploadMessage = valueCallback;
            openImageChooserActivity();
        }

        // For Android >= 5.0
        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            mUploadCallbackAboveL = filePathCallback;
            openImageChooserActivity();
            return true;
        }
    };

    class JS2AndroidUtil extends Object {
        @JavascriptInterface
        public void onFinishActivity() {
            finish();
        }
    }

    private final int FILE_CHOOSER_RESULT_CODE = 102;

    private void openImageChooserActivity() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "图片选择"), FILE_CHOOSER_RESULT_CODE);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent intent) {
        if (requestCode != FILE_CHOOSER_RESULT_CODE || mUploadCallbackAboveL == null)
            return;
        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (intent != null) {
                String dataString = intent.getDataString();
                ClipData clipData = intent.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        mUploadCallbackAboveL.onReceiveValue(results);
        mUploadCallbackAboveL = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_CHOOSER_RESULT_CODE) {
            if (null == mUploadMessage && null == mUploadCallbackAboveL) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (mUploadCallbackAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (mUploadMessage != null) {
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }
        }
    }
    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.include_top_iv_back) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
            } else {
                finish();
            }
        }
    }
}
