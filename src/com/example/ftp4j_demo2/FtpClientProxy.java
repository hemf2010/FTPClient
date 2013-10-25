package com.example.ftp4j_demo2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.net.ssl.SSLSocketFactory;

import android.util.Log;

import it.sauronsoftware.ftp4j.FTPAbortedException;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPCommunicationListener;
import it.sauronsoftware.ftp4j.FTPConnector;
import it.sauronsoftware.ftp4j.FTPDataTransferException;
import it.sauronsoftware.ftp4j.FTPDataTransferListener;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPFile;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;
import it.sauronsoftware.ftp4j.FTPListParseException;
import it.sauronsoftware.ftp4j.FTPListParser;
import it.sauronsoftware.ftp4j.FTPReply;
import it.sauronsoftware.ftp4j.FTPTextualExtensionRecognizer;

public class FtpClientProxy extends FTPClient {
	
	private FtpLoginInfor mFtpLoginInfor;
	private static String TAG = "FtpClientProxy";
	private boolean isReady = false;

	public FtpClientProxy() {
		super();
		// TODO Auto-generated constructor stub
	}

	//start new create methods 
	
	public FtpLoginInfor getmFtpLoginInfor() {
		return mFtpLoginInfor;
	}
	

	public boolean isReady() {
		return isReady;
	}

	public void setmFtpLoginInfor(FtpLoginInfor mFtpLoginInfor) {
		isReady = true;
		this.mFtpLoginInfor = mFtpLoginInfor;
	}
	
	
	
