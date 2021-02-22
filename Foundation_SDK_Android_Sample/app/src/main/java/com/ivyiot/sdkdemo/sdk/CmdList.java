package com.ivyiot.sdkdemo.sdk;

/**
 * app与ipc透传指令编号集合
 */
public class CmdList {
    private CmdList() {
    }

    /** 基数 -- mark 2019 版新SDK (^v^) */
    private static final int BASE_CTRL_MSG = 2019;

    public static final int NET_STATE_DISCONNECT = 88;
    public static final int NET_STATE_RECONNECT = 89; // IVY Device has no this event, it is reserve
    //
    public static final int IVY_CTRL_MSG_ADD_ACCOUNT = BASE_CTRL_MSG + 20000;
    public static final int IVY_CTRL_MSG_ADD_ACCOUNT_RESP = 22020;
    public static final int IVY_CTRL_MSG_DEL_ACCOUNT = 22021;
    public static final int IVY_CTRL_MSG_DEL_ACCOUNT_RESP = 22022;
    public static final int IVY_CTRL_MSG_CHANGE_USER_INFO = 22023;
    public static final int IVY_CTRL_MSG_CHANGE_USER_INFO_RESP = 22024;
    public static final int IVY_CTRL_MSG_GET_DEVINFO = 22025;
    public static final int IVY_CTRL_MSG_GET_DEVINFO_RESP = 22026;
    public static final int IVY_CTRL_MSG_SET_DEVINFO = 22027;
    public static final int IVY_CTRL_MSG_SET_DEVINFO_RESP = 22028;
    public static final int IVY_CTRL_MSG_GET_DEVABILITY = 22029;
    public static final int IVY_CTRL_MSG_GET_DEVABILITY_RESP = 22030;
    //media
    public static final int IVY_CTRL_MSG_SET_VIDEOSTREAM_PARAM = BASE_CTRL_MSG + 22000;
    public static final int IVY_CTRL_MSG_SET_VIDEOSTREAM_PARAM_RESP = 24020;
    public static final int IVY_CTRL_MSG_GET_VIDEOSTREAM_PARAM = 24021;
    public static final int IVY_CTRL_MSG_GET_VIDEOSTREAM_PARAM_RESP = 24022;
    public static final int IVY_CTRL_MSG_SET_VIDEOSTREAM_MODE = 24023;
    public static final int IVY_CTRL_MSG_SET_VIDEOSTREAM_MODE_RESP = 24024;
    public static final int IVY_CTRL_MSG_GET_VIDEOSTREAM_MODE = 24025;
    public static final int IVY_CTRL_MSG_GET_VIDEOSTREAM_MODE_RESP = 24026;
    public static final int IVY_CTRL_MSG_SET_IMAGE_PARAM = 24027;
    public static final int IVY_CTRL_MSG_SET_IMAGE_PARAM_RESP = 24028;
    public static final int IVY_CTRL_MSG_GET_IMAGE_PARAM = 24029;
    public static final int IVY_CTRL_MSG_GET_IMAGE_PARAM_RESP = 24030;
    public static final int IVY_CTRL_MSG_SET_MOTION_DETECT_CONFIG = 24031;
    public static final int IVY_CTRL_MSG_SET_MOTION_DETECT_CONFIG_RESP = 24032;
    public static final int IVY_CTRL_MSG_GET_MOTION_DETECT_CONFIG = 24033;
    public static final int IVY_CTRL_MSG_GET_MOTION_DETECT_CONFIG_RESP = 24034;
    public static final int IVY_CTRL_MSG_SET_VIDEO_MIRROR_FLIP = 24035;
    public static final int IVY_CTRL_MSG_SET_VIDEO_MIRROR_FLIP_RESP = 24036;
    public static final int IVY_CTRL_MSG_GET_VIDEO_MIRROR_FLIP = 24037;
    public static final int IVY_CTRL_MSG_GET_VIDEO_MIRROR_FLIP_RESP = 24038;
    public static final int IVY_CTRL_MSG_SET_ENVIRONMENT = 24039;
    public static final int IVY_CTRL_MSG_SET_ENVIRONMENT_RESP = 24040;
    public static final int IVY_CTRL_MSG_GET_ENVIRONMENT = 24041;
    public static final int IVY_CTRL_MSG_GET_ENVIRONMENT_RESP = 24042;
    public static final int IVY_CTRL_MSG_SET_OSD_PARAM = 24043;
    public static final int IVY_CTRL_MSG_SET_OSD_PARAM_RESP = 24044;
    public static final int IVY_CTRL_MSG_GET_OSD_PARAM = 24045;
    public static final int IVY_CTRL_MSG_GET_OSD_PARAM_RESP = 24046;
    public static final int IVY_CTRL_MSG_SET_AUDIO_VOLUME = 24047;
    public static final int IVY_CTRL_MSG_SET_AUDIO_VOLUME_RESP = 24048;
    public static final int IVY_CTRL_MSG_GET_AUDIO_VOLUME = 24049;
    public static final int IVY_CTRL_MSG_GET_AUDIO_VOLUME_RESP = 24050;
    public static final int IVY_CTRL_MSG_GET_VIDEO_STREAM_ABLITY = 24051;
    public static final int IVY_CTRL_MSG_GET_VIDEO_STREAM_ABLITY_RESP = 24052;
    public static final int IVY_CTRL_MSG_SET_DAY_NIGHT_MODE = 24053;
    public static final int IVY_CTRL_MSG_SET_DAY_NIGHT_MODE_RESP = 24054;
    public static final int IVY_CTRL_MSG_GET_DAY_NIGHT_MODE = 24055;
    public static final int IVY_CTRL_MSG_GET_DAY_NIGHT_MODE_RESP = 24056;
    public static final int IVY_CTRL_MSG_SET_NIGHT_VISION_SCHEDULE = 24057;
    public static final int IVY_CTRL_MSG_SET_NIGHT_VISION_SCHEDULE_RESP = 24058;
    public static final int IVY_CTRL_MSG_GET_NIGHT_VISION_SCHEDULE = 24059;
    public static final int IVY_CTRL_MSG_GET_NIGHT_VISION_SCHEDULE_RESP = 24060;
    public static final int IVY_CTRL_MSG_SNAP_PICTURE = 24061;
    public static final int IVY_CTRL_MSG_SNAP_PICTURE_RESP = 24062;
    public static final int IVY_CTRL_MSG_GET_MUSIC_PLAY_STATE = 24063;
    public static final int IVY_CTRL_MSG_GET_MUSIC_PLAY_STATE_RESP = 24064;
    public static final int IVY_CTRL_MSG_SET_MUSIC_PLAY_START = 24065;
    public static final int IVY_CTRL_MSG_SET_MUSIC_PLAY_START_RESP = 24066;
    public static final int IVY_CTRL_MSG_SET_MUSIC_PLAY_STOP = 24067;
    public static final int IVY_CTRL_MSG_SET_MUSIC_PLAY_STOP_RESP = 24068;
    public static final int IVY_CTRL_MSG_SET_MUSIC_PLAY_PREV = 24069;
    public static final int IVY_CTRL_MSG_SET_MUSIC_PLAY_PREV_RESP = 24070;
    public static final int IVY_CTRL_MSG_SET_MUSIC_PLAY_NEXT = 24071;
    public static final int IVY_CTRL_MSG_SET_MUSIC_PLAY_NEXT_RESP = 24072;
    public static final int IVY_CTRL_MSG_GET_MUSIC_NAME_LIST = 24073;
    public static final int IVY_CTRL_MSG_GET_MUSIC_NAME_LIST_RESP = 24074;
    //system
    public static final int IVY_CTRL_MSG_SET_SYSTEM_TIME = BASE_CTRL_MSG + 24000;
    public static final int IVY_CTRL_MSG_SET_SYSTEM_TIME_RESP = 26020;
    public static final int IVY_CTRL_MSG_GET_SYSTEM_TIME = 26021;
    public static final int IVY_CTRL_MSG_GET_SYSTEM_TIME_RESP = 26022;
    public static final int IVY_CTRL_MSG_REBOOT_SYSTEM = 26023;
    public static final int IVY_CTRL_MSG_REBOOT_SYSTEM_RESP = 26024;
    public static final int IVY_CTRL_MSG_POWEROFF = 26025;
    public static final int IVY_CTRL_MSG_POWEROFF_RESP = 26026;
    public static final int IVY_CTRL_MSG_FACTORY_RESET = 26027;
    public static final int IVY_CTRL_MSG_FACTORY_RESET_RESP = 26028;
    public static final int IVY_CTRL_MSG_PTZ_CONTROL_CMD = 26029;
    public static final int IVY_CTRL_MSG_PTZ_CONTROL_CMD_RESP = 26030;
    public static final int IVY_CTRL_MSG_GET_DISK_INFO = 26031;
    public static final int IVY_CTRL_MSG_GET_DISK_INFO_RESP = 26032;
    public static final int IVY_CTRL_MSG_DISK_FORMAT = 26033;
    public static final int IVY_CTRL_MSG_DISK_FORMAT_RESP = 26034;
    public static final int IVY_CTRL_MSG_SET_ONLINE_UPGRADE = 26035;
    public static final int IVY_CTRL_MSG_SET_ONLINE_UPGRADE_RESP = 26036;
    public static final int IVY_CTRL_MSG_GET_SD_CARD_INFO = 26037;
    public static final int IVY_CTRL_MSG_GET_SD_CARD_INFO_RESP = 26038;
    public static final int IVY_CTRL_MSG_SD_CARD_FORMAT = 26039;
    public static final int IVY_CTRL_MSG_SD_CARD_FORMAT_RESP = 26040;
    public static final int IVY_CTRL_MSG_SET_UPNP_CONIG = 26041;
    public static final int IVY_CTRL_MSG_SET_UPNP_CONIG_RESP = 26042;
    public static final int IVY_CTRL_MSG_GET_UPNP_CONIG = 26043;
    public static final int IVY_CTRL_MSG_GET_UPNP_CONIG_RESP = 26044;
    public static final int IVY_CTRL_MSG_SET_LED_CONFIG = 26045;
    public static final int IVY_CTRL_MSG_SET_LED_CONFIG_RESP = 26046;// Deprecated
    public static final int IVY_CTRL_MSG_GET_LED_CONFIG = 26047;// Deprecated
    public static final int IVY_CTRL_MSG_GET_LED_CONFIG_RESP = 26048;// Deprecated
    public static final int IVY_CTRL_MSG_SET_MAINTAIN_CONFIG = 26049;// Deprecated
    public static final int IVY_CTRL_MSG_SET_MAINTAIN_CONFIG_RESP = 26050;
    public static final int IVY_CTRL_MSG_GET_MAINTAIN_CONFIG = 26051;
    public static final int IVY_CTRL_MSG_GET_MAINTAIN_CONFIG_RESP = 26052;
    public static final int IVY_CTRL_MSG_SET_SILENT_UPGRADE_CONFIG = 26053;
    public static final int IVY_CTRL_MSG_SET_SILENT_UPGRADE_CONFIG_RESP = 26054;
    public static final int IVY_CTRL_MSG_GET_SILENT_UPGRADE_CONFIG = 26055;
    public static final int IVY_CTRL_MSG_GET_SILENT_UPGRADE_CONFIG_RESP = 26056;
    public static final int IVY_CTRL_MSG_SWITCH_BUZZER = 26057;
    public static final int IVY_CTRL_MSG_SWITCH_BUZZER_RESP = 26058;
    public static final int IVY_CTRL_MSG_GET_BPI_BATTERY_STATUS = 26059;
    public static final int IVY_CTRL_MSG_GET_BPI_BATTERY_STATUS_RESP = 26060;
    public static final int IVY_CTRL_MSG_SET_LED_STATE = 26061;
    public static final int IVY_CTRL_MSG_SET_LED_STATE_RESP = 26062;
    public static final int IVY_CTRL_MSG_GET_LED_STATE = 26063;
    public static final int IVY_CTRL_MSG_GET_LED_STATE_RESP = 26064;
    public static final int IVY_CTRL_MSG_SET_VOICE_STATE = 26065;
    public static final int IVY_CTRL_MSG_SET_VOICE_STATE_RESP = 26066;
    public static final int IVY_CTRL_MSG_GET_VOICE_STATE = 26067;
    public static final int IVY_CTRL_MSG_GET_VOICE_STATE_RESP = 26068;
    public static final int IVY_CTRL_MSG_SET_NIGHT_LIGHT = 26069;
    public static final int IVY_CTRL_MSG_SET_NIGHT_LIGHT_RESP = 26070;
    public static final int IVY_CTRL_MSG_GET_NIGHT_LIGHT = 26071;
    public static final int IVY_CTRL_MSG_GET_NIGHT_LIGHT_RESP = 26072;
    public static final int IVY_CTRL_MSG_SET_AUDIO_ENABLE_STATE = 26073;
    // only for foscam bpi
    public static final int IVY_CTRL_MSG_SET_AUDIO_ENABLE_STATE_RESP = 26074;
    // only for foscam bpi
    public static final int IVY_CTRL_MSG_GET_AUDIO_ENABLE_STATE = 26075;
    // only for foscam bpi
    public static final int IVY_CTRL_MSG_GET_AUDIO_ENABLE_STATE_RESP = 26076;

