package com.ivyiot.sdkdemo.video;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

import com.ivyio.sdk.FrameData;
import com.ivyio.sdk.IvyIoSdkJni;
import com.ivyio.sdk.StreamType;

/**
 * SD卡录像 声音播放线程
 */
public class SDAudioThread extends Thread {
    /**
     * 是否在播放音频
     */
    public boolean isPlayAudio = true;

    private AudioTrack mAudioTrack;
    /**
     * 音频率
     */
    final int sampleRateInHz = 8000;
    /**
     * 默认的音频数据buff大小
     */
    final int defalutBufSize = 960;

    /**
     * ipc连接handler
     */
    private int cameraHandlerNo;

    public SDAudioThread(int camHandler) {
        cameraHandlerNo = camHandler;
    }

    /**
     * 初始化
     */
    public void init() {
        int minBufSize = AudioTrack.getMinBufferSize(sampleRateInHz, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);

        // "getMinBufferSize(): error querying hardware";
        if ((minBufSize == -1) || (minBufSize == 0)) {
            minBufSize = defalutBufSize;
        }
        mAudioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, sampleRateInHz, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, minBufSize, AudioTrack.MODE_STREAM);

        if (mAudioTrack.getState() == AudioTrack.STATE_UNINITIALIZED) {
            this.init();
        } else {
            mAudioTrack.play();
        }
    }

    @Override
    public void run() {
        while (isPlayAudio) {
            if (mAudioTrack.getPlayState() != AudioTrack.PLAYSTATE_PAUSED) {

                // 音频数据
                FrameData audioData = new FrameData();
                Integer i = new Integer(-1);
                IvyIoSdkJni.getPlaybackRawStreamData(cameraHandlerNo, StreamType.STREAM_AUDIO, audioData, i, 0);
                if (null != mAudioTrack && audioData.data != null) {
                    mAudioTrack.write(audioData.data, 0, audioData.dataLen);
                }
            }
        }
        super.run();
    }

    public void StopRun() {
        if (mAudioTrack != null) {
            try {
                if (mAudioTrack.getState() != AudioTrack.STATE_UNINITIALIZED) {
                    if (mAudioTrack.getPlayState() != AudioTrack.PLAYSTATE_STOPPED) {
                        mAudioTrack.stop();
                    }
                }
                mAudioTrack.release();
            } catch (Exception ignored) {
            }
        }
        mAudioTrack = null;
        isPlayAudio = false;
    }
}