	public void connectAndLogin(){
			try {
				connect(mFtpLoginInfor.getmFTPHost(), mFtpLoginInfor.getmFTPPort());
				login(mFtpLoginInfor.getmFTPUser(), mFtpLoginInfor.getmFTPPassword());
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FTPIllegalReplyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FTPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public String[] connect(){
		String[] tempStr = null;
		try {
			tempStr = connect(mFtpLoginInfor.getmFTPHost(), mFtpLoginInfor.getmFTPPort());
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FTPIllegalReplyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FTPException e) {
			// TODO Auto-generated catch block
			Log.v(TAG, e.getCode()+"/"+e.getMessage());
			e.printStackTrace();
		}
		return tempStr;
	}
	
	public void login(){
		try {
			login(mFtpLoginInfor.getmFTPUser(), mFtpLoginInfor.getmFTPPassword());
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FTPIllegalReplyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FTPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//end new create methods
	@Override
	public void abortCurrentConnectionAttempt() {
		// TODO Auto-generated method stub
		super.abortCurrentConnectionAttempt();
	}

	@Override
	public void abortCurrentDataTransfer(boolean arg0) throws IOException,
			FTPIllegalReplyException {
		// TODO Auto-generated method stub
		super.abortCurrentDataTransfer(arg0);
	}

	@Override
	public void abruptlyCloseCommunication() {
		// TODO Auto-generated method stub
		super.abruptlyCloseCommunication();
	}

	@Override
	public void addCommunicationListener(FTPCommunicationListener listener) {
		// TODO Auto-generated method stub
		super.addCommunicationListener(listener);
	}

	@Override
	public void addListParser(FTPListParser listParser) {
		// TODO Auto-generated method stub
		super.addListParser(listParser);
	}

	@Override
	public void append(File arg0, FTPDataTransferListener arg1)
			throws IllegalStateException, FileNotFoundException, IOException,
			FTPIllegalReplyException, FTPException, FTPDataTransferException,
			FTPAbortedException {
		// TODO Auto-generated method stub
		super.append(arg0, arg1);
	}

	@Override
	public void append(File file) throws IllegalStateException,
			FileNotFoundException, IOException, FTPIllegalReplyException,
			FTPException, FTPDataTransferException, FTPAbortedException {
		// TODO Auto-generated method stub
		super.append(file);
	}

	@Override
	public void append(String arg0, InputStream arg1, long arg2,
			FTPDataTransferListener arg3) throws IllegalStateException,
			IOException, FTPIllegalReplyException, FTPException,
			FTPDataTransferException, FTPAbortedException {
		// TODO Auto-generated method stub
		super.append(arg0, arg1, arg2, arg3);
	}

	@Override
	public void changeAccount(String arg0) throws IllegalStateException,
			IOException, FTPIllegalReplyException, FTPException {
		// TODO Auto-generated method stub
		super.changeAccount(arg0);
	}

	@Override
	public void changeDirectory(String arg0) throws IllegalStateException,
			IOException, FTPIllegalReplyException, FTPException {
		// TODO Auto-generated method stub
		super.changeDirectory(arg0);
	}

	@Override
	public void changeDirectoryUp() throws IllegalStateException, IOException,
			FTPIllegalReplyException, FTPException {
		// TODO Auto-generated method stub
		super.changeDirectoryUp();
	}

	@Override
	public String[] connect(String arg0, int arg1)
			throws IllegalStateException, IOException,
			FTPIllegalReplyException, FTPException {
		// TODO Auto-generated method stub
		return super.connect(arg0, arg1);
	}

	@Override
	public String[] connect(String arg0) throws IllegalStateException,
			IOException, FTPIllegalReplyException, FTPException {
		// TODO Auto-generated method stub
		return super.connect(arg0);
	}

	@Override
	public void createDirectory(String arg0) throws IllegalStateException,
			IOException, FTPIllegalReplyException, FTPException {
		// TODO Auto-generated method stub
		super.createDirectory(arg0);
	}

	@Override
	public String currentDirectory() throws IllegalStateException, IOException,
			FTPIllegalReplyException, FTPException {
		// TODO Auto-generated method stub
		return super.currentDirectory();
	}

	@Override
	public void deleteDirectory(String arg0) throws IllegalStateException,
			IOException, FTPIllegalReplyException, FTPException {
		// TODO Auto-generated method stub
		super.deleteDirectory(arg0);
	}

	@Override
	public void deleteFile(String arg0) throws IllegalStateException,
			IOException, FTPIllegalReplyException, FTPException {
		// TODO Auto-generated method stub
		super.deleteFile(arg0);
	}

	@Override
	public void disconnect(boolean arg0) throws IllegalStateException,
			IOException, FTPIllegalReplyException, FTPException {
		// TODO Auto-generated method stub
		super.disconnect(arg0);
	}

	@Override
	public void download(String remoteFileName, File localFile,
			FTPDataTransferListener listener) throws IllegalStateException,
			FileNotFoundException, IOException, FTPIllegalReplyException,
			FTPException, FTPDataTransferException, FTPAbortedException {
		// TODO Auto-generated method stub
		super.download(remoteFileName, localFile, listener);
	}

	@Override
	public void download(String arg0, File arg1, long arg2,
			FTPDataTransferListener arg3) throws IllegalStateException,
			FileNotFoundException, IOException, FTPIllegalReplyException,
			FTPException, FTPDataTransferException, FTPAbortedException {
		// TODO Auto-generated method stub
		super.download(arg0, arg1, arg2, arg3);
	}

	@Override
	public void download(String remoteFileName, File localFile, long restartAt)
			throws IllegalStateException, FileNotFoundException, IOException,
			FTPIllegalReplyException, FTPException, FTPDataTransferException,
			FTPAbortedException {
		// TODO Auto-generated method stub
		super.download(remoteFileName, localFile, restartAt);
	}

	@Override
	public void download(String remoteFileName, File localFile)
			throws IllegalStateException, FileNotFoundException, IOException,
			FTPIllegalReplyException, FTPException, FTPDataTransferException,
			FTPAbortedException {
		// TODO Auto-generated method stub
		super.download(remoteFileName, localFile);
	}

	@Override
	public void download(String arg0, OutputStream arg1, long arg2,
			FTPDataTransferListener arg3) throws IllegalStateException,
			IOException, FTPIllegalReplyException, FTPException,
			FTPDataTransferException, FTPAbortedException {
		// TODO Auto-generated method stub
		super.download(arg0, arg1, arg2, arg3);
	}

	@Override
	public long fileSize(String arg0) throws IllegalStateException,
			IOException, FTPIllegalReplyException, FTPException {
		// TODO Auto-generated method stub
		return super.fileSize(arg0);
	}

	@Override
	public long getAutoNoopTimeout() {
		// TODO Auto-generated method stub
		return super.getAutoNoopTimeout();
	}

	@Override
	public String getCharset() {
		// TODO Auto-generated method stub
		return super.getCharset();
	}

	@Override
	public FTPCommunicationListener[] getCommunicationListeners() {
		// TODO Auto-generated method stub
		return super.getCommunicationListeners();
	}

	@Override
	public FTPConnector getConnector() {
		// TODO Auto-generated method stub
		return super.getConnector();
	}

	@Override
	public String getHost() {
		// TODO Auto-generated method stub
		return super.getHost();
	}

	@Override
	public FTPListParser[] getListParsers() {
		// TODO Auto-generated method stub
		return super.getListParsers();
	}

	@Override
	public int getMLSDPolicy() {
		// TODO Auto-generated method stub
		return super.getMLSDPolicy();
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return super.getPassword();
	}

	@Override
	public int getPort() {
		// TODO Auto-generated method stub
		return super.getPort();
	}

	@Override
	public SSLSocketFactory getSSLSocketFactory() {
		// TODO Auto-generated method stub
		return super.getSSLSocketFactory();
	}

	@Override
	public int getSecurity() {
		// TODO Auto-generated method stub
		return super.getSecurity();
	}

	@Override
	public FTPTextualExtensionRecognizer getTextualExtensionRecognizer() {
		// TODO Auto-generated method stub
		return super.getTextualExtensionRecognizer();
	}

	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return super.getType();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return super.getUsername();
	}

	@Override
	public String[] help() throws IllegalStateException, IOException,
			FTPIllegalReplyException, FTPException {
		// TODO Auto-generated method stub
		return super.help();
	}

	@Override
	public boolean isAuthenticated() {
		// TODO Auto-generated method stub
		return super.isAuthenticated();
	}

	@Override
	public boolean isCompressionEnabled() {
		// TODO Auto-generated method stub
		return super.isCompressionEnabled();
	}

	@Override
	public boolean isCompressionSupported() {
		// TODO Auto-generated method stub
		return super.isCompressionSupported();
	}

	@Override
	public boolean isConnected() {
		// TODO Auto-generated method stub
		return super.isConnected();
	}

	@Override
	public boolean isPassive() {
		// TODO Auto-generated method stub
		return super.isPassive();
	}

	@Override
	public boolean isResumeSupported() {
		// TODO Auto-generated method stub
		return super.isResumeSupported();
	}

	@Override
	public FTPFile[] list() throws IllegalStateException, IOException,
			FTPIllegalReplyException, FTPException, FTPDataTransferException,
			FTPAbortedException, FTPListParseException {
		// TODO Auto-generated method stub
		return super.list();
	}

	@Override
	public FTPFile[] list(String arg0) throws IllegalStateException,
			IOException, FTPIllegalReplyException, FTPException,
			FTPDataTransferException, FTPAbortedException,
			FTPListParseException {
		// TODO Auto-generated method stub
		return super.list(arg0);
	}

	@Override
	public String[] listNames() throws IllegalStateException, IOException,
			FTPIllegalReplyException, FTPException, FTPDataTransferException,
			FTPAbortedException, FTPListParseException {
		// TODO Auto-generated method stub
		return super.listNames();
	}

	@Override
	public void login(String arg0, String arg1, String arg2)
			throws IllegalStateException, IOException,
			FTPIllegalReplyException, FTPException {
		// TODO Auto-generated method stub
		super.login(arg0, arg1, arg2);
	}

	@Override
	public void login(String username, String password)
			throws IllegalStateException, IOException,
			FTPIllegalReplyException, FTPException {
		// TODO Auto-generated method stub
		super.login(username, password);
	}

	@Override
	public void logout() throws IllegalStateException, IOException,
			FTPIllegalReplyException, FTPException {
		// TODO Auto-generated method stub
		super.logout();
	}

	@Override
	public Date modifiedDate(String arg0) throws IllegalStateException,
			IOException, FTPIllegalReplyException, FTPException {
		// TODO Auto-generated method stub
		return super.modifiedDate(arg0);
	}

	@Override
	public void noop() throws IllegalStateException, IOException,
			FTPIllegalReplyException, FTPException {
		// TODO Auto-generated method stub
		super.noop();
	}

	@Override
	public void removeCommunicationListener(FTPCommunicationListener listener) {
		// TODO Auto-generated method stub
		super.removeCommunicationListener(listener);
	}

	@Override
	public void removeListParser(FTPListParser listParser) {
		// TODO Auto-generated method stub
		super.removeListParser(listParser);
	}

	@Override
	public void rename(String arg0, String arg1) throws IllegalStateException,
			IOException, FTPIllegalReplyException, FTPException {
		// TODO Auto-generated method stub
		super.rename(arg0, arg1);
	}

	@Override
	public FTPReply sendCustomCommand(String command)
			throws IllegalStateException, IOException, FTPIllegalReplyException {
		// TODO Auto-generated method stub
		return super.sendCustomCommand(command);
	}

	@Override
	public FTPReply sendSiteCommand(String command)
			throws IllegalStateException, IOException, FTPIllegalReplyException {
		// TODO Auto-generated method stub
		return super.sendSiteCommand(command);
	}

	@Override
	public String[] serverStatus() throws IllegalStateException, IOException,
			FTPIllegalReplyException, FTPException {
		// TODO Auto-generated method stub
		return super.serverStatus();
	}

	@Override
	public void setAutoNoopTimeout(long arg0) {
		// TODO Auto-generated method stub
		super.setAutoNoopTimeout(arg0);
	}

	@Override
	public void setCharset(String arg0) {
		// TODO Auto-generated method stub
		super.setCharset(arg0);
	}

	@Override
	public void setCompressionEnabled(boolean compressionEnabled) {
		// TODO Auto-generated method stub
		super.setCompressionEnabled(compressionEnabled);
	}

	@Override
	public void setConnector(FTPConnector connector) {
		// TODO Auto-generated method stub
		super.setConnector(connector);
	}

	@Override
	public void setMLSDPolicy(int mlsdPolicy) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		super.setMLSDPolicy(mlsdPolicy);
	}

	@Override
	public void setPassive(boolean passive) {
		// TODO Auto-generated method stub
		super.setPassive(passive);
	}

	@Override
	public void setSSLSocketFactory(SSLSocketFactory sslSocketFactory) {
		// TODO Auto-generated method stub
		super.setSSLSocketFactory(sslSocketFactory);
	}

	@Override
	public void setSecurity(int security) throws IllegalStateException,
			IllegalArgumentException {
		// TODO Auto-generated method stub
		super.setSecurity(security);
	}

	@Override
	public void setTextualExtensionRecognizer(
			FTPTextualExtensionRecognizer textualExtensionRecognizer) {
		// TODO Auto-generated method stub
		super.setTextualExtensionRecognizer(textualExtensionRecognizer);
	}

	@Override
	public void setType(int type) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		super.setType(type);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	@Override
	public void upload(File file, FTPDataTransferListener listener)
			throws IllegalStateException, FileNotFoundException, IOException,
			FTPIllegalReplyException, FTPException, FTPDataTransferException,
			FTPAbortedException {
		// TODO Auto-generated method stub
		super.upload(file, listener);
	}

	@Override
	public void upload(File arg0, long arg1, FTPDataTransferListener arg2)
			throws IllegalStateException, FileNotFoundException, IOException,
			FTPIllegalReplyException, FTPException, FTPDataTransferException,
			FTPAbortedException {
		// TODO Auto-generated method stub
		super.upload(arg0, arg1, arg2);
	}

	@Override
	public void upload(File file, long restartAt) throws IllegalStateException,
			FileNotFoundException, IOException, FTPIllegalReplyException,
			FTPException, FTPDataTransferException, FTPAbortedException {
		// TODO Auto-generated method stub
		super.upload(file, restartAt);
	}

	@Override
	public void upload(File file) throws IllegalStateException,
			FileNotFoundException, IOException, FTPIllegalReplyException,
			FTPException, FTPDataTransferException, FTPAbortedException {
		// TODO Auto-generated method stub
		super.upload(file);
	}

	@Override
	public void upload(String arg0, InputStream arg1, long arg2, long arg3,
			FTPDataTransferListener arg4) throws IllegalStateException,
			IOException, FTPIllegalReplyException, FTPException,
			FTPDataTransferException, FTPAbortedException {
		// TODO Auto-generated method stub
		super.upload(arg0, arg1, arg2, arg3, arg4);
	}			
	
	
	
	
}
