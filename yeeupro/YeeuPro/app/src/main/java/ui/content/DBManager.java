package ui.content;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import base.component.GApplication;

public class DBManager {
    private final int BUFFER_SIZE = 400000;

    public static final String DB_PATH = "/data"
            + Environment.getDataDirectory().getAbsolutePath() + "/";  //在手机里存放数据库的位置

    private SQLiteDatabase database;
    private Context context;
    private String db_name = ""; //保存的数据库文件名
    private int rawDatabaseInput;//导入的数据资源

    public DBManager(Context context,String DBName,int rawDatabaseInput) {
        this.context = context;
        this.db_name=DBName;
        this.rawDatabaseInput=rawDatabaseInput;

    }

    public SQLiteDatabase openDatabase() {
        this.database = this.openDatabase(DB_PATH+GApplication.PACKAGE_NAME + "/" + db_name);
        return database;
    }

    private SQLiteDatabase openDatabase(String dbfile) {
        try {
            if (!(new File(dbfile).exists())) {//判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库
                InputStream is = this.context.getResources().openRawResource(rawDatabaseInput); //欲导入的数据库
                FileOutputStream fos = new FileOutputStream(dbfile);
                byte[] buffer = new byte[BUFFER_SIZE];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }
            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbfile,
                    null);
            return db;
        } catch (FileNotFoundException e) {
            Log.e("Database", "File not found");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("Database", "IO exception");
            e.printStackTrace();
        }
        return null;
    }
    public void closeDatabase() {
        this.database.close();
    }
}