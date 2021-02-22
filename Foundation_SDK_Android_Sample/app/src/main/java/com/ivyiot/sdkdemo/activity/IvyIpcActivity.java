package com.ivyiot.sdkdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ivyio.sdk.ClosePlaybackArgsType0;
import com.ivyio.sdk.DiscoveryNode;
import com.ivyio.sdk.GetPlaybackListArgsType0;
import com.ivyio.sdk.IvyIoSdkJni;
import com.ivyio.sdk.OpenPlaybackArgsType0;
import com.ivyio.sdk.PlaybackRecordListArgsArrayType0;
import com.ivyio.sdk.PlaybackRecordListInfoArgsType0;
import com.ivyio.sdk.Response;
import com.ivyio.sdk.Result;
import com.ivyio.sdk.VideoStreamType;
import com.ivyiot.sdkdemo.R;
import com.ivyiot.sdkdemo.common.Global;
import com.ivyiot.sdkdemo.common.Logger;
import com.ivyiot.sdkdemo.common.ToastHelper;
import com.ivyiot.sdkdemo.entity.DeviceAbility;
import com.ivyiot.sdkdemo.sdk.CmdList;
import com.ivyiot.sdkdemo.video.SDVideoSurfaceView;
import com.ivyiot.sdkdemo.video.VideoSurfaceView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;

