package com.ivyiot.sdkdemo.sdk;

import android.text.TextUtils;

import com.ivyio.sdk.IvyIoSdkJni;
import com.ivyio.sdk.Response;
import com.ivyio.sdk.Result;
import com.ivyiot.sdkdemo.common.Logger;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * SDK 透传命令 辅助类
 */
public class CmdHelper {
    private static final String TAG = "CmdHelper";

    /** 超时时长(ms) */
    private static final int COMMAND_TIMEOUT = 30000;

    /**
     * sdk 透传命令
     *
     * @param handle      连接句柄
     * @param cmd         命令值，APP 与 设备端对齐定义
     * @param cmdJsonData 命令内容，json格式的字符串
     * @return 返回命令结果，json格式
     */
    public static CommandResult sendCmd(int handle, int cmd, String cmdJsonData) {
        JSONObject returnJSON = null;
        Response response = new Response();
        Logger.d(TAG, "IvyIoSdkJni.sendCommand start.");
        int result = IvyIoSdkJni.sendCommand(handle, cmd, cmdJsonData, response, COMMAND_TIMEOUT);
        Logger.d(TAG, "IvyIoSdkJni.sendCommand end.  result=" + result + ",handler=" + handle + ",cmd=" + cmd + ",cmdData=" + cmdJsonData + ",response=" + response.resp);
        if (result == Result.OK && !TextUtils.isEmpty(response.resp)) {//每条命令的 response 都会返回  {"ret":0}
            try {
                returnJSON = new JSONObject(response.resp);
            } catch (JSONException e) {
                e.printStackTrace();
                Logger.e(TAG, "IvyIoSdkJni.sendCommand error, response format error.");
            }
        }

        CommandResult returnData = new CommandResult();
        returnData.result = result;
        returnData.json = returnJSON;
        return returnData;
    }
}
