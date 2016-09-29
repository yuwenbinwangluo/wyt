package baseframe.manager;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import baseframe.base.Singleton;

/**
 *
 */
public class ActivityManager extends Singleton{

    public static String HOME_PAGE="android.curstom.MainActivity";
    public static String LUCKDRAW_PAGE="android.curstom.LuckDrawActivity";
    public static String USERCENTER_PAGE="android.curstom.UserCeneterActivity";
    public static String MORE_PAGE="android.curstom.MoreActivity";
    public static String CREATE_MERCHANT_PAGE="android.curstom.CreateMerchantActivity";//创建商铺

    private List<Activity> activities=new ArrayList<Activity>();

    public void open(Activity activity)
    {
        if(activities.indexOf(activity)==-1)
        {
            activities.add(activity);
        }
    }
    public void close(Activity activity)
    {
        if(activities.indexOf(activity)!=-1)
        {
            activities.remove(activity);
        }
    }
    public Activity getTop()
    {
        if(activities.size()>0)
        {
            return activities.get(activities.size()-1);
        }
        return null;
    }
    public void removeAll()
    {
        while(activities.size()>0)
        {
            Activity activity=activities.get(0);
            activities.remove(0);
            activity.finish();
        }
    }



}
