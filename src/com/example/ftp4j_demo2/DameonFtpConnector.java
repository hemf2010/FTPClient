package com.example.ftp4j_demo2;

import android.util.Log;

public class DameonFtpConnector implements Runnable {

	private String TAG = "DameonFtpConnector";
	private boolean mDameonRunning = true;
	private FtpClientProxy mFTPClient;
	
	public DameonFtpConnector(FtpClientProxy mFTPClient) {
		super();
		this.mFTPClient = mFTPClient;
	}


	@Override
	public void run() {
		Log.v(TAG, "DameonFtpConnector ### run");
		while (mDameonRunning) {
			if (mFTPClient != null && !mFTPClient.isConnected()&& mFTPClient.isReady()) {
				mFTPClient.connectAndLogin();
			}
			try {
				Thread.sleep(GlobalConstant.MAX_DAMEON_TIME_WAIT);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	public boolean ismDameonRunning() {
		return mDameonRunning;
	}


	public void setmDameonRunning(boolean mDameonRunning) {
		this.mDameonRunning = mDameonRunning;
	}


	public void setmFTPClient(FtpClientProxy mFTPClient) {
		this.mFTPClient = mFTPClient;
	}
	
	
}

