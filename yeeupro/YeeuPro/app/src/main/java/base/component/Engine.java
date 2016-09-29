package base.component;

import android.content.Context;

import baseframe.base.Updater;
import baseframe.base.UpdaterListener;
import baseframe.config.AppConfig;
import baseframe.config.FormatConfig;
import baseframe.net.HttpManager;
import baseframe.resource.CorePool;

/**
 * 2015.5
 * @author yu
 * GPower Engine  <application layer>
 *
 */
public class Engine {
	private static boolean _isInited=false;
	private static Updater updater=null;

	
	
	public static void init(Context context){
		if(!Engine._isInited){
			Engine._isInited=true;
			AppConfig.init(context);
			FormatConfig.init();
//			LoadManager.init();//暂时废弃不用改用 Afinal
			
			updater=new Updater();
			updater.setUpdaterListener(new UpdaterListener() {
				@Override
				public void update() {
					Engine.update();
				}
			});
			updater.init();
			updater.start();
		}
	}
	
	
	public static void update(){
//		Util.logInfo("invoke-Egine-update-once!");
		
		HttpManager.update();
		CorePool.update();
	}

	public static boolean isInited(){
		return Engine._isInited;
	}
	
	
	
	
	
	
	
	

}
