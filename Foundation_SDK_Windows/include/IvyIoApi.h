#ifndef _IVYIO_API_H_1E5F81C248D74503B196BCF1FB3E03E6_
#define _IVYIO_API_H_1E5F81C248D74503B196BCF1FB3E03E6_

#include "IvyIoDef.h"

#ifdef __cplusplus
extern "C" {
#endif


	
	
	// Description: SDK init method
	// Returns: none
	// Remark: Call it before you use sdk
	
	void IVYIO_API IVYIO_Init();
	
	// Description: Get SDK version
	// Parameter: szVer  string buffer, better more then 16 bytes
	// Returns: none
	// Remark:  
	
	void IVYIO_API IVYIO_Version(char *szVer);
	
	// Description: Set log config
	// Parameter: iLevel Reference IVYIO_LOG_LEVEL_NO / IVYIO_LOG_LEVEL_ERR / IVYIO_LOG_LEVEL_DBG / IVYIO_LOG_LEVEL_ALL in IvyIoDef.h
	// Parameter: szPath Log full path 
	// Parameter: iMaxSize The max size of file, unit is 1M, is default 5M.
	// Returns: none
	// Remark: If szPath length is 0 or is NULL, it just output on console, not write file.
	
	void IVYIO_API IVYIO_SetLog(int iLevel, char *szPath, int iMaxSize);
	
	// Description: Dsicovery device
	// Parameter: nodes Array of device node 
	// Parameter: iCountOfNode As input it is count of nodes array, as output it will tell actual number
	// Returns: none
	// Remark:  
	
	void IVYIO_API IVYIO_Discovery(IVYIO_DEV_NODE *nodes, int *iCountOfNode);
	
	// Description: Restart discovery
	// Returns: none
	// Remark: 
	
	void IVYIO_API IVYIO_RestartDiscovery();
	
	// Description: Stop discovery
	// Returns: none
	// Remark: 
	
	void IVYIO_API IVYIO_StopDiscovery();
	
	// Description: Get discovery state
	// Returns: 0: start 1: stop
	// Remark: 
	
	int IVYIO_API IVYIO_GetDiscoveryState();
	
	// Description: Create SDK instance handle
	// Parameter: url device url and port
	// Parameter: szUid device uid, can be NULL
	// Parameter: szUser device username
	// Parameter: szPassword device password
	// Parameter: mode P2P mode
	// Returns: IVYIO_HANDLE handle of SDK
	// Remark: 
	
	IVYIO_HANDLE IVYIO_API IVYIO_Create(IVYIO_URL *url, char *szUid, char *szUser, char *szPassword, IVYIO_P2P_MODE mode);
	
	// Description: Create SDK instance handle
	// Parameter: url device url and port
	// Parameter: szUid device uid, can be NULL
	// Parameter: szUser device username
	// Parameter: szPassword device password
	// Parameter: mode P2P mode
	// Parameter: lowPower 1: low power device, other: same as IVYIO_Create
	// Returns: IVYIO_HANDLE handle of SDK
	// Remark: 

	IVYIO_HANDLE IVYIO_API IVYIO_Create_1(IVYIO_URL *url, char *szUid, char *szUser, char *szPassword, IVYIO_P2P_MODE mode, int lowPower);

	// Description: Destroy SDK instance
	// Parameter: handle SDK handle
	// Returns: none
	// Remark: 
	
	void IVYIO_API IVYIO_Destroy(IVYIO_HANDLE handle);
	
	// Description: Check device state
	// Parameter: handle SDK handle
	// Returns: IVYIO_HANDLE_STATE Device state
	// Remark: 
	
	IVYIO_HANDLE_STATE IVYIO_API IVYIO_CheckHandle(IVYIO_HANDLE handle);
	
	// Description: Login device
	// Parameter: handle SDK handle
	// Parameter: iTimeout unit is ms
	// Returns: IVYIO_RESULT Execution results
	// Remark: 
	
	IVYIO_RESULT IVYIO_API IVYIO_Login(IVYIO_HANDLE handle, int iTimeout);
	
	// Description: Logout device
	// Parameter: handle SDK handle
	// Returns: none
	// Remark: 
	
	void IVYIO_API IVYIO_Logout(IVYIO_HANDLE handle);
	
	// Description: Open video
	// Parameter: handle SDK handle
	// Parameter: args Args for open video, reference IVYIO_OPEN_VIDEO_ARGS_TYPE0
	// Parameter: iTimeout Unit ms
	// Parameter: iChannel Bit0-31 means channel0-31, you can open multiple channels
	// Returns: IVYIO_RESULT Execution results
	// Remark: 
	
	IVYIO_RESULT IVYIO_API IVYIO_OpenVideo(IVYIO_HANDLE handle, void *args, int iTimeout, int iChannel);
	
	// Description: Close video
	// Parameter: handle SDK handle
	// Parameter: iTimeout Unit is ms
	// Parameter: iChannel Same as OpenVideo
	// Returns: IVYIO_RESULT Execution results
	// Remark: 
	
	IVYIO_RESULT IVYIO_API IVYIO_CloseVideo(IVYIO_HANDLE handle, int iTimeout, int iChannel);
	
	// Description: Open audio
	// Parameter: handle SDK handle
	// Parameter: stream Stream type
	// Parameter: iTimeout Unit is ms
	// Parameter: iChannel Same as open video
	// Returns: IVYIO_RESULT Execution results
	// Remark: You must call open video before you call this function
	
	IVYIO_RESULT IVYIO_API IVYIO_OpenAudio(IVYIO_HANDLE handle, IVYIO_AUDIO_STREAM_TYPE stream, int iTimeout, int iChannel);
	
	// Description: Close audio
	// Parameter: handle SDK handle
	// Parameter: iTimeout Unit is ms
	// Parameter: iChannel Same as open video
	// Returns: IVYIO_RESULT Execution results
	// Remark: 
	
	IVYIO_RESULT IVYIO_API IVYIO_CloseAudio(IVYIO_HANDLE handle, int iTimeout, int iChannel);
	
	// Description: Get playback record list
	// Parameter: handle SDK handle
	// Parameter: args Reference IVYIO_GET_RECORD_LIST_ARGS_TYPE1
	// Parameter: list Reference IVYIO_RECORD_LIST_ARGS_TYPE1
	// Parameter: iTimeout Unit is ms
	// Parameter: iChannel Same as open video
	// Returns: IVYIO_RESULT Execution results
	// Remark: 
	
	IVYIO_RESULT IVYIO_API IVYIO_GetPlaybackRecordList(IVYIO_HANDLE handle, void *args, void *list, int iTimeout, int iChannel);
	
	// Description: Open play back
	// Parameter: handle SDK handle
	// Parameter: args Reference IVYIO_OPEN_PLAY_BACK_ARGS_TYPE1
	// Parameter: iTimeout Unit is ms
	// Parameter: iChannel Same as open video
	// Returns: IVYIO_RESULT Execution results
	// Remark: 
	
	IVYIO_RESULT IVYIO_API IVYIO_OpenPlayback(IVYIO_HANDLE handle, void *args, int iTimeout, int iChannel);

    // Description: Open play back
    // Parameter: handle SDK handle
    // Parameter: args Reference IVYIO_OPEN_PLAY_BACK_ARGS_TYPE1
    // Parameter: out out data, when device is Foscam IPC, out struct is IVYIO_PLAY_BACK_VIDEO_INFO_4_FOSIPC, you can get video information that can help you control and display playback 
    // Parameter: iTimeout Unit is ms
    // Parameter: iChannel Same as open video
    // Returns: IVYIO_RESULT Execution results
    // Remark:

    IVYIO_RESULT IVYIO_API IVYIO_OpenPlaybackEx(IVYIO_HANDLE handle, void *args, void *out, int iTimeout, int iChannel);
	
	// Description: Close play back
	// Parameter: handle SDK handle
	// Parameter: args Reference _IVYIO_CLOSE_PLAY_BACK_ARGS_TYPE1
	// Parameter: iTimeout Unit is ms
	// Parameter: iChannel Same as open video
	// Returns: IVYIO_RESULT Execution results
	// Remark: 
	
	IVYIO_RESULT IVYIO_API IVYIO_ClosePlayback(IVYIO_HANDLE handle, void *args, int iTimeout, int iChannel);
	
	// Description: Playback command
	// Parameter: handle SDK handle
	// Parameter: cmd Reference IVYIO_PLAYBACK_CMD
	// Parameter: cmdData The buffer of cmd data
	// Parameter: iTimeout Unit is ms
	// Parameter: iChannel Same as open video
	// Returns: IVYIO_RESULT Execution results
	// Remark: 
	
	IVYIO_RESULT IVYIO_API IVYIO_PlaybackCmd(IVYIO_HANDLE handle, IVYIO_PLAYBACK_CMD cmd, unsigned char *cmdData, int iTimeout, int iChannel);
	
    // Description: Playback command
    // Parameter: handle SDK handle
    // Parameter: cmd Reference IVYIO_PLAYBACK_CMD
    // Parameter: cmdData The buffer of cmd data
    // Parameter: outData The buffer of output data
    // Parameter: sizeOfOutData The actual size of output data
    // Parameter: iTimeout Unit is ms
    // Parameter: iChannel Same as open video
    // Returns: IVYIO_RESULT Execution results
    // Remark:

    IVYIO_RESULT IVYIO_API IVYIO_PlaybackCmdEx(IVYIO_HANDLE handle, IVYIO_PLAYBACK_CMD cmd, unsigned char *cmdData, unsigned char *outData, int *sizeOfOutData, int iTimeout, int iChannel);

	// Description: Open talk
	// Parameter: handle SDK handle
	// Parameter: iTimeout Unit is ms
	// Parameter: iChannel Channel No 0 - 31, 
	// Returns: IVYIO_RESULT Execution results
	// Remark: 
	
	IVYIO_RESULT IVYIO_API IVYIO_OpenTalk(IVYIO_HANDLE handle, int iTimeout, int iChannel);
	
	// Description: Close talk
	// Parameter: handle SDK handle
	// Parameter: iTimeout Unit is ms
	// Parameter: iChannel Channel No 0 - 31
	// Returns: IVYIO_RESULT Execution results
	// Remark: 
	
	IVYIO_RESULT IVYIO_API IVYIO_CloseTalk(IVYIO_HANDLE handle, int iTimeout, int iChannel);
	
	// Description: Send talk data
	// Parameter: handle SDK handle
	// Parameter: data The buffer of data
	// Parameter: iSizeOfData Talk data size
	// Parameter: iChannel Channel No 0 - 31
	// Returns: IVYIO_RESULT Execution results
	// Remark: 
	
	IVYIO_RESULT IVYIO_API IVYIO_SendTalkData(IVYIO_HANDLE handle, unsigned char *data, int iSizeOfData, int iChannel);
	
	// Description: Stream callback
	// Parameter: handle SDK handle
	// Parameter: cb Reference IVYIO_FUN_STREAM_CB
	// Parameter: type Reference IVYIO_STREAM_CB_TYPE
	// Parameter: iDecodeFmt Reference IVYIO_DECODE_FMT
	// Parameter: userData UserData you input, you will get it in callback
	// Returns: IVYIO_RESULT Execution results
	// Remark: None implementation
	
	IVYIO_RESULT IVYIO_API IVYIO_SetStreamCallback(IVYIO_HANDLE handle, 
		IVYIO_FUN_STREAM_CB cb, 
		IVYIO_STREAM_CB_TYPE type, 
		int iDecodeFmt, 
		void *userData);
	
	// Description: Get live video(decode) or audio stream 
	// Parameter: handle SDK handle
	// Parameter: stream Reference IVYIO_STREAM_TYPE
	// Parameter: data The pointer of data, SDK manage memory, caller don't alloc memory
	// Parameter: iOutLen Length of data
	// Parameter: iSpeed Stream speed
	// Parameter: iDecodeFmt Decode format reference IVYIO_DECODE_FMT
	// Parameter: iChannel Channel No 0 - 31
	// Returns: IVYIO_RESULT Execution results
	// Remark: If you call IVYIO_CloseVideo, data you get by this function will be free.
	
	IVYIO_RESULT IVYIO_API IVYIO_GetStreamData(IVYIO_HANDLE handle, 
		IVYIO_STREAM_TYPE stream, 
		unsigned char **data, 
		int *iOutLen, 
		int *iSpeed, 
		int iDecodeFmt, 
		int iChannel);
	
	// Description: Get raw stream data (video / audio)
	// Parameter: handle SDK handle
	// Parameter: stream Reference IVYIO_STREAM_TYPE
	// Parameter: data The pointer of data, SDK manage memory, caller don't alloc memory
	// Parameter: iOutLen Length of data
	// Parameter: iSpeed Stream speed
	// Parameter: iChannel Channel No 0 - 31
	// Returns: IVYIO_RESULT Execution results
	// Remark: If you call IVYIO_CloseVideo, data you get by this function will be free.
	
	IVYIO_RESULT IVYIO_API IVYIO_GetRawStreamData(IVYIO_HANDLE handle,
		IVYIO_STREAM_TYPE stream,
		unsigned char **data,
		int *iOutLen,
		int *iSpeed,
		int iChannel);


	
	// Description: Get video stream, stream data format is YUV420P
	// Parameter: handle SDK handle
	// Parameter: y y planar data
	// Parameter: u u planar data
	// Parameter: v v planar data
	// Parameter: yLineSize y line size
	// Parameter: uLineSize v line size
	// Parameter: vLineSize v line size
	// Parameter: w width
	// Parameter: h height
	// Parameter: bitRate bitRate
	// Parameter: frameRate frameRate
	// Parameter: key key frame
	// Parameter: iChannel Channel No 0 - 31
	// Returns: IVYIO_RESULT Execution results
	// Remark: SDK manage y u v memory. SDK don't convert stream format, so it is faster than IVYIO_GetStreamData
	
	IVYIO_RESULT IVYIO_API IVYIO_GetStreamYUV420P(IVYIO_HANDLE handle, 
		unsigned char **y,
		unsigned char **u,
		unsigned char **v,
		int *yLineSize,
		int *uLineSize,
		int *vLineSize,
		int *w, 
		int *h,
		int *bitRate,
		int *frameRate,
		int *key,
		int *speed,
		int iChannel);

	// Description: Get video stream, the stream split to two view
	// Parameter: handle SDK handle
	// Parameter: data1 The pointer of data, SDK manage memory, caller don't alloc memory
	// Parameter: iOutLen1 Length of data
	// Parameter: data2 The pointer of data, SDK manage memory, caller don't alloc memory
	// Parameter: iOutLen2 Length of data
	// Parameter: iSpeed Stream speed
	// Parameter: iDecodeFmt dst decode format, reference IVYIO_DECODE_FMT
	// Parameter: iChannel Channel No 0 - 31
	// Returns: IVYIO_RESULT Execution results
	// Remark: If you call IVYIO_CloseVideo, data you get by this function will be free.
	IVYIO_RESULT IVYIO_API IVYIO_GetSplitStreamData(IVYIO_HANDLE handle, unsigned char **data1, int *iOutLen1, unsigned char **data2, int *iOutLen2, int *iSpeed, int iDecodeFmt, int iChannel);

	// Description: Set play back stream callback
	// Parameter: handle SDK handle
	// Parameter: cb callback function 
	// Parameter: type callback type
	// Parameter: iDecodeFmt Decode type reference IVYIO_DECODE_FMT
	// Parameter: userData UserData you input, you will get it in callback
	// Returns: IVYIO_RESULT Execution results
	// Remark: 
	
	IVYIO_RESULT IVYIO_API IVYIO_SetPlaybackStreamCallback(IVYIO_HANDLE handle,
		IVYIO_FUN_STREAM_CB cb,
		IVYIO_STREAM_CB_TYPE type,
		int iDecodeFmt,
		void *userData);
	
	// Description: Get playback video stream, the stream split to two view
	// Parameter: handle SDK handle
	// Parameter: data1 The pointer of data, SDK manage memory, caller don't alloc memory
	// Parameter: iOutLen1 Length of data
	// Parameter: data2 The pointer of data, SDK manage memory, caller don't alloc memory
	// Parameter: iOutLen2 Length of data
	// Parameter: iSpeed Stream speed
	// Parameter: iDecodeFmt dst decode format, reference IVYIO_DECODE_FMT
	// Parameter: iChannel Channel No 0 - 31
	// Returns: IVYIO_RESULT Execution results
	// Remark: If you call IVYIO_CloseVideo, data you get by this function will be free.
	IVYIO_RESULT IVYIO_API IVYIO_GetPlaybackSplitStreamData(IVYIO_HANDLE handle, unsigned char **data1, int *iOutLen1, unsigned char **data2, int *iOutLen2, int *iSpeed, int iDecodeFmt, int iChannel);



	// Description: Get playback video(decode) or audio stream
	// Parameter: handle SDK handle
	// Parameter: stream Stream type
	// Parameter: data The pointer of data, SDK manage memory, caller don't alloc memory
	// Parameter: iOutLen Length of data
	// Parameter: iSpeed Media speed
	// Parameter: iDecodeFmt Reference IVYIO_DECODE_FMT
	// Parameter: iChannel Channel No 0 - 31
	// Returns: IVYIO_RESULT Execution results
	// Remark: 
	
	IVYIO_RESULT IVYIO_API IVYIO_GetPlaybackStreamData(IVYIO_HANDLE handle, 
		IVYIO_STREAM_TYPE stream, 
		unsigned char **data, 
		int *iOutLen, 
		int *iSpeed, 
		int iDecodeFmt, 
		int iChannel);
	
	// Description: Get playback raw video or audio stream
	// Parameter: handle SDK handle
	// Parameter: stream Stream type
	// Parameter: data The pointer of data, SDK manage memory, caller don't alloc memory
	// Parameter: iOutLen Length of data
	// Parameter: iSpeed Media speed
	// Parameter: iChannel Channel No 0 - 31
	// Returns: IVYIO_RESULT Execution results
	// Remark: 
	
	IVYIO_RESULT IVYIO_API IVYIO_GetPlaybackRawStreamData(IVYIO_HANDLE handle,
		IVYIO_STREAM_TYPE stream,
		unsigned char **data,
		int *iOutLen,
		int *iSpeed,
		int iChannel);


	
	// Description: Get playback YUV420P stream data
	// Parameter: handle SDK handle
	// Parameter: y y planar
	// Parameter: u u planar
	// Parameter: v v planar
	// Parameter: yLineSize line size y
	// Parameter: uLineSize line size u
	// Parameter: vLineSize line size v
	// Parameter: w width
	// Parameter: h height
	// Parameter: bitRate bit rate
	// Parameter: frameRate frame rate
	// Parameter: key key frame
	// Parameter: index index
	// Parameter: frameTag frame tag
	// Parameter: pts pts
	// Parameter: iChannel iChannel Channel No 0 - 31
	// Returns: IVYIO_RESULT Execution results
	// Remark: 
	
	IVYIO_RESULT IVYIO_API IVYIO_GetPlaybackStreamYUV420P(IVYIO_HANDLE handle, 
														unsigned char **y,
														unsigned char **u,
														unsigned char **v,
														int *yLineSize,
														int *uLineSize,
														int *vLineSize,
														int *w, 
														int *h,
														int *bitRate,
														int *frameRate,
														int *key,
														unsigned int *index, 
														unsigned int *frameTag,
														unsigned long long *pts,
														int iChannel);

	
	// Description: Record start
	// Parameter: handle SDK handle
	// Parameter: type Record type reference IVYIO_RECORD_TYPE
	// Parameter: szFileName Record full path
	// Parameter: iMaxSize The max size of record, 0 is default 256M, unit is bytes
	// Parameter: iChannel Channel No 0 - 31
	// Returns: IVYIO_RESULT Execution results
	// Remark: 
	
	IVYIO_RESULT IVYIO_API IVYIO_StartRecord(IVYIO_HANDLE handle, IVYIO_RECORD_TYPE type, const char *szFileName, int iMaxSize, int iChannel);
	
	// Description: Record stop
	// Parameter: handle SDK handle
	// Parameter: iChannel iChannel Channel No 0 - 31
	// Returns: IVYIO_RESULT Execution results
	// Remark: 
	
	IVYIO_RESULT IVYIO_API IVYIO_StopRecord(IVYIO_HANDLE handle, int iChannel);
	
	// Description: Send command
	// Parameter: handle SDK handle
	// Parameter: cmd Command id, it defined by this sdk user and embedded sdk user
	// Parameter: cmdData Command data buffer
	// Parameter: iSizeOfCmdData Command data buffer size
	// Parameter: response Response buffer
	// Parameter: iSizeOfResponse Response buffer size
	// Parameter: iTimeout Unit is ms
	// Returns: IVYIO_RESULT Execution results
	// Remark: 
	
	IVYIO_RESULT IVYIO_API IVYIO_SendCommand(IVYIO_HANDLE handle,
		unsigned int cmd,
		unsigned char *cmdData,
		int iSizeOfCmdData,
		unsigned char *response,
		int *iSizeOfResponse,
		int iTimeout);
	
	// Description: Get event
	// Parameter: handle SDK handle
	// Parameter: event Event id and data format It define by this sdk user and embedded sdk user
	// Returns: IVYIO_RESULT Execution results
	// Remark: You must get all event, or else event will hold memory; you got one event, a memory of event will be free
	
	IVYIO_RESULT IVYIO_API IVYIO_GetEvent(IVYIO_HANDLE handle, IVYIO_EVENT *event);

	// Description: Get event, only support IVY device
	// Parameter: handle SDK handle
	// Parameter: event Event id and data format It define by this sdk user and embedded sdk user
	// Returns: IVYIO_RESULT Execution results
	// Remark: You must get all event, or else event will hold memory; you got one event, a memory of event will be free.
	// buffer of event->data provide by caller.

	IVYIO_RESULT IVYIO_API IVYIO_GetEvent2(IVYIO_HANDLE handle, IVYIO_EVENT2 *event);
	
	// Description: Set event callback
	// Parameter: handle SDK handle
	// Parameter: cb Event callback
	// Parameter: userData UserData you input, you will get it in callback
	// Returns: IVYIO_RESULT Execution results
	// Remark: 
	
	IVYIO_RESULT IVYIO_API IVYIO_SetEventCallback(IVYIO_HANDLE handle, IVYIO_FUN_EVENT_CB cb, void *userData);
	
	// Description: Convert aac to pcm
	// Parameter: handle SDK handle
	// Parameter: aac Src data buffer
	// Parameter: aacSize Size of src data buffer
	// Parameter: pcm Dst data buffer
	// Parameter: pcmSize Size of dst data buffer
	// Parameter: channels
	// Parameter: bitsPerSample Bits of per sample
	// Parameter: channelLayout
	// Returns: IVYIO_RESULT Execution results
	// Remark: 
	
	IVYIO_RESULT IVYIO_API IVYIO_AAC2PCM(IVYIO_HANDLE handle, 
		unsigned char *aac, 
		unsigned int aacSize, 
		unsigned char *pcm, 
		unsigned int *pcmSize,  
		int channels, 
		int bitsPerSample, 
		int channelLayout);


	
	// Description: Set cpu count
	// Parameter: handle SDK handle
	// Parameter: count count of cpu
	// Returns: IVYIO_RESULT Execution results
	// Remark: SDK will set FFmpeg decoder use multi threads, if not set, default use single thread
	
	IVYIO_RESULT IVYIO_API IVYIO_SetCPUCount(IVYIO_HANDLE handle, int count);

	// Description: Relogin
	// Parameter: handle SDK handle
	// Parameter: usr username
	// Parameter: pwd password
	// Parameter: timeout 
	// Returns: IVYIO_RESULT Execution results
	// Remark: When changed device username or password, you can call it relogin instead of call destroy / create / login

	IVYIO_RESULT IVYIO_API IVYIO_ReloginAfterUserInfoChanged(IVYIO_HANDLE handle, char *usr, char *pwd, int timeout);
    
	// Description: Download records 
	// Parameter: handle SDK handle
	// Parameter: records record informations
	// Parameter: count count of records array
	// Returns: IVYIO_RESULT Execution results
	// Remark: It's only support Foscam nvr device.

	IVYIO_RESULT IVYIO_API IVYIO_DownLoadRecord(IVYIO_HANDLE handle, IVYIO_DOWNLOAD_RECORD *records, const char *dstPath, int timeout);

	// Description: Cancel download record
	// Parameter: handle SDK handle
	// Returns: IVYIO_RESULT Execution results
	// Remark: It's only support Foscam nvr device.

	IVYIO_RESULT IVYIO_API IVYIO_DownLoadCancel(IVYIO_HANDLE handle);

	// Description: Upgrade device
	// Parameter: handle SDK handle
	// Parameter: path Upgrade bin file path
	// Parameter: type Upgrade type, reference IVYIO_UPGRADE_TYPE
	// Parameter: channel device channel
	// Parameter: state Upgrade state, reference IVYIO_UPGRADE_STATE.
	// Parameter: timeout timeout
	// Returns: IVYIO_RESULT Execution results
	// Remark: If device is NVR or IPC, channel is zero. If device is NVR's channel, bit0 is channel1 ,bit1 is channel2......
	//			Only one channel on NVR can be upgraded at a time.

	IVYIO_RESULT IVYIO_API IVYIO_Upgrade(IVYIO_HANDLE handle, const char *path, int type, int channel, int *state, int timeout);
	IVYIO_RESULT IVYIO_API IVYIO_UpgradeEx(IVYIO_HANDLE handle, const char *path, int type, int channel, int *state, int timeout, int sleepMs);

	// Description: Set SDK flag to free buffer when call ClosePlayback
	// Parameter: handle SDK handle
	// Parameter: bFree true or false
	// Returns: None
	// Remark: None
	void IVYIO_API IVYIO_SetClosePlaybackFreeBuffer(IVYIO_HANDLE handle, bool bFree);

	// Description: Set SDK flag to quit playback put frame loop by call IVYIO_ManualForcePlaybackQuitPutFrame
	// Parameter: handle SDK handle
	// Parameter: bEnable true or false
	// Returns: None
	// Remark: None
	void IVYIO_API IVYIO_SetPlayBackForceQuitPutFrameByApp(IVYIO_HANDLE handle, bool bEnable);

	// Description: Manual quit playback put frame loop if set IVYIO_SetClosePlaybackFreeBuffer is true
	// Parameter: handle SDK handle
	// Parameter: bForce true or false
	// Returns: None
	// Remark: None
	void IVYIO_API IVYIO_ManualForcePlaybackQuitPutFrame(IVYIO_HANDLE handle, bool bForce);

	// Description: Down load Foscam IPC record in SDCard
	// Parameter: handle SDK handle
	// Parameter: srcPath Record file path, get it from GetPlaybackRecodList
	// Parameter: dstPath Dest file path
	// Parameter: timeout Timeout
	// Returns: IVYIO_RESULT Execution results
	// Remark: None
	IVYIO_RESULT IVYIO_API IVYIO_DownLoadFosIPCRecord(IVYIO_HANDLE handle, const char *srcPath, const char *dstPath, int timeout);

	// Description: Cannel download Foscam IPC record
	// Parameter: handle SDK handle
	// Parameter: timeout
	// Returns: IVYIO_RESULT Execution results
	// Remark: If download is not complete, record file have valid video frame, file is valid and can be play.
	IVYIO_RESULT IVYIO_API IVYIO_DownLoadFosIPCRecordCancel(IVYIO_HANDLE handle, int timeout);

    // Description: Set a flag to open talk interface
    // Parameter: handle SDK handle
    // Parameter: flag A flag
    // Returns: None
    // Remark: flag > 0 enable flag, else disable flag, just support Foscam IPC
    void IVYIO_API IVYIO_SetOpenTalkFlag(IVYIO_HANDLE handle, int flag);

    // Description: Set a flag to enable fix command id sync if device support
    // Parameter: handle SDK handle
    // Parameter: bEnable enable or disable
    // Returns: None
    // Remark: Only support foscam ipc
    void IVYIO_API IVYIO_SetEnableSyncCmdId(IVYIO_HANDLE handle, bool bEnable);

	// Description: Set a flag to enable decrypt video with sm4
	// Parameter: handle SDK handle
	// Parameter: bEnable enable or disable
	// Returns: None
	// Remark: Only support IVY ipc
	void IVYIO_API IVYIO_SetEnableVideoWithSM4(IVYIO_HANDLE handle, bool bEnable);

	// Description: Get a flag that enable decrypt video with sm4
	// Parameter: handle SDK handle
	// Returns: Enable or disable
	// Remark: Only support IVY ipc
	bool IVYIO_API IVYIO_GetEnableVideoWithSM4(IVYIO_HANDLE handle);

	// Description: Get picture list
	// Parameter: handle SDK handle
	// Parameter: A pointer to args, args Reference IVYIO_PICTURE_SEARCH
	// Parameter: A pointer to list, list Reference IVYIO_PICTURE_LIST
	// Parameter: iTimeout Unit is ms
	// Parameter: iChannel Same as open video
	// Returns: IVYIO_RESULT Execution results
	// Remark: list can be a array if you want more result. One IVYIO_PICTURE_LIST capacity is 20 picture infomation,
	// if you want to get 100 picture infomation, array count is 5. Only support IVY device.
	IVYIO_RESULT IVYIO_API IVYIO_GetPictureList(IVYIO_HANDLE handle, void *args, void *list, int iTimeout, int iChannel);

	// Description: Get picture data
	// Parameter: handle SDK handle
	// Parameter: args File information to request, a pointer to args, args Reference IVYIO_GET_PICTURE
	// Parameter: files File data, a pointer array, files pointer structure Reference IVYIO_PICTURE_FILE
	// Parameter: fileCount SDK got picture count
	// Parameter: iTimeout Unit is ms
	// Parameter: iChannel Channel number is 0-31, the channel must be 0 for IVY IPC 
	// Returns: IVYIO_RESULT Execution results
	// Remark: files param memory support by caller. Timeout must be more than 50 * 20 ms
	IVYIO_RESULT IVYIO_API IVYIO_GetPicture(IVYIO_HANDLE handle, void *args, void *files[20], int *fileCount, int iTimeout, int iChannel);

	// Description: tell ipc we will start send video
	// Parameter: handle SDK handle
	// Parameter: op bit0 video bit1 audio
	// Parameter: iTimeout Unit is ms
	// Returns: IVYIO_RESULT Execution results
	IVYIO_RESULT IVYIO_API IVYIO_OpenSendLive(IVYIO_HANDLE handle, int op, int timeout);

	// Description: tell ipc we will stop send video
	// Parameter: handle SDK handle
	// Parameter: op bit0 video bit1 audio
	// Parameter: iTimeout Unit is ms
	// Returns: IVYIO_RESULT Execution results
	IVYIO_RESULT IVYIO_API IVYIO_CloseSendLive(IVYIO_HANDLE handle, int op, int timeout);


	// Description: send video to ipc
	// Parameter: handle SDK handle
	// Parameter: frame buffer for IVYIO_FRAME 
	// Parameter: len buffer size
	// Returns: IVYIO_RESULT Execution results
	IVYIO_RESULT IVYIO_API IVYIO_SendVideo(IVYIO_HANDLE handle, char *frame, int len);

	// Description: send audio to ipc
	// Parameter: handle SDK handle
	// Parameter: frame buffer for IVYIO_FRAME 
	// Parameter: len buffer size
	// Returns: IVYIO_RESULT Execution results
	IVYIO_RESULT IVYIO_API IVYIO_SendAudio(IVYIO_HANDLE handle, char *frame, int len);

	// Description: reject video call
	// Parameter: handle SDK handle
	// Parameter: timeout timeout 
	// Returns: IVYIO_RESULT Execution results
	IVYIO_RESULT IVYIO_API IVYIO_RejectVideoCall(IVYIO_HANDLE handle, int timeout);

	// Description: get video call state
	// Parameter: handle SDK handle
	// Parameter: state 0:idle  1:calling  2:busy
	// Parameter: timeout timeout 
	// Returns: IVYIO_RESULT Execution results
	IVYIO_RESULT IVYIO_API IVYIO_GetVideoCallState(IVYIO_HANDLE handle, int *state, int timeout);

	// Description: scale NV12 resolution
	// Parameter: handle SDK handle
	// Parameter: srcBuf NV12 data
	// Parameter: srcSize NV12 data size
	// Parameter: srcW src width
	// Parameter: srcH src height 
	// Parameter: dstW dst width
	// Parameter: dstH dst height
	// Parameter: dstBuf dst NV12 buffer
	// Parameter: dstSize dst NV12 data size
	// Returns: IVYIO_RESULT Execution results
	IVYIO_RESULT IVYIO_API IVYIO_ScaleYUV(IVYIO_HANDLE handle, unsigned char *srcBuf, int srcSize, int srcW, int srcH, int dstW, int dstH, unsigned char **dstBuf, int *dstSize);

	// Description: decode playback video
	// Parameter: handle SDK handle
	// Parameter: srcFrame The pointer of IVYIO_FRAME 
	// Parameter: dstFrame The dst frame
	// Parameter: dstFrameSize dst decode frame size
	// Parameter: dstDecodeFmt dst decode format
	// Parameter: channel channel
	// Returns: IVYIO_RESULT Execution results
	IVYIO_RESULT IVYIO_API IVYIO_DecodePlaybackVideo(IVYIO_HANDLE handle, unsigned char *srcFrame, unsigned char **dstFrame, int *dstFrameSize, int dstDecodeFmt, int channel);

#ifdef __cplusplus
}
#endif

#endif
