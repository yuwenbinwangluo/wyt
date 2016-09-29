package baseframe.core;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

import baseframe.config.AppConfig;
import baseframe.manager.ActivityManager;
import baseframe.tools.Util;

public class GActivity extends Activity {
	protected String TAG="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TAG= Util.getCurrentClassName(this);
		((ActivityManager)ActivityManager.getInstance(ActivityManager.class)).open(this);


	}

	@Override
	public void onConfigurationChanged(Configuration configuration)
	{
		super.onConfigurationChanged(configuration);
		AppConfig.init(this);
		if(configuration.orientation==Configuration.ORIENTATION_PORTRAIT){
			Toast.makeText(this, "现在是竖屏", Toast.LENGTH_SHORT).show();
		}
		if(configuration.orientation==Configuration.ORIENTATION_LANDSCAPE){
			Toast.makeText(this, "现在是横屏", Toast.LENGTH_SHORT).show();
		}
	}


	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		((ActivityManager)ActivityManager.getInstance(ActivityManager.class)).close(this);
	}
	

}
