package com.ivyiot.sdkdemo.video;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.os.Build;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.ivyio.sdk.ClosePlaybackArgs;
import com.ivyio.sdk.FrameData;
import com.ivyio.sdk.IvyIoSdkJni;
import com.ivyio.sdk.OpenPlaybackArgs;
import com.ivyio.sdk.StreamType;
import com.ivyiot.sdkdemo.common.Global;
import com.ivyiot.sdkdemo.common.Logger;
import com.ivyiot.sdkdemo.entity.Camera;
import com.ivyiot.sdkdemo.entity.FosDecFmt;

import java.nio.ByteBuffer;

/**
 * SD卡录像播放器
 */
public class SDVideoSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = "SDVideoSurfaceView";

    /**
     * 绘制开关，true表示打开绘制，false为关闭绘制
     */
    private boolean isDraw = false;
    /**
     * 视频数据
     */
    private FrameData frameData;
    private SurfaceHolder surfaceHolder;
    private Canvas canvas;
    private Bitmap mBit;
    /**
     * 要裁剪的图片区域，如果为Null，则默认显示整个图片
     */
    private Rect src;
    /**
     * 图片显示在画布上的区域（居中显示）
     */
    private Rect dst;
    /**
     * mSurface view width
     */
    public int surfaceWidth = 0;
    /**
     * mSurface view height
     */
    public int surfaceHeight = 0;
    /**
     * 每帧图片休眠的时间（单位：毫秒）
     */
    public int sleepTime = 0;
    /**
     * 软解码线程
     */
    private SoftDecodeThread softDecodeThread;
    /**
     * 硬解码线程
     */
    private HardDecodeThread hardDecodeThread;

    /**
     * 音频播放线程
     */
    private SDAudioThread mAudioThread;

    /**
     * 超过设定次，认为硬解码失败，会转为软解码
     */
    private static final int INDEX_ILLEGATLITY_TIMES = 50;

    private int MAX_TIMES_NO_GET_VIDEO_DATA = 4000;

    private int noDataTimes = 0;

    /**
     * 对硬解支持有问题的机型列表，若属于这些机型则走软解
     */
    private String[] supportHardDecodeWeakMobile = {"HUAWEI P6-T00", "samsung", "H60-L01", "Coolpad 8675", "smartisan", "HM 2A"};

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        float ratio = 16.0f / 9.0f;
        float height = width / ratio;
        setMeasuredDimension(width, (int) height);
    }

    public SDVideoSurfaceView(Context context) {
        super(context);
        initSurfaceView();
    }

    public SDVideoSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSurfaceView();
    }

    public SDVideoSurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initSurfaceView();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        surfaceWidth = this.getWidth();
        surfaceHeight = this.getHeight();

        if (mBit != null) {
            surfaceWidth = width;
            surfaceHeight = height;
            calcImgSize(mBit.getWidth(), mBit.getHeight());
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopDraw();
    }

    /**
     * 初始化播放器参数
     */
    private void initSurfaceView() {
        surfaceHolder = this.getHolder();
        surfaceHolder.addCallback(this);
        canvas = new Canvas();

        src = new Rect();
        dst = new Rect();

        surfaceWidth = this.getWidth();
        surfaceHeight = this.getHeight();
    }

    /**
     * 打开回放视频
     */
    public void openPlaybackVideo(final Camera camera, final OpenPlaybackArgs openPlaybackArgs) {
        if (camera.getHandle() == 0) {
            Toast.makeText(getContext(), "请先登陆设备", Toast.LENGTH_SHORT).show();
        }
        Global.es.execute(new Runnable() {
            @Override
            public void run() {
                Logger.d(TAG, "openPalybackVideo start.handle=" + camera.getHandle());
                //openPlayback为耗时操作，必须在子线程中执行
                int result = IvyIoSdkJni.openPlayback(camera.getHandle(), openPlaybackArgs, Global.TIMEOUT, 1);
                Logger.d(TAG, "openPalybackVideo end.handle=" + camera.getHandle() + ",result=" + result);
            }
        });
        startDraw(camera);
    }

    /**
     * 关闭回放视频
     */
    public void closePlaybackVideo(final Camera camera, final ClosePlaybackArgs closePlaybackArgs) {
        if (camera.getHandle() == 0) {
            Toast.makeText(getContext(), "请先登陆设备", Toast.LENGTH_SHORT).show();
        }
        Global.es.execute(new Runnable() {
            @Override
            public void run() {
                Logger.d(TAG, "closePlaybackVideo start.handle=" + camera.getHandle());
                int result = IvyIoSdkJni.closePlayback(camera.getHandle(), closePlaybackArgs, Global.TIMEOUT, 1);
                Logger.d(TAG, "closePlaybackVideo end.handle=" + camera.getHandle() + ",result=" + result);
            }
        });
        stopDraw();
    }

    /**
     * 开始绘制
     */
    public void startDraw(Camera camera) {
        isDraw = true;
        if (supportHardDecode()) {
            // FIXME h264线程
            if (hardDecodeThread == null) {
                frameData = new FrameData();
                hardDecodeThread = new HardDecodeThread(camera, surfaceHolder.getSurface());
                hardDecodeThread.start();
            }
        } else {
            if (softDecodeThread == null) {
                frameData = new FrameData();
                softDecodeThread = new SoftDecodeThread(camera);
                softDecodeThread.start();
            }
        }
        startAudio(camera.getHandle());
    }

    /**
     * 开启音频线程
     */
    private void startAudio(int handler) {
        if (mAudioThread == null) {
            mAudioThread = new SDAudioThread(handler);
            mAudioThread.isPlayAudio = true;
            mAudioThread.init();
            mAudioThread.start();
        }
    }

    /**
     * 停止绘制
     */
    public void stopDraw() {
        isDraw = false;
        frameData = null;

        if (null != mBit && !mBit.isRecycled()) {
            mBit.recycle();
        }
        mBit = null;

        if (null != softDecodeThread) {
            while (true) {
                try {
                    softDecodeThread.join();
                    break;
                } catch (InterruptedException e) {
                    // retry
                }
            }
        }
        softDecodeThread = null;
        canvas = null;

        if (null != hardDecodeThread) {
            while (true) {
                try {
                    hardDecodeThread.join();
                    break;
                } catch (InterruptedException e) {
                    // retry
                }
            }
        }
        hardDecodeThread = null;

        stopAudio();

    }

    /**
     * 停止音频线程
     */
    private void stopAudio() {
        if (null != mAudioThread) {
            mAudioThread.isPlayAudio = false;
            while (true) {
                try {
                    mAudioThread.join();
                    break;
                } catch (InterruptedException e) {
                    // retry
                }
            }
            mAudioThread.StopRun();
        }
        mAudioThread = null;
    }

    /**
     * 根据surface的实际宽高，设置显示图片的宽高
     *
     * @param srcPicWidth  原始图片的宽
     * @param srcPicHeight 原始图片的高
     */
    private void calcImgSize(int srcPicWidth, int srcPicHeight) {
        src.left = 0;
        src.top = 0;
        src.right = srcPicWidth;
        src.bottom = srcPicHeight;

        dst.left = 0;
        dst.top = 0;
        dst.right = surfaceWidth;
        dst.bottom = surfaceHeight;
    }

    /**
     * 创建 bitmap
     */
    private Bitmap createBitmap(int width, int height) {
        Bitmap bitmap = null;
        try {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError e) {
            System.gc();
            System.runFinalization();
        }
        return bitmap;
    }

    /**
     * draw thread(soft decode)
     */
    private class SoftDecodeThread extends Thread {
        /**
         * 计算流量
         */
        private Integer flow_value = new Integer(-1);
        private long lastTime = 0;
        private Camera mCamera;

        SoftDecodeThread(Camera camera) {
            this.mCamera = camera;
        }

        @Override
        public void run() {
            while (isDraw) {
                while (!surfaceHolder.getSurface().isValid()) {
                    Logger.e(TAG, "surface不可用");
                    SystemClock.sleep(5);
                    if (!isDraw) {
                        return;
                    }
                }

                lastTime = System.currentTimeMillis();
                if (mCamera.getHandle() > 0 && frameData != null) {
                    IvyIoSdkJni.getPlaybackStreamData(mCamera.getHandle(), com.ivyio.sdk.StreamType.STREAM_VIDEO, frameData, flow_value, FosDecFmt.FOSDECTYPE_RGBA32, 0);
                } else {
                    // 如果IPC断线，则等待重连
                    Logger.d(TAG, "断线------soft----------断线-------------断线------------");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // 不需要判断getVideoPBData的结果，插空帧？
                if (null != frameData && null != frameData.data && frameData.data.length > 0) {
                    try {
                        canvas = surfaceHolder.lockCanvas();

                        ByteBuffer buffer = ByteBuffer.wrap(frameData.data);
                        if (buffer != null && buffer.capacity() > 0) {
                            if (mBit == null || (mBit.getWidth() != frameData.video_w
                                    || mBit.getHeight() != frameData.video_h))// 更改分辨率时，需要重新初始化bitmap，不然图片的宽高不会变化
                            {
                                if (mBit != null && !mBit.isRecycled()) {
                                    mBit.recycle();
                                }
                                mBit = null;
                                mBit = createBitmap(frameData.video_w, frameData.video_h);
                                calcImgSize(mBit.getWidth(), mBit.getHeight());
                            }
                        }
                        if (mBit != null) {
                            mBit.copyPixelsFromBuffer(buffer);
                            if (buffer != null) {
                                buffer.rewind();
                            }
                            canvas.drawColor(Color.BLACK);
                            canvas.drawBitmap(mBit, src, dst, null);

                            // 与上一帧数据获取的时间比较，如果两帧数据获取的时间间隔小于视频帧率，则需要休眠一小段时间，否则直接播放。
                            long currTime = System.currentTimeMillis();
                            int dt = (int) (currTime - lastTime);
                            if (dt < sleepTime) {
                                Thread.sleep(sleepTime - dt);
                            }
                        }
                    } catch (Exception ex) {
                        if (null != frameData) {
                            if (null != frameData.data) {
                                frameData.data = null;
                            }
                            frameData = null;
                        }
                        ex.printStackTrace();
                    } finally {
                        if (canvas != null) {
                            surfaceHolder.unlockCanvasAndPost(canvas);
                        }
                    }
                }
            }
            Logger.e(TAG, "结束软解码线程");
        }
    }

    /**
     * draw thread(hard decode)
     */
    class HardDecodeThread extends Thread {
        private MediaCodec mDecoder;
        private Surface mSurface;
        private Camera mCamera;

        private HardDecodeThread(Camera camera, Surface surface) {
            this.mSurface = surface;
            this.mCamera = camera;
        }

        @Override
        public void run() {
            Logger.d(TAG, "MediaCodecLowThread thread start.");

            // 判断surface是否有效
            while (isDraw) {
                if (surfaceHolder.getSurface().isValid()) {
                    break;
                }
                Logger.e(TAG, "mSurface is invalid.");
                SystemClock.sleep(50);
            }

            String key_mime = "video/avc";
            int w = 1920;
            int h = 1080;
            MediaFormat mediaFormat = MediaFormat.createVideoFormat(key_mime, w, h);

            try {
                mDecoder = MediaCodec.createDecoderByType(key_mime);
                mediaFormat.setInteger(MediaFormat.KEY_MAX_INPUT_SIZE, 0);
                mediaFormat.setInteger(MediaFormat.KEY_FRAME_RATE, 15);
                mDecoder.configure(mediaFormat, mSurface, null, 0);
                mDecoder.start();
                Logger.d(TAG, "mDecoder start.");
            } catch (Throwable e) {
                Logger.e(TAG, "硬解码初始化或配置时异常，改为软解码，Exception=" + e.getMessage());
                e.printStackTrace();

                // 改为软解
                //                mDecoder.flush();//@throws IllegalStateException if not in the Executing state.
                mDecoder.stop();
                mDecoder.release();
                mDecoder = null;

                frameData = new FrameData();
                softDecodeThread = new SoftDecodeThread(mCamera);
                softDecodeThread.start();

                return;
            }

            boolean chgSoftDecode = false;
            ByteBuffer[] inputBuffers = mDecoder.getInputBuffers();

            // 硬解码输出buff非法次数（index小于0的次数），当连续超过设定次，会被认为硬解失败，转为软解
            int outputBufferIndexIllegalityTimes = 0;
            while (isDraw) {
                try {
                    // 检查是否有可用的buffer空间（返回-1表示无可用buffer空间）
                    int inputBuffIndex = mDecoder.dequeueInputBuffer(0);
                    // 取到的视频数据长度
                    int len = 0;
                    if (inputBuffIndex >= 0) {
                        Integer flow_value = new Integer(0);

                        if (mCamera.getHandle() > 0 && frameData != null) {
                            frameData.dataLen = 0;
                            IvyIoSdkJni.getPlaybackRawStreamData(mCamera.getHandle(), StreamType.STREAM_VIDEO, frameData, flow_value, 0);
                            len = frameData.dataLen;
                        }
                        if (len > 0) {
                            ByteBuffer buffer = inputBuffers[inputBuffIndex];
                            buffer.clear();
                            buffer.put(frameData.data, 0, frameData.data.length);

                            Thread.sleep(60);
                        }
                        // 将填充数据后的buffer传递给解码器
                        mDecoder.queueInputBuffer(inputBuffIndex, 0, len, 0, 0);
                    }
                    MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
                    int outputBufferIndex = mDecoder.dequeueOutputBuffer(info, 0);
                    if (outputBufferIndex >= 0) {
                        mDecoder.releaseOutputBuffer(outputBufferIndex, true);
                        outputBufferIndexIllegalityTimes = 0;
                    } else {
                        if (len > 0) {
                            outputBufferIndexIllegalityTimes++;
                            if (outputBufferIndexIllegalityTimes > INDEX_ILLEGATLITY_TIMES) {
                                // 改成软解
                                chgSoftDecode = true;
                                break;
                            }
                        }
                    }
                } catch (Throwable e) {
                    Logger.e(TAG, "硬解码数据播放异常，改为软解码，Throwable = " + e.getMessage());

                    // 改成软解
                    chgSoftDecode = true;
                    break;
                }
            }
            try {
                /*
                 * Galaxy S6 (zerofltetmo) / Galaxy S6 (zerofltespr)<br>
                 * Android 5.1<br>
                 * by google play<br>
                 * java.lang.IllegalStateException<br>
                 * at android.media.MediaCodec.native_stop(Native Method)<br>
                 * at android.media.MediaCodec.stop(MediaCodec.java:628)<br>
                 * at com.foscam.foscam.extend.an.run(Unknown Source)
                 */
                mDecoder.stop();
                mDecoder.release();
            } catch (Throwable e) {
                Logger.e(TAG, "解码器释放时异常，Throwable = " + e.getMessage());
            }

            if (chgSoftDecode) {
                frameData = new FrameData();
                softDecodeThread = new SoftDecodeThread(mCamera);
                softDecodeThread.start();
            }
            Logger.e(TAG, "结束硬解码线程");
        }
    }

    /**
     * 判断Android设备是否支持硬解码
     * <p/>
     * API15及以下的版本不支持硬解
     */
    private boolean supportHardDecode() {
        Logger.d(TAG, "Android OS Version:" + Build.VERSION.SDK_INT
                + "\nModel:" + Build.MODEL
                + "\nManufacturer:" + Build.MANUFACTURER);
        for (String phone_mode : supportHardDecodeWeakMobile) {
            if (Build.MODEL.endsWith(phone_mode)) {
                return false;
            }
            if (Build.MANUFACTURER.equals(phone_mode)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取当前视频数据的pts
     *
     * @return 如果视频数据为空，则返回-1
     */

    public long getFramePTS() {
        if (frameData != null) {
            return frameData.pts;
        }
        return -1L;
    }
    /**
     * 获取当前视频数据的getframeTag
     *
     * @return ivy设备视频数据为1178944080为视频结束
     */
    public long getFrameTag() {
        if (frameData != null) {
            return frameData.frameTag;
        }
        return -1L;
    }
}
