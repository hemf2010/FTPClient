package com.example.ftp4j_demo2;

public interface GlobalConstant {
	
	public static final int GLOBAL_START = 0;
	public static final int MSG_CMD_CONNECT_OK = GLOBAL_START + 1;
	public static final int MSG_CMD_CONNECT_FAILED = GLOBAL_START + 2;
	public static final int MSG_CMD_LIST_OK = GLOBAL_START + 3;
	public static final int MSG_CMD_LIST_FAILED = GLOBAL_START + 4;
	public static final int MSG_CMD_CWD_OK = GLOBAL_START + 5;
	public static final int MSG_CMD_CWD_FAILED = GLOBAL_START + 6;
	public static final int MSG_CMD_DELE_OK = GLOBAL_START + 7;
	public static final int MSG_CMD_DELE_FAILED = GLOBAL_START + 8;
	public static final int MSG_CMD_RENAME_OK = GLOBAL_START + 9;
	public static final int MSG_CMD_RENAME_FAILED = GLOBAL_START + 10;
	public static final int MAX_THREAD_NUMBER = 5;
	public static final int MAX_DAMEON_TIME_WAIT = 2 * 1000; // millisecond
	public static final int MSG_CMD_REINPUT = GLOBAL_START + 11;
	public static final int MSG_CMD_CDUP_OK = GLOBAL_START + 12;
	public static final int MSG_CMD_CDUP_FAILED = GLOBAL_START + 13;
	public static final int MSG_PROGRESSBAR_DIALOG_DISMISS = GLOBAL_START + 14;
	public static final int MSG_PROGRESSBAR_DOWNLOAD_SET_MAX = GLOBAL_START + 15;
	public static final int MSG_PROGRESSBAR_DOWNLOAD_SET_VALSE = GLOBAL_START + 16;
}
