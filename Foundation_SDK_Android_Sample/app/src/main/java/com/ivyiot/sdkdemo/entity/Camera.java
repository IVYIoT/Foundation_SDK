package com.ivyiot.sdkdemo.entity;

import com.ivyio.sdk.DevType;
import com.ivyio.sdk.HandleState;
import com.ivyio.sdk.IvyIoSdkJni;
import com.ivyio.sdk.P2PMode;
import com.ivyio.sdk.Result;
import com.ivyio.sdk.VideoStreamType;
import com.ivyiot.sdkdemo.common.Logger;

import java.io.Serializable;

public class Camera implements Serializable {

    /**
     * IPC连接句柄
     */
    private int handle = 0;
    /**
     * IPC uid
     */
    public String uid;
    /**
     * IPC 登陆名
     */
    public String userName = "admin";
    /**
     * IPC 登陆密码
     */
    public String password = "";
    private String TAG = "Camera";

    /**
     * IPC码流类型
     */
    private int streamType = VideoStreamType.MAIN_VIDEO_STREAM;

    /**
     * 设备能力集
     */
    private DeviceAbility mDeviceAbility;


    public Camera() {

    }

    public Camera(String uid, String userName, String password) {
        this.uid = uid;
        this.userName = userName;
        this.password = password;
    }

    public int getHandle() {
        return handle;
    }

    public void setHandle(int handle) {
        this.handle = handle;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStreamType() {
        return streamType;
    }

    public void setStreamType(int streamType) {
        this.streamType = streamType;
    }

    public DeviceAbility getDeviceAbility() {
        return mDeviceAbility;
    }

    public void setDeviceAbility(DeviceAbility deviceAbility) {
        mDeviceAbility = deviceAbility;
    }

    /**
     * 创建 ipc 连接句柄
     */
    private int create() {
        if (handle > 0) {
            return handle;
        }
        synchronized (this) {
            if (handle > 0) {
                return handle;
            }

            handle = IvyIoSdkJni.createEx(null, uid, null, userName, password, P2PMode.P2P_MODE_UDP, DevType.FOS_IPC);
            String logStr = "\n=================== IPC create info ===================\n" +
                    "===== handler        = " + handle + "\n" +
                    "===== uid            = " + uid + "\n" +
                    "===== usrName        = " + userName + "\n" +
                    "===== password       = " + password + "\n" +
                    "=======================================================\n";
            Logger.d(TAG, logStr);
            return handle;
        }
    }

    /**
     * 设备登录<p>耗时操作，不要在 UI 线程中执行。</p>
     *
     * @return @see {@link com.ivyio.sdk.Result}
     */
    public int login() {
        //调用登陆接口前，先创建句柄
        create();
        Logger.d(TAG, "login start.handle=" + handle);
        int state = IvyIoSdkJni.checkHandle(handle);
        Logger.d(TAG, "login check.handleState=" + state);
        switch (state) {
            case HandleState.HANDLE_STATE_ONLINE:
            case HandleState.HANDLE_STATE_ON_RESET:
                return Result.OK;
            case HandleState.HANDLE_STATE_USR_OR_PWD_ERR:
                return Result.USR_OR_PWD_ERR;
            default:
                break;
        }
        int timeout = 60000;
        int result = IvyIoSdkJni.login(handle, timeout);
        Logger.d(TAG, "login end.handle=" + handle + ",result=" + result);
        return result;
    }

    /**
     * 设备登出<p>句柄不会释放</p>
     */
    public void logout() {
        Logger.d(TAG, "logout start.handle=" + handle);
        IvyIoSdkJni.logout(handle);
        Logger.d(TAG, "logout end.handle=" + handle);
    }

    /**
     * 释放连接 <p>句柄销毁</p>
     */
    public void destroy() {
        logout();
        Logger.d(TAG, "destroy start.handle=" + handle);
        IvyIoSdkJni.destroy(handle);
        Logger.d(TAG, "destroy end.handle=" + handle);
        handle = 0;
    }

}
