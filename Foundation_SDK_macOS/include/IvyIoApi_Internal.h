#ifndef _IVYIO_API_INTERNAL_H_1E5F81C248D74503B111BCF1FB3E03E6_
#define _IVYIO_API_INTERNAL_H_1E5F81C248D74503B111BCF1FB3E03E6_

//
//
// Internal api
//
//

#include "IvyIoDef.h"
#include "IvyIoDef_Internal.h"

#ifdef __cplusplus
extern "C" {
#endif

	
	// Description: Create SDK instance handle
	// Parameter: url Url and port
	// Parameter: szUid Device uid, SDK distinguish Ivy device and Foscam device by uid
	// Parameter: szUser Device name
	// Parameter: szPassword Device password
	// Parameter: mode P2P mode
	// Parameter: devType Foscam device type reference IVYIO_DEV_TYPE
	// Returns: IVYIO_HANDLE Execution results
	// Remark: Only Foscam app use this function
	
	IVYIO_HANDLE IVYIO_API IVYIO_CreateEx(IVYIO_URL *url, char *szUid, char *szUser, char *szPassword, IVYIO_P2P_MODE mode, int devType);
	
	// Description: Create SDK instance handle
	// Parameter: url Url and port
	// Parameter: szUid Device uid, SDK distinguish Ivy device and Foscam device by uid
	// Parameter: szMac Device mac
	// Parameter: szUser Device name
	// Parameter: szPassword Device password
	// Parameter: mode P2P mode
	// Parameter: devType Foscam device type reference IVYIO_DEV_TYPE
	// Parameter: streamType Foscam IPC streamType 0:main 1:sub, If it's not FoscamIPC, ignore this param.
	// Returns: IVYIO_HANDLE Execution results
	// Remark: Only Foscam app use this function

	IVYIO_HANDLE IVYIO_API IVYIO_CreateEx_1(IVYIO_URL *url, char *szUid, char *szMac, char *szUser, char *szPassword, IVYIO_P2P_MODE mode, int devType, int streamType);

    // Description: Create SDK instance handle
    // Parameter: url Url and port
    // Parameter: szUid Device uid, SDK distinguish Ivy device and Foscam device by uid
    // Parameter: szMac Device mac
    // Parameter: szUser Device name
    // Parameter: szPassword Device password
    // Parameter: mode P2P mode
    // Parameter: doorbellMode doorbell P2P mode
    // Parameter: devType Foscam device type reference IVYIO_DEV_TYPE
    // Parameter: streamType Foscam IPC streamType 0:main 1:sub, If it's not FoscamIPC, ignore this param.
    // Returns: IVYIO_HANDLE Execution results
    // Remark: Only Foscam app or Doorbell use this function

    IVYIO_HANDLE IVYIO_API IVYIO_CreateEx_2(IVYIO_URL *url, char *szUid, char *szMac, char *szUser, char *szPassword, IVYIO_P2P_MODE mode, IVYIO_P2P_MODE doorbellMode, int devType, int streamType);

	// Description: Get model No
	// Parameter: handle SDK handle
	// Parameter: iModel Foscam device model No.
	// Parameter: iTimeout Unit is ms
	// Returns: IVYIO_RESULT Execution results
	// Remark: Only Foscam app use this function
	
	IVYIO_RESULT IVYIO_API IVYIO_GetModel(IVYIO_HANDLE handle, int *iModel, int iTimeout);
	
	// Description: Get device sdk version
	// Parameter: handle SDK handle
	// Returns: int Ivy sdk or Foscam sdk. 0: ivy sdk 1: foscam sdk
	// Remark: 
	
	int IVYIO_API IVYIO_DevSdkVer(IVYIO_HANDLE handle);
	
	// Description: Convert ts to mp4
	// Parameter: szSrcPath TS file path
	// Parameter: iSrcLen Length of TS file path
	// Parameter: szDstPath MP4 file path
	// Parameter: iDstLen Length of MP4 file path
	// Parameter: iDstHasAudio 1:Has audio 0: none audio
	// Returns: none
	// Remark: 
	
	void IVYIO_API IVYIO_TS2MP4(char *szSrcPath, int iSrcLen, char *szDstPath, int iDstLen, int iDstHasAudio);
	
	// Description: Mstar aec auth challenge
	// Parameter: handle SDK handle
	// Parameter: challenge Buffer of challenge
	// Parameter: chlLen Size of challenge buffer
	// Parameter: response Resonse buffer
	// Parameter: rspLen Response buffer size
	// Parameter: timeout Unit is ms
	// Returns: IVYIO_RESULT Execution results
	// Remark: Only Foscam app use it
	
	IVYIO_RESULT IVYIO_API IVYIO_MstarAecChallenge(IVYIO_HANDLE handle, char *challenge, int chlLen, char *response, int *rspLen, int timeout);
	
	// Description: Snap a picture
	// Parameter: handle SDK handle
	// Parameter: szPath Save path
	// Parameter: timeout Unit is ms
	// Returns: IVYIO_RESULT Execution results
	// Remark: Only Foscam app use it
	
	IVYIO_RESULT IVYIO_API IVYIO_NetSnapPicture(IVYIO_HANDLE handle, char *szPath, int timeout);
	
	// Description: Convert a key frame data to picture data
	// Parameter: handle SDK handle
	// Parameter: inData Key frame data buffer
	// Parameter: iLenOfInData Key frame data buffer size
	// Parameter: outData Picture data buffer
	// Parameter: iLenOfOut As input is dst buffer size, as output is actual size
	// Parameter: bH265Src 0: H264 1: H265
	// Returns: IVYIO_RESULT Execution results
	// Remark: Only Foscam app use it
	
	IVYIO_RESULT IVYIO_API IVYIO_SnapshotH264RawVideoData(IVYIO_HANDLE handle, char *inData, int iLenOfInData, char *outData, int *iLenOfOut, int bH265Src);
	
	// Description: Get permission
	// Parameter: handle SDK handle
	// Parameter: lv permission level
	// Returns: IVYIO_RESULT Execution results
	// Remark: Only Foscam app use it
	
	IVYIO_RESULT IVYIO_API IVYIO_GetPermissionLevel(IVYIO_HANDLE handle, int *lv);
	
	// Description: Convert key frame to a picture
	// Parameter: handle SDK handle
	// Parameter: inData Key frame data buffer
	// Parameter: iLenOfInData Key frame data buffer size
	// Parameter: szDstPicturePath Picture path
	// Parameter: int channel
	// Returns: IVYIO_RESULT Execution results
	// Remark: 
	
	IVYIO_RESULT IVYIO_API IVYIO_KeyFrame2Picture(IVYIO_HANDLE handle, char *inData, int iLenOfInData, char *outData, int *iLenOfOutData, int channel);
	
	// Description: Get connection information
	// Parameter: handle SDK handle
	// Parameter: info Connection information buffer
	// Returns: IVYIO_RESULT Execution results
	// Remark: 
	
	IVYIO_RESULT IVYIO_API IVYIO_GetConnectInfo(IVYIO_HANDLE handle, IVYIO_CONNECT_INFO *info);
	
	// Description: Set P2P dispatch server address
	// Parameter: handle SDK handle
	// Parameter: svr Server address
	// Returns: IVYIO_RESULT Execution results
	// Remark: 
	
	IVYIO_RESULT IVYIO_API IVYIO_SetFoscamDispatchServer(IVYIO_HANDLE handle, char *svr);
    
    // Description: Set a path to sdk
    // Parameter: handle SDK handle
    // Parameter: path a path
    // Returns: none
    // Remark:
    
    void IVYIO_API IVYIO_SetDebugPath(IVYIO_HANDLE handle, char *path);
    
    // Description: Read hevc stream from mp4 and reencode it and rebuild a mp4
    // Parameter: szSrc mp4 source path
    // Parameter: szSrc mp4 dst path
    // Returns: // -1: alloc buffer error -2: demuxer error -3: recordTools error -4: w/h/bitrate/framerate error -5: unsupport
    // Remark: This function is only for mp4 made by jiegao nvr, sub stream 640x360, it can't handle other mp4.
    
    int IVYIO_API IVYIO_MP4_Rebuild(const char *szSrc, const char *szDst);

	// Description: Init tutk lib or uninit it
	// Parameter: initOrUninit 0: uninit 1:init
	// Returns: none
	// Remark: This function is only for app is on background if device is Foscam app, it's unused for other devices

	void IVYIO_API IVYIO_TUTK_InitOrUninit(int initOrUninit);

	// Description: Set heart beat maximum interval 
	// Parameter: handle SDK handle
	// Parameter: max time, unit is ms, sdk default value is 30s
	// Returns: none
	// Remark: Only support Ivy devices

	void IVYIO_API IVYIO_SetHeartBeatMaxInterval(IVYIO_HANDLE handle, unsigned int max);

	// Description: Send CGI to Foscam IPC devices 
	// Parameter: handle SDK handle
	// Parameter: szCGI Foscam cgi
	// Parameter: szXmlResp response buffer, xml format
	// Parameter: iSizeOfResp tell function response buffer size as input, function tell you real size as output 
	// Parameter: iTimeout timeout
	// Returns: IVYIO_RESULT Execution results
	// Remark: It's only support Foscam device.

	IVYIO_RESULT IVYIO_API IVYIO_DoCGI(IVYIO_HANDLE handle, const char *szCGI, char *szXmlResp, int *iSizeOfResp, int iTimeout);


	// Description: Get media data trasmit speed
	// Parameter: handle SDK handle
	// Returns: speed value
	// Remark: It's only support Foscam nvr device.
	int IVYIO_API IVYIO_MediaDataTransmitSpeed(IVYIO_HANDLE handle);

	// Description: 8000Hz resampled to 44100Hz
	// Parameter: handle SDK handle
	// Parameter: inBuffer Input data
	// Parameter: inSize Input data size
	// Parameter: outBuffer Out data buffer
	// Returns: Resampled audio size
	// Remark: It's only support Foscam nvr device.
	int IVYIO_API IVYIO_AudioResampled(IVYIO_HANDLE handle, char *inBuffer, int inSize, char *outBuffer);

    // Description: Probe local network state
    // Returns: 0:ok 1: may be error
    // Remark: Detect state by sending udp
    int IVYIO_API IVYIO_ProbeLocalNetworkState();


	// Description: Download Foscam NVR record
	// Parameter: handle SDK handle
	// Parameter: record Record informations array for download
	// Parameter: dstPath Download path
	// Parameter: count Record array size
	// Returns: IVYIO_RESULT Execution results 
	// Remark: None
	IVYIO_RESULT IVYIO_API IVYIO_DownLoadFosNVRRecord(IVYIO_HANDLE handle, IVYIO_RECORD_INFO *record, const char *dstPath, int count);


	// Description: Cancel download Foscam NVR record
	// Parameter: handle SDK handle
	// Returns: IVYIO_RESULT Execution results 
	// Remark: None
	IVYIO_RESULT IVYIO_API IVYIO_DownloadFosNVRCancel(IVYIO_HANDLE handle);

	// Description: Doolbell open video
	// Parameter: handle SDK handle
	// Parameter: iTimeout timeout
	// Returns: IVYIO_RESULT Execution results 
	// Remark: Don't need login first, you can call it directly
	IVYIO_RESULT IVYIO_API IVYIO_DoorBell_OpenVideo(IVYIO_HANDLE handle, int iTimeout);
	
	// Description: Doolbell close video
	// Parameter: handle SDK handle
	// Parameter: iTimeout timeout
	// Returns: IVYIO_RESULT Execution results 
	// Remark: None
	IVYIO_RESULT IVYIO_API IVYIO_DoorBell_CloseVideo(IVYIO_HANDLE handle, int iTimeout);
	
	// Description: Doolbell open audio
	// Parameter: handle SDK handle
	// Parameter: iTimeout timeout
	// Returns: IVYIO_RESULT Execution results 
	// Remark: Don't need login first, you can call it directly
	IVYIO_RESULT IVYIO_API IVYIO_DoorBell_OpenAudio(IVYIO_HANDLE handle, int iTimeout);
	
	// Description: Doolbell close audio
	// Parameter: handle SDK handle
	// Parameter: iTimeout timeout
	// Returns: IVYIO_RESULT Execution results 
	// Remark: None
	IVYIO_RESULT IVYIO_API IVYIO_DoorBell_CloseAudio(IVYIO_HANDLE handle, int iTimeout);
	
	// Description: Doolbell open talk
	// Parameter: handle SDK handle
	// Parameter: iTimeout timeout
	// Returns: IVYIO_RESULT Execution results 
	// Remark: Need call IVYIO_DoorBell_OpenAudio first.
	IVYIO_RESULT IVYIO_API IVYIO_DoorBell_OpenTalk(IVYIO_HANDLE handle, int iTimeout);
	
	// Description: Doolbell close talk
	// Parameter: handle SDK handle
	// Parameter: iTimeout timeout
	// Returns: IVYIO_RESULT Execution results 
	// Remark: None
	IVYIO_RESULT IVYIO_API IVYIO_DoorBell_CloseTalk(IVYIO_HANDLE handle, int iTimeout);
	
	// Description: Doolbell send talk
	// Parameter: handle SDK handle
	// Parameter: data talk data
	// Parameter: dataLne talk data length
	// Returns: IVYIO_RESULT Execution results 
	// Remark: None
	IVYIO_RESULT IVYIO_API IVYIO_DoorBell_SendTalkData(IVYIO_HANDLE handle, char *data, int dataLen);
	
	// Description: Get live video(decode) or audio stream 
	// Parameter: handle SDK handle
	// Parameter: stream Reference IVYIO_STREAM_TYPE
	// Parameter: data The pointer of data, SDK manage memory, caller don't alloc memory
	// Parameter: iOutLen Length of data
	// Parameter: iSpeed Stream speed
	// Parameter: iDecodeFmt Decode format reference IVYIO_DECODE_FMT
	// Returns: IVYIO_RESULT Execution results
	// Remark: If you call IVYIO_CloseVideo, data you get by this function will be free.

	IVYIO_RESULT IVYIO_API IVYIO_DoorBell_GetStreamData(IVYIO_HANDLE handle, 
		IVYIO_STREAM_TYPE stream, 
		unsigned char **data, 
		int *iOutLen, 
		int *iSpeed, 
		int iDecodeFmt);

	// Description: Get raw stream data (video / audio)
	// Parameter: handle SDK handle
	// Parameter: stream Reference IVYIO_STREAM_TYPE
	// Parameter: data The pointer of data, SDK manage memory, caller don't alloc memory
	// Parameter: iOutLen Length of data
	// Parameter: iSpeed Stream speed
	// Returns: IVYIO_RESULT Execution results
	// Remark: If you call IVYIO_CloseVideo, data you get by this function will be free.

	IVYIO_RESULT IVYIO_API IVYIO_DoorBell_GetRawStreamData(IVYIO_HANDLE handle,
		IVYIO_STREAM_TYPE stream,
		unsigned char **data,
		int *iOutLen,
		int *iSpeed);
		
	// Description: Start doolbell record 
	// Parameter: handle SDK handle
	// Parameter: type Reference IVYIO_RECORD_TYPE
	// Parameter: szFileName Record file name
	// Returns: IVYIO_RESULT Execution results
	// Remark: Just support mp4
	
	IVYIO_RESULT IVYIO_API IVYIO_DoorBell_StartRecord(IVYIO_HANDLE handle, IVYIO_RECORD_TYPE type, const char *szFileName);

	// Description: Stop doolbell record 
	// Parameter: handle SDK handle
	// Returns: IVYIO_RESULT Execution results
	// Remark: None
	
	IVYIO_RESULT IVYIO_API IVYIO_DoorBell_StopRecord(IVYIO_HANDLE handle);

	// Description: Check handle state
	// Parameter: handle SDK handle
	// Returns: IVYIO_HANDLE_STATE 
	// Remark: None
	
	IVYIO_HANDLE_STATE IVYIO_API IVYIO_DoorBell_CheckHandle(IVYIO_HANDLE handle);

	// Description: Get doorbell event
	// Parameter: handle SDK handle
	// Parameter: event SDK event
	// Returns: IVYIO_RESULT Execution results 
	// Remark: None
	
	IVYIO_RESULT IVYIO_API IVYIO_DoorBell_GetEvent(IVYIO_HANDLE handle, IVYIO_EVENT *event);

    // Description: Set flag about opentalk to SDK
    // Parameter: handle SDK handle
    // Parameter: flag flag value, 0 or >0
    // Returns: None
    // Remark: Just only for Foscam IPC, support iOS or macOS. Device will do somthing about sound speaker when you set this flag > 0. Using this api reason is iOS AEC make sound volume down , device may turn the volume up.

    void IVYIO_API IVYIO_DoorBell_SetOpenTalkFlag(IVYIO_HANDLE handle, int flag);

#ifdef __cplusplus
}
#endif

#endif
