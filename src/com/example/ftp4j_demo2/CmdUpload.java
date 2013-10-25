package com.example.ftp4j_demo2;

import java.io.File;

import android.os.AsyncTask;
import android.os.Handler;

public class CmdUpload extends AsyncTask<String, Integer, Boolean> {

	String path;
	private FtpClientProxy mFTPClient;
	private Handler mHandler;

	public CmdUpload(FtpClientProxy mFTPClient, Handler mHandler) {
		this.mFTPClient = mFTPClient;
		this.mHandler = mHandler;
	}

	@Override
	protected Boolean doInBackground(String... params) {
		path = params[0];
		try {
			File file = new File(path);
			mFTPClient.upload(file, new DownloadFTPDataTransferListener(
					file.length(), mHandler));
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
}