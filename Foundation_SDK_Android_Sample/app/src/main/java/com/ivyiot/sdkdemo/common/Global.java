package com.ivyiot.sdkdemo.common;

import com.ivyiot.sdkdemo.entity.Camera;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Global {
    /**
     * 全局使用的线程池
     */
    public static ExecutorService es = Executors.newCachedThreadPool();

    /**
     * 各个函数的超时时间 （单位：1ms）
     */
    public static final int TIMEOUT = 30000;

    /**
     * Foscam IPC
     */
    public static Camera mFosCamera = new Camera();

    /**
     * IVY IPC
     */
    public static Camera mIvyCamera = new Camera();

}
