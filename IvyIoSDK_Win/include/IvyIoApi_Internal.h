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

#ifdef __cplusplus
}
#endif

#endif
