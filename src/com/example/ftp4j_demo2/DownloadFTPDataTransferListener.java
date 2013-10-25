package com.example.ftp4j_demo2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.style.BulletSpan;

import com.example.ftp4j_demo2.ext.LogUtils;

import it.sauronsoftware.ftp4j.FTPDataTransferListener;

class DownloadFTPDataTransferListener implements
		FTPDataTransferListener {

	private static final String TAG = "DownloadFTPDataTransferListener";
	private int totolTransferred = 0;
	private long fileSize = -1;
	private Handler mHandler;

	public DownloadFTPDataTransferListener(long fileSize, Handler mHandler) {
		if (fileSize <= 0) {
			throw new RuntimeException(
					"the size of file muset be larger than zero.");
		}
		this.fileSize = fileSize;
		this.mHandler =  mHandler;
	}

	@Override
	public void aborted() {
		// TODO Auto-generated method stub
		LogUtils.v(TAG, "FTPDataTransferListener : aborted");
	}

	@Override
	public void completed() {
		// TODO Auto-generated method stub
		LogUtils.v(TAG, "FTPDataTransferListener : completed");
		//setLoadProgress(mPbLoad.getMax());
		mHandler.sendEmptyMessage(GlobalConstant.MSG_PROGRESSBAR_DOWNLOAD_SET_MAX);
	}

	@Override
	public void failed() {
		// TODO Auto-generated method stub
		LogUtils.v(TAG, "FTPDataTransferListener : failed");
	}

	@Override
	public void started() {
		// TODO Auto-generated method stub
		LogUtils.v(TAG, "FTPDataTransferListener : started");
	}

	@Override
	public void transferred(int length) {
		totolTransferred += length;
		float percent = (float) totolTransferred / this.fileSize;
		LogUtils.v(TAG, "FTPDataTransferListener : transferred # percent @@" + percent);
		//setLoadProgress((int) (percent * mPbLoad.getMax()));
		Message msg = new Message();
		Bundle data = new Bundle();
		data.putFloat("percent", percent);
		msg.setData(data);
		mHandler.sendMessage(msg);
		
		
	}
}