package com.ivyiot.sdkdemo.common;

import android.app.Activity;
import android.widget.Toast;

public class ToastHelper {
    public static void toast(final Activity activity, final String str) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, str, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
