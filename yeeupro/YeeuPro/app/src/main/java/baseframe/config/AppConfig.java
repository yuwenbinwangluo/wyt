package baseframe.config;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import baseframe.tools.Util;


public class AppConfig {
	
	public enum NET_WORK_STATE{
		NO,//no-net
		WIFI,//wifi
		GPRS,//GPRS-2g
		N3G,//3G
		N4G//4G
	}
	public enum PLATFORM_OS{
		NO,
		ANDROID,//android
		IOS,//
		PB,//
		WIN//windows
	}
	
	public static final int APP_TITLEBAR_H=38;
	public static int APP_DW=480;
	public static int APP_DH=762;
	public static int APP_W=480;
	public static int APP_H=800;
	public static int APP_NOTITLE_H=762;
	public static float DENSITY_W_RATE=1.0f;//
	public static float DENSITY_RATE=1.0f;//
	public static int DENSITY_DPI=160;//
	public static String RES_ROOT_DIR_NAME="/wyt/";
	//!!!历史原因需要和web版本共享资源
//	public static final String SERVER_DOMAIN="http://101.201.72.187:8080";
	public static final String SERVER_DOMAIN="http://172.27.35.1:8080";
	public static final String SERVER_NAME="/wyt";
	//php服务器根域名!!!历史原因需要和web版本共享资源
	public static final String SERVER_PHP_DOMAIN="http://wap.wanyi8.net";//远程资源服务器host
	//servlets
	public static final String SERVER_SEVERLET_LOGIN="/login";
	public static final String SERVER_SEVERLET_BUSINESS="/business";//商家
	public static final String SERVER_SEVERLET_CREATEBUSINESS="/createBusiness";//创建商家
	public static final String SERVER_SEVERLET_GET_DISTRICTDATA="/district";//转换城市地区数据
	public static final String SERVER_SEVERLET_LUCKDRAW="/luckdraw";//抽奖相关
	
	public static float deltaTime=16.6f;
	public static int frameRate=60;
	public static PLATFORM_OS platformOS=PLATFORM_OS.ANDROID;
	
	

//	public static String PRO_DATA="";
	
	public static void init(Context context){

		DisplayMetrics dm = new DisplayMetrics();
		WindowManager windowMgr = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		windowMgr.getDefaultDisplay().getMetrics(dm);
		AppConfig.APP_W= dm.widthPixels;
		AppConfig.APP_H = dm.heightPixels;
		AppConfig.APP_NOTITLE_H = dm.heightPixels-AppConfig.APP_TITLEBAR_H;
		AppConfig.DENSITY_W_RATE =((float)AppConfig.APP_W/AppConfig.APP_DW); 
		AppConfig.DENSITY_RATE = dm.density; 
		AppConfig.DENSITY_DPI= dm.densityDpi;
		Util.logInfo("screenW:" + AppConfig.APP_W  + "-----screenH:=" + AppConfig.APP_H+"-----densityRate:"+AppConfig.DENSITY_RATE+"------densityDPI:"+AppConfig.DENSITY_DPI);  
		
		AppConfig.deltaTime=1000/AppConfig.frameRate;
		
		

//		AppConfig.PRO_DATA=LoadManager.loadString(activity,"test_product_data.txt");
	}
	
	
	
	
	
	

}
