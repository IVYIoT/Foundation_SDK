package com.ivyiot.sdkdemo.sdk;

public class EventID {
    /** 基站在线状态变化消息id, APP定义的。 */
    public static final int BASESTATION_HANLE_STATE_CHG = 54225;

    public static final int IVY_CTRL_MSG_DEVICE_LINE_STATE = 89;//for NVR line state
    public static final int NET_STATE_DISCONNECT = 88;
    public static final int IVY_CTRL_MSG_ONLINE_UPGRADE_STATE = 42019;
    public static final int IVY_CTRL_MSG_IPCLIST_CHG = 42020;
    public static final int IVY_CTRL_MSG_MIRROR_FLIP_CHG = 42021; // for foscam device
    public static final int IVY_CTRL_MSG_DAY_NIGHT_MODE_CHG = 42022; // (IRCUT? For foscam device)
    public static final int IVY_CTRL_MSG_PRESET_CHG = 42023; // for foscam device
    public static final int IVY_CTRL_MSG_CRUISE_CHG = 42024; // for foscam device
    public static final int IVY_CTRL_MSG_PRESET_REACHED = 42025; // for foscam device
    public static final int IVY_CTRL_MSG_CURRENT_CRUISE_MAP_STATE_CHG = 42026; // for foscam device
    public static final int IVY_CTRL_MSG_EDGE_ARRIVED = 42027; // for foscam device
    public static final int IVY_CTRL_MSG_START_PLAY_RESULT = 42028; // only for foscam device
    public static final int IVY_CTRL_MSG_GET_PRODUCT_ALL_INFO = 42029; // only for foscam device
    public static final int IVY_CTRL_MSG_CHANNEL_STATE_CHG = 42030; // for foscam nvr and bpi
    public static final int IVY_CTRL_MSG_ARRIVE_MAX_DURATION = 42031; // only for foscam bpi
    public static final int IVY_CTRL_MSG_EXT_STATE_CHG = 42032; // only for foscam bpi
    public static final int IVY_CTRL_MSG_STREAM_PARAM_CHG = 42033; // only for foscam ipc
    public static final int IVY_CTRL_MSG_SUB_STREAM_PARAM_CHG = 42034; // only for foscam ipc
    public static final int IVY_CTRL_MSG_STREAM_TYPE_CHG = 42035; // only for foscam ipc
    public static final int IVY_CTRL_MSG_SUB_STREAM_TYPE_CHG = 42036; // only for foscam ipc
    public static final int IVY_CTRL_MSG_ABILITY_CHG = 42037; // Foscam NVR BPI use, maybe IVY Nvr use
    public static final int IVY_CTRL_MSG_VOLUME_CHG = 42038; // Foscam IPC use
    public static final int IVY_CTRL_MSG_RECORD_ERROR_NO_ENOUGE_SPACE = 2052; // Foscam IPC use
    public static final int IVY_CTRL_MSG_RECORD_ERROR_MAX_FILE = 2053; // Foscam IPC use
    public static final int IVY_CTRL_MSG_RECORD_ERROR_SOLUTION_CHG = 2054;   // Foscam IPC use
    public static final int IVY_CTRL_MSG_RECORD_ERROR_FILE_PATH_NOEXIST = 2055;  // Foscam IPC use
    public static final int IVY_CTRL_MSG_RECORD_ERROR_UNKNOW = 2056; // Foscam IPC use
    public static final int IVY_CTRL_MSG_MUSIC_STATE_CHG = 42044;// Foscam IPC use
    public static final int IVY_CTRL_MSG_BIND_DEVICE_STATE_CHG = 42045; // Foscam BPI use, mybe IVY BPI use
}
