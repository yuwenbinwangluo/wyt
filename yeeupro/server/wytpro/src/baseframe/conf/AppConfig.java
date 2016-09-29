package baseframe.conf;

public class AppConfig {
	public enum NET_WORK_STATE{
		NO,//no-net
		WIFI,//wifi
		GPRS,//GPRS-2g连接
		N3G,//3G连接
		N4G//4G连接
	}
	public enum PLATFORM_OS{
		NO,
		ANDROID,//android
		IOS,//未越狱ios
		PB,//越狱ios
		WIN//windows
	}
	//根域名
	public static final String WYTROOT="http://wap.wanyi8.net/";
	
	
	
	
	

}