public class IvyIpcActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = "IvyIpcActivity";
    private Button mSearch;
    private EditText mUid;
    private EditText mUserName;
    private EditText mPwd;
    private Button mLogin;
    private Button mLogout;

    private Button mH264Play;
    private Button mRgbPlay;
    private VideoSurfaceView mVideoPlayer;
    private Button mStopPlay;
    private Button mVoiceDeliver;
    private Button mQrCodeScan;
    private Button mModify;
    private Button mPlayBack;
    private Button mVideoSearch;
    private ArrayList<PlaybackRecordListInfoArgsType0> mVideoArr;
    private SDVideoSurfaceView mSDVideoSurfaceView;
    private Button mStopPlayback;

    private Handler mHandler;
    private Button mAbility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ivy_ipc);
        mHandler = new Handler();
        mSearch = findViewById(R.id.bt_search);
        mLogin = findViewById(R.id.bt_login);
        mLogout = findViewById(R.id.bt_logout);
        mUid = findViewById(R.id.et_uid);
        mUserName = findViewById(R.id.et_userName);
        mPwd = findViewById(R.id.et_pwd);
        mH264Play = findViewById(R.id.bt_h264_stream);
        mRgbPlay = findViewById(R.id.bt_rgb_stream);
        mVideoPlayer = findViewById(R.id.videoPlayer);
        mStopPlay = findViewById(R.id.bt_stop_play);
        mVoiceDeliver = findViewById(R.id.bt_voice_deliver);
        mQrCodeScan = findViewById(R.id.bt_qr_code_scan);
        mModify = findViewById(R.id.bt_modify);
        mPlayBack = findViewById(R.id.bt_video_playback);
        mVideoSearch = findViewById(R.id.bt_video_search);
        mSDVideoSurfaceView = findViewById(R.id.video_playback);
        mStopPlayback = findViewById(R.id.bt_stop_playback);
        mAbility = findViewById(R.id.bt_get_ability);


        mSearch.setOnClickListener(this);
        mLogin.setOnClickListener(this);
        mLogout.setOnClickListener(this);
        mH264Play.setOnClickListener(this);
        mRgbPlay.setOnClickListener(this);
        mStopPlay.setOnClickListener(this);
        mVoiceDeliver.setOnClickListener(this);
        mQrCodeScan.setOnClickListener(this);
        mModify.setOnClickListener(this);
        mPlayBack.setOnClickListener(this);
        mVideoSearch.setOnClickListener(this);
        mStopPlayback.setOnClickListener(this);
        mAbility.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mUserName.setText(Global.mIvyCamera.getUserName());
        mPwd.setText(Global.mIvyCamera.getPassword());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_search:
                doSearch();

                break;
            case R.id.bt_login:
                doLogin();

                break;
            case R.id.bt_logout:
                doRelease();
                break;
            case R.id.bt_modify:
                Intent intent = new Intent(this, ModifyAccountActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_h264_stream:
                doPlay(true);
                mH264Play.setEnabled(false);
                mRgbPlay.setEnabled(false);
                mStopPlay.setEnabled(true);
                break;
            case R.id.bt_rgb_stream:
                doPlay(false);
                mH264Play.setEnabled(false);
                mRgbPlay.setEnabled(false);
                mStopPlay.setEnabled(true);
                break;
            case R.id.bt_stop_play:
                doStop();
                mH264Play.setEnabled(true);
                mRgbPlay.setEnabled(true);
                mStopPlay.setEnabled(false);
                break;
            case R.id.bt_voice_deliver:
                startActivity(new Intent(this, VoiceDeliverActivity.class));
                break;
            case R.id.bt_qr_code_scan:
                startActivity(new Intent(this, QrcodeActivity.class));
                break;
            case R.id.bt_video_search:
                doVideoSearch();
                break;
            case R.id.bt_video_playback:
                if (null == mVideoArr || mVideoArr.size() == 0) {
                    ToastHelper.toast(this, "请先进行SD卡搜索");
                    return;
                }
                mPlayBack.setEnabled(false);
                mStopPlayback.setEnabled(true);
                doStartPlayback();
                break;

            case R.id.bt_stop_playback:
                mPlayBack.setEnabled(true);
                mStopPlayback.setEnabled(false);
                doStopPlayback();
                break;
            case R.id.bt_get_ability:
                doGetDeviceAbility();
                break;

            default:
                break;
        }

    }

    /**
     * 结束sd卡回放
     */
    private void doStopPlayback() {
        mSDVideoSurfaceView.setVisibility(View.GONE);
        ClosePlaybackArgsType0 closePlaybackArgs = new ClosePlaybackArgsType0();
        mSDVideoSurfaceView.closePlaybackVideo(Global.mIvyCamera, closePlaybackArgs);
        mHandler.removeCallbacks(checkPlaybackState);
    }

    /**
     * 开始sd卡回放
     */
    private void doStartPlayback() {
        mSDVideoSurfaceView.setVisibility(View.VISIBLE);
        //播放录像列表的第一个录像
        PlaybackRecordListInfoArgsType0 mPlaybackRecordListInfoArgsType0 = mVideoArr.get(0);
        OpenPlaybackArgsType0 playbackArgsType0 = new OpenPlaybackArgsType0();
        playbackArgsType0.eTime = mPlaybackRecordListInfoArgsType0.eTime;
        playbackArgsType0.sTime = mPlaybackRecordListInfoArgsType0.sTime;
        playbackArgsType0.streamType = VideoStreamType.MAIN_VIDEO_STREAM;
        mSDVideoSurfaceView.openPlaybackVideo(Global.mIvyCamera, playbackArgsType0);
        //开启检测线程检测录像播放进度
        mHandler.postDelayed(checkPlaybackState, 1000);
    }

    /**
     * 搜索局域网内的设备信息
     */
    private void doSearch() {
        DiscoveryNode[] contain = new DiscoveryNode[50];
        int discoveryCount = IvyIoSdkJni.discovery(contain, contain.length);
        Logger.d(TAG, "搜索到设备数量:" + discoveryCount);
        if (discoveryCount > 0 && null != contain[0]) {
            mUid.setText(contain[0].uid);
        }
    }

    /**
     * 登陆设备
     */
    private void doLogin() {
        String uid = mUid.getText().toString();
        String userName = mUserName.getText().toString();
        String pwd = mPwd.getText().toString();
        if (TextUtils.isEmpty(uid) || TextUtils.isEmpty(userName)) {
            ToastHelper.toast(this, "请输入UID或者用户名");
            return;
        }
        Global.mIvyCamera.setUid(uid);
        Global.mIvyCamera.setUserName(userName);
        Global.mIvyCamera.setPassword(pwd);
        Global.es.submit(new Runnable() {
            @Override
            public void run() {
                //login为耗时操作，必须子线程调用
                final int ret = Global.mIvyCamera.login();
                Logger.d(TAG, "login result:" + ret);
                switch (ret) {
                    case Result.OK:
                        ToastHelper.toast(IvyIpcActivity.this, "登陆成功");
                        break;
                    case Result.USR_OR_PWD_ERR:
                        ToastHelper.toast(IvyIpcActivity.this, "用户名密码错误");
                        //用户名密码错误时，需要释放句柄重新登陆
                        doRelease();
                        break;
                    case Result.OK_ON_RESET_STATE:
                        ToastHelper.toast(IvyIpcActivity.this, "重置状态，请修改用户名密码");
                        break;
                    //更多错误类型，请查看com.ivyio.sdk.Result
                    default:
                        ToastHelper.toast(IvyIpcActivity.this, "登陆失败");
                        break;
                }
            }
        });
    }

    /**
     * 登出设备
     */
    private void doRelease() {
        Global.es.submit(new Runnable() {
            @Override
            public void run() {
                Global.mIvyCamera.destroy();
                Logger.d(TAG, "release camera!");
            }
        });

    }

    /**
     * 开始直播
     */
    private void doPlay(boolean h264Stream) {
        mVideoPlayer.setVisibility(View.VISIBLE);
        mVideoPlayer.openVideo(h264Stream, Global.mIvyCamera);
    }

    /**
     * 结束直播
     */
    private void doStop() {
        mVideoPlayer.setVisibility(View.GONE);
        mVideoPlayer.closeVideo(Global.mIvyCamera);
    }

    private static final int SDCARD_QUERY_RECORD_LENGTH = 40;

    /**
     * sd卡录像搜索
     */
    private void doVideoSearch() {
        if (Global.mIvyCamera.getHandle() == 0) {
            ToastHelper.toast(IvyIpcActivity.this, "请先登录设备");
            return;
        }
        Global.es.execute(new Runnable() {
            @Override
            public void run() {
                final int loginRes = Global.mIvyCamera.login();
                if (Result.OK == loginRes) {
                    String data = new JSONObject().toString();
                    Response raw = new Response();

                    int re = IvyIoSdkJni.sendCommand(Global.mIvyCamera.getHandle(), CmdList.IVY_CTRL_MSG_GET_SD_CARD_INFO, data, raw, Global.TIMEOUT);
                    if (re == Result.OK && !TextUtils.isEmpty(raw.resp)) {
                        JSONObject jsonObject;
                        try {
                            jsonObject = new JSONObject(raw.resp);
                            int ret = jsonObject.getInt("ret");
                            if (ret == Result.OK && !jsonObject.isNull("isExist")) {
                                if (jsonObject.getInt("isExist") == 0) {
                                    ToastHelper.toast(IvyIpcActivity.this, "没有SD卡");
                                    return;
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        ToastHelper.toast(IvyIpcActivity.this, "获取sd卡信息失败");
                        return;
                    }
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
                    int startTime = (int) (calendar.getTimeInMillis() / 1000);
                    GetPlaybackListArgsType0 getPlaybackListArgsType0 = new GetPlaybackListArgsType0();
                    getPlaybackListArgsType0.sTime = startTime;
                    getPlaybackListArgsType0.eTime = (startTime + 1440 * 60 - 1);
                    getPlaybackListArgsType0.startNo = 0;
                    getPlaybackListArgsType0.type = 511;
                    getPlaybackListArgsType0.cnt = SDCARD_QUERY_RECORD_LENGTH;
                    final PlaybackRecordListArgsArrayType0 playbackRecordListArgsType0 = new PlaybackRecordListArgsArrayType0();
                    int ret = IvyIoSdkJni.getPlaybackRecordList(Global.mIvyCamera.getHandle(), getPlaybackListArgsType0, playbackRecordListArgsType0, Global.TIMEOUT, 0);

                    if (ret == Result.OK) {
                        mVideoArr = new ArrayList<>();
                        int totalCnt;// 录像总数
                        int currCnt;//当前以及查询到的录像数
                        if (null != playbackRecordListArgsType0.list && playbackRecordListArgsType0.list.length > 0 && null != playbackRecordListArgsType0.list[0]) {
                            totalCnt = playbackRecordListArgsType0.list[0].totalCnt;
                            currCnt = playbackRecordListArgsType0.list[0].curCnt;

                            for (int i = 0; i < currCnt; i++) {
                                PlaybackRecordListInfoArgsType0 recordInfo = playbackRecordListArgsType0.list[0].list[i];
                                mVideoArr.add(recordInfo);
                            }
                            if (totalCnt > SDCARD_QUERY_RECORD_LENGTH) {
                                do {
                                    getPlaybackListArgsType0.startNo = currCnt;
                                    getPlaybackListArgsType0.eTime = (startTime + 1440 * 60 - 1);
                                    getPlaybackListArgsType0.sTime = startTime;
                                    getPlaybackListArgsType0.cnt = SDCARD_QUERY_RECORD_LENGTH;
                                    getPlaybackListArgsType0.type = 511;
                                    ret = IvyIoSdkJni.getPlaybackRecordList(Global.mIvyCamera.getHandle(), getPlaybackListArgsType0, playbackRecordListArgsType0, Global.TIMEOUT, 0);
                                    if (Result.OK == ret) {
                                        int temp_curCnt = playbackRecordListArgsType0.list[0].curCnt;
                                        ArrayList<PlaybackRecordListInfoArgsType0> tempVideoArr = new ArrayList<>();
                                        for (int i = 0; i < temp_curCnt; i++) {
                                            PlaybackRecordListInfoArgsType0 recordInfo = playbackRecordListArgsType0.list[0].list[i];
                                            tempVideoArr.add(recordInfo);
                                        }
                                        currCnt += temp_curCnt;
                                        mVideoArr.addAll(tempVideoArr);

                                    } else {
                                        break;
                                    }
                                } while (currCnt < totalCnt);

                            }
                            ToastHelper.toast(IvyIpcActivity.this, "SD卡录像数量:" + mVideoArr.size());
                        } else {
                            ToastHelper.toast(IvyIpcActivity.this, "没有录像");
                        }
                    } else {
                        ToastHelper.toast(IvyIpcActivity.this, "搜索失败");
                    }

                } else {
                    ToastHelper.toast(IvyIpcActivity.this, "登陆失败");
                }
            }
        });

    }

    /**
     * 获取设备能力集
     */
    private void doGetDeviceAbility() {
        if (Global.mIvyCamera.getHandle() == 0) {
            ToastHelper.toast(IvyIpcActivity.this, "请先登录设备");
            return;
        }
        Global.es.execute(new Runnable() {
            @Override
            public void run() {
                final int loginRes = Global.mIvyCamera.login();
                if (Result.OK == loginRes) {
                    try {
                        Response response = new Response();
                        int re2 = IvyIoSdkJni.sendCommand(Global.mIvyCamera.getHandle(), CmdList.IVY_CTRL_MSG_GET_DEVABILITY, "", response, Global.TIMEOUT);
                        Logger.d("", "GET_DEVABILITY:" + response.resp);
                        if (re2 == Result.OK && !TextUtils.isEmpty(response.resp)) {
                            JSONObject devAbility = new JSONObject(response.resp);
                            int ret = devAbility.getInt("ret");
                            if (ret == Result.OK) {
                                DeviceAbility deviceAbility = new DeviceAbility();
                                deviceAbility.reserve = new int[4];
                                deviceAbility.reserveFlag = new int[4];
                                if (!devAbility.isNull("val1")) {
                                    deviceAbility.reserve[0] = devAbility.getInt("val1");
                                }
                                if (!devAbility.isNull("val2")) {
                                    deviceAbility.reserve[1] = devAbility.getInt("val2");
                                    deviceAbility.isEnableAudioDetect = getBData(deviceAbility.reserve[1], 1);
                                    deviceAbility.isEnableTemperatureDetect = getBData(deviceAbility.reserve[1], 2);
                                    deviceAbility.isEnableHumidityDetect = getBData(deviceAbility.reserve[1], 3);
                                    deviceAbility.isEnablePIRDetect = getBData(deviceAbility.reserve[1], 4);

                                }
                                if (!devAbility.isNull("val3")) {
                                    deviceAbility.reserve[2] = devAbility.getInt("val3");
                                }
                                if (!devAbility.isNull("val4")) {
                                    deviceAbility.reserve[3] = devAbility.getInt("val4");
                                    deviceAbility.isEnableLedOnOff = getBData(deviceAbility.reserve[3], 4);
                                    deviceAbility.isEnableNightLight = getBData(deviceAbility.reserve[3], 5);
                                    deviceAbility.isEnableTimeStamp = getBData(deviceAbility.reserve[3], 10);
                                    deviceAbility.isEnableDevName = getBData(deviceAbility.reserve[3], 11);
                                }
                                if (!devAbility.isNull("val5")) {
                                    deviceAbility.reserveFlag[0] = devAbility.getInt("val5");
                                }
                                if (!devAbility.isNull("val6")) {
                                    deviceAbility.reserveFlag[1] = devAbility.getInt("val6");
                                }
                                if (!devAbility.isNull("val7")) {
                                    deviceAbility.reserveFlag[2] = devAbility.getInt("val7");
                                }
                                if (!devAbility.isNull("val8")) {
                                    deviceAbility.reserveFlag[3] = devAbility.getInt("val8");
                                }
                                if (!devAbility.isNull("val0")) {
                                    deviceAbility.sdFlag = getBData(devAbility.getInt("val0"), 0);
                                    deviceAbility.outdoorFlag = getBData(devAbility.getInt("val0"), 1);
                                    deviceAbility.ptFlag = getBData(devAbility.getInt("val0"), 2);
                                    deviceAbility.zoomFlag = getBData(devAbility.getInt("val0"), 3);
                                    deviceAbility.rs485Flag = getBData(devAbility.getInt("val0"), 4);
                                    deviceAbility.ioAlarmFlag = getBData(devAbility.getInt("val0"), 5);
                                    deviceAbility.onvifFlag = getBData(devAbility.getInt("val0"), 6);
                                    deviceAbility.p2pFlag = getBData(devAbility.getInt("val0"), 7);
                                    deviceAbility.wpsFlag = getBData(devAbility.getInt("val0"), 8);
                                    deviceAbility.audioFlag = getBData(devAbility.getInt("val0"), 9);
                                    deviceAbility.talkFlag = getBData(devAbility.getInt("val0"), 10);
                                    deviceAbility.bDuplexVoice = (getBData(devAbility.getInt("val0"), 11) == 1);
                                    deviceAbility.bStreamMode = (getBData(devAbility.getInt("val0"), 13) == 1);
                                    deviceAbility.bMotionArea = (getBData(devAbility.getInt("val0"), 14) == 1);
                                    deviceAbility.bOneKeyCall = (getBData(devAbility.getInt("val0"), 15) == 1);
                                    deviceAbility.bNightVisionSchedule = (getBData(devAbility.getInt("val0"), 16) == 1);

                                }
                                Global.mIvyCamera.setDeviceAbility(deviceAbility);
                            }
                            ToastHelper.toast(IvyIpcActivity.this, "能力集获取成功");
                        } else {
                            ToastHelper.toast(IvyIpcActivity.this, "能力集获取失败");
                        }
                    } catch (JSONException exception) {
                        ToastHelper.toast(IvyIpcActivity.this, "JSONException:" + exception.getMessage());
                    }


                } else {
                    ToastHelper.toast(IvyIpcActivity.this, "登陆失败");
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(checkPlaybackState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        doRelease();
    }


    private Runnable checkPlaybackState = new Runnable() {
        @Override
        public void run() {

            if (mSDVideoSurfaceView.getFramePTS() == -1L) {
                return;
            }
            if (mSDVideoSurfaceView.getFrameTag() == 1178944080) {
                ToastHelper.toast(IvyIpcActivity.this, "播放结束");
                return;
            }
            mHandler.postDelayed(this, 1000);
        }

    };

    /**
     * 返回二进制数的第index位的值
     *
     * @param num   要获取二进制值的数
     * @param index 右边第一位为0，第二位为1，依次类推
     */
    public static int getBData(int num, int index) {
        if (0 > num) {
            return -1;
        }
        return (num & (0x01 << index)) >> index;
    }
}
