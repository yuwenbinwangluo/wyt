package baseframe.manager;

import java.util.ArrayList;

import baseframe.db.model.CouponConfig;
import baseframe.serverlet.LuckDrawServlet;


//信息管理器
public class InfoManager{

	private static InfoManager _instance=new InfoManager();
	
	private ArrayList<CouponConfig> couponConfigs=null;
	private int dayOfSecond=3600*24;//一天的秒数
	
	public static InfoManager getInstance()
	{
		return _instance;
	}
	
	
	public void init() {
		couponConfigs=LuckDrawServlet.getCouponConfig();
		if(couponConfigs.size()>0)
		{
			System.out.println("-----------获取奖品配置信息成功---------");
		}else{
			System.out.println("-----------获取奖品配置信息失败---------");
		}
		
		
		
		
		
	}
	//获取奖品配置
	public CouponConfig getCouponConfig()
	{
		if(couponConfigs.size()>0)
		{
			return couponConfigs.get(0);
		}
		return null;
	}
	public int getDayOfSecond()
	{
		return dayOfSecond;
	}
	
	


}
