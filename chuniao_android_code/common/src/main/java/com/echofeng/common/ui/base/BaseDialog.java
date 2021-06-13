package com.echofeng.common.ui.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.echofeng.common.MyApplication;
import com.echofeng.common.R;
import com.echofeng.common.ui.widget.dialog.LoadingDialog;
import com.echofeng.common.utils.LogUtil;

import org.jetbrains.annotations.NotNull;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * createBy: echo
 * date: 2018/8/26
 * description:
 */
public abstract class BaseDialog extends Dialog {
    protected WindowManager.LayoutParams params;
    private LoadingDialog loadingDialog;
    private boolean clickable;
    private Unbinder unbinder;
    protected View dialogView;
    //    private QMUITipDialog qmuiTipDialogLoading;

    public BaseDialog(@NonNull Context context) {
        this(context, R.style.CustomDialog);
    }

    public BaseDialog(Context context, int style) {
        super(context, R.style.QMUI_Dialog);
        init();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void init() {
        dialogView = LayoutInflater.from(getContext()).inflate(provideViewId(), null);
        unbinder = ButterKnife.bind(this, dialogView);
        setContentView(dialogView);
    }

    protected abstract int provideViewId();

    protected abstract void initView();

    public void copyString(String string) {
        ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setText(string);
        Toast.makeText(getContext(), getContext().getResources().getString(R.string.toast_copy_success), Toast.LENGTH_SHORT).show();
    }

    @NotNull
    public String getStringValues(int resId) {
        return getContext().getString(resId);
    }

    /**
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    /**
     * 宽充满屏幕
     *
     * @return
     */
    public BaseDialog fullWidth() {

        if (params == null) {
            params = getWindow().getAttributes();
        }
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(params);
        return this;
    }

    /**
     * 宽充满屏幕
     *
     * @return
     */
    public BaseDialog fullHeight() {
        if (params == null) {
            params = getWindow().getAttributes();
        }
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(params);
        return this;
    }

    /**
     * 位置在屏幕最底部
     *
     * @return
     */
    public BaseDialog bottom() {
        if (params == null) {
            params = getWindow().getAttributes();
        }
        params.gravity = Gravity.BOTTOM;
        getWindow().setAttributes(params);
        return this;
    }

    /**
     * 位置在右上角
     *
     * @return
     */
    public BaseDialog rightTop() {
        if (params == null) {
            params = getWindow().getAttributes();
        }
        params.gravity = Gravity.RIGHT | Gravity.TOP;
        getWindow().setAttributes(params);
        return this;
    }

    /**
     * 位置在右上角
     *
     * @return
     */
    public BaseDialog leftTop() {
        if (params == null) {
            params = getWindow().getAttributes();
        }
        params.gravity = Gravity.LEFT | Gravity.TOP;
        getWindow().setAttributes(params);
        return this;
    }

    protected void showLoading(String title) {
        try {
            if (loadingDialog == null) {
                loadingDialog = new LoadingDialog(getContext());
                loadingDialog.setTitle(title);
                loadingDialog.setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        clickable = true;
                    }
                });
                loadingDialog.showDialog();
            } else {
                loadingDialog.setTitle(title);
                loadingDialog.showDialog();
            }
        } catch (Exception e) {
            LogUtil.error("ramon", e.toString());
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (unbinder != null) {

            try {
                unbinder.unbind();
            } catch (Exception e) {

            }

        }
        if (loadingDialog != null && this.isShowing()) {
            try {
                loadingDialog.dismiss();
            } catch (Exception e) {
                LogUtil.error("ramon", e.toString());
            }
        }
    }


    protected void dismissLoading() {
        if (loadingDialog != null && this.isShowing()) {
            try {
                loadingDialog.dismiss();
            } catch (Exception e) {
                LogUtil.error("ramon", e.toString());
            }
        }
    }
}
