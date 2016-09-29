package baseframe.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Message;
import android.telephony.TelephonyManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import baseframe.config.AppConfig;
import baseframe.config.LogicConfig;
import baseframe.manager.ActivityManager;
import baseframe.tools.Util;

public class HttpManager {
	public static final String NET_TYPE_JSON = "json";
	
	
	private static ArrayList<HttpRequest> loadQueue = new ArrayList<HttpRequest>();
	private static HttpRequest target = null;
	private static boolean isloading = false;
	
	
	public static void addLoadFile(HttpRequest req) {
		if(HttpManager.loadQueue.indexOf(req)==-1){
			HttpManager.loadQueue.add(req);
		}
	}
	
	private static void loadNext() {
		if (!HttpManager.isloading) {
			if (HttpManager.loadQueue.size() > 0) {
				HttpRequest req =	HttpManager.loadQueue.get(0);
			
				HttpManager.loadOneFile(req);
			}
		}
	}
	private static void loadOneFile(HttpRequest req) {
		HttpManager.isloading = true;
		HttpManager.target = req;
		if (HttpManager.target.url == "") {
			HttpManager.breakLoadNext();
			return;
		}
		Context context= ((ActivityManager)ActivityManager.getInstance(ActivityManager.class)).getTop();
		//
		if(context!=null&&HttpManager.checkNetWorkState(context)!=AppConfig.NET_WORK_STATE.NO){
			HttpManager.loadSpecial();
		}else{
			//
			Message msg=new Message();
			msg.what=LogicConfig.NETWORK_BREAK_CODE;
			req.handler.sendMessage(msg);
			if(req.isBreakClear){
				HttpManager.breakLoadNext();
			}
		}
	}
	public static AppConfig.NET_WORK_STATE checkNetWorkState(Context context) {
		AppConfig.NET_WORK_STATE states = AppConfig.NET_WORK_STATE.NO;
		ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		//
		NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
		//
		if (null == networkInfo) {
			states=AppConfig.NET_WORK_STATE.NO;
		} else {
			boolean available = networkInfo.isAvailable();
			if (available) {
				if(networkInfo.getType() == ConnectivityManager.TYPE_WIFI){
					states=AppConfig.NET_WORK_STATE.WIFI;
				}else if(networkInfo.getType() == ConnectivityManager.TYPE_MOBILE){
					int networkType = tm.getNetworkType();
					switch (networkType) {
						case TelephonyManager.NETWORK_TYPE_GPRS:
				        case TelephonyManager.NETWORK_TYPE_EDGE:
				        case TelephonyManager.NETWORK_TYPE_CDMA:
				        case TelephonyManager.NETWORK_TYPE_1xRTT:
				        case TelephonyManager.NETWORK_TYPE_IDEN:
				        	states = AppConfig.NET_WORK_STATE.GPRS;
				        	break;
				        case TelephonyManager.NETWORK_TYPE_UMTS:
				        case TelephonyManager.NETWORK_TYPE_EVDO_0:
				        case TelephonyManager.NETWORK_TYPE_EVDO_A:
				        case TelephonyManager.NETWORK_TYPE_HSDPA:
				        case TelephonyManager.NETWORK_TYPE_HSUPA:
				        case TelephonyManager.NETWORK_TYPE_HSPA:
				        case TelephonyManager.NETWORK_TYPE_EVDO_B:
				        case TelephonyManager.NETWORK_TYPE_EHRPD:
				        case TelephonyManager.NETWORK_TYPE_HSPAP:
				        	states = AppConfig.NET_WORK_STATE.N3G;
				        	break;
				        case TelephonyManager.NETWORK_TYPE_LTE:
				        	states = AppConfig.NET_WORK_STATE.N4G;
				        	break;
					}
				}
			} else {
				states = AppConfig.NET_WORK_STATE.NO;
			}
		}
		return states;
	}
	
	private static void loadSpecial() {
		String result="";
		if(HttpManager.target.method==HttpRequest.RequestMethod.POST){
			result =HttpManager.doPost(HttpManager.target .url, HttpManager.target .params);
		}else{
			result =HttpManager.doGet(HttpManager.target .url);
		}
		Util.logInfo(result);
		JsonHandler.reciveData(result);
		
		HttpManager.breakLoadNext();
	}
	private static void breakLoadNext() {
		HttpRequest req=HttpManager.loadQueue.remove(0);
		Util.disposeObject(req);
		HttpManager.target = null;
		HttpManager.isloading = false;
		HttpManager.loadNext();
	}
	
	public static void update(){
		HttpManager.loadNext();
	}
	
	
	
	
	

	
	
	
	
	
	
	private static String doGet(String url){
        HttpGet httpRequest = new HttpGet(url);  
        HttpClient httpclient = new DefaultHttpClient();  
		try {
			HttpResponse httpResponse = httpclient.execute(httpRequest);
			 //
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)  
            {  
                //
                String strResult = EntityUtils.toString(httpResponse.getEntity());
                return strResult;
            }  
            else 
            {  
                Util.logInfo("");
            }  
		}catch (Exception e) {
			e.printStackTrace();
			 Util.logInfo(e.getMessage());
		} 
		return "";
	}
	private static String doPost(String url,ArrayList<BasicNameValuePair> params){
        HttpPost httpRequest = new HttpPost(url);  
        HttpEntity httpentity;
		try {
			httpentity = new UrlEncodedFormEntity(params, "utf-8");
	        httpRequest.setEntity(httpentity);
	        HttpClient httpclient = new DefaultHttpClient();  
	        HttpResponse httpResponse;
			try {
				httpResponse = httpclient.execute(httpRequest);
		        //
		        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)  
		        {  
		           //
		           String strResult = EntityUtils.toString(httpResponse.getEntity());
		           return strResult;
		        }else{  
		           Util.logInfo("");
		        }  
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}  
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		return "";
	}
	
	//no implement
	public static String streamGet() {
        String result = null;
        URL url = null;
        HttpURLConnection connection = null;
        InputStreamReader in = null;
        try {
//            url = new URL(AppConfig.server_request_uri);
			url = new URL(AppConfig.SERVER_SEVERLET_BUSINESS);//--------零时
            connection = (HttpURLConnection) url.openConnection();
            in = new InputStreamReader(connection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(in);
            StringBuffer strBuffer = new StringBuffer();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                strBuffer.append(line);
            }
            result = strBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
 
        }
        Util.logInfo(result);
        return result;
    }
	
	
	
	
	
	
	
	

}
