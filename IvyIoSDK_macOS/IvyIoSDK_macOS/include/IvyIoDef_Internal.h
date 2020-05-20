#ifndef _IVYIO_DEF_INTERNAL_H_B4B0A801ACC123be80EE295D810FD0E7_
#define _IVYIO_DEF_INTERNAL_H_B4B0A801ACC123be80EE295D810FD0E7_

//
//
// This is internal interface
//
//

#include "IvyIoDef.h"


// Device sdk type
enum
{
	DEV_SDK_IVY = 0,
	DEV_SDK_FOS = 1
};


#ifndef _WIN32
#define ATTRIBUTE_PACKED __attribute__((packed))
#else
#define ATTRIBUTE_PACKED
#pragma pack(push)
#pragma pack(1)
#endif


#define MAX_COUNT_OF_RECORD_FOS 10
#define MAX_LENGTH_OF_RECORD_FOS 256

// Request open video
// It's for opening foscam nvr and bpi
typedef struct _IVYIO_OPEN_VIDEO_ARGS_TYPE1_
{
	int openVideoArgsType; // always 1
	int streamType;
	int videoMode;
	int qcMode;
	int chMode;
	int reDecChs;
}ATTRIBUTE_PACKED IVYIO_OPEN_VIDEO_ARGS_TYPE1, *PIVYIO_OPEN_VIDEO_ARGS_TYPE1;


// Request record list arg 
typedef struct _IVYIO_GET_RECORD_LIST_ARGS_TYPE2_
{
	int getRecordListArgsType;
	char recordPath[256];
	unsigned int startTime;
	unsigned int endTime;
	int recordType;
	int startNo;
}ATTRIBUTE_PACKED IVYIO_GET_RECORD_LIST_ARGS_TYPE2, *PIVYIO_GET_RECORD_LIST_ARGS_TYPE2;	

// record list
typedef struct _IVYIO_RECORD_LIST_ARGS_TYPE2_
{
	int totalCnt;												//total count.
	int curCnt;												//current count.
	char recordInfo[MAX_COUNT_OF_RECORD_FOS][MAX_LENGTH_OF_RECORD_FOS];
}ATTRIBUTE_PACKED IVYIO_RECORD_LIST_ARGS_TYPE2, *PIVYIO_RECORD_LIST_ARGS_TYPE2;	

// Foscam nvr requset args
typedef struct _IVYIO_GET_RECORD_LIST_ARGS_TYPE3_
{
	int getRecordListArgsType;
	unsigned long long startTime;
	unsigned long long endTime;
	int recordType; // 1:schedule 2:manual 4:motion 8:ioalarm 1|2|4|8:all
	int startNo;
}ATTRIBUTE_PACKED IVYIO_GET_RECORD_LIST_ARGS_TYPE3, *PIVYIO_GET_RECORD_LIST_ARGS_TYPE3;


typedef struct _IVYIO_RECORD_INFO_
{
	unsigned int			indexNO;
	char			        channel;
	unsigned int			fileSize;
	unsigned int			tmStart;
	unsigned int			tmEnd;
	char			        recordType;
}ATTRIBUTE_PACKED IVYIO_RECORD_INFO, *PIVYIO_RECORD_INFO;

typedef struct _IVYIO_RECORD_LIST_ARGS_TYPE3_
{
	int total;
	int curCnt;
	IVYIO_RECORD_INFO recordInfo[MAX_COUNT_OF_RECORD_FOS];
}ATTRIBUTE_PACKED IVYIO_RECORD_LIST_ARGS_TYPE3, *PIVYIO_RECORD_LIST_ARGS_TYPE3;


// open play back
typedef struct _IVYIO_OPEN_PLAY_BACK_ARGS_TYPE2_{
	int openPlaybackArgsType; // always 2
	unsigned int sTime;
	unsigned int eTime;
	unsigned int offsetTime;
	int videoMode;
}ATTRIBUTE_PACKED IVYIO_OPEN_PLAY_BACK_ARGS_TYPE2, *PIVYIO_OPEN_PLAY_BACK_ARGS_TYPE2;

typedef struct _IVYIO_OPEN_PLAY_BACK_ARGS_TYPE3_
{
	int openPlaybackArgsType; // always 3
	char szFilePath[256];
}ATTRIBUTE_PACKED IVYIO_OPEN_PLAY_BACK_ARGS_TYPE3, *PIVYIO_OPEN_PLAY_BACK_ARGS_TYPE3;


// seek
typedef struct _IVYIO_PLAY_BACK_SEEK_ARGS_TYPE3_
{
	int openPlaybackArgsType;
	unsigned int seekTime;
}ATTRIBUTE_PACKED IVYIO_PLAY_BACK_SEEK_ARGS_TYPE3, *PIVYIO_PLAY_BACK_SEEK_ARGS_TYPE3;

// Foscam IPC record information
typedef struct _IVYIO_PLAY_BACK_VIDEO_INFO_4_FOSIPC_
{
    int w;
    int h;
    unsigned int totalFrame;
    unsigned int totalTime;
}ATTRIBUTE_PACKED IVYIO_PLAY_BACK_VIDEO_INFO_4_FOSIPC, *pIVYIO_PLAY_BACK_VIDEO_INFO_4_FOSIPC;
// event

#ifdef _WIN32
#pragma pack(pop)
#endif


#endif

