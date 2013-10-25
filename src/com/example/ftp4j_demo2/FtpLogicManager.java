package com.example.ftp4j_demo2;

import android.content.Context;

public class FtpLogicManager {
	private FtpLoginInfor mFtpLoginInfor;
	private boolean mExpirated;
	private LoginXmlParse mLoginXmlParse;
	private Context mContext;
	public FtpLogicManager(Context mContext) {
		this.mContext = mContext;
		mLoginXmlParse = new LoginXmlParse(mContext);
		if(mLoginXmlParse.LoginFileExist()){
			mFtpLoginInfor =  mLoginXmlParse.ParseLoginInfor();
		}else{
			mFtpLoginInfor = new FtpLoginInfor("192.168.0.1", 2121, "anonymous", "password",0);
			mExpirated = true;
		}
	}
	public FtpLoginInfor getmFtpLoginInfor() {
		return mFtpLoginInfor;
	}
	
	public boolean saveLoginInfor(){
		return mLoginXmlParse.SaveLoginInfor(mFtpLoginInfor);
	}
	public boolean isExpirated() {
		long now = System.currentTimeMillis();
		if((now - mFtpLoginInfor.getExpirated())/1000 > 3600){
			mExpirated = true;
		}else{
			mExpirated = false;
		}
		
		return mExpirated;
	}
	
	
}
