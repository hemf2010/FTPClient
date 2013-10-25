package com.example.ftp4j_demo2;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceProxy {
	
	private ExecutorService mThreadPool;
	private CmdFactory mCmdFactory;
	
	
	
	private ExecutorServiceProxy() {
		mThreadPool = Executors.newFixedThreadPool(GlobalConstant.MAX_THREAD_NUMBER);
	}
	
	public static ExecutorServiceProxy getInstance(){
		return new ExecutorServiceProxy();
	}

	public void executeConnectRequest() {
		mThreadPool.execute(mCmdFactory.createCmdConnect());
	}

	public void executeDisConnectRequest() {
		mThreadPool.execute(mCmdFactory.createCmdDisConnect());
	}

	public void executePWDRequest() {
		mThreadPool.execute(mCmdFactory.createCmdPWD());
	}

	public void executeLISTRequest() {
		mThreadPool.execute(mCmdFactory.createCmdLIST());
	}

	public void executeCWDRequest(String path) {
		mThreadPool.execute(mCmdFactory.createCmdCWD(path));
	}

	public void executeDELERequest(String path, boolean isDirectory) {
		mThreadPool.execute(mCmdFactory.createCmdDEL(path, isDirectory));
	}
	
	public void executeCDUPRequest() {
		mThreadPool.execute(mCmdFactory.createCmdCDUP());
	}

	public void executeREANMERequest(String newPath, int mSelectedPosistion) {
		mThreadPool.execute(mCmdFactory.createCmdRENAME(newPath, mSelectedPosistion));
	}
	
	public List<Runnable> shutdownNow(){
		return mThreadPool.shutdownNow();
	}

	public void setmCmdFactory(CmdFactory mCmdFactory) {
		this.mCmdFactory = mCmdFactory;
	}
	
	
	
}
