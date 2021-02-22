package com.ivyiot.sdkdemo.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ivyiot.sdkdemo.R;
import com.ivyiot.sdkdemo.common.NetManager;
import com.ivyiot.sdkdemo.common.PermissionUtil;
import com.ivyiot.sdkdemo.common.ToastHelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import voice.encoder.VoiceData;
import voice.encoder.VoicePlayer;
import voice.encoder.VoicePlayerListener;

public class VoiceDeliverActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * 申请权限编号
     */
    private static final int PERMISSIONS_WIFI_SSID = 2;
    private Button mSend;
    private VoicePlayer player;
    private EditText mUid;
    private EditText mSsid;
    private EditText mPwd;
    /**
     * Android 8.0及以上版本没有允许定位权限或者关闭了定位，ssid获取都为<unknown ssid>
     */
    private static final String UNKNOW_SSID = "<unknown ssid>";
    /**
     * 当前是否为foscam ipc
     */
    private boolean mFoscamIpc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_deliver);
        mSend = findViewById(R.id.bt_send_voice);
        mUid = findViewById(R.id.et_voice_send_uid);
        mSsid = findViewById(R.id.et_ssid);
        mPwd = findViewById(R.id.et_wifi_pwd);
        mSend.setOnClickListener(this);
        mFoscamIpc = getIntent().getBooleanExtra("foscamIpc", false);
        mUid.setVisibility(mFoscamIpc ? View.VISIBLE : View.GONE);
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

            } else {
                ToastHelper.toast(this, "请连接到无线网络");
            }
        } else {
            ToastHelper.toast(this, "请打开无线网络");
        }
    }

    @Override
    public void onClick(View v) {
        if (!validInput()) {
            return;
        }
        //获取声波信息
        final String[] soundWaveContents = mFoscamIpc ? VoiceData.getSoundWaveEncryptContent(
                mUid.getText().toString(), mSsid.getText().toString(), mPwd.getText().toString()) :
                VoiceData.getSoundWaveEncryptContent_ivy(mSsid.getText().toString(), mPwd.getText().toString());


        player = new VoicePlayer(44100, 8000);
        player.setListener(new VoicePlayerListener() {
            int playCount = 0;

            @Override
            public void onPlayStart() {

            }

            @Override
            public void onPlayEnd() {
                playCount++;
                if (playCount < soundWaveContents.length) {
                    player.play(soundWaveContents[playCount]);
                }

            }
        });
        if (soundWaveContents.length > 0) {
            player.play(soundWaveContents[0]);
        }

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
}
