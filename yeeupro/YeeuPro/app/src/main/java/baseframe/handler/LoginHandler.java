package baseframe.handler;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import baseframe.config.Command;
import baseframe.net.HttpRequest;
import baseframe.net.JsonHandler;
import baseframe.resource.CorePool;

/**
 * 登录模块数据处理
 */
public class LoginHandler {
	
	
	public static void sendLoginData(){
		HttpRequest req=(HttpRequest)CorePool.getInstanceFromPool("baseframe.net.HttpRequest");
		req.params=new ArrayList<BasicNameValuePair>();
		req.params.add(new BasicNameValuePair(Command.COMMAND_MAX, Integer.valueOf(Command.LOGIN_MODEL).toString()));
		req.params.add(new BasicNameValuePair(Command.COMMAND_MIN, Integer.valueOf(Command.C_LOGIN).toString()));
		//data
		req.params.add(new BasicNameValuePair("cname", ""));
		JsonHandler.sendData(req);
	}
	
	
	public static void reciveData(JSONObject json){
//		try {
//			String ctype=json.get(Command.COMMAND_MIN).toString();
//			switch(ctype){
//			case Command.C_LOGIN:
//				LoginHandler.reciveLoginData(json);
//				break;
//
//			}
//
//
//
//
//
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
	}
	
	public static void reciveLoginData(JSONObject json){
		//update UI ----sendMessage



		
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
