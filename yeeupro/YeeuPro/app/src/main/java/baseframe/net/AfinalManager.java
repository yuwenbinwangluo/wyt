package baseframe.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;

import com.yu.wyt.R;

import net.tsz.afinal.FinalBitmap;

/**
 *
 */
public class AfinalManager {

    private static AfinalManager _instance=new AfinalManager();
    private static Bitmap loadLoading=null;
    private static Bitmap loadFail=null;
    private static int MAX_W=800;
    private static int MAX_H=800;
    private static int CACHESIZE=4;//系统的1/4

    public static AfinalManager getInstance()
    {
        return _instance;
    }



    public void init(Context context)
    {
        loadLoading= ((BitmapDrawable) ContextCompat.getDrawable(context, R.drawable.loading)).getBitmap();
        loadFail= ((BitmapDrawable) ContextCompat.getDrawable(context, R.drawable.loadfail)).getBitmap();
    }
    public FinalBitmap getAFinalBitmap(Context context)
    {
        FinalBitmap fb= FinalBitmap.create(context);
        fb.configBitmapMaxHeight(MAX_W); // 配置默认图片的最大的高度
        fb.configBitmapMaxWidth(MAX_H); // 配置默认图片的最大的宽度
        //默认路径是包名下 cache
//        fb.configDiskCachePath(Environment.getExternalStorageDirectory().getPath());//设置文件缓存的位置
        fb.configDiskCacheSize(CACHESIZE);// 内存缓存的百分比(系统内存1/4)
        fb.configLoadingImage(loadLoading);
        fb.configLoadfailImage(loadFail);
        return fb;
    }








}
