package com.ivyiot.sdkdemo.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ivyio.sdk.DevType;
import com.ivyio.sdk.DiscoveryNode;
import com.ivyio.sdk.IvyIoSdkJni;
import com.ivyiot.sdkdemo.R;
import com.ivyiot.sdkdemo.common.Global;
import com.ivyiot.sdkdemo.common.Logger;
import com.ivyiot.sdkdemo.common.NetManager;
import com.ivyiot.sdkdemo.common.PermissionUtil;
import com.ivyiot.sdkdemo.common.ToastHelper;
import com.ivyiot.sdkdemo.zxing.encode.QRCodeEncoder;

import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class QrcodeActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 申请权限编号
     */
    private static final int PERMISSIONS_WIFI_SSID = 2;
    /**
     * Wifi加密类型
     */
    private byte mWifiEncryptType = (byte) 255;

    /**
     * Android 8.0及以上版本没有允许定位权限或者关闭了定位，ssid获取都为<unknown ssid>
     */
    private static final String UNKNOW_SSID = "<unknown ssid>";
    private EditText mSsid;
    private EditText mPwd;
    private Button mGenCode;
    private ImageView mQrCode;
    private EditText mUid;
    private String TAG = "QrcodeActivity";
    private boolean mFoscamIpc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        mSsid = findViewById(R.id.et_ssid);
        mPwd = findViewById(R.id.et_wifi_pwd);
        mGenCode = findViewById(R.id.bt_gen_qr_code);
        mQrCode = findViewById(R.id.iv_scan_generate_qr);
        mUid = findViewById(R.id.et_uid);
        mFoscamIpc = getIntent().getBooleanExtra("foscamIpc", false);
        mUid.setVisibility(mFoscamIpc ? View.VISIBLE : View.GONE);
        mGenCode.setOnClickListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //8.0以上获取ssid需要用户授权。
            if (PermissionUtil.chkPermission(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_WIFI_SSID)) {
                getCurrWifiInfo();
            }
        } else {
            getCurrWifiInfo();
        }
    }

    /**
     * 获取手机当前连接着的wifi信息
     */
    private void getCurrWifiInfo() {
        boolean isWifiEnable = NetManager.isWifiEnable(this);
        if (isWifiEnable) {
            WifiInfo wifiInfo = NetManager.getCurrConnWifi(this);
            if (wifiInfo != null) {
                String ssid = wifiInfo.getSSID();
                if (ssid.startsWith("\"") && ssid.endsWith("\"")) {
                    ssid = ssid.substring(1, ssid.length() - 1);
                }
                if (!UNKNOW_SSID.equals(ssid)) {
                    mSsid.setText(ssid);
                } else {
                    ToastHelper.toast(this, "请打开GPS开关以获取WiFi名称");
                }
                List<ScanResult> srList = NetManager.getScanResultList(this);
                if (null != srList) {
                    for (ScanResult sr : srList) {
                        if (sr.SSID.equals(ssid)) {
                            String capabilities = sr.capabilities;
                            if (capabilities.contains("WEP")) {
                                mWifiEncryptType = 1;
                            } else if (capabilities.contains("WPA2-") && capabilities.contains("WPA-")) {
                                mWifiEncryptType = 4;
                            } else if (capabilities.contains("WPA2-")) {
                                mWifiEncryptType = 3;
                            } else if (capabilities.contains("WPA-")) {
                                mWifiEncryptType = 2;
                            } else {// 无密码
                                mWifiEncryptType = 0;
                            }
                            break;
                        }
                    }
                }

            } else {
                ToastHelper.toast(this, "请连接到无线网络");
            }
        } else {
            ToastHelper.toast(this, "请打开无线网络");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_WIFI_SSID) {

            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrWifiInfo();
            } else {
                ToastHelper.toast(this, "权限未授权");
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onClick(View v) {
        if (validInput()) {
            startScan(mSsid.getText().toString(), mPwd.getText().toString());
        }

    }

    /**
     * 开始配网，需要把生成的二维码对准摄像机，同时局域网内扫描，请留意摄像机语音提示
     */
    private void startScan(final String ssid, final String pwd) {
        Global.es.submit(new Runnable() {
                             @Override
                             public void run() {
                                 int smallerDimension = 0;
                                 WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                                 if (manager != null) {
                                     Display display = manager.getDefaultDisplay();
                                     Point displaySize = new Point();
                                     display.getSize(displaySize);
                                     int width = displaySize.x;
                                     int height = displaySize.y;
                                     smallerDimension = width < height ? width : height;
                                 }
                                 smallerDimension = smallerDimension * 7 / 8;

                                 String encodeResult = "<S>" + ssid + "</S>"
                                         + "<P>" + pwd + "</P>"
                                         + "<T>" + (mWifiEncryptType == -1 ? "255" : mWifiEncryptType) + "</T>";
                                 QRCodeEncoder QRCodeEncoder = new QRCodeEncoder(QrcodeActivity.this, encodeResult, smallerDimension, false);
                                 final Bitmap bitmap = QRCodeEncoder.encodeAsBitmap();
                                 QrcodeActivity.this.runOnUiThread(new Runnable() {
                                     @Override
                                     public void run() {
                                         if (bitmap != null) {
                                             mQrCode.setImageBitmap(bitmap);
                                         }
                                     }
                                 });
                                 long start = System.currentTimeMillis();
                                 while (true) {
                                     String uid = mUid.getText().toString();
                                     DiscoveryNode device = mFoscamIpc ? discoveryDeviceInWLAN(uid)
                                             : discoveryDeviceInWLAN();
                                     if (device != null) {
                                         Logger.d(TAG, "设备配网成功");
                                         ToastHelper.toast(QrcodeActivity.this, "设备配网成功");
                                         break;
                                     }

                                     if (System.currentTimeMillis() - start >= 90000) {
                                         Logger.d(TAG, "扫码超时");
                                         ToastHelper.toast(QrcodeActivity.this, "扫码超时");
                                         break;
                                     }
                                     SystemClock.sleep(20);
                                 }
                             }
                         }
        );
    }

    /**
     * 验证输入框内容的合法性
     */
    private boolean validInput() {
        String ssid = mSsid.getText().toString();
        if (TextUtils.isEmpty(ssid) || UNKNOW_SSID.equals(ssid)) {
            ToastHelper.toast(this, "请输入wifi名称");
            return false;
        }
        String uid = mUid.getText().toString();
        if (mFoscamIpc && TextUtils.isEmpty(uid)) {
            ToastHelper.toast(this, "请输入uid");
            return false;
        }
        return true;
    }

    /**
     * 局域网内搜索指定uid设备
     *
     * @param uid 要搜索的设备uid
     * @return 是否在局域网内搜索到
     */
    public DiscoveryNode discoveryDeviceInWLAN(String uid) {
        DiscoveryNode device = null;
        String lowerUid = uid.toLowerCase(Locale.US);
        DiscoveryNode[] devList = new DiscoveryNode[50];
        int outCount = IvyIoSdkJni.discovery(devList, devList.length);
        if (outCount <= devList.length) {
            for (int i = 0; i < outCount; i++) {

                DiscoveryNode temp = devList[i];
                if (temp.type == DevType.FOS_IPC || temp.type == DevType.IVY_IPC) {
                    String deviceUID = temp.uid.toLowerCase(Locale.US);
                    if ((deviceUID.equals(lowerUid))
                            // 这两种情况表示ipc还未分配到正确的ip
                            && !("0.0.0.0".equals(temp.ip)) && !("192.168.233.233".equals(temp.ip))) {
                        device = temp;
                        break;
                    }
                }
            }
        }
        return device;
    }


    public DiscoveryNode discoveryDeviceInWLAN() {
        DiscoveryNode[] devList = new DiscoveryNode[50];
        int outCount = IvyIoSdkJni.discovery(devList, devList.length);
        for (int i = 0; i < outCount; i++) {
            if (devList != null) {
                if (devList[i].type == DevType.IVY_IPC && devList[i].state != 0) {
                    return devList[i];
                }
            }
        }
        return null;
    }
}
