package com.ivyiot.sdkdemo.sdk;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

import com.ivyio.sdk.Event;
import com.ivyio.sdk.IvyIoSdkJni;
import com.ivyio.sdk.Result;
import com.ivyiot.sdkdemo.common.Logger;

public class GetEventThread extends Thread {
    private static final String TAG = "GetEventThread";
    /**
     * 事件封装类
     */
    private Event mEventData = new Event();
    /**
     * 获取事件的IPC句柄
     */
    private int mIpcHandler;
    /**
     * 事件处理器
     */
    private Handler mHandler;
    /**
     * 运行标记
     */
    private boolean mRunnable = true;

    public GetEventThread(int ipcHandler, Handler handler) {
        mIpcHandler = ipcHandler;
        this.mHandler = handler;
    }

    /**
     * 设置设备连接句柄
     *
     * @param ipcHandler sdk 设备连接句柄
     */
    public void setIpcHandler(int ipcHandler) {
        this.mIpcHandler = ipcHandler;
    }

    /**
     * 设置界面消息句柄
     *
     * @param handler mHandler
     */
    public void setHandler(Handler handler) {
        this.mHandler = handler;
    }

    @Override
    public void run() {
        mEventData.data = null;
        while (mRunnable) {
            Logger.d(TAG, "get event thread running");
            int re = IvyIoSdkJni.getEvent(mIpcHandler, mEventData);
            if (re == Result.OK) {
                String eventStr = mEventData.data;
                Logger.d(TAG, "event msg id:" + mEventData.id + " msg:" + eventStr);
                if (mHandler != null) {
                    Message msg = new Message();
                    msg.what = mEventData.id;
                    msg.obj = eventStr;
                    mHandler.sendMessage(msg);
                }
            } else {//获取事件失败才休眠，否则一直取。
                //sleep 20 ms
                SystemClock.sleep(20);
            }
        }
    }

    public void stopRun() {
        mRunnable = false;
    }
}
