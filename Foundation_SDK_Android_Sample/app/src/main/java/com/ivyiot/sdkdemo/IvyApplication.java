package com.ivyiot.sdkdemo;

import android.app.Application;

import com.ivyio.sdk.IvyIoSdkJni;

/**
 *
 */
public class IvyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        IvyIoSdkJni.init();
    }
}
