package com.ivyiot.sdkdemo.video;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.ivyio.sdk.DecodeFormat;
import com.ivyio.sdk.FrameData;
import com.ivyio.sdk.IvyIoSdkJni;
import com.ivyio.sdk.OpenVideoArgsType0;
import com.ivyio.sdk.Result;
import com.ivyio.sdk.StreamType;
import com.ivyiot.sdkdemo.common.Global;
import com.ivyiot.sdkdemo.common.Logger;
import com.ivyiot.sdkdemo.entity.Camera;

import java.nio.ByteBuffer;


/**
 * 直播播放器
 */
public class VideoSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = "VideoSurfaceView";


    /**
     * 是否打印log
     */
    private static final boolean LOG = true;
    /**
     * 播放超时时间
     */
    private static final int INDEX_ILLEGATLITY_TIMES = 1000;

    private SurfaceHolder sfh;
    /**
     * 硬解码线程
     */
    private MediaCodecThread mediaCodecThread;
    /**
     * 软解码线程
     */
    private SoftDecodeThread softDecodeThread;
    /**
     * 播放标志
     */
    private boolean isPlaying = false;

    /**
     * 要裁剪的图片区域，如果为Null，则默认显示整个图片
     */
    private Rect src;
    /**
     * 图片显示在画布上的区域（居中显示）
     */
    private Rect dst;

    /**
     * surface view width
     */
    public int surfaceWidth = 0;
    /**
     * surface view height
     */
    public int surfaceHeight = 0;


    public VideoSurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initVideoSurfaceView();
    }

    public VideoSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initVideoSurfaceView();
    }

    public VideoSurfaceView(Context context) {
        super(context);
        initVideoSurfaceView();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        float ratio = 16.0f / 9.0f;
        float height = width / ratio;
        setMeasuredDimension(width, (int) height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }


    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int screen_w, int screen_h) {
        surfaceWidth = screen_w;
        surfaceHeight = screen_h;
        if (sfh != null) {
            sfh.setFixedSize(surfaceWidth, surfaceHeight);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        stopDraw();
    }

    /**
     * 打开视频
     */
    public void openVideo(final boolean h264Stream, final Camera camera) {
        if (camera.getHandle() == 0) {
            Toast.makeText(getContext(), "请先登陆设备", Toast.LENGTH_SHORT).show();
        }
        Global.es.execute(new Runnable() {
            @Override
            public void run() {
                Logger.d(TAG, "openVideo start.handle=" + camera.getHandle());
                OpenVideoArgsType0 argsType0 = new OpenVideoArgsType0();
                argsType0.streamType = camera.getStreamType();
                //openVideo为耗时操作，必须在子线程中执行
                int result = IvyIoSdkJni.openVideo(camera.getHandle(), argsType0, Global.TIMEOUT, 1);
                Logger.d(TAG, "openVideo end.handle=" + camera.getHandle() + ",result=" + result);
            }
        });
        startDraw(h264Stream, camera);
    }

    /**
     * 关闭视频
     */
    public void closeVideo(final Camera camera) {
        if (camera.getHandle() == 0) {
            Toast.makeText(getContext(), "请先登陆设备", Toast.LENGTH_SHORT).show();
        }
        Global.es.execute(new Runnable() {
            @Override
            public void run() {
                Logger.d(TAG, "closeVideo start.handle=" + camera.getHandle());
                int result = IvyIoSdkJni.closeVideo(camera.getHandle(), Global.TIMEOUT, 1);
                Logger.d(TAG, "closeVideo end.handle=" + camera.getHandle() + ",result=" + result);
            }
        });
        stopDraw();
    }


    /**
     * 开始画图
     */
    private void startDraw(boolean rawData, Camera camera) {
        isPlaying = true;
        if (rawData) {
            if (null == mediaCodecThread) {
                mediaCodecThread = new MediaCodecThread(camera);
                mediaCodecThread.start();

            }
        } else {
            if (null == softDecodeThread) {
                softDecodeThread = new SoftDecodeThread(camera);
                softDecodeThread.start();
            }
        }
    }

    /**
     * initialize surface view
     */
    private void initVideoSurfaceView() {
        sfh = this.getHolder();
        sfh.addCallback(this);
        src = new Rect();
        dst = new Rect();
        surfaceWidth = this.getWidth();
        surfaceHeight = this.getHeight();

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
            Logger.d(TAG, "createBitmap start. width=" + width + ",height=" + height);
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Logger.d(TAG, "createBitmap end.");
        } catch (OutOfMemoryError e) {
            Logger.e(TAG, "createBitmap OutOfMemoryError, run System.gc()");
        }
        return bitmap;
    }


    /**
     * MediaCodec
     */
    private class MediaCodecThread extends Thread {
        private MediaCodec decoder;
        private Surface surface;
        private Camera camera;

        MediaCodecThread(Camera camera) {
            this.surface = sfh.getSurface();
            this.camera = camera;
        }

        @Override
        public void run() {
            Logger.d(TAG, "MediaCodecLowThread thread start.");

            // 判断surface是否有效
            while (isPlaying) {
                if (sfh.getSurface().isValid()) {
                    break;
                }
                Logger.e(TAG, "surface is invalid.");
                SystemClock.sleep(50);
            }

            String key_mime = "video/avc";
            int w = 1920;
            int h = 1080;
            MediaFormat mediaFormat = MediaFormat.createVideoFormat(key_mime, w, h);
            try {
                decoder = MediaCodec.createDecoderByType(key_mime);
                mediaFormat.setInteger(MediaFormat.KEY_MAX_INPUT_SIZE, 0);
                mediaFormat.setInteger(MediaFormat.KEY_FRAME_RATE, 15);
                decoder.configure(mediaFormat, surface, null, 0);
                decoder.start();
                Logger.d(TAG, "decoder start.");
            } catch (Throwable e) {
                Logger.e(TAG, "硬解码初始化或配置时异常，Exception=" + e.getMessage());
                e.printStackTrace();

                // 改为软解
                decoder.stop();
                decoder.release();
                decoder = null;
                return;
            }

            ByteBuffer[] inputBuffers = decoder.getInputBuffers();
            int outputBufferIndexIllegalityTimes = 0;
            while (isPlaying) {
                if (camera.getHandle() <= 0) {
                    Logger.d(TAG, "cameraHandle is invalid. cameraHandle=" + camera.getHandle());
                    SystemClock.sleep(15);
                    continue;
                }

                try {
                    if (LOG) {
                        Logger.d(TAG, "mediaCodec start");
                    }
                    int inputBuffIndex = decoder.dequeueInputBuffer(0);
                    if (LOG) {
                        Logger.d(TAG, "dequeueInputBuffer index=" + inputBuffIndex);
                    }

                    // 视频数据长度
                    int len = 0;
                    if (inputBuffIndex >= 0) {
                        Integer flow_value = new Integer(0);
                        if (LOG) {
                            Logger.d(TAG, "get video data start. cameraHandle=" + camera.getHandle());
                        }
                        FrameData frameData = new FrameData();
                        int result = IvyIoSdkJni.getRawStreamData(camera.getHandle(), StreamType.STREAM_VIDEO, frameData, flow_value, 0);
                        if (LOG) {
                            Logger.d(TAG, "get video data end. cameraHandle=" + camera.getHandle() + ",result=" + result + ",dataLen=" + frameData.dataLen);
                        }
                        len = frameData.dataLen;
                        if (len > 0) {
                            inputBuffers[inputBuffIndex].clear();
                            inputBuffers[inputBuffIndex].put(frameData.data, 0, frameData.dataLen);
                        } else {
                            SystemClock.sleep(15);
                        }
                        decoder.queueInputBuffer(inputBuffIndex, 0, len, 0, 0);
                    } else {
                        outputBufferIndexIllegalityTimes++;
                        if (outputBufferIndexIllegalityTimes > INDEX_ILLEGATLITY_TIMES) {
                            Logger.e(TAG, "dequeueInputBuffer<0 " + outputBufferIndexIllegalityTimes + " times,stop play.");
                            break;
                        }
                    }

                    MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
                    int outputBufferIndex = decoder.dequeueOutputBuffer(info, 0);
                    if (LOG) {
                        Logger.d(TAG, "dequeueOutputBuffer index=" + outputBufferIndex);
                    }
                    if (outputBufferIndex >= 0) {
                        decoder.releaseOutputBuffer(outputBufferIndex, true);
                        if (LOG) {
                            Logger.i(TAG, "mediaCodec end.");
                        }
                        outputBufferIndexIllegalityTimes = 0;
                    } else {
                        if (len > 0) {// 过滤刚开始打开视频但取不到数据导致解码结果一直为-1的情况
                            outputBufferIndexIllegalityTimes++;
                            if (outputBufferIndexIllegalityTimes > INDEX_ILLEGATLITY_TIMES) {
                                Logger.e(TAG, "dequeueOutputBuffer<0 " + outputBufferIndexIllegalityTimes + " times,stop play.");
                                break;
                            }
                        }
                    }
                } catch (Throwable e) {
                    Logger.e(TAG, "硬解码数据播放异常，Throwable=" + e.getMessage());
                    break;
                }
            }

            // 本线程结束后要释放解码器
            try {
                decoder.stop();
                decoder.release();
                decoder = null;
            } catch (Throwable e) {
                Logger.e(TAG, "解码器释放时异常，Throwable=" + e.getMessage());
            }
            Logger.e(TAG,"硬解码线程结束");
        }
    }

    /**
     * Canvas
     */
    private class SoftDecodeThread extends Thread {
        // 视频数据
        private FrameData videoData;
        private Camera camera;
        private Bitmap bit;
        private Canvas canvas;

        SoftDecodeThread(Camera camera) {
            this.camera = camera;
        }

        @Override
        public void run() {
            Logger.d(TAG, "SoftDecodeThread start.");

            while (isPlaying) {
                if (!sfh.getSurface().isValid()) {
                    Logger.e(TAG, "surface is invalid.");
                    SystemClock.sleep(50);
                    continue;
                }

                if (camera.getHandle() <= 0) {
                    Logger.e(TAG, "cameraHandle is invalid. cameraHandle=" + camera.getHandle());
                    SystemClock.sleep(15);
                    continue;
                }

                if (videoData == null) {
                    videoData = new FrameData();
                }

                videoData.dataLen = 0;
                // 流量值
                Integer flow_value = new Integer(0);
                if (LOG) {
                    Logger.d(TAG, "get video Data start. cameraHandle=" + camera.getHandle());
                }
                int result = IvyIoSdkJni.getStreamData(camera.getHandle(), StreamType.STREAM_VIDEO, videoData, flow_value, DecodeFormat.RGBA32, 0);

                if (LOG) {
                    Logger.d(TAG, "get video Data end. cameraHandle=" + camera.getHandle() + ",result=" + result + ",dataLen=" + videoData.dataLen);
                }

                if (videoData.dataLen <= 0 || result != Result.OK) {
                    SystemClock.sleep(15);
                    continue;
                }

                try {
                    canvas = sfh.lockCanvas();
                    if (LOG) {
                        Logger.d(TAG, "canvas locked.");
                    }
                    // 视频数据转换为buffer
                    ByteBuffer buffer = ByteBuffer.wrap(videoData.data);
                    if (buffer.capacity() > 0) {
                        // 需要初始化bitmap
                        if (bit == null) {
                            bit = createBitmap(videoData.video_w, videoData.video_h);
                            if (bit == null) {
                                Logger.e(TAG, "bitmap init,bitmap is null.");
                                continue;// 执行finally部分
                            }
                            calcImgSize(bit.getWidth(), bit.getHeight());
                        }

                        // 更改分辨率时，需要重新初始化bitmap，不然图片的宽高不会变化
                        if (bit.getWidth() != videoData.video_w || bit.getHeight() != videoData.video_h) {
                            if (!bit.isRecycled()) {
                                bit.recycle();
                                bit = null;
                            }
                            bit = createBitmap(videoData.video_w, videoData.video_h);
                            if (bit == null) {
                                Logger.e(TAG, "resolution change,bitmap is null.");
                                continue;// 执行finally部分
                            }
                            calcImgSize(bit.getWidth(), bit.getHeight());
                        }

                        // 绘图
                        if (bit != null) {
                            bit.copyPixelsFromBuffer(buffer);
                            if (null != canvas) {
                                canvas.drawBitmap(bit, src, dst, null);
                            }
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Logger.e(TAG, "soft decode thread exception.msg=" + ex.getMessage());
                    videoData = null;
                } finally {
                    try {
                        if (canvas != null) {
                            sfh.unlockCanvasAndPost(canvas);
                        } else {
                            Logger.e(TAG, "canvas is null.");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Logger.e(TAG, "unlockCanvasAndPost exception.msg=" + e.getMessage());
                    }
                }
            }
            if (null != bit && !bit.isRecycled()) {
                bit.recycle();
            }
            canvas=null;
            Logger.e(TAG,"软解码线程结束");
        }
    }

    /**
     * 停止绘图
     */
    private void stopDraw() {
        isPlaying = false;
        if (softDecodeThread != null) {
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
        if (null != mediaCodecThread) {
            while (true) {
                try {
                    mediaCodecThread.join();
                    break;
                } catch (InterruptedException e) {
                    // retry
                }
            }
        }
        mediaCodecThread = null;
    }

}
