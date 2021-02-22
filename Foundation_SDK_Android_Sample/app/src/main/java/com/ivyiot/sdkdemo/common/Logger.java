package com.ivyiot.sdkdemo.common;

import android.util.Log;
public class Logger {

    private static final boolean ENABLE = true;

    public static void i(String tag, String msg){
        if (ENABLE){
            Log.i(tag, msg);
        }
    }
    public static void v(String tag, String msg){
        if (ENABLE){
            Log.v(tag, msg);
        }
    }
    public static void d(String tag, String msg){
        if (ENABLE){
            Log.d(tag, msg);
        }
    }
    public static void w(String tag, String msg){
        if (ENABLE){
            Log.w(tag, msg);
        }
    }
    public static void e(String tag, String msg){
        if (ENABLE){
            Log.e(tag, msg);
        }
    }

}
