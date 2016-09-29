package baseframe.resource;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Message;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import baseframe.base.Updater;
import baseframe.base.UpdaterListener;
import baseframe.config.AppConfig;
import baseframe.core.LoadFile;
import baseframe.tools.Util;

public class LoadManager {
	private static ArrayList<LoadFile> loadQueue = new ArrayList<LoadFile>();
	private static HashMap<String, Object> loadFileInfo = new HashMap<String, Object>();
	private static LoadFile target = null;
	private static boolean isloading = false;
	private static String curUrl = "";
	private static String curfileDirName = "";//
//	private static String curfileName = "";
//	private static String curfileType = "";
	private static Updater loadUpdater=null;



	public static void init(){
		loadUpdater=new Updater();
		loadUpdater.setUpdaterListener(new UpdaterListener() {
			@Override
			public void update() {
				LoadManager.update();
			}
		});
		loadUpdater.init();
		loadUpdater.start();
	}
	public static void addLoadFile(LoadFile file) {
		file.fileType = LoadManager.getFileType(file.url);
		file.name = LoadManager.getFileName(file.url);
		file.fileDirName = LoadManager.getFileDirName(file.url);
		if (LoadManager.findAlreadyLoad(file)) {
			LoadManager.sendMsg(file);
		} else {
			LoadManager.loadQueue.add(file);
		}
	}
	public static void sendMsg(LoadFile file){
		if(file.groupID!=0){
			if(file.listener!=null){
				file.listener.loadComHandler(file);
			}
		}else{
			if(file.handler!=null){
				Message msg=new Message();
				msg.what=file.id;
				msg.obj=file;
				file.handler.sendMessage(msg);
			}
		}
	}

	private static void loadNext() {
		if (!LoadManager.isloading) {
			if (LoadManager.loadQueue.size() > 0) {
				LoadFile curf = loadQueue.get(0);
				if (LoadManager.findAlreadyLoad(curf)) {
					LoadManager.loadQueue.remove(0);
					LoadManager.sendMsg(curf);
					LoadManager.loadNext();
				} else {
					LoadManager.loadOneFile(curf);
				}
			}
		}
	}

	private static void loadOneFile(LoadFile file) {
		LoadManager.isloading = true;
		LoadManager.target = file;
		if (LoadManager.target.url == "") {
			LoadManager.loadQueue.remove(0);
			LoadManager.target = null;
			LoadManager.isloading = false;
			LoadManager.loadNext();
			return;
		}
		LoadManager.curUrl = target.url;
//		LoadManager.curfileName = target.name;
		LoadManager.curfileDirName = target.fileDirName;
//		LoadManager.curfileType = target.fileType;
		 if (LoadManager.isStoreFileExist (LoadManager.curfileDirName)) {
			 LoadManager.curUrl = LoadManager.getStorageDir() + LoadManager.curfileDirName;
		 }
		LoadManager.loadSpecial();
	}

	@SuppressWarnings("unchecked")
	private static void loadSpecial() {
		Bitmap bitmap =null;
		if(LoadManager.isStorageUrl(LoadManager.curUrl )){
		    bitmap = LoadManager.loadSD_CardImage(LoadManager.curUrl );
		}else{
		    bitmap = LoadManager.loadNetImage(LoadManager.curUrl );
		}
		LoadManager.loadFileInfo.put(LoadManager.curfileDirName , bitmap);
		if (LoadManager.target != null) {
			if (LoadManager.target.data == null) {
				LoadManager.target.data = new ArrayList<Object>();
			}
			((ArrayList<Object>) (LoadManager.target.data)).add(LoadManager.loadFileInfo.get(LoadManager.curfileDirName));
			if(!LoadManager.isStorageUrl(LoadManager.curUrl )){
			    if(LoadManager.target.isSaveLocal){
			    	LoadManager.saveImageToSD_Card(bitmap,LoadManager.target);
				}
			}
			LoadManager.sendMsg(LoadManager.target);
			
			LoadManager.breakLoadNext();
		} else {
			LoadManager.breakLoadNext();
		}
	}

	private static void breakLoadNext() {
		LoadManager.loadQueue.remove(0);
		LoadManager.target = null;
		LoadManager.isloading = false;
		LoadManager.loadNext();
	}

