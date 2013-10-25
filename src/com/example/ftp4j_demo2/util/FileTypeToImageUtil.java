package com.example.ftp4j_demo2.util;

import it.sauronsoftware.ftp4j.FTPFile;

import java.util.HashMap;


import com.example.ftp4j_demo2.R;
import com.example.ftp4j_demo2.ext.FileInfo;
import com.example.ftp4j_demo2.ext.MediaFile;
import com.example.ftp4j_demo2.ext.MediaFile.MediaFileType;

public class FileTypeToImageUtil {
		
		private HashMap<Integer, Integer> mFileTypeToDrawable;
					
		public static int addFileTypeToDrawable(int filetype){
			switch(filetype) {
			case MediaFile.FILE_TYPE_TEXT:
				return R.drawable.text;
			default:
				return R.drawable.question;
			}
		}
		
		public static int changeFileTypeToDrawable(){
			return R.drawable.question;
		}
		
		public static int changeFileTypeToDrawable(String path){
			MediaFileType mMediaFileType = MediaFile.getFileType(path);
			if (mMediaFileType == null) {
				return R.drawable.question;
			}else {
				return addFileTypeToDrawable(mMediaFileType.fileType);
			}
			
		}
		
		public static int changeFileTypeToDrawable(FTPFile mFile){
			if (mFile.getType() == FTPFile.TYPE_DIRECTORY) {
				return R.drawable.dir;
			}else{
				return changeFileTypeToDrawable(mFile.getName());
			}
		}
		
		public static int changeFileTypeToDrawable(FileInfo mFile){
			if (mFile.isDirectory()) {
				return R.drawable.dir;
			}else{
				return changeFileTypeToDrawable(mFile.getFileName());
			}
		}
}
