package com.ivyiot.sdkdemo.common;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.util.List;

public class NetManager {
    /**
     * 判断Wifi是否已经打开
     *
     * @return true：打开
     */
    public static boolean isWifiEnable(Context context) {
        boolean enable = false;
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null) {
            enable = wifiManager.isWifiEnabled();
        }
        return enable;
    }
    /**
     * 获取扫描到的Wifi列表，Android 6.0及以上版本需要GPS权限:<br>
     * ACCESS_FINE_LOCATION ACCESS_FINE_LOCATION<br>
     * ACCESS_COARSE_LOCATION ACCESS_COARSE_LOCATION
     *
     */
    public static List<ScanResult> getScanResultList(Activity activity) {
        List<ScanResult> srList = null;
        if (PermissionUtil.chkPermission(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},0)) {
            WifiManager wifiManager = (WifiManager) activity.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            // 先扫描再获取，这样多次获取的wifi列表才是实时扫描得到的列表。
            if (wifiManager != null) {
                wifiManager.startScan();
                srList = wifiManager.getScanResults();
            }
        }
        return srList;
    }
    /**
     * 获取当前连接的Wifi信息
     *
     * @param context context
     * @return 当前连接着的Wifi
     */
    public static WifiInfo getCurrConnWifi(Context context) {
        WifiInfo currWifi = null;
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null) {
            currWifi = wifiManager.getConnectionInfo();
        }
        return currWifi;
    }
}
