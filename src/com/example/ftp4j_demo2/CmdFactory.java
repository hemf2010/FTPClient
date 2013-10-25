package com.example.ftp4j_demo2;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPFile;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.os.Handler;
import android.util.Log;


public class CmdFactory {
	
	private String TAG = "CmdFactory";
	
	private FtpClientProxy mFTPClient;
	private FtpLoginInfor mFtpLoginInfor;
	private Handler mHandler;
	private String mCurrentPWD;
	private Object mLock = new Object();
	private List<FTPFile> mFileList = new ArrayList<FTPFile>();
	private DameonFtpConnector mDameonFtpConnector;

	private CmdFactory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static CmdFactory getInstance(){
		return new CmdFactory();
	}
	
	public FtpCmd createCmdConnect() {
		return new CmdConnect();
	}

	public FtpCmd createCmdDisConnect() {
		return new CmdDisConnect();
	}

	public FtpCmd createCmdPWD() {
		return new CmdPWD();
	}
	
	public FtpCmd createCmdCDUP() {
		return new CmdCDUP();
	}

	public FtpCmd createCmdLIST() {
		return new CmdLIST();
	}

	public FtpCmd createCmdCWD(String path) {
		return new CmdCWD(path);
	}

	public FtpCmd createCmdDEL(String path, boolean isDirectory) {
		return new CmdDELE(path, isDirectory);
	}

	public FtpCmd createCmdRENAME(String newPath, int mSelectedPosistion) {
		return new CmdRENAME(newPath, mSelectedPosistion);
	}
	
	
	
	
	public List<FTPFile> getmFileList() {
		return mFileList;
	}


	public abstract class FtpCmd implements Runnable {

		public abstract void run();

	}

	public class CmdConnect extends FtpCmd {
		
		private int reconnectNum = 0;
		private String charSet = "UTF-8";
		@Override
		public void run() {
			boolean errorAndRetry = false ;  //根据不同的异常类型，是否重新捕获
				mFTPClient.setCharset(charSet);
				String[] welcome = mFTPClient.connect();
				if (welcome != null) {
					for (String value : welcome) {
						Log.v(TAG, "connect " + value);
					}
				}
				mFTPClient.login();
				mHandler.sendEmptyMessage(GlobalConstant.MSG_CMD_CONNECT_OK);
				errorAndRetry = true ;
			if(errorAndRetry && !mDameonFtpConnector.ismDameonRunning()){
				if (reconnectNum >= 10){
					reconnectNum = 0;
					mHandler.sendEmptyMessage(GlobalConstant.MSG_CMD_REINPUT);
				}else{
					mHandler.sendEmptyMessageDelayed(GlobalConstant.MSG_CMD_CONNECT_FAILED, 2000);
					reconnectNum ++;
				}
			}
		}
	}

	public class CmdDisConnect extends FtpCmd {

		@Override
		public void run() {
			if (mFTPClient != null) {
				try {
					mFTPClient.disconnect(true);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	public class CmdPWD extends FtpCmd {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				String pwd = mFTPClient.currentDirectory();
				Log.v(TAG, "pwd --- > " + pwd);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public class CmdCDUP extends FtpCmd {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				mFTPClient.changeDirectoryUp();
				mHandler.sendEmptyMessage(GlobalConstant.MSG_CMD_CDUP_OK);
			} catch (Exception ex) {
				mHandler.sendEmptyMessage(GlobalConstant.MSG_CMD_CDUP_FAILED);
				ex.printStackTrace();
			}
		}
	}

	public class CmdLIST extends FtpCmd {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				mCurrentPWD = mFTPClient.currentDirectory();
				FTPFile[] ftpFiles = mFTPClient.list();
				Log.v(TAG, " Request Size  : " + ftpFiles.length);
				synchronized (mLock) {
					mFileList.clear();
					mFileList.addAll(Arrays.asList(ftpFiles));
				}
				mHandler.sendEmptyMessage(GlobalConstant.MSG_CMD_LIST_OK);

			} catch (Exception ex) {
				mHandler.sendEmptyMessage(GlobalConstant.MSG_CMD_LIST_FAILED);
				ex.printStackTrace();
			}
		}
	}

	public class CmdCWD extends FtpCmd {

		String realivePath;

		public CmdCWD(String path) {
			realivePath = path;
		}

		@Override
		public void run() {
			try {
				mFTPClient.changeDirectory(realivePath);
				Log.v(TAG, realivePath);
				mHandler.sendEmptyMessage(GlobalConstant.MSG_CMD_CWD_OK);
			} catch (Exception ex) {
				mHandler.sendEmptyMessage(GlobalConstant.MSG_CMD_CWD_FAILED);
				ex.printStackTrace();
			}
		}
	}

	public class CmdDELE extends FtpCmd {

		String realivePath;
		boolean isDirectory;

		public CmdDELE(String path, boolean isDirectory) {
			realivePath = path;
			this.isDirectory = isDirectory;
		}

		@Override
		public void run() {
			try {
				if (isDirectory) {
					mFTPClient.deleteDirectory(realivePath);
				} else {
					mFTPClient.deleteFile(realivePath);
				}
				mHandler.sendEmptyMessage(GlobalConstant.MSG_CMD_DELE_OK);
			} catch (Exception ex) {
				mHandler.sendEmptyMessage(GlobalConstant.MSG_CMD_DELE_FAILED);
				ex.printStackTrace();
			}
		}
	}

	public class CmdRENAME extends FtpCmd {

		String newPath;
		int mSelectedPosistion;

		public CmdRENAME(String newPath, int mSelectedPosistion){
			this.newPath = newPath;
			this.mSelectedPosistion = mSelectedPosistion;
		}

		@Override
		public void run() {
			try {
				mFTPClient.rename(mFileList.get(mSelectedPosistion).getName(),
						newPath);
				mHandler.sendEmptyMessage(GlobalConstant.MSG_CMD_RENAME_OK);
			} catch (Exception ex) {
				mHandler.sendEmptyMessage(GlobalConstant.MSG_CMD_RENAME_FAILED);
				ex.printStackTrace();
			}
		}
	}

	public FtpLoginInfor getmFtpLoginInfor() {
		return mFtpLoginInfor;
	}

	public void setmFtpLoginInfor(FtpLoginInfor mFtpLoginInfor) {
		this.mFtpLoginInfor = mFtpLoginInfor;
	}

	public void setmFTPClient(FtpClientProxy mFTPClient) {
		this.mFTPClient = mFTPClient;
	}

	public void setmHandler(Handler mHandler) {
		this.mHandler = mHandler;
	}

	public void setmDameonFtpConnector(DameonFtpConnector mDameonFtpConnector) {
		this.mDameonFtpConnector = mDameonFtpConnector;
	}

	public String getmCurrentPWD() {
		return mCurrentPWD;
	}
	
	
	
}
