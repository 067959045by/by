package com.echofeng.common.ui.base;

import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public abstract class PermissionActivity extends BaseActivity {
    protected abstract void onPermissionsResult(int resultCode, String[] permissions);


/*    调用案例
String[] permissions = new String[]{android.Manifest.permission.CAMERA};
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        if (!checkPermission(permissions)) {
            requestPermissions(permissions, 100);
            return true;
        }
    }*/

    /**
     * 是否获取到权限
     */
    public boolean isGetPermission(String[] permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!checkPermission(permissions)) {
//                requestPermissions(permissions, 100);
                ActivityCompat.requestPermissions(this, permissions, 100);
                return false;
            } else if (checkPermission(permissions)) {
                return true;
            } else {  // 后续有其余情形可补充
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean checkPermission(String permission) {
        return checkPermission(new String[]{permission});
    }

    public boolean checkPermission(String[] permissions) {
        boolean granted = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                granted = checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
                if (!granted) {
                    break;
                }
            }
        }
        return granted;
    }

    /**
     * 批量权限判断方法
     *
     * @param permissions
     * @return
     */
    public boolean hasPermissions(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 单个权限判断方法
     *
     * @param permissions
     * @return
     */
    public boolean hasPermission(String permissions) {
        if (ContextCompat.checkSelfPermission(this, permissions) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    /**
     * 权限请求方法，混合权限或单组权限
     *
     * @param permissions
     */
    public void requestPermission(String... permissions) {
        int code = 0;
        ActivityCompat.requestPermissions(this, permissions, code);
    }


    /**
     * 指定方法的权限请求
     *
     * @param code
     * @param permissions
     */

    public void requestPermission(int code, String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, code);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        onPermissionsResult(requestCode, permissions);
        switch (requestCode) {
            case Constans.CALENDAR_CODE:
                doCalendarPermission();
                break;
            case Constans.CALL_PHONE_CODE:
                doCallPhonePermission();
                break;
            case Constans.CAMERA_CODE:
                doCameraPermission();
                break;
            case Constans.LOCATION_CODE:
                doLocationPermission();
                break;
            case Constans.MICROPHONE_CODE:
                doMicrophonePermission();
                break;
            case Constans.READ_EXTERNAL_CODE:
                doReadExternalPermission();
                break;
            case Constans.SMS_CODE:
                doSmsPermission();
                break;
            case Constans.WRITE_EXTERNAL_CODE:
                doWriteExternalPermission();
                break;
        }
    }

    /**
     * 默认的日历权限处理
     */
    public void doCalendarPermission() {
    }

    /**
     * 默认的打电话权限处理
     */
    public void doCallPhonePermission() {
    }

    /**
     * 默认的相册权限处理
     */
    public void doCameraPermission() {
    }

    /**
     * 默认的位置获取权限处理
     */
    public void doLocationPermission() {
    }

    /**
     * 默认的麦克风权限处理
     */
    public void doMicrophonePermission() {
    }

    /**
     * 默认的读SD权限处理
     */
    public void doReadExternalPermission() {
    }

    /**
     * 默认的写SD权限处理
     */
    public void doWriteExternalPermission() {
    }

    /**
     * 默认的短信权限处理
     */
    public void doSmsPermission() {
    }


    public class Constans {
        /**
         * 权限常量相关
         */
        public static final int WRITE_EXTERNAL_CODE = 0x01;
        public static final int READ_EXTERNAL_CODE = 0x02;
        public static final int CALL_PHONE_CODE = 0x03;
        public static final int CALENDAR_CODE = 0x04;
        public static final int CAMERA_CODE = 0x05;
        public static final int LOCATION_CODE = 0x06;
        public static final int MICROPHONE_CODE = 0x07;
        public static final int SMS_CODE = 0x08;
    }

    public static class PermissionsKey {
        /**
         * 权限常量相关
         */
        public static final String[] CALENDAR = new String[]{"android.permission.READ_CALENDAR", "android.permission.WRITE_CALENDAR"};
        public static final String[] CAMERA = new String[]{"android.permission.CAMERA"};
        public static final String[] CONTACTS = new String[]{"android.permission.READ_CONTACTS", "android.permission.WRITE_CONTACTS", "android.permission.GET_ACCOUNTS"};
        public static final String[] LOCATION = new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"};
        public static final String[] MICROPHONE = new String[]{"android.permission.RECORD_AUDIO"};
        public static final String[] PHONE = new String[]{"android.permission.READ_PHONE_STATE", "android.permission.CALL_PHONE", "android.permission.READ_CALL_LOG", "android.permission.WRITE_CALL_LOG", "android.permission.ADD_VOICEMAIL", "android.permission.USE_SIP", "android.permission.PROCESS_OUTGOING_CALLS"};
        public static final String[] SENSORS = new String[]{"android.permission.BODY_SENSORS"};
        public static final String[] SMS = new String[]{"android.permission.SEND_SMS", "android.permission.RECEIVE_SMS", "android.permission.READ_SMS", "android.permission.RECEIVE_WAP_PUSH", "android.permission.RECEIVE_MMS"};
        public static final String[] STORAGE = new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};

    }
}
