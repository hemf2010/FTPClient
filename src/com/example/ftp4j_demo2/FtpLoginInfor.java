package com.example.ftp4j_demo2;

public class FtpLoginInfor {

	private String mFTPHost ;
	private int mFTPPort ;
	private String mFTPUser ;
	private String mFTPPassword ;
	private long Expirated;
	
	
	
	public FtpLoginInfor() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FtpLoginInfor(String mFTPHost, int mFTPPort, String mFTPUser,
			String mFTPPassword,long Expirated) {
		super();
		this.mFTPHost = mFTPHost;
		this.mFTPPort = mFTPPort;
		this.mFTPUser = mFTPUser;
		this.mFTPPassword = mFTPPassword;
		this.Expirated = Expirated;
	}
	
	public String getmFTPHost() {
		return mFTPHost;
	}
	public void setmFTPHost(String mFTPHost) {
		this.mFTPHost = mFTPHost;
	}
	public int getmFTPPort() {
		return mFTPPort;
	}
	public void setmFTPPort(int mFTPPort) {
		this.mFTPPort = mFTPPort;
	}
	public String getmFTPUser() {
		return mFTPUser;
	}
	public void setmFTPUser(String mFTPUser) {
		this.mFTPUser = mFTPUser;
	}
	public String getmFTPPassword() {
		return mFTPPassword;
	}
	public void setmFTPPassword(String mFTPPassword) {
		this.mFTPPassword = mFTPPassword;
	}

	public long getExpirated() {
		return Expirated;
	}

	public void setExpirated(long expirated) {
		Expirated = expirated;
	}
	
	
}
