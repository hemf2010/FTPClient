package com.example.ftp4j_demo2;

import it.sauronsoftware.ftp4j.FTPDataTransferListener;
import it.sauronsoftware.ftp4j.FTPFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.ftp4j_demo2.ext.FileInfo;


public class FtpMainActivity extends Activity implements OnClickListener {

	private static String TAG = FtpMainActivity.class.getName();

	private CmdFactory mCmdFactory;
	private ExecutorServiceProxy mExecutorServiceProxy;
	private FtpClientProxy mFTPClient;
	private FtpLoginInfor mFtpLoginInfor;
	private DameonFtpConnector mDameonFtpConnector;
	private FtpLogicManager mFtpLogicManager;

	private ProgressBar mPbLoad = null;

	private ListView mListViewRemote;
	private ListView mListViewLocal;
	private FtpFileAdapter mAdapter;
	private int mSelectedPosistion = -1;
	private GestureDetector mGestureDetector;

	// Upload
	private ListView mGridView;
	private View fileChooserView;
	private TextView mTvPath;
	private String mSdcardRootPath;
	private String mLastFilePath;
	private List<FileInfo> mUploadFileList;
	private UploadFileChooserAdapter mUploadAdapter;
	private LinearLayout currDirView;
	private TextView serverRoot;
	private Button localRoot;
	private Button remoteRoot;
	//

	private Dialog progressDialog;
	private Dialog uploadDialog;

	private Thread mDameonThread = null ;
	private List<FTPFile> mFileList;

	private static final int MENU_OPTIONS_BASE = 0;

	private static final int MENU_OPTIONS_DOWNLOAD = MENU_OPTIONS_BASE + 20;
	private static final int MENU_OPTIONS_RENAME = MENU_OPTIONS_BASE + 21;
	private static final int MENU_OPTIONS_DELETE = MENU_OPTIONS_BASE + 22;
	
	private static final int MENU_OPTIONS_LOCAL_UPLOAD = MENU_OPTIONS_BASE + 23;
	private static final int MENU_OPTIONS_LOCAL_RENAME = MENU_OPTIONS_BASE + 24;
	private static final int MENU_OPTIONS_LOCAL_DELETE = MENU_OPTIONS_BASE + 25;
	
	private static final int MENU_DEFAULT_GROUP = 0;

	private static final int DIALOG_LOAD = MENU_OPTIONS_BASE + 40;
	private static final int DIALOG_RENAME = MENU_OPTIONS_BASE + 41;
	private static final int DIALOG_FTP_LOGIN = MENU_OPTIONS_BASE + 42;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		registerForContextMenu(mListViewRemote);
		registerForContextMenu(mListViewLocal);
		
		init();

