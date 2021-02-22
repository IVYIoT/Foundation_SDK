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

enum
{
    IVYIO_LOCAL_NETWORK_OK = 0,
    IVYIO_LOCAL_NETWORK_MAYBE_ERROR = 1
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

typedef enum
{
	IVYIO_FOS_RECORD_TYPE_V3_SCHEDULE = 0x00000001 << 0,
	IVYIO_FOS_RECORD_TYPE_V3_MANUAL = 0x00000001 << 1,
	IVYIO_FOS_RECORD_TYPE_V3_MDALARM = 0x00000001 << 2,
	IVYIO_FOS_RECORD_TYPE_V3_SDALARM = 0x00000001 << 3,
	IVYIO_FOS_RECORD_TYPE_V3_IOALARM = 0x00000001 << 4,
	IVYIO_FOS_RECORD_TYPE_V3_TDALARM = 0x00000001 << 5,
	IVYIO_FOS_RECORD_TYPE_V3_HDALARM = 0x00000001 << 6,
	IVYIO_FOS_RECORD_TYPE_V3_HMALARM = 0x00000001 << 7,
	IVYIO_FOS_RECORD_TYPE_V3_LIVERCD = 0x00000001 << 8,
	IVYIO_FOS_RECORD_TYPE_V3_TMALARM = 0x00000001 << 9,
	IVYIO_FOS_RECORD_TYPE_V3_BKALARM = 0x00000001 << 10,
	IVYIO_FOS_RECORD_TYPE_V3_CLALARM = 0x00000001 << 11, 
	IVYIO_FOS_RECORD_TYPE_V3_LMALARM = 0x00000001 << 12, 
	IVYIO_FOS_RECORD_TYPE_V3_FDALARM = 0x00000001 << 13	
} IVYIO_FOS_RECORD_TYPE_V3;




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

typedef struct _IVYIO_GET_RECORD_LIST_ARGS_TYPE5_
{
    int getRecordListArgsType;
    unsigned int startTime;
    unsigned int endTime;
    int recordType;
    int startNo;
}ATTRIBUTE_PACKED IVYIO_GET_RECORD_LIST_ARGS_TYPE5, *PIVYIO_GET_RECORD_LIST_ARGS_TYPE5;

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


// Foscam IPC SD record search parameter type 4
typedef struct _IVYIO_GET_RECORD_LIST_ARGS_TYPE4_
{
	int getRecordListArgsType; // always 4
	int recordPath; // Must be 0, SD card
	unsigned int startTime;
	unsigned int endTime;
	int recordType; // See IVYIO_FOS_RECORD_TYPE_V3
	int startNo;
	int cnt;
}ATTRIBUTE_PACKED IVYIO_GET_RECORD_LIST_ARGS_TYPE4, *PIVYIO_GET_RECORD_LIST_ARGS_TYPE4;

typedef struct _IVYIO_RECORD_INFO_TYPE4_
{
	unsigned int startTime;
	unsigned int endTime;
	unsigned int recordType; // See IVYIO_FOS_RECORD_TYPE_V3
	char fileName[256];
}ATTRIBUTE_PACKED IVYIO_RECORD_INFO_TYPE4, *PIVYIO_RECORD_INFO_TYPE4;

typedef struct _IVYIO_RECORD_LIST_ARGS_TYPE4_
{
	int totalCnt;					
	int curCnt;								
	IVYIO_RECORD_INFO_TYPE4 list[50];
}ATTRIBUTE_PACKED IVYIO_RECORD_LIST_ARGS_TYPE4, *PIVYIO_RECORD_LIST_ARGS_TYPE4;



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
    int streamFmt;
}ATTRIBUTE_PACKED IVYIO_PLAY_BACK_VIDEO_INFO_4_FOSIPC, *pIVYIO_PLAY_BACK_VIDEO_INFO_4_FOSIPC;


// Foscam NVR fast/slow-forward fast-reverse
// fast forward: 4x, 8x, 16x, 32 -> value: 4, 8, 16, 32
// slow fowward: 1/2x, 1/4x, 1/8x, 1/16x, 1/32x -> value: 2, 4, 8, 16, 32
// fast reverse: 4x, 8x, 16x, 32 -> value: 4, 8, 16, 32
typedef struct _IVYIO_FOS_NVR_PLAY_BACK_CMD_VALUE_
{
	unsigned int time; // start time, unit second
	int value; // cmd value
}ATTRIBUTE_PACKED IVYIO_FOS_NVR_PLAY_BACK_CMD_VALUE, *PIVYIO_FOS_NVR_PLAY_BACK_CMD_VALUE;

// event

#ifdef _WIN32
#pragma pack(pop)
#endif


#endif

