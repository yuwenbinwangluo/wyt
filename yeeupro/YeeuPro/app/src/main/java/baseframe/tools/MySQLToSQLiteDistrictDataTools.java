package baseframe.tools;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.google.gson.reflect.TypeToken;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import base.component.GApplication;
import baseframe.config.AppConfig;
import baseframe.config.Command;
import baseframe.net.NetManager;
import ui.content.DBManager;
import ui.modes.DistrictData;

/**
 * 从mysql数据库请求数据导入到SQLite数据库工具类
 */
public class MySQLToSQLiteDistrictDataTools {
    protected static String TAG = "MySQLToSQLiteDistrictDataTools";
    public static final String DBNAME = "china_province_city_district.db";

    public static void toDo(final Activity activity) {
        HashMap<String, String> mapParams = new HashMap<String, String>();
        AjaxParams params = new AjaxParams(mapParams);
        NetManager.Post(AppConfig.SERVER_DOMAIN + AppConfig.SERVER_NAME + AppConfig.SERVER_SEVERLET_GET_DISTRICTDATA, params, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object result) {
                Log.d(TAG, TAG + result);
                String resultstr = result.toString();

                String[] strarr = resultstr.split("\\^");
                if (strarr[0].equals(Command.Code0)) {
                    Log.d(TAG, "获取城市地区数据成功");
                    //登录成功
                    //返回的是json用户自己数据
                    ArrayList<DistrictData> districtDatas = Util.fromJson(strarr[1], new TypeToken<ArrayList<DistrictData>>() {
                    }.getType());
                    success(districtDatas, activity);

                } else if (strarr[0].equals(Command.Code1)) {
                    //登录失败
                    Log.d(TAG, "获取城市地区数据失败");

                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                try {
                    throw new Exception(strMsg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private static void success(ArrayList<DistrictData> districtDatas, Activity activity) {
        SQLiteDatabase database = ((GApplication) activity.getApplication()).getDbManager().openDatabase();

        int len = districtDatas.size();
        ContentValues contentValues = new ContentValues();
        DistrictData districtData = null;
        for (int i = 0; i < len; i++) {
            districtData = districtDatas.get(i);
            contentValues.clear();
            contentValues.put("name", districtData.getName());
            contentValues.put("level", districtData.getLevel());
            contentValues.put("upid", districtData.getUpid());
            database.insert("district", "NULL", contentValues);
        }

        Log.d(TAG, "存储SQLite数据库-城市地区数据成功");
        toSave(activity);


    }

    //把数据库下的文件存储到外部
    public static void toSave(final Activity activity) {
        final String DATABASE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/dictionary";
        String databaseFilename = DATABASE_PATH + "/" + DBNAME;
        File dir = new File(DATABASE_PATH);
        if (!dir.exists()) dir.mkdir();

        String databsaePath = DBManager.DB_PATH + "/" + DBNAME;
        File databaseFile = new File(databsaePath);
        if (databaseFile.exists()) {
            InputStream is = null;
            FileOutputStream fos = null;

            try {
                is = new FileInputStream(databaseFile);
                int len = is.available();
                fos = new FileOutputStream(databaseFilename);
                byte[] buffer = new byte[len];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }

                fos.close();
                is.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
