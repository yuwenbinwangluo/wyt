package baseframe.manager;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Hashtable;

import ui.content.LoginActivity;
import ui.modes.Customer;

/**
 * 用户管理器
 */
public class CustomerManager {
	
	private Hashtable<String,Customer> tables=new Hashtable<String,Customer>();
	private Customer self=null;

	private static CustomerManager _instance=new CustomerManager();
	public static CustomerManager getInstance()
	{
		return _instance;
	}
	
	public boolean addUser(String username,String password)
	{
		if(!tables.containsKey(username))
		{
			Customer user=new Customer();
			user.setUsername(username);
			user.setPassword(password);
			tables.put(username, user);
			return true;
		}
		return false;
	}
	public boolean addUser(Customer user)
	{
		if(!tables.containsKey(user.getUsername()))
		{
			tables.put(user.getUsername(), user);
			return true;
		}
		return false;
	}
	public void removeUser(Customer user)
	{
		if(tables.containsKey(user.getUsername()))
		{
			tables.remove(user.getUsername());
		}
	}


	//设置自己
	public void setUserSelf(Customer user)
	{
		this.self=user;
	}
	//获取用户自己
	public Customer getUserSelf()
	{
		return this.self;
	}

	//保存用户名密码到本地
	public void saveLocalSelfData(Activity activity)
	{
		if(this.self!=null)
		{
			SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(activity);
			SharedPreferences.Editor editor=preferences.edit();
			editor.putString("username",self.getUsername());
			editor.putString("password",self.getPassword());
			editor.commit();
		}
	}
	//是否已经登录过(如果已经登陆过则保存self对象)
	public boolean isAlreadyLogin(Activity activity)
	{
		SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(activity);
		String username=preferences.getString("username","");
		String password=preferences.getString("password","");
		if(password.equals(""))
		{
			return false;
		}
		if(this.self==null)
		{
			self=new Customer();
			self.setUsername(username);
			self.setPassword(password);
		}
		return true;
	}
	public void loginOut(Activity activity)
	{
		SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(activity);
		SharedPreferences.Editor editor=preferences.edit();
		editor.remove("username");
		editor.remove("password");
		editor.commit();
		self=null;
		//跳转到登录
		//注意先结束掉已有的栈activitys
		((ActivityManager)ActivityManager.getInstance(ActivityManager.class)).removeAll();
		//重置其他数据信息
		resetData();
		Intent intent=new Intent(activity,LoginActivity.class);
		activity.startActivity(intent);

	}
	//重置其他数据信息
	private void resetData()
	{

	}
	
	
	
}