	private static void update() {
		LoadManager.loadNext();
	}
	public static void disposeByUrl(String url){
	    String type=LoadManager.getFileType(url);
	    if(type.equals(LoadFile.F_JPG)||type.equals(LoadFile.F_PNG)){
	    	
	    	((Bitmap)(LoadManager.loadFileInfo.get(url))).recycle();
	    	
	    }else if(type.equals(LoadFile.F_JSON)){
	    	
	    }
	    LoadManager.loadFileInfo.remove(url);
	}
	
	
	

	public static String getFileType(String url) {
		int index = url.lastIndexOf(".");
		String type = url.substring(index);
		return type;
	}

	public static String getFileName(String url) {
		int begin = url.lastIndexOf("/");
		int end = url.lastIndexOf(".");
		String name = url.substring(begin + 1, end);
		return name;
	}
	public static String getFileDirName(String url) {
		int begin = url.lastIndexOf(AppConfig.RES_ROOT_DIR_NAME)+AppConfig.RES_ROOT_DIR_NAME.length();
		return  url.substring(begin);
	}
	//sd_card_dir
	public static String getStorageDir(){
		return Environment.getExternalStorageDirectory().getPath()+AppConfig.RES_ROOT_DIR_NAME;
	}
	public static boolean isStoreFileExist(String fileName){
		String imgFilePath = LoadManager.getStorageDir();
		File file = new File(imgFilePath+fileName);
		return file.exists();
	}
	public static boolean isStorageUrl(String url){
		return (url.indexOf("http://")==-1);
	}
	@SuppressWarnings("unchecked")
	private static boolean findAlreadyLoad(LoadFile file) {
		if (LoadManager.loadFileInfo.containsKey(file.fileDirName)) {
			if (file.data == null) {
				file.data = new ArrayList<Object>();
			}
			Object ob = LoadManager.loadFileInfo.get(file.fileDirName);
			((ArrayList<Object>) (file.data)).add(ob);
			return true;
		}
		return false;
	}
	private static Bitmap loadNetImage(String path) {
		URL url;
		InputStream inputStream=null;
		try {
			url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			inputStream = conn.getInputStream();
			Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
			inputStream.close();
			conn.disconnect();
			return bitmap;
		} catch (IOException e) {
			Util.logError(e.getMessage());
		}
		return null;
	}
	public static Bitmap loadSD_CardImage(String path) {
		File file = new File(path);
		if (file.exists()) {
			try {
				FileInputStream in = new FileInputStream(file);
				// byte[] b = new byte[in.available()];
				// in.read(b);
				Bitmap bitmap = BitmapFactory.decodeStream(in);
				in.close();
				return bitmap;
			} catch (IOException e) {
				Util.logError(e.getMessage());
			}
		}
		return null;
	}
	public static void saveImageToSD_Card(Bitmap bitmap,LoadFile target) {
		String imgFilePath = LoadManager.getStorageDir();
		try {
			File file = new File(imgFilePath);
			if (!file.exists()) {
				file.mkdir();
			}
			File file2 = new File(imgFilePath + target.fileDirName);
			if (!file2.exists()) {
				file2.createNewFile();
				BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(file2));
				if(target.fileType.equals(LoadFile.F_JPG)){
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
				}else if(target.fileType.equals(LoadFile.F_PNG)){
					bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
				}
				os.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static String loadString(Context context,String path) { 
		AssetManager asm = context.getAssets();
		InputStream is;
	    StringBuffer sb = new StringBuffer("");  
		try {
			is = asm.open(path);
			   InputStreamReader inputStreamReader = null;  
			   inputStreamReader = new InputStreamReader(is, "utf-8");  
			   BufferedReader reader = new BufferedReader(inputStreamReader);  
			   String line;  
			    try {  
			        while ((line = reader.readLine()) != null) {  
			            sb.append(line);  
			            sb.append("\n");  
			        }  
			    } catch (IOException e) {  
			        e.printStackTrace();  
			    }  
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return sb.toString();  
	} 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static Bitmap loadAssetImage(Context context) {
		AssetManager asm = context.getAssets();
		InputStream is;
		Bitmap bitmap = null;
		try {
			is = asm.open("icon0.jpg");
			bitmap = BitmapFactory.decodeStream(is);
			// LoadManager.saveImage(context,bitmap);
			// LoadManager.readSDCardImage();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}








}
