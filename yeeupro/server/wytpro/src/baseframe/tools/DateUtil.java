package baseframe.tools;


import java.util.Calendar;

import java.util.Date;
import java.text.SimpleDateFormat;






/**
 * 日期时间处理 
 * @author Administrator
 *
 */
public class DateUtil {
	
	/**
	 * 获取当前日期
	 * @return
	 */
	public static String getCurrentAllTime() {
		Calendar c = Calendar.getInstance();
//		c.setTime(new Date(World.getCurrentTime()));
		StringBuffer now = new StringBuffer();
		now.append(c.get(Calendar.YEAR));
		now.append('-');
		now.append(c.get(Calendar.MONTH) + 1);
		now.append('-');
		now.append(c.get(Calendar.DAY_OF_MONTH));
		now.append('_');
		now.append(c.get(Calendar.HOUR_OF_DAY)<10?"0"+c.get(Calendar.HOUR_OF_DAY):c.get(Calendar.HOUR_OF_DAY));
		now.append(':');
		now.append(c.get(Calendar.MINUTE)<10?"0"+c.get(Calendar.MINUTE):c.get(Calendar.MINUTE));
		now.append(':');
		now.append(c.get(Calendar.SECOND)<10?"0"+c.get(Calendar.SECOND):c.get(Calendar.SECOND));
		return now.toString();
	}
	