    // only for foscam bpi
    //record
    public static final int IVY_CTRL_MSG_SET_RECORD_INFO = BASE_CTRL_MSG + 26000;
    public static final int IVY_CTRL_MSG_SET_RECORD_INFO_RESP = 28020;
    public static final int IVY_CTRL_MSG_GET_RECORD_INFO = 28021;
    public static final int IVY_CTRL_MSG_GET_RECORD_INFO_RESP = 28022;
    public static final int IVY_CTRL_MSG_GET_RECORD_LIST_RESERVE = 28023;
    // reserve
    public static final int IVY_CTRL_MSG_GET_RECORD_LIST_RESERVE_RESP = 28024;
    // reserve
    public static final int IVY_CTRL_MSG_RECORD_FILES_DOWNLOAD = 28025;
    public static final int IVY_CTRL_MSG_RECORD_FILES_DOWNLOAD_RESP = 28026;
    public static final int IVY_CTRL_MSG_RECORD_PLAYCONTROL_RESERVE = 28027;
    // reserve
    public static final int IVY_CTRL_MSG_RECORD_PLAYCONTROL_RESERVE_RESP = 28028;
    // reserve
    public static final int IVY_CTRL_MSG_SET_RECORD_HAS_AUDIO = 28029;
    public static final int IVY_CTRL_MSG_SET_RECORD_HAS_AUDIO_RESP = 28030;
    public static final int IVY_CTRL_MSG_GET_RECORD_HAS_AUDIO = 28031;
    public static final int IVY_CTRL_MSG_GET_RECORD_HAS_AUDIO_RESP = 28032;
    public static final int IVY_CTRL_MSG_SET_RECORD_MODE = 28033;
    public static final int IVY_CTRL_MSG_SET_RECORD_MODE_RESP = 28034;
    public static final int IVY_CTRL_MSG_GET_RECORD_MODE = 28035;
    public static final int IVY_CTRL_MSG_GET_RECORD_MODE_RESP = 28036;
    public static final int IVY_CTRL_MSG_SET_RECORD_STORE_LOCATION = 28037;
    public static final int IVY_CTRL_MSG_SET_RECORD_STORE_LOCATION_RESP = 28038;
    public static final int IVY_CTRL_MSG_GET_RECORD_STORE_LOCATION = 28039;
    public static final int IVY_CTRL_MSG_GET_RECORD_STORE_LOCATION_RESP = 28040;
    public static final int IVY_CTRL_MSG_SET_ENABLE_ALGORITHM = 28041;
    public static final int IVY_CTRL_MSG_SET_ENABLE_ALGORITHM_RESP = 28042;
    public static final int IVY_CTRL_MSG_GET_ENABLE_ALGORITHM = 28043;
    public static final int IVY_CTRL_MSG_GET_ENABLE_ALGORITHM_RESP = 28044;
    public static final int IVY_CTRL_MSG_SET_STROGE_CONFIG = 28045;
    public static final int IVY_CTRL_MSG_SET_STROGE_CONFIG_RESP = 28046;
    public static final int IVY_CTRL_MSG_GET_STROGE_CONFIG = 28047;
    public static final int IVY_CTRL_MSG_GET_STROGE_CONFIG_RESP = 28048;
    //network
    public static final int IVY_CTRL_MSG_SET_NET_PARAM = BASE_CTRL_MSG + 28000;
    public static final int IVY_CTRL_MSG_SET_NET_PARAM_RESP = 30020;
    public static final int IVY_CTRL_MSG_GET_NET_PARAM = 30021;
    public static final int IVY_CTRL_MSG_GET_NET_PARAM_RESP = 30022;
    public static final int IVY_CTRL_MSG_SET_WIFI_PARAM = 30023;
    public static final int IVY_CTRL_MSG_SET_WIFI_PARAM_RESP = 30024;
    public static final int IVY_CTRL_MSG_GET_WIFI_PARAM = 30025;
    public static final int IVY_CTRL_MSG_GET_WIFI_PARAM_RESP = 30026;
    public static final int IVY_CTRL_MSG_REFRESH_WIFI_AP_LIST = 30027;
    public static final int IVY_CTRL_MSG_REFRESH_WIFI_AP_LIST_RESP = 30028;
    public static final int IVY_CTRL_MSG_GET_WIFI_AP_LIST = 30029;
    public static final int IVY_CTRL_MSG_GET_WIFI_AP_LIST_RESP = 30030;
    public static final int IVY_CTRL_MSG_TEST_WIFI = 30031;
    public static final int IVY_CTRL_MSG_TEST_WIFI_RESP = 30032;
    public static final int IVY_CTRL_MSG_SET_DDNS_CONFIG = 30033;
    public static final int IVY_CTRL_MSG_SET_DDNS_CONFIG_RESP = 30034;
    public static final int IVY_CTRL_MSG_GET_DDNS_CONFIG = 30035;
    public static final int IVY_CTRL_MSG_GET_DDNS_CONFIG_RESP = 30036;
    public static final int IVY_CTRL_MSG_SET_P2P_INFO = 30037;
    public static final int IVY_CTRL_MSG_SET_P2P_INFO_RESP = 30038;
    public static final int IVY_CTRL_MSG_GET_P2P_INFO = 30039;
    public static final int IVY_CTRL_MSG_GET_P2P_INFO_RESP = 30040;
    public static final int IVY_CTRL_MSG_SET_NETWORK_ADAPTATION = 30041;
    public static final int IVY_CTRL_MSG_SET_NETWORK_ADAPTATION_RESP = 30042;
    public static final int IVY_CTRL_MSG_GET_NETWORK_ADAPTATION = 30043;
    public static final int IVY_CTRL_MSG_GET_NETWORK_ADAPTATION_RESP = 30044;
    //alarm
    public static final int IVY_CTRL_MSG_SET_AUDIO_ALARM_CONFIG = BASE_CTRL_MSG + 30000;
    public static final int IVY_CTRL_MSG_SET_AUDIO_ALARM_CONFIG_RESP = 32020;
    public static final int IVY_CTRL_MSG_GET_AUDIO_ALARM_CONFIG = 32021;
    public static final int IVY_CTRL_MSG_GET_AUDIO_ALARM_CONFIG_RESP = 32022;
    public static final int IVY_CTRL_MSG_SET_IO_ALARM_CONFIG = 32023;
    public static final int IVY_CTRL_MSG_SET_IO_ALARM_CONFIG_RESP = 32024;
    public static final int IVY_CTRL_MSG_GET_IO_ALARM_CONFIG = 32025;
    public static final int IVY_CTRL_MSG_GET_IO_ALARM_CONFIG_RESP = 32026;
    public static final int IVY_CTRL_MSG_SET_HUMIDITY_ALARM_CONFIG = 32027;
    public static final int IVY_CTRL_MSG_SET_HUMIDITY_ALARM_CONFIG_RESP = 32028;
    public static final int IVY_CTRL_MSG_GET_HUMIDITY_ALARM_CONFIG = 32029;
    public static final int IVY_CTRL_MSG_GET_HUMIDITY_ALARM_CONFIG_RESP = 32030;
    public static final int IVY_CTRL_MSG_SET_TEMPERATURE_ALARM_CONFIG = 32031;
    public static final int IVY_CTRL_MSG_SET_TEMPERATURE_ALARM_CONFIG_RESP = 32032;
    public static final int IVY_CTRL_MSG_GET_TEMPERATURE_ALARM_CONFIG = 32033;
    public static final int IVY_CTRL_MSG_GET_TEMPERATURE_ALARM_CONFIG_RESP = 32034;
    public static final int IVY_CTRL_MSG_CLEAN_IO_ALARM_OUTPUT = 32035;
    public static final int IVY_CTRL_MSG_CLEAN_IO_ALARM_OUTPUT_RESP = 32036;
    public static final int IVY_CTRL_MSG_SET_ONE_KEY_ALARM_CONFIG = 32037;
    public static final int IVY_CTRL_MSG_SET_ONE_KEY_ALARM_CONFIG_RESP = 32038;
    public static final int IVY_CTRL_MSG_GET_ONE_KEY_ALARM_CONFIG = 32039;
    public static final int IVY_CTRL_MSG_GET_ONE_KEY_ALARM_CONFIG_RESP = 32040;
    public static final int IVY_CTRL_MSG_SET_PEDESTRIAN_DECTECT_CONFIG = 32041;
    public static final int IVY_CTRL_MSG_SET_PEDESTRIAN_DECTECT_CONFIG_RESP = 32042;
    public static final int IVY_CTRL_MSG_GET_PEDESTRIAN_DECTECT_CONFIG = 32043;
    public static final int IVY_CTRL_MSG_GET_PEDESTRIAN_DECTECT_CONFIG_RESP = 32044;
    public static final int IVY_CTRL_MSG_SET_HUMAN_ALARM_CONFIG = 32045;
    public static final int IVY_CTRL_MSG_SET_HUMAN_ALARM_CONFIG_RESP = 32046;
    public static final int IVY_CTRL_MSG_GET_HUMAN_ALARM_CONFIG = 32047;
    public static final int IVY_CTRL_MSG_GET_HUMAN_ALARM_CONFIG_RESP = 32048;

