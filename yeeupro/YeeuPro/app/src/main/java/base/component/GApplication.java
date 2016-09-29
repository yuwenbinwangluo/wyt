package base.component;

import android.app.Application;
import android.util.Log;

import com.yu.wyt.R;

import baseframe.net.AfinalManager;
import baseframe.tools.MySQLToSQLiteDistrictDataTools;
import baseframe.tools.Util;
import ui.content.DBManager;

/**
 * 应用程序类
 */
public class GApplication extends Application {

    public static String PACKAGE_NAME = "com.yu.wyt";
    private DBManager dbManager;

    @Override
    public void onCreate()
    {
        super.onCreate();

        Engine.init(this);
        //初始化城市地区数据库
        PACKAGE_NAME=this.getPackageName();
        dbManager=new DBManager(this, MySQLToSQLiteDistrictDataTools.DBNAME, R.raw.china_province_city_district);
        AfinalManager.getInstance().init(this);

        Log.d(Util.getCurrentClassName(this), "onCreate");
    }
    public DBManager getDbManager()
    {
        return dbManager;
    }

    @Override
    public void onTerminate()
    {
        super.onTerminate();
        // 程序终止的时候执行
        Log.d(Util.getCurrentClassName(this), "onTerminate");
    }

    @Override
    public void onTrimMemory(int level)
    {
        super.onTrimMemory(level);
        Log.d(Util.getCurrentClassName(this), "onTrimMemory");
    }

    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
        Log.d(Util.getCurrentClassName(this), "onLowMemory");
    }

}
