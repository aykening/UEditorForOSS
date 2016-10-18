package com.baidu.ueditor.upload;

import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.State;

import java.io.InputStream;

import cc.cocoso.uefoross.OSSManager;


public class StorageManager {
	//public static final int BUFFER_SIZE = 8192;
	public static OSSManager  ossManager;
	static {
		ossManager=new OSSManager();
	}
	public StorageManager() {

	}

	/**
	 * 存储上传的文件到真实路径
	 * @param data
	 * @param path
     * @return
     */
	public static State saveBinaryFile(byte[] data, String path) {

		State state=null;

		try {//如果需要上传代码到阿里云OOS，仅需要改此处的代码
			state=ossManager.uploadFile(data,path);
			return state;
		} catch (Exception ioe) {
			return new BaseState(false, AppInfo.IO_ERROR);
		}
	}

	public static State saveFileByInputStream(InputStream is, String path,
			long maxSize) {
		State state = null;

		try {
			state=ossManager.uploadFile(is,path);
			return state;
			
		} catch (Exception e) {
			return new BaseState(false, AppInfo.IO_ERROR);
		}
	}

	public static State saveFileByInputStream(InputStream is, String path) {
		State state=null;
			try{
				state=ossManager.uploadFile(is,path);
				return state;
			}catch (Exception e){
				return new BaseState(false, AppInfo.IO_ERROR);
			}

	}

	/*private static File getTmpFile() {
		File tmpDir = FileUtils.getTempDirectory();
		String tmpFileName = (Math.random() * 10000 + "").replace(".", "");
		return new File(tmpDir, tmpFileName);
	}

	private static State saveTmpFile(File tmpFile, String path) {
		State state = null;
		File targetFile = new File(path);

		if (targetFile.canWrite()) {
			return new BaseState(false, AppInfo.PERMISSION_DENIED);
		}
		try {
			FileUtils.moveFile(tmpFile, targetFile);
		} catch (IOException e) {
			return new BaseState(false, AppInfo.IO_ERROR);
		}

		state = new BaseState(true);
		state.putInfo( "size", targetFile.length() );
		state.putInfo( "title", targetFile.getName() );

		return state;
	}

	private static State valid(File file) {
		File parentPath = file.getParentFile();

		if ((!parentPath.exists()) && (!parentPath.mkdirs())) {
			return new BaseState(false, AppInfo.FAILED_CREATE_FILE);
		}

		if (!parentPath.canWrite()) {
			return new BaseState(false, AppInfo.PERMISSION_DENIED);
		}

		return new BaseState(true);
	}*/
}
