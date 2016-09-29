package baseframe.tools;

import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import baseframe.conf.Command;
import baseframe.conf.ServerConfig;

public class Util {
	
	
	//获取输出对象
	public static PrintWriter getOuter(HttpServletResponse response){
		PrintWriter out=null;
		try{
			response.setContentType(ServerConfig.SERVER_CLIENT_CHARSET);
			response.setCharacterEncoding("utf-8");
			out=response.getWriter();
		}catch(Exception e){
			e.printStackTrace();
		}
		return out;
	}
	public static void logInfo(String msg){
		if(msg!=null){
			System.out.println(msg);
		}
	}
	public static void logError(String msg,Exception e){
		Util.logInfo(msg+"------"+e.getMessage());
	
	}
	/**
	 * 像客户端发送数据
	 * @param response
	 * @param mesg
	 */
	public static void sendData(HttpServletResponse response,String mesg){
		Util.getOuter(response).println(mesg);
	}
	//向客户端发送json数据
	public static  void writeToJson(Object records,boolean isSuccess,PrintWriter out)
	{
		if(isSuccess)
		{
			String str="temp";
			if(records!=null)
			{
				Gson gson=new Gson();
				str=gson.toJson(records);
			}
			out.write(Command.Code0+"^"+str);//0:成功 1：失败
		}else{
			out.write(Command.Code1+"^-1");
		}
	}
	
	//获取当前时间
	public static int getCurrentDateWithSecond(){
		return (int)(System.currentTimeMillis()/1000);
	}
	//获取当前时间
	public static Date getCurrentDateBySecond(int second){
		Date date=new Date(second*1000);
		return date;
	}
	//获取当前时间
	public static String getCurrentDateStr(){
		return getDateByFormatString(new Date(),"yyyy-MM-dd-HH:mm:ss");
	}
	//获取当前时间
	public static String getCurrentDateStrNoF(){
		return getDateByFormatString(new Date(),"yyyyMMddHHmmss");
	}
	//获取uuid
	public static String getUUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}
	//时间格式 年月日 时分秒
	public static String getDateStrbyLong(long date){
		return getDateByFormatString(new Date(date),"yyyy-MM-dd-HH:mm:ss");
	}
	//时间格式  时分
	public static String getDateStrbyLongWithMini(long date){
		return getDateByFormatString(new Date(date),"HH:mm");
	}
	//时间格式 年月日
	public static String getDateFormatString(Date day){
		if(day==null){
			return "";
		}
		SimpleDateFormat	dateFormat=new SimpleDateFormat();
		dateFormat.applyPattern("yyyy-MM-dd");
		return dateFormat.format(day);
	}
	//时间格式
	public static String getDateByFormatString(Date day,String format){
		if(day==null){
			return "";
		}
		SimpleDateFormat	dateFormat=new SimpleDateFormat();
		dateFormat.applyPattern(format);
		return dateFormat.format(day);
	}
	  /** 
     * 格式化string为Date 
     * @param datestr 
     * @return date 
     */ 
    public static Date parseDate(String datestr) {  
        if (null == datestr || "".equals(datestr)) {  
            return null;  
        }  
        try {  
            String fmtstr = null;  
            if (datestr.indexOf(':') > 0) {  
                fmtstr = "yyyy-MM-dd HH:mm:ss";  
            } else {  
                fmtstr = "yyyy-MM-dd";  
            }  
            SimpleDateFormat sdf = new SimpleDateFormat(fmtstr, Locale.UK);  
            return sdf.parse(datestr);  
        } catch (Exception e) {  
            return null;  
        }  
    }  
    /** 
     * 日期转化为String 
     * @param date 
     * @return date string 
     */ 
    public static String fmtDate(Date date) {  
        if (null == date) {  
            return null;  
        }  
        try {  
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);  
            return sdf.format(date);  
        } catch (Exception e) {  
            return null;  
        }  
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
    //获取MD5值截取(n位)
    public static String getMd5(String str,int start,int end) {
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
		return builder.toString().substring(start, end);
	}
	//检测邮箱是否合格
	public static boolean checkEmail(String email)
	{
		Pattern pattern=Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
	//检测电话是否合格
	public static  boolean checkPhone(String phone)
	{
		Pattern p = Pattern.compile("^((1[0-9][0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");  
		Matcher m = p.matcher(phone);  
		return m.matches();
	}

}