	/**
	 * 获取当前日期
	 * @return
	 */
	public static String getCurrentTime() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(System.currentTimeMillis()));
		StringBuffer now = new StringBuffer();
		now.append(c.get(Calendar.MONTH) + 1);
		now.append('月');
		now.append(c.get(Calendar.DAY_OF_MONTH));
		now.append('日');
		now.append(c.get(Calendar.HOUR_OF_DAY));
		now.append('时');
		now.append(c.get(Calendar.MINUTE));
		now.append('分');
		return now.toString();		
	}

	/**
	 * 获得当前日期数
	 * @return
	 */
	public static int getCurrentDate() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(System.currentTimeMillis()));
		return c.get(Calendar.DAY_OF_MONTH);		
	}
	
	/**
	 * 获取当前小时数
	 * @return
	 */
	public static int getCurrentHour() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(System.currentTimeMillis()));
		return c.get(Calendar.HOUR_OF_DAY);
	}
	/**
	 * 获取当前小时数
	 * @return
	 */
	public static int getCurrentHour(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.HOUR_OF_DAY);
	}
	
	/**
	 * 获得当前分钟数
	 * @return
	 */
	public static int getCurrentMinute() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(System.currentTimeMillis()));
		return c.get(Calendar.MINUTE);
	}
	
	/***
	 * 时间
	 * @param d
	 * @param format
	 * @return
	 */
	public static String format(Date d, String format) {
        if (d == null)
            return "";
        SimpleDateFormat myFormatter = new SimpleDateFormat(format);
        return myFormatter.format(d);
    }

    public static long toLong(Date d) {
        if (d==null)
            return 0;
        else
            return d.getTime();
    }

    public static String toLongString(Date d) {
        return "" + toLong(d);
    }
  
    public static Date parse(String time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date d = null;
        try {
        	d = sdf.parse(time);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return d;
    }

    public static String format(Calendar cal, String format) {
        if (cal == null)
            return "";
        SimpleDateFormat myFormatter = new SimpleDateFormat(format);
        return myFormatter.format(cal.getTime());
    }

    
    public static Calendar add(java.util.Date d, int day) {
        if (d == null)
            return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.DATE, day);
        return cal;
    }

    public static Date addDate(java.util.Date d, int day) {
        if (d == null)
            return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.DATE, day);
        return cal.getTime();
    }

    public static Date addHourDate(java.util.Date d, int h) {
        if (d == null)
            return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.HOUR, h);
        return cal.getTime();
    }

    public static Calendar addHour(java.util.Date d, int h) {
        if (d == null)
            return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.HOUR, h);
        return cal;
    }

    public static Date addMinuteDate(java.util.Date d, int m) {
        if (d == null)
            return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.MINUTE, m);
        return cal.getTime();
    }

    public static Calendar addMinute(java.util.Date d, int m) {
        if (d == null)
            return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.MINUTE, m);
        return cal;
    }

    public static int compare(Calendar c1, Calendar c2) {
        if (c1 == null || c2 == null)
            return -1;
        long r = c1.getTimeInMillis() - c2.getTimeInMillis();
        if (r > 0)
            return 1;
        else if (r == 0)
            return 0;
        else
            return 2;
    }

    public static int compare(Date c1, Date c2) {
        if (c1 == null || c2 == null)
            return -1;
        long r = c1.getTime() - c2.getTime();
        
        if (r > 0)
            return 1;
        else if (r == 0)
            return 0;
        else
            return 2;
    }
    
    public static int datediff(Calendar c1, Calendar c2) {
        if (c1 == null || c2 == null)
            return -1;
        long r = c1.getTimeInMillis() - c2.getTimeInMillis();
        r = r / (24 * 60 * 60 * 1000);
        return (int) r;
    }
   
    public static int datediff(Date c1, Date c2) {
        if (c1 == null || c2 == null)
            return -1;
        long r = c1.getTime() - c2.getTime();
        r = r / (24 * 60 * 60 * 1000);
        return (int) r;
    }
   
    public static int datediffMinute(Date c1, Date c2) {
        if (c1 == null || c2 == null)
            return 0;
        long r = c1.getTime() - c2.getTime();
        r = r / (60 * 1000);
        return (int) r;
    }

    public static int datediffMinute(Calendar c1, Calendar c2) {
        if (c1 == null || c2 == null)
            return 0;
        long r = c1.getTimeInMillis() - c2.getTimeInMillis();
        r = r / (60 * 1000);
        return (int) r;
    }
    
    public static int getDayCount(int year, int month) {
        int daysInMonth[] = {
                            31, 28, 31, 30, 31, 30, 31, 31,
                            30, 31, 30, 31};
        
        if (1 == month)
            return ((0 == year % 4) && (0 != (year % 100))) ||
                    (0 == year % 400) ? 29 : 28;
        else
            return daysInMonth[month];
    }
    public static String getYMDDate(Date d)
    {
        Calendar c=Calendar.getInstance();
        c.setTime(d);
        return getChinessNumber(c.get(Calendar.YEAR))+"年"+getChinessNumber((c.get(Calendar.MONTH)+1))+"月" + getChinessNumber(c.get(Calendar.DAY_OF_MONTH))+"日";
    }
    public static String getChinessNumber(int number) {
        StringBuffer chiness = new StringBuffer();
        String temp = String.valueOf(number);
        for (int index = 0; index < temp.length(); index++) {
            String str = "";
            switch (temp.charAt(index)) {
            case '0':
                str = "零";
                break;
            case '1':
                str = "一";
                break;
            case '2':
                str = "二";
                break;
            case '3':
                str = "三";
                break;
            case '4':
                str = "四";
                break;
            case '5':
                str = "五";
                break;
            case '6':
                str = "六";
                break;
            case '7':
                str = "七";
                break;
            case '8':
                str = "八";
                break;
            case '9':
                str = "九";
                break;
            }
            chiness.append(str);
        }
        if (number <= 31) {
            if (number % 10 == 0 && number!=10) {
                chiness.delete(1, 2);
                chiness.replace(1, 1, "十");
            } else if (number > 20) {     
                chiness.insert(1, "十");
            } else if (number > 10) {
                chiness.replace(0, 1, "十");
            } else if (number == 10) {
                chiness.delete(0, 2);
                chiness.insert(0, "十");
            }
        }
        return chiness.toString();

    }
    public static String getFormatDate(Date d)
    {
        Calendar c=Calendar.getInstance();
        c.setTime(d);
        return c.get(Calendar.YEAR)+"年"+(c.get(Calendar.MONTH)+1)+"月" + c.get(Calendar.DAY_OF_MONTH)+"日"+"  " +c.get(Calendar.HOUR)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND)+":"+c.get(Calendar.MILLISECOND);
    }
    public static String getFormatDataByShort(Date d)
    {
        Calendar c=Calendar.getInstance();
        c.setTime(d);
//        return c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-" + c.get(Calendar.DAY_OF_MONTH)+"  " +(c.get(Calendar.HOUR_OF_DAY)>=10?c.get(Calendar.HOUR_OF_DAY):"0"+c.get(Calendar.HOUR_OF_DAY))+":"+(c.get(Calendar.MINUTE)>=10?c.get(Calendar.MINUTE):"0"+c.get(Calendar.MINUTE));
        return c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-" + c.get(Calendar.DAY_OF_MONTH);
    }
    public static String getFormatDate(Calendar date)
    {
        Calendar c=date;
        return c.get(Calendar.YEAR)+"年"+(c.get(Calendar.MONTH)+1)+"月" + c.get(Calendar.DAY_OF_MONTH)+"日"+"  " +c.get(Calendar.HOUR)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND)+":"+c.get(Calendar.MILLISECOND);
    }
    public static String getFormatDate(long l)
    {
        Calendar c=Calendar.getInstance();
        c.setTime(new Date(l));
        return c.get(Calendar.YEAR)+"年"+(c.get(Calendar.MONTH)+1)+"月" + c.get(Calendar.DAY_OF_MONTH)+"日"+"  " +c.get(Calendar.HOUR)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND)+":"+c.get(Calendar.MILLISECOND);
    }
	public static String getNextDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, 1); 
		StringBuffer now = new StringBuffer();
		now.append(c.get(Calendar.YEAR));
		now.append('_');
		now.append(c.get(Calendar.MONTH) + 1);
		now.append('_');
		now.append(c.get(Calendar.DAY_OF_MONTH));
		return now.toString();		
	}
	
	public static boolean checkSunday() {
		Calendar c = Calendar.getInstance();
//		c.setTime(new Date(World.getCurrentTime()));
		return (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);		 
	}

	
	/**
	 * 判断两个时间是否在规定的天数内
	 * @param c1
	 * @param c2
	 * @param num 天数
	 * @return
	 */
	public static boolean isCompareByDate(long c1,long c2,int num){ //c1保存的使用时间，c2当前时间，num是不是在num天内
/*		
 		c1+=8*60*60*1000;
		c2+=8*60*60*1000;
		c1 = c1 /(24*60*60*1000)+1;//过了几天
		c2 = c2 /(24*60*60*1000)+1;
		long c = c2-c1;
		if (c1  > c2){
			c =-c;
		}
//		c++;
//		if (c<=num){
//			return true;
//		}
//		return false;
		
*/
		long c = (c2-c1)/(24*60*60*1000);
		if(c>=num){			//如果过了num天，就是真  HCHC
			return true;
		}
		return false;
	}
	public static void main(String[] args){
//		Calendar cal1 = Calendar.getInstance();
//		for (int j=0;j<=7;j++){
//			System.out.println("------------比较天数"+j+"-----------");
//			for (int i=0;i<=7;i++){
//				Calendar cal2 = Calendar.getInstance();
//				cal2.add(Calendar.DATE, i);
//				System.out.println(get(cal1)+" vs "+get(cal2)+"----"+isCompareByDate(cal1.getTimeInMillis(),cal2.getTimeInMillis(),j));
//			}
//		}
	}
	public static String get(Calendar c) {
//		c.setTime(new Date(System.currentTimeMillis()));
		StringBuffer now = new StringBuffer();
		now.append(c.get(Calendar.MONTH) + 1);
		now.append('月');
		now.append(c.get(Calendar.DAY_OF_MONTH));
		now.append('日');
		now.append(c.get(Calendar.HOUR_OF_DAY));
		now.append('时');
		now.append(c.get(Calendar.MINUTE));
		now.append('分');
		return now.toString();		
	}
	public  static int getWeekInYear(){
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(System.currentTimeMillis()-1000 * 60 * 60*24));
		return c.get(Calendar.WEEK_OF_YEAR);
	}
	/**
	 * 1天的毫秒数
	 */
	public static final long  ONE_DAY = 1000 * 60 * 60*24;
	/**
	 * 1小时的毫秒数
	 */
	public static final long ONE_HOUR=1000 * 60 * 60;
	/**
	 * 1分钟的毫秒数
	 */
	public static final long ONE_MIN=1000*60;
	/**
	 * 1秒钟的毫秒数
	 */
	public static final long ONE_SECOND=1000;
	/**
	 * 每天截止时间    0代表晚上12点。
	 */
	private static final int TICK_GIVE_GIFT=0; 

	/**
	 * 北京时间和system起始时间的间隔 8小时
	 */
	public static final long Time_ZONE=8*60*60*1000;	
	public static long getDays(){
		return  (System.currentTimeMillis()+Time_ZONE-ONE_HOUR*TICK_GIVE_GIFT) / ONE_DAY;
	}
	public static long getDays(long time){
		return  (time+Time_ZONE-ONE_HOUR*TICK_GIVE_GIFT) / ONE_DAY;
	}
	/**
	 * 获得当前时间到几点的秒数
	 * @return
	 */
	public  static  int getSecondToHour(int hour){
		Calendar ca =Calendar.getInstance();
		ca.set(Calendar.HOUR_OF_DAY, hour);
		ca.set(Calendar.MINUTE, 0);
		ca.set(Calendar.SECOND, 0);
		return (int)(ca.getTimeInMillis()-System.currentTimeMillis())/1000;
	}
	/**
	 * 获得明天几点的时间
	 * @return
	 */
	public  static long getNextDayTime(int hour){
		Calendar ca =Calendar.getInstance();
		ca.add(Calendar.DATE, 1);
		ca.set(Calendar.HOUR_OF_DAY, hour);
		ca.set(Calendar.MINUTE, 0);
		ca.set(Calendar.SECOND, 0);
		return ca.getTimeInMillis();
	}
	public static Date getDateByString(String dateTime){
		Date startDate=DateUtil.parse(dateTime, "yyyy-MM-dd");
		return startDate;
	}
	public static Date getNextDateByString(String dateTime,int day){
		Date startDate=DateUtil.parse(dateTime, "yyyy-MM-dd");
		Date resultDate=addDate(startDate,day);
		return resultDate;
	}
}