		Log.v(TAG, "mFTPHost #" + mFtpLoginInfor.getmFTPHost() + " mFTPPort #" + mFtpLoginInfor.getmFTPPort() 
				+ " mFTPUser #" + mFtpLoginInfor.getmFTPUser() + " mFTPPassword #" + mFtpLoginInfor.getmFTPPassword());
		if(mFtpLogicManager.isExpirated()) {
			showDialog(DIALOG_FTP_LOGIN);
		}else{
			mExecutorServiceProxy.executeConnectRequest();
		}
	}

	private void init(){
		// init code
		mSdcardRootPath = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		// mFtpLoginInfor = new FtpLoginInfor();
		mFtpLogicManager = new FtpLogicManager(getApplicationContext());
		mFtpLoginInfor = mFtpLogicManager.getmFtpLoginInfor();
		mCmdFactory = CmdFactory.getInstance();
		mCmdFactory.setmFtpLoginInfor(mFtpLoginInfor);
		mFTPClient = new FtpClientProxy();
		mFTPClient.setmFtpLoginInfor(mFtpLoginInfor);
		mCmdFactory.setmFTPClient(mFTPClient);
		mCmdFactory.setmHandler(mHandler);

		mDameonFtpConnector = new DameonFtpConnector(mFTPClient);
		mCmdFactory.setmDameonFtpConnector(mDameonFtpConnector);
		mExecutorServiceProxy = ExecutorServiceProxy.getInstance();
		mExecutorServiceProxy.setmCmdFactory(mCmdFactory);
	}
	
	

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		
		mExecutorServiceProxy.executeCDUPRequest();
	}





	private void initView() {
		
		mListViewRemote = (ListView) findViewById(R.id.listviewRemoteFile);
		mListViewLocal = (ListView) findViewById(R.id.listviewLocalFile);
		currDirView = (LinearLayout) findViewById(R.id.currDir);
		serverRoot = (TextView) findViewById(R.id.serverRoot);
		serverRoot.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mExecutorServiceProxy.executeCWDRequest("/");
			}
		});
		mListViewRemote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapterView, View view,
					int positioin, long id) {
				if (mFileList.get(positioin).getType() == FTPFile.TYPE_DIRECTORY) {
					mExecutorServiceProxy.executeCWDRequest(mFileList.get(positioin).getName());
				}
			}
		});

		mListViewRemote.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

					public boolean onItemLongClick(AdapterView<?> adapterView,
							View view, int positioin, long id) {
						mSelectedPosistion = positioin;
						return false;
					}
				});

		mListViewRemote.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {

					public void onCreateContextMenu(ContextMenu menu, View v,
							ContextMenuInfo menuInfo) {
						Log.v(TAG, "onCreateContextMenu ");
					}

				});
		
		mListViewLocal.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				FileInfo fileInfo = (FileInfo) (((UploadFileChooserAdapter) adapterView
						.getAdapter()).getItem(position));
				if (fileInfo.isDirectory()) {
					updateFileItems(fileInfo.getFileAbsolutePath());
				} 
				
			}
			
		});

		mListViewLocal.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

					public boolean onItemLongClick(AdapterView<?> adapterView,
							View view, int positioin, long id) {
						mSelectedPosistion = positioin;
						return false;
					}
				});

		mListViewLocal.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {

					public void onCreateContextMenu(ContextMenu menu, View v,
							ContextMenuInfo menuInfo) {
						Log.v(TAG, "onCreateContextMenu ");
					}

				});
		localRoot = (Button) findViewById(R.id.local_root);
		
		localRoot.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				updateFileItems(mSdcardRootPath);
				mUploadAdapter = new UploadFileChooserAdapter(getApplicationContext(), mUploadFileList);
				mListViewLocal.setAdapter(mUploadAdapter);
			}
		});
		
		
		
		remoteRoot = (Button) findViewById(R.id.remote_root);
		remoteRoot.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {

			}
		});
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		if (v.getId() == R.id.listviewRemoteFile) {
			menu.setHeaderTitle("文件操作");
			menu.add(MENU_DEFAULT_GROUP, MENU_OPTIONS_DOWNLOAD, Menu.NONE, "下载");
			menu.add(MENU_DEFAULT_GROUP, MENU_OPTIONS_RENAME, Menu.NONE, "重命名");
			menu.add(MENU_DEFAULT_GROUP, MENU_OPTIONS_DELETE, Menu.NONE, "删除");
		}
		if (v.getId() == R.id.listviewLocalFile) {
			menu.setHeaderTitle("文件操作");
			menu.add(MENU_DEFAULT_GROUP, MENU_OPTIONS_LOCAL_UPLOAD, Menu.NONE, "上传");
			menu.add(MENU_DEFAULT_GROUP, MENU_OPTIONS_LOCAL_RENAME, Menu.NONE, "重命名");
			menu.add(MENU_DEFAULT_GROUP, MENU_OPTIONS_LOCAL_DELETE, Menu.NONE, "删除");
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (mSelectedPosistion < 0 || mFileList.size() < 0) {
			return false;
		}
		AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item
				.getMenuInfo();
		switch (item.getItemId()) {
		case MENU_OPTIONS_DOWNLOAD:
			if (mFileList.get(mSelectedPosistion).getType() == FTPFile.TYPE_FILE) {
				showDialog(DIALOG_LOAD);
				new CmdDownLoad(mFTPClient, mProcessBarHandler, mFileList.get(mSelectedPosistion)).execute();
			} else {
				toast("只能上传文件");
			}
			break;
		case MENU_OPTIONS_RENAME:
			showDialog(DIALOG_RENAME);
			break;
		case MENU_OPTIONS_DELETE:
			mExecutorServiceProxy.executeDELERequest(
					mFileList.get(mSelectedPosistion).getName(),
					mFileList.get(mSelectedPosistion).getType() == FTPFile.TYPE_DIRECTORY);

			break;
		default:
			return super.onContextItemSelected(item);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.menu_reflush:
			return true;
		case R.id.menu_change_account:
			showDialog(DIALOG_FTP_LOGIN);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_LOAD:
			return createLoadDialog();
		case DIALOG_RENAME:
			return createRenameDialog();
		case DIALOG_FTP_LOGIN :
			return createFTPLoginDialog();
		default:
			return null;
		}
	}

	private Dialog createFTPLoginDialog() {

		View rootLoadView = getLayoutInflater().inflate(R.layout.dialog_ftp_login,
				null);
		final EditText editHost = (EditText) rootLoadView.findViewById(R.id.editFTPHost);
		final EditText editPort= (EditText) rootLoadView.findViewById(R.id.editFTPPort);
		editPort.setText("2121");
		final EditText editUser = (EditText) rootLoadView.findViewById(R.id.editFTPUser);
		editUser.setText(R.string.username);
		editUser.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(hasFocus){
					if(((EditText)v).getText().equals("anonymous")){
						((EditText)v).setText("");
					}
				}
			}
		});
		final EditText editPasword= (EditText) rootLoadView.findViewById(R.id.editPassword);
		return new AlertDialog.Builder(this)
				.setTitle("请输入FTP信息")
				.setView(rootLoadView)
				.setPositiveButton("连接FTP", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface uploadDialog, int which) {
						int tempFtpPort;
						if (TextUtils.isEmpty(editHost.getText()) || 
								TextUtils.isEmpty(editPort.getText()) || 
								TextUtils.isEmpty(editUser.getText()) ||
								TextUtils.isEmpty(editUser.getText())) {
							  toast("请将资料填写完整");
							  FtpMainActivity.this.finish();
							  return ;
						}
						try{
							tempFtpPort = Integer.parseInt(editPort.getText().toString().trim());
						}
						catch(NumberFormatException nfEx){
							nfEx.printStackTrace();
							toast("端口输入有误，请重试");
							return ;
						}
						mFtpLoginInfor.setmFTPHost(editHost.getText().toString().trim()); 
						mFtpLoginInfor.setmFTPPort(tempFtpPort);
						mFtpLoginInfor.setmFTPUser(editUser.getText().toString().trim());
						mFtpLoginInfor.setmFTPPassword(editPasword.getText().toString().trim());
						mFtpLoginInfor.setExpirated(System.currentTimeMillis());
						Log.v(TAG, "mFTPHost #" + mFtpLoginInfor.getmFTPHost() + " mFTPPort #" + mFtpLoginInfor.getmFTPPort() 
								+ " mFTPUser #" + mFtpLoginInfor.getmFTPUser() + " mFTPPassword #" + mFtpLoginInfor.getmFTPPassword()
								+"Expirated" + mFtpLoginInfor.getExpirated());
						mExecutorServiceProxy.executeConnectRequest();
					}
				}).create();
	}
	
	private Dialog createLoadDialog() {

		View rootLoadView = getLayoutInflater().inflate(
				R.layout.dialog_load_file, null);
		mPbLoad = (ProgressBar) rootLoadView.findViewById(R.id.pbLoadFile);

		progressDialog = new AlertDialog.Builder(this).setTitle("请稍等片刻...")
				.setView(rootLoadView).setCancelable(false).create();

		progressDialog
				.setOnDismissListener(new DialogInterface.OnDismissListener() {

					public void onDismiss(DialogInterface dialog) {
						// TODO Auto-generated method stub
						setLoadProgress(0);
					}
				});

		return progressDialog;
	}

	private Dialog createRenameDialog() {

		View rootLoadView = getLayoutInflater().inflate(R.layout.dialog_rename,
				null);
		final EditText edit = (EditText) rootLoadView
				.findViewById(R.id.editNewPath);

		return new AlertDialog.Builder(this)
				.setTitle("重命名...")
				.setView(rootLoadView)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface uploadDialog, int which) {
						// TODO Auto-generated method stub
						if (!TextUtils.isEmpty(edit.getText())) {
							mExecutorServiceProxy.executeREANMERequest(edit.getText().toString(), mSelectedPosistion);
						}
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface uploadDialog, int which) {
						// TODO Auto-generated method stub

					}
				}).create();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onDestroy() {
		mDameonFtpConnector.setmDameonRunning(false);
		Thread thread = new Thread(mCmdFactory.createCmdDisConnect()) ;
		thread.start();
		//等待连接中断
		try {
			thread.join(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mExecutorServiceProxy.shutdownNow();
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			logv("mHandler --->" + msg.what);
			switch (msg.what) {
			case GlobalConstant.MSG_CMD_CONNECT_OK:
				toast("FTP服务器连接成功");
				/*if(mDameonThread == null){
					//启动守护进程。
					mDameonThread = new Thread(mDameonFtpConnector);
					mDameonThread.setDaemon(true);
					mDameonThread.start();
				}*/
				mFtpLogicManager.saveLoginInfor();
				mExecutorServiceProxy.executeLISTRequest();
				break;
			case GlobalConstant.MSG_CMD_REINPUT:
				showDialog(DIALOG_FTP_LOGIN);
				break;
			case GlobalConstant.MSG_CMD_CONNECT_FAILED:
				toast("FTP服务器连接失败，正在重新连接");
				mExecutorServiceProxy.executeConnectRequest();
				break;
			case GlobalConstant.MSG_CMD_LIST_OK:
				toast("请求数据成功。");
				mFileList = mCmdFactory.getmFileList();
				buildOrUpdateDataset();
				break;
			case GlobalConstant.MSG_CMD_LIST_FAILED:
				toast("请求数据失败。");
				break;
			case GlobalConstant.MSG_CMD_CWD_OK:
				toast("请求数据成功。");
				mExecutorServiceProxy.executeLISTRequest();
				break;
			case GlobalConstant.MSG_CMD_CDUP_OK:
				toast("返回上级目录成功");
				mExecutorServiceProxy.executeLISTRequest();
				break;
			case GlobalConstant.MSG_CMD_CDUP_FAILED:
				toast("返回上级目录失败");
				mExecutorServiceProxy.executeLISTRequest();
				break;
			case GlobalConstant.MSG_CMD_CWD_FAILED:
				toast("请求数据失败。");
				break;
			case GlobalConstant.MSG_CMD_DELE_OK:
				toast("请求数据成功。");
				mExecutorServiceProxy.executeLISTRequest();
				break;
			case GlobalConstant.MSG_CMD_DELE_FAILED:
				toast("请求数据失败。");
				break;
			case GlobalConstant.MSG_CMD_RENAME_OK:
				toast("请求数据成功。");
				mExecutorServiceProxy.executeLISTRequest();
				break;
			case GlobalConstant.MSG_CMD_RENAME_FAILED:
				toast("请求数据失败。");
				break;
			default:
				break;
			}
		}
	};
	
	
	private Handler mProcessBarHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case GlobalConstant.MSG_PROGRESSBAR_DIALOG_DISMISS:
				progressDialog.dismiss();
				break;
			case GlobalConstant.MSG_PROGRESSBAR_DOWNLOAD_SET_MAX:
				setLoadProgress(mPbLoad.getMax());
				break;
			case GlobalConstant.MSG_PROGRESSBAR_DOWNLOAD_SET_VALSE:
				Bundle data = msg.getData();
				setLoadProgress((int) (data.getFloat("percent")*mPbLoad.getMax()));
				break;
				
			}
		}
		
	};

	private void buildOrUpdateDataset() {
		if (mAdapter == null) {
			mAdapter = new FtpFileAdapter(this, mFileList);
			mListViewRemote.setAdapter(mAdapter);
		}
		mAdapter.notifyDataSetChanged();
		updateCurrDirControl();
	}


	public void updateCurrDirControl() {
		// TODO Auto-generated method stub
		String currDir = mCmdFactory.getmCurrentPWD();
		String[] eachDir = currDir.split("/");
		currDirView.removeAllViews();
		StringBuilder mStringBuilder = new StringBuilder();
		mStringBuilder.append("/");
		
		for (int i= 1; i < eachDir.length; i ++){
			View eachDirView = getLayoutInflater().inflate(R.layout.dir_show, null);
			TextView meachDirName =  (TextView) eachDirView.findViewById(R.id.dirName);
			mStringBuilder.append(eachDir[i]+ "/");
			meachDirName.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String CwdDir = (String) v.getTag();
					mExecutorServiceProxy.executeCWDRequest(CwdDir);
					logv("onClick TextView " + CwdDir);
					
				}
			});
			meachDirName.setTag(mStringBuilder.toString());
			meachDirName.setText(eachDir[i]);
			currDirView.addView(eachDirView);
		}
	}

	private void logv(String log) {
		Log.v(TAG, log);
	}

	private void toast(String hint) {
		//Toast.makeText(this, hint, Toast.LENGTH_SHORT).show();
	}
    
	/*//文件选择器相关功能实现
	private void openFileDialog() {
		initDialog();
		uploadDialog = new AlertDialog.Builder(this).create();
		Window window = uploadDialog.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		window.setAttributes(lp);
		uploadDialog.show();
		uploadDialog.getWindow().setContentView(fileChooserView,
				new RelativeLayout.LayoutParams(400, 640));
	}

	private void initDialog() {
		fileChooserView = getLayoutInflater().inflate(
				R.layout.filechooser_show, null);
		fileChooserView.findViewById(R.id.imgBackFolder).setOnClickListener(
				mClickListener);
		mTvPath = (TextView) fileChooserView.findViewById(R.id.tvPath);
		mGridView = (ListView) fileChooserView.findViewById(R.id.gvFileChooser);
		mGridView.setEmptyView(fileChooserView.findViewById(R.id.tvEmptyHint));
		mGridView.setOnItemClickListener(mItemClickListener);
		setGridViewAdapter(mSdcardRootPath);
	}*/

	/*private void setGridViewAdapter(String filePath) {
		updateFileItems(filePath);
		mUploadAdapter = new UploadFileChooserAdapter(this, mUploadFileList);
		mGridView.setAdapter(mUploadAdapter);
	}*/

	private void updateFileItems(String filePath) {
		mLastFilePath = filePath;
		//mTvPath.setText(mLastFilePath);

		if (mUploadFileList == null)
			mUploadFileList = new ArrayList<FileInfo>();
		if (!mUploadFileList.isEmpty())
			mUploadFileList.clear();

		File[] files = folderScan(filePath);

		for (int i = 0; i < files.length; i++) {
			if (files[i].isHidden()) // Ignore the hidden file
				continue;

			FileInfo fileInfo = new FileInfo(files[i]);

			mUploadFileList.add(fileInfo);
		}
		// When first enter , the object of mAdatper don't initialized
		if (mUploadAdapter != null)
			mUploadAdapter.notifyDataSetChanged();
	}

	private File[] folderScan(String path) {
		File file = new File(path);
		File[] files = file.listFiles();
		return files;
	}

	/*private AdapterView.OnItemClickListener mItemClickListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> adapterView, View view,
				int position, long id) {
			FileInfo fileInfo = (FileInfo) (((UploadFileChooserAdapter) adapterView
					.getAdapter()).getItem(position));
			if (fileInfo.isDirectory()) {
				updateFileItems(fileInfo.getFileAbsolutePath());
			} else {
				showDialog(DIALOG_LOAD);
				new CmdUpload(mFTPClient, mProcessBarHandler).execute(fileInfo.getFileAbsolutePath());
			}
		}
	};*/

	/*private View.OnClickListener mClickListener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.imgBackFolder:
				backProcess();
				break;
			}
		}
	};
*/
	public void backProcess() {
		if (!mLastFilePath.equals(mSdcardRootPath)) {
			File thisFile = new File(mLastFilePath);
			String parentFilePath = thisFile.getParent();
			updateFileItems(parentFilePath);
		} else {
			setResult(RESULT_CANCELED);
			uploadDialog.dismiss();
		}
	}

	public void setLoadProgress(int progress) {
		if (mPbLoad != null) {
			mPbLoad.setProgress(progress);
		}
	}

	/*@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		toast("...onFling...");   
	       if(e1.getX() > e2.getX()) {//move to left   
	           mViewFlipper.showNext();   
	       }else if(e1.getX() < e2.getX()) {   
	    	   mViewFlipper.setInAnimation(getApplicationContext(), R.anim.push_right_in);   
	           mViewFlipper.setOutAnimation(getApplicationContext(), R.anim.push_right_out);
	              
	           mViewFlipper.showPrevious();   
	           mViewFlipper.setInAnimation(getApplicationContext(), R.anim.push_left_in);   
	           mViewFlipper.setOutAnimation(getApplicationContext(), R.anim.push_left_out);
	              
	       }else {   
	           return false;   
	       }   
	       return true; 

	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return mGestureDetector.onTouchEvent(event);
	}
*/

}
