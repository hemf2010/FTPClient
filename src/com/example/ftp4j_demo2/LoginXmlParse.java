package com.example.ftp4j_demo2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;

public class LoginXmlParse {

	public Context mContext;
	public XmlPullParser xpp;
	public static String FILE_NAME = "users.xml";
	public static String TAG = "LoginXmlParse";
	public static String FTP_SERVICE_TAG = "FtpService";
	public static String FTP_ADDRESS = "Address";
	public static String FTP_PORT = "Port";
	public static String FTP_USERNAME = "Username";
	public static String FTP_PASSWORD = "Password";
	public static String FTP_EXPIRATED = "Time";
	
	public LoginXmlParse(Context mContext) {
		super();
		this.mContext = mContext;
		Log.v(TAG, FILE_NAME);
		// TODO Auto-generated constructor stub		
	}
	
	
	public boolean LoginFileExist(){
		try {
			InputStream inputStream = mContext.openFileInput(FILE_NAME);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			return false;
		}
		return true;
	}
	
	public FtpLoginInfor ParseLoginInfor(){
		
		FtpLoginInfor tempFtpLoginInfor = new FtpLoginInfor();
		
		try {
			InputStream inputStream = mContext.openFileInput(FILE_NAME);
			XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
			xpp = xmlPullParserFactory.newPullParser();
			xpp.setInput(inputStream, "utf-8");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (XmlPullParserException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
				if(xpp.getEventType() == XmlPullParser.START_TAG){
					Log.v(TAG, xpp.getName());
					if(xpp.getName().equals(FTP_ADDRESS)){
						 getFtpAddress(xpp, tempFtpLoginInfor);
					}else if(xpp.getName().equals(FTP_PORT)){
						getFtpPort(xpp, tempFtpLoginInfor);
					}else if(xpp.getName().equals(FTP_USERNAME)){
						getFtpUserName(xpp, tempFtpLoginInfor);
					}else if(xpp.getName().equals(FTP_PASSWORD)){
						getFtpPassword(xpp, tempFtpLoginInfor);
					}else if(xpp.getName().equals(FTP_EXPIRATED)){
						getFtpExpirated(xpp,tempFtpLoginInfor);
					}
				}
				xpp.next();
				
			}
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return tempFtpLoginInfor;	
	}
	
	
	public boolean SaveLoginInfor(FtpLoginInfor mFtpLoginInfor){
		
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer=new StringWriter();
		try {
			serializer.setOutput(writer);
			serializer.startDocument("utf-8", true);
			
			serializer.startTag("", FTP_SERVICE_TAG);
			
			serializer.startTag("", FTP_ADDRESS);
			serializer.text(mFtpLoginInfor.getmFTPHost());
			serializer.endTag("", FTP_ADDRESS);
			
			serializer.startTag("", FTP_PORT);
			serializer.text(String.valueOf(mFtpLoginInfor.getmFTPPort()));
			serializer.endTag("", FTP_PORT);
			
			serializer.startTag("", FTP_USERNAME);
			serializer.text(mFtpLoginInfor.getmFTPUser());
			serializer.endTag("", FTP_USERNAME);
			
			serializer.startTag("", FTP_PASSWORD);
			serializer.text(mFtpLoginInfor.getmFTPPassword());
			serializer.endTag("", FTP_PASSWORD);
			
			serializer.startTag("", FTP_EXPIRATED);
			serializer.text(String.valueOf(mFtpLoginInfor.getExpirated()));
			serializer.endTag("", FTP_EXPIRATED);
			
			serializer.endTag("", FTP_SERVICE_TAG);
			serializer.endDocument();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return writeToXml(mContext, writer.toString());
	}
	
	public boolean writeToXml(Context context,String str){
        try {
            OutputStream out=context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            OutputStreamWriter outw=new OutputStreamWriter(out);
            try {
                outw.write(str);
                outw.close();
                out.close();
                return true;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                return false;
            	//e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            return false;
        	//e.printStackTrace();
        }
    }
	
	public void getFtpExpirated(XmlPullParser xpp,
			FtpLoginInfor tempFtpLoginInfor) {
		// TODO Auto-generated method stub
		try {
			if(xpp.getEventType() == XmlPullParser.START_TAG 
					&& xpp.getName().equals(FTP_EXPIRATED)){
				while(true){
					xpp.next();
					if(xpp.getEventType() == XmlPullParser.TEXT){
						tempFtpLoginInfor.setExpirated(Long.valueOf(xpp.getText().trim()));
						Log.v(TAG, xpp.getText());
						break;
					}
				}
			}
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getFtpPassword(XmlPullParser xpp,
			FtpLoginInfor tempFtpLoginInfor) {
		// TODO Auto-generated method stub
		try {
			if(xpp.getEventType() == XmlPullParser.START_TAG 
					&& xpp.getName().equals(FTP_PASSWORD)){
				while(true){
					xpp.next();
					if(xpp.getEventType() == XmlPullParser.TEXT){
						tempFtpLoginInfor.setmFTPPassword(xpp.getText().trim());
						Log.v(TAG, xpp.getText());
						break;
					}
				}
			}
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getFtpUserName(XmlPullParser xpp,
			FtpLoginInfor tempFtpLoginInfor) {
		// TODO Auto-generated method stub
		try {
			if(xpp.getEventType() == XmlPullParser.START_TAG 
					&& xpp.getName().equals(FTP_USERNAME)){
				while(true){
					xpp.next();
					if(xpp.getEventType() == XmlPullParser.TEXT){
						tempFtpLoginInfor.setmFTPUser(xpp.getText().trim());
						Log.v(TAG, xpp.getText());
						break;
					}
				}
			}
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getFtpPort(XmlPullParser xpp, 
			FtpLoginInfor tempFtpLoginInfor) {
		// TODO Auto-generated method stub
		try {
			if(xpp.getEventType() == XmlPullParser.START_TAG 
					&& xpp.getName().equals(FTP_PORT)){
				while(true){
					xpp.next();
					if(xpp.getEventType() == XmlPullParser.TEXT){
						tempFtpLoginInfor.setmFTPPort(Integer.valueOf(xpp.getText().trim()));
						Log.v(TAG, xpp.getText());
						break;
					}
				}
			}
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getFtpAddress(XmlPullParser xpp,
			FtpLoginInfor tempFtpLoginInfor) {
		// TODO Auto-generated method stub
			try {
				if(xpp.getEventType() == XmlPullParser.START_TAG 
						&& xpp.getName().equals(FTP_ADDRESS)){
					while(true){
						xpp.next();
						if(xpp.getEventType() == XmlPullParser.TEXT){
							tempFtpLoginInfor.setmFTPHost(xpp.getText().trim());
							Log.v(TAG, xpp.getText());
							break;
						}
					}
				}
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