    //Foscam Cloud
    public static final int IVY_CTRL_MSG_SET_PUSH_CONFIG = BASE_CTRL_MSG + 32000;
    public static final int IVY_CTRL_MSG_SET_PUSH_CONFIG_RESP = 34020;
    public static final int IVY_CTRL_MSG_GET_PUSH_CONFIG = 34021;
    public static final int IVY_CTRL_MSG_GET_PUSH_CONFIG_RESP = 34022;
    public static final int IVY_CTRL_MSG_SET_CLOUD_CONFIG = 34023;
    public static final int IVY_CTRL_MSG_SET_CLOUD_CONFIG_RESP = 34024;
    public static final int IVY_CTRL_MSG_GET_CLOUD_CONFIG = 34025;
    public static final int IVY_CTRL_MSG_GET_CLOUD_CONFIG_RESP = 34026;
    public static final int IVY_CTRL_MSG_SET_RTMP_CONFIG = 34027;
    public static final int IVY_CTRL_MSG_SET_RTMP_CONFIG_RESP = 34028;
    public static final int IVY_CTRL_MSG_GET_RTMP_CONFIG = 34029;
    public static final int IVY_CTRL_MSG_GET_RTMP_CONFIG_RESP = 34030;
    public static final int IVY_CTRL_MSG_SET_ALEXA_ENABLE = 34031;
    public static final int IVY_CTRL_MSG_SET_ALEXA_ENABLE_RESP = 34032;
    public static final int IVY_CTRL_MSG_GET_ALEXA_ENABLE = 34033;
    public static final int IVY_CTRL_MSG_GET_ALEXA_ENABLE_RESP = 34034;
    public static final int IVY_CTRL_MSG_SET_ALEXA_SLEEP = 34035;
    public static final int IVY_CTRL_MSG_SET_ALEXA_SLEEP_RESP = 34036;
    public static final int IVY_CTRL_MSG_SET_ALEXA_SERVER = 34037;
    public static final int IVY_CTRL_MSG_SET_ALEXA_SERVER_RESP = 34038;
    public static final int IVY_CTRL_MSG_GET_ALEXA_STATE = 34039;
    public static final int IVY_CTRL_MSG_GET_ALEXA_STATE_RESP = 34040;
    public static final int IVY_CTRL_MSG_SET_ALEXA_WAKEUP = 34041;
    public static final int IVY_CTRL_MSG_SET_ALEXA_WAKEUP_RESP = 34042;
    public static final int IVY_CTRL_MSG_TEST_PUSH = 34043;
    public static final int IVY_CTRL_MSG_TEST_PUSH_RESP = 34044;
    public static final int IVY_CTRL_MSG_SET_CHANNEL_SVR_EANBLE_BITS = 34045;
    public static final int IVY_CTRL_MSG_SET_CHANNEL_SVR_EANBLE_BITS_RESP = 34046;
    public static final int IVY_CTRL_MSG_GET_CHANNEL_SVR_EANBLE_BITS = 34047;
    public static final int IVY_CTRL_MSG_GET_CHANNEL_SVR_EANBLE_BITS_RESP = 34048;

    //NVR
    public static final int IVY_CTRL_MSG_ADD_IPC = BASE_CTRL_MSG + 34000;
    public static final int IVY_CTRL_MSG_ADD_IPC_RESP = 36020;
    public static final int IVY_CTRL_MSG_DEL_IPC = 36021;
    public static final int IVY_CTRL_MSG_DEL_IPC_RESP = 36022;
    public static final int IVY_CTRL_MSG_GET_IPC_LIST_INFO = 36023;
    public static final int IVY_CTRL_MSG_GET_IPC_LIST_INFO_RESP = 36024;
}
