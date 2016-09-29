package baseframe.tools;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yu.wyt.R;

import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import baseframe.base.IBaseObject;
import baseframe.config.AppConfig;
import baseframe.config.BaseConfig;
import baseframe.core.GFloatColor;
import baseframe.core.GImageView;
import baseframe.core.GPoint;
import baseframe.core.LoadFile;
import baseframe.net.IResponseCallBack;
import baseframe.resource.LoadManager;

public class Util {
	public static final String LOG_DEFAULT_TAG="wyt_log";
	public static final RectF tempRect=new RectF();
	public static final Point tempPoint=new Point();
	
	
	public static void logInfo(String msg){
		if(msg!=null){
			Log.i(Util.LOG_DEFAULT_TAG, msg);
		}
	}
	public static void logDebug(String msg){
		if(msg!=null){
			Log.d(Util.LOG_DEFAULT_TAG, msg);
		}
	}
	public static void logError(String msg){
		if(msg!=null){
			Log.e(Util.LOG_DEFAULT_TAG, msg);
		}
	}
	public static void logToastInfo(Context context,String msg){
		if(msg!=null){
			Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
		}
	}

	public static boolean isSuit(int composite,int sing){
		return  (composite&sing)!=0;
	}

	//argb
	public static GFloatColor ConvertIntToColor32(int color)
	{
		int r=(color>>16)&0xff;
		int g=(color>>8)&0xff;
		int b=color&0xff;
		int a=(color>>24)&0xff;
		return new GFloatColor((float)r/255,(float)g/255,(float)b/255,(float)a/255);
	}
	public static GFloatColor ConvertIntToColor24(int color)
	{
		int r=(color>>16)&0xff;
		int g=(color>>8)&0xff;
		int b=color&0xff;
		return new GFloatColor((float)r/255,(float)g/255,(float)b/255,1.0f);
	}
	public static long getUint32(long l){
		return l & 0x00000000ffffffff;
	}
	//获取颜色变换的图片
	public static Bitmap getColorBitmap(Bitmap srcBitmap,int scolor)
	{
		GFloatColor color= Util.ConvertIntToColor32(scolor);
		float[] src = new float[]{
				color.getR(), 0, 0, 0, 0,
				0, color.getG(), 0, 0, 0,
				0, 0, color.getB(), 0, 0,
				0, 0, 0, color.getA(), 0};
		Bitmap afterBitmap=Bitmap.createBitmap(srcBitmap.getWidth(),srcBitmap.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas=new Canvas(afterBitmap);
		Paint paint=new Paint();
		paint.setColorFilter(new ColorMatrixColorFilter(src));
		canvas.drawBitmap(srcBitmap,new Matrix(),paint);
		return afterBitmap;
	}







	public static void noTitleBar(Activity activity){
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);//no titleBar
	}
	public static void fullScreen(Activity activity){
		activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);//
	}
	public static void fullScreenAndNoTitleBar(Activity activity){
		Util.noTitleBar(activity);
		Util.fullScreen(activity);
	}
	
	public static void loadOneResource(String url,int msgId,Handler handler){
		LoadFile file=new LoadFile();
		file.url=url;
		file.id=msgId;
		file.handler=handler;
		LoadManager.addLoadFile(file);
	}
	
	
	
	public static void removeEventListener(View v,int type){
		if(v!=null){
			if(type==BaseConfig.CLICK){
				v.setOnClickListener(null);
			}else if(type==BaseConfig.TOUCH){
				v.setOnTouchListener(null);
			}else if(type==BaseConfig.FOCUSCHANGE){
				v.setOnFocusChangeListener(null);
			}
		}
	}
	public static void removeHandler(Handler handler){
		if(handler!=null){
			handler.removeCallbacksAndMessages(null);
		}
	}
	
	public static void disposeObject(IBaseObject obj){
		if(obj!=null){
			obj.dispose();
		}
	}
	public static void disposeObjectWithData(IBaseObject obj){
		if(obj!=null){
			if(obj instanceof  GImageView){//image
				BitmapDrawable d=(BitmapDrawable)(((GImageView)obj).getDrawable());
				if(d!=null){
					Bitmap b=d.getBitmap();
					if(b!=null){
						b.recycle();
					}
				}
			}
			obj.dispose();
		}
	}
	public static void disposeObjectWithData(Bitmap obj){
		if(obj!=null){
			obj.recycle();
		}
	}
	public static void disposeList(List<?> li){
		if(li!=null){
			IBaseObject obj=null;
			for(int i=0;i<li.size();i++){
				obj=(IBaseObject)li.get(i);
				if(obj!=null){
					obj.dispose();
				}
			}
			li.clear();
		}
	}

	public enum LogType
	{
		Debug,
		Warnning,
		Error
	}

	//log输出
	public static void Log(String tag,String msg,LogType type)
	{
		if(type==LogType.Debug)
		{
			Log.d(tag,msg);
		}else if(type==LogType.Warnning)
		{
			Log.w(tag, msg);
		}
		else if(type==LogType.Error)
		{
			Log.e(tag,msg);
		}
	}



	//矩形区域中是否包含点
	public static boolean isContain(float px,float py,float tx,float ty,float tw,float th)
	{
		return px>=tx&&px<=tx+tw&&py>=ty&&py<=ty+th;
	}
	//在矩形区域内随机一个点
	public static GPoint getRandomPoint(float x,float y,float w,float h)
	{
		return new GPoint((float)(Math.random()*(w-x)+x),(float)(Math.random()*(h-y)+y));
	}
	//在矩形区域内随机一个点
	public static GPoint getRandomPoint(float x,float y,float w,float h,GPoint point)
	{
		point.x=(float)(Math.random()*w+x);
		point.y=(float)(Math.random()*h+y);
		return point;
	}
	//在矩形区域内随机一个点
	public static GPoint getRandomPointPoint(float x,float y,float mx,float my,GPoint point)
	{
		point.x=(float)(Math.random()*(mx-x)+x);
		point.y=(float)(Math.random()*(my-y)+y);
		return point;
	}
	public static Object[] randomArray(Object[] arr)
	{
		int index = 0;
		int rlen = arr.length;
		int len = rlen;
		Object temp;
		for (int i = 0; i < len; i++) {
			index = (int)(Math.random() * rlen);
			temp = arr[index];
			arr[index]=arr[len-1];
			arr[len-1]=temp;

			rlen--;
		}
		return arr;
	}
	//随机列表
	public static void randomList(List<Object> list)
	{
		int index = 0;
		int rlen = list.size();
		int len = rlen;
		Object temp;
		for (int i = 0; i < len; i++) {
			index = (int)(Math.random() * rlen);
			temp = list.get(index);
			list.remove(index);
			list.add(temp);
			rlen--;
		}
	}
	//根据手机的分辨率从 dp 的单位 转成为 px(像素)
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	//根据手机的分辨率从 px(像素) 的单位 转成为 dp
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	//弧度转角度
	public static double getDegreesByRadians(double radians)
	{
		return radians * 180/Math.PI;
	}
	//角度转弧度
	public static double GetRadiansByDegrees(double degrees)
	{
		return degrees * Math.PI/180;
	}
	public static float getDistanceTowPoint(float curpx,float curpy,float targetpx,float targetpy){
		float disx=targetpx-curpx;
		float disy=targetpy-curpy;
		return (float)(Math.sqrt(disx*disx+disy*disy));
	}
	public static float getDistanceTowPosx(float curpx,float targetpx){
		return Math.abs(targetpx-curpx) ;
	}
	public static float getDistanceTowPointNoSq(float curpx,float curpy,float targetpx,float targetpy){
		float disx=targetpx-curpx;
		float disy=targetpy-curpy;
		return disx*disx+disy*disy;
	}

	public static GImageView getGImageView(Context context,ViewGroup parent,int resId,float x,float y,int width,int height)
	{
		GImageView view=new GImageView(context);
		view.setLayoutParams(new FrameLayout.LayoutParams(width, height));//大小可以动态获取
		view.setScaleType(ImageView.ScaleType.FIT_XY);
		view.setImage(resId);
		view.setX(x);
		view.setY(y);
		parent.addView(view);
		return view;
	}
	public static void removeFromParent(View view)
	{
		if(view!=null&&view.getParent()!=null)
		{
			((ViewGroup)view.getParent()).removeView(view);
		}
	}
	public static void clearAnimation(View view)
	{
		if(view!=null)
		{
			view.clearAnimation();
		}
	}
	//剪裁字符串长度
	public static String trimStringLen(String src,int len)
	{
		if(src.length()>len)
		{
			src=src.substring(0,len)+"...";
		}
		return src;
	}

	//检测邮箱是否合格
	public static boolean checkEmail(String email)
	{
		Pattern pattern=Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
	//检测电话是否合格
	public static boolean checkPhone(String phone)
	{
		Pattern p = Pattern.compile("^((1[0-9][0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(phone);
		return m.matches();
	}
	//通过Uri获取Bitmap
	public static Bitmap getBitmapFromUri(Context context, Uri uri, int width, int addedscaling) throws FileNotFoundException {
		String picturePath = getRealFilePath(context,uri);
		Bitmap bitmap = getBitmapByWidth(picturePath, width, addedscaling);
		return bitmap;
	}

	public static Bitmap getBitmapFromUri(Context context, Uri uri, int width) throws FileNotFoundException {
		return getBitmapFromUri(context, uri, width, 0);
	}


	/**
	 * 根据宽度从本地图片路径获取该图片的缩略图
	 *
	 * @param picturePath  本地图片的路径
	 * @param width        缩略图的宽
	 * @param addedScaling 额外可以加的缩放比例
	 * @return bitmap 指定宽高的缩略图
	 */
	public static Bitmap getBitmapByWidth(String picturePath, int width,
										  int addedScaling) {
		if (TextUtils.isEmpty(picturePath)) {
			return null;
		}

		Bitmap temBitmap = null;

		try {
			BitmapFactory.Options outOptions = new BitmapFactory.Options();

			// 设置该属性为true，不加载图片到内存，只返回图片的宽高到options中。
			outOptions.inJustDecodeBounds = true;
			// 加载获取图片的宽高
			BitmapFactory.decodeFile(picturePath, outOptions);

			int height = outOptions.outHeight;

			if (outOptions.outWidth > width) {
				// 根据宽设置缩放比例
				outOptions.inSampleSize = outOptions.outWidth / width + 1
						+ addedScaling;
				outOptions.outWidth = width;

				// 计算缩放后的高度
				height = outOptions.outHeight / outOptions.inSampleSize;
				outOptions.outHeight = height;
			}
			// 重新设置该属性为false，加载图片返回
			outOptions.inJustDecodeBounds = false;
			outOptions.inPurgeable = true;
			outOptions.inInputShareable = true;
			temBitmap = BitmapFactory.decodeFile(picturePath, outOptions);
		} catch (Throwable t) {
			t.printStackTrace();
		}

		return temBitmap;
	}

	//获取物理路径
	public static String getRealFilePath( final Context context, final Uri uri ) {
		if ( null == uri ) return null;
		final String scheme = uri.getScheme();
		String data = null;
		if ( scheme == null )
			data = uri.getPath();
		else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
			data = uri.getPath();
		} else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
			Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
			if ( null != cursor ) {
				if ( cursor.moveToFirst() ) {
					int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
					if ( index > -1 ) {
						data = cursor.getString( index );
					}
				}
				cursor.close();
			}
		}
		return data;
	}

	public static Bitmap createCircleImage(Bitmap source, int min) {
		final Paint paint = new Paint();
		paint.setAntiAlias(true);
		// 注意一定要用ARGB_8888，否则因为背景不透明导致遮罩失败
		Bitmap target = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);
		// 产生一个同样大小的画布
		Canvas canvas = new Canvas(target);
		// 首先绘制圆形
		canvas.drawCircle(min / 2, min / 2, min / 2, paint);
		// 使用SRC_IN
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		// 绘制图片
		canvas.drawBitmap(source, 0, 0, paint);
		return target;
	}
	public static void toToast(Context context,String msg)
	{
		Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
	}
	public static void toToast(final String msg,final Activity activity)
	{
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Util.toToast(activity,msg);
			}
		});

	}
	public static String toJson(Object object)
	{
		return new Gson().toJson(object);
	}
	public static <T> T fromJson(String string,Type type)
	{
		Gson gson=new Gson();
		T obj=gson.fromJson(string,type);
		return obj;
	}


	//根据条件查询列表
	public static <T> ArrayList<T> find(ArrayList<T> tArrayList,IFindRule rule)
	{
		ArrayList<T> result=null;
		for(int i=0;i<tArrayList.size();i++)
		{
			if(result==null)
			{
				result=new ArrayList<T>();
			}
			if(rule.find())
			{
				result.add(tArrayList.get(i));
			}
		}
		return tArrayList;
	}
	//根据条件执行方法列表
	public static <T> ArrayList<T> invoke(ArrayList<T> tArrayList,IInvokeRule rule)
	{
		for(int i=0;i<tArrayList.size();i++)
		{
			rule.invoke(tArrayList.get(i));
		}
		return tArrayList;
	}
	//根据条件执行多个列表的方法
	public static <T> void invokeArray(ArrayList<T>[] tArrayList,IInvokeRule rule)
	{
		for(int i=0;i<tArrayList.length;i++)
		{
			invoke(tArrayList[i],rule);
		}
	}
	public interface IFindRule
	{
		boolean find();
	}
	public interface IInvokeRule<T>
	{
		void invoke(T obj);
	}

	//获取当前类名称
	public static String getCurrentClassName(Object object)
	{
		return object.getClass().getName();
	}

	//获取MD5值(32位)
	public static String getMd5(String str) {
		StringBuilder builder = new StringBuilder();
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("md5");
			byte[] cipherData = md5.digest(str.getBytes());
			for (byte cipher : cipherData) {
				String toHexStr = Integer.toHexString(cipher & 0xff);
				builder.append(toHexStr.length() == 1 ? "0" + toHexStr: toHexStr);
			}
			System.out.println(builder.toString());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}
	//延迟调用方法
	public static Timer delayPerform(final IResponseCallBack<Object> callBack, long delay)
	{
		return delayPerform(callBack,delay,null);
	}
	public static <T> Timer delayPerform(final IResponseCallBack<T> callBack, long delay,final T parame)
	{
		Timer timer=new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				callBack.callback(parame);
			}
		},delay);
		return timer;
	}
	//添加下载路径服务器地址
	public static String getDownloadURL(String path)
	{
		return AppConfig.SERVER_DOMAIN+path;
	}
	//解析下载路径默认选取第一个
	public static String parseDownLoadURL(String srcPath,Context context)
	{
		ArrayList<HashMap<String,String>> pathset=parseDownLoadURL(srcPath);
		if(pathset.size()>0)
		{
			HashMap<String,String> map=pathset.get(0);
			Iterator<String> iterator=map.keySet().iterator();
			while(iterator.hasNext())
			{
				return AppConfig.SERVER_PHP_DOMAIN+map.get(iterator.next());
			}
		}
		//无效路径则返回一张失败图片
		Uri uri = Uri.parse("android.resource://"+context.getPackageName()+"/"+R.drawable.loadfail);
		return getRealFilePath(context,uri);
	}
	//解析下载路径(394=path1,395=path2,395=path3)
	public static ArrayList<HashMap<String,String>> parseDownLoadURL(String srcPath)
	{
		srcPath=srcPath.replace("{","");
		srcPath=srcPath.replace("}","");
		ArrayList<HashMap<String,String>> result=new ArrayList<HashMap<String,String>>();
		String[] p1=srcPath.split(",");
		String[] p2;
		HashMap<String,String> map;
		for(int i=0;i<p1.length;i++)
		{
			p2=p1[i].split("=");
			map=new HashMap<String,String>();
			map.put(p2[0],p2[1]);
			result.add(map);
		}
		return result;
	}



}
