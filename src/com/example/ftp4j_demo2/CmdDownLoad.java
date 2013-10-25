package com.example.ftp4j_demo2;

import it.sauronsoftware.ftp4j.FTPFile;

import java.io.File;


import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;

public class CmdDownLoad extends AsyncTask<Void, Integer, Boolean> {

	private static final String OLIVE_DIR_NAME = "OliveDownload";
	private static String mAtSDCardPath;
	private static FTPFile downloadFile;
	private FtpClientProxy mFTPClient;
	private Handler mHandler;
	
	public CmdDownLoad(FtpClientProxy mFTPClient, Handler mHandler, FTPFile downloadFile) {
		
		this.mFTPClient = mFTPClient;
		this.mHandler = mHandler;
		this.downloadFile = downloadFile;
	}

	private static String getParentRootPath() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			if (mAtSDCardPath != null) {
				return mAtSDCardPath;
			} else {
				mAtSDCardPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + OLIVE_DIR_NAME;
				File rootFile = new File(mAtSDCardPath);
				if (!rootFile.exists()) {
					rootFile.mkdir();
				}
				return mAtSDCardPath;
			}
		}
		return null;
	}
	
	@Override
	protected Boolean doInBackground(Void... params) {
		try {
			String localPath = getParentRootPath() + File.separator
					+ downloadFile.getName();
			mFTPClient.download(
					downloadFile.getName(),
					new File(localPath),
					new DownloadFTPDataTransferListener(downloadFile.getSize(), mHandler));
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	protected void onProgressUpdate(Integer... progress) {

	}

	protected void onPostExecute(Boolean result) {
		mHandler.sendEmptyMessage(GlobalConstant.MSG_PROGRESSBAR_DIALOG_DISMISS);
	}

	public static FTPFile getDownloadFile() {
		return downloadFile;
	}

	public static void setDownloadFile(FTPFile downloadFile) {
		CmdDownLoad.downloadFile = downloadFile;
	}

	
	
}
