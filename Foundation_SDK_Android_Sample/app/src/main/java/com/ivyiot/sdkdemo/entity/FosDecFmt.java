package com.ivyiot.sdkdemo.entity;

public class FosDecFmt {
		
	public static final int FOSDECTYPE_VIDEORAW = 0;
	public static final int FOSDECTYPE_ARGB32 = 1; //packed ARGB 8:8:8:8, 32bpp, ARGBARGB...
	public static final int FOSDECTYPE_RGBA32 = 2; //packed RGBA 8:8:8:8, 32bpp, RGBARGBA...
	public static final int FOSDECTYPE_ABGR32 = 3; //packed ABGR 8:8:8:8, 32bpp, ABGRABGR...
	public static final int FOSDECTYPE_BGRA32 = 4; //packed BGRA 8:8:8:8, 32bpp, BGRABGRA...
	public static final int FOSDECTYPE_RGB24 = 5;  //packed RGB 8:8:8, 24bpp, RGBRGB...
	public static final int FOSDECTYPE_BGR24 = 6;  //packed RGB 8:8:8, 24bpp, BGRBGR...
	public static final int FOSDECTYPE_RGB565BE = 7; //packed RGB 5:6:5, 16bpp, (msb)   5R 6G 5B(lsb), big-endian
	public static final int FOSDECTYPE_RGB565LE = 8; //packed RGB 5:6:5, 16bpp, (msb)   5R 6G 5B(lsb), little-endian
	public static final int FOSDECTYPE_BGR565BE = 9; //packed BGR 5:6:5, 16bpp, (msb)   5B 6G 5R(lsb), big-endian
	public static final int FOSDECTYPE_BGR565LE = 10; //packed BGR 5:6:5, 16bpp, (msb)   5B 6G 5R(lsb), little-endian
	public static final int FOSDECTYPE_YUV420 = 11;
	public static final int FOSDECTYPE_YUV422 = 12;
	public static final int FOSDECTYPE_UYVY422 = 13;
	public static final int FOSDECTYPE_H264 = 14;
	public static final int FOSDECTYPE_MJPEG = 15;
	
	public static final int FOSDECTYPE_MJPEG_BASE64 = 16;
	public static final int FOSDECTYPE_H264_BASE64 = 17;
	
	public static final int FOSDECTYPE_AUDIORAW = 18;
	public static final int FOSDECTYPE_G726 = 19;
	public static final int FOSDECTYPE_G711U = 20;
	public static final int FOSDECTYPE_PCM = 21;
	public static final int FOSDECTYPE_ADPCM = 22;
	public static final int FOSDECTYPE_G711A = 23;
	public static final int FOSDECTYPE_AAC = 24;
	public static final int FOSDECTYPE_HEVC = 25;
	
}
