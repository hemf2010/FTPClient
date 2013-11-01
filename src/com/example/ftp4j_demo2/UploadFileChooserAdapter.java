package com.example.ftp4j_demo2;

import it.sauronsoftware.ftp4j.FTPFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.ftp4j_demo2.FtpFileAdapter.ViewHolder;
import com.example.ftp4j_demo2.ext.FileInfo;
import com.example.ftp4j_demo2.util.FileTypeToImageUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class UploadFileChooserAdapter extends BaseAdapter {

	private List<FileInfo> mFileLists;
	private LayoutInflater mLayoutInflater = null;
	private Context mContext = null ;

	public UploadFileChooserAdapter(Context context, List<FileInfo> fileLists) {
		super();
		mFileLists = fileLists;
		mLayoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mContext = context;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return mFileLists.size();
	}

	public FileInfo getItem(int position) {
		// TODO Auto-generated method stub
		return mFileLists.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		/*View view = null;
		ViewHolder holder = null;
		if (convertView == null || convertView.getTag() == null) {
			view = mLayoutInflater.inflate(R.layout.filechooser_gridview_item,
					null);
			holder = new ViewHolder(view);
			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder) convertView.getTag();
		}

		FileInfo fileInfo = getItem(position);
        //TODO 
		
		holder.tvFileName.setText(fileInfo.getFileName());
		
		if(fileInfo.isDirectory()){      //文件夹
			holder.tvFileName.setTextColor(Color.GRAY);
		}
		
		else {                           //未知文件
			holder.tvFileName.setTextColor(Color.GRAY);
		}
		return view;*/
		
		View view = null;
		ViewHolder holder = null;
		
		view = mLayoutInflater.inflate(R.layout.ftp_file_item, null);
		holder = new ViewHolder(view);
		view.setTag(holder);
		
		FileInfo mFileInfo =  getItem(position);
		
		holder.appIcon.setImageResource(FileTypeToImageUtil.changeFileTypeToDrawable(mFileInfo));
		holder.tvName.setText(mFileInfo.getFileName());
		holder.tvAuthor.setText(Formatter.formatFileSize(mContext, mFileInfo.getFileSize()));
		holder.tvDuration.setText(makeSimpleDateStringFromLong(mFileInfo.getNewModifiedTime()));
		return view;
	}

	static class ViewHolder 
	{
		ImageView appIcon;
		TextView tvName;
		TextView tvAuthor;
        TextView tvDuration ;
        
		public ViewHolder(View view)
		{
			this.appIcon = (ImageView) view.findViewById(R.id.imgMuisc);
			this.tvName = (TextView) view.findViewById(R.id.tvName);
			this.tvAuthor = (TextView) view.findViewById(R.id.tvFileSize);
			this.tvDuration = (TextView) view.findViewById(R.id.tvDuration);
		}
	}

	private static final String SIMPLE_FORMAT_SHOW_TIME = "yyyy-MM-dd hh:mm";

	private static SimpleDateFormat sSimpleDateFormat = new SimpleDateFormat(SIMPLE_FORMAT_SHOW_TIME);
	
	//时间格式转换
	public static CharSequence makeSimpleDateStringFromLong(long inTimeInMillis) {
		return sSimpleDateFormat.format(new Date(inTimeInMillis));
	}
	
	}

	
	//enum FileType {
	//	FILE , DIRECTORY;
	////}

	/*// =========================
	// Model
	// =========================
	static class FileInfo {
		private FileType fileType;
		private String fileName;
		private String filePath;

		public FileInfo(String filePath, String fileName, boolean isDirectory) {
			this.filePath = filePath;
			this.fileName = fileName;
			fileType = isDirectory ? FileType.DIRECTORY : FileType.FILE;
		}
  
		public boolean isDirectory(){
			if(fileType == FileType.DIRECTORY)
				return true ;
			else
				return false ;
		}
		
		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		public String getFilePath() {
			return filePath;
		}

		public void setFilePath(String filePath) {
			this.filePath = filePath;
		}

		@Override
		public String toString() {
			return "FileInfo [fileType=" + fileType + ", fileName=" + fileName
					+ ", filePath=" + filePath + "]";
		}
	}
*/
//}
