package com.ivyiot.sdkdemo.common;

import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

public class PermissionUtil {
    /**
     * 检查权限是否已授权，如果没有授权，需要请求权限
     *
     * @param activity      activity
     * @param permissions   权限组
     * @param permissonCode 在请求授权的回调方法中标识申请的是哪个权限
     * @return true 已授权，直接调用后续业务逻辑方法；false 未授权，需要在授权回调方法中调用业务逻辑方法
     */
    public static boolean chkPermission(Activity activity, String[] permissions, int permissonCode) {

        for (String per : permissions) {
            if (ActivityCompat.checkSelfPermission(activity, per) != PackageManager.PERMISSION_GRANTED) {
                requestPermission(activity, permissions, permissonCode);
                return false;
            }
        }

        return true;
    }

    private static void requestPermission(final Activity activity, final String[] permissions, final int permissonCode) {
        boolean shouldShow = false;
        for (String per : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, per)) {
                shouldShow = true;
                break;
            }
        }

        if (shouldShow) {
            ActivityCompat.requestPermissions(activity, permissions, permissonCode);
        } else {
            ActivityCompat.requestPermissions(activity, permissions, permissonCode);
        }
    }
}
