package com.ivyiot.sdkdemo.entity;

public class DeviceAbility {

    public int[] reserve;
    /**
     * 声音侦测标志位（所有机器升级到2.X.2.6版本以后，talkFlag为1的都支持声音侦测功能）
     */
    public int isEnableAudioDetect;
    /**
     * 温度侦测标志位
     */
    public int isEnableTemperatureDetect;
    /**
     * 湿度侦测标志位
     */
    public int isEnableHumidityDetect;
    /**
     * PIR侦测标志位
     */
    public int isEnablePIRDetect;
    /**
     * Led开关
     */
    public int isEnableLedOnOff;
    /**
     * 小夜灯
     */
    public int isEnableNightLight;
    /**
     * 语音提示开关
     */
    public int isEnableIpcTipVoice;
    /**
     * 音量控制/（童谣播放启用也显示）
     */
    public int isEnableChangeVoice;

    /**
     * reserveFlag[0]
     * OEM及定制标志位
     * 0：Foscam品牌机型
     * 1：美国定制机型
     * 100：OEM机型
     * 101：航舦机型
     * <p>
     * reserveFlag[1]
     * Bit[0]：双马达自动聚焦功能；
     * Bit[1]：云台巡航看守位功能；
     */
    public int[] reserveFlag;

    /**
     * 是否支持存储卡
     * 0：不支持存储卡(SD/TF)
     * 1：支持存储卡(SD/TF)
     */
    public int sdFlag;
    /**
     * 户外机型标志位
     * 0：室内机型
     * 1：户外机型
     */
    public int outdoorFlag;
    /**
     * 云台功能标志位
     * 0：不带云台
     * 1：带云台
     */
    public int ptFlag;
    /**
     * 镜头变焦功能标志位
     * 0：不支持镜头变焦
     * 1：支持镜头变焦
     */
    public int zoomFlag;
    /**
     * Rs485功能标志位
     * 0：支持rs485扩展
     * 1：不支持rs485扩展
     */
    public int rs485Flag;
    /**
     * IO报警功能标志位
     * 0：不支持IO报警功能
     * 1：支持IO报警功能
     */
    public int ioAlarmFlag;
    /**
     * Onvif功能标志位
     * 0：不支持Onvif功能
     * 1：支持Onvif功能
     */
    public int onvifFlag;
    /**
     * P2P功能标志位
     * 0：不支持P2P功能
     * 1：支持P2P功能
     */
    public int p2pFlag;
    /**
     * WPS功能标志位
     * 0：无WPS功能
     * 1：有WPS功能
     */
    public int wpsFlag;
    /**
     * 音频播放功能标志位
     * 0：不支持声音播放功能
     * 1：支持声音播放功能
     */
    public int audioFlag;
    /**
     * 音频对讲功能标志位
     * 0：不支持音频对讲功能
     * 1：支持音频对讲功能
     */
    public int talkFlag;
    /**
     * 全双工功能标志位
     * true:支持
     * false:不支持
     */
    public boolean bDuplexVoice;
    /**
     * true：只支持切换码流
     * false：切换分辨率
     */
    public boolean bStreamMode;
    /**
     * true：支持区域选择
     * false：不支持区域选择
     */
    public boolean bMotionArea;
    /**
     * 一键呼叫功能标志位
     * true:支持
     * false:不支持
     */
    public boolean bOneKeyCall;
    /**
     * 夜视计划功能标志位
     * true:支持
     * false:不支持
     */
    public boolean bNightVisionSchedule;


    /**
     * 是否展示时间戳标志位
     * 0：不展示
     * 1：展示
     *
     */
    public int isEnableTimeStamp;
    /**
     * 是否展示设备名称标志位
     * 0：不展示
     * 1：展示
     */
    public int isEnableDevName;


}
