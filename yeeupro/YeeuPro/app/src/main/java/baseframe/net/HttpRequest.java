package baseframe.net;

import android.os.Handler;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import baseframe.base.RefObject;

public class HttpRequest extends RefObject {
	public enum RequestMethod{
		POST,GET
	}
	
	public String url="";
	public ArrayList<BasicNameValuePair> params=null;
	public RequestMethod method=RequestMethod.POST;
	public Handler handler=null;
	public boolean isBreakClear=true;//
	
	
    @Override
	public void dispose(){
		url="";
		method=RequestMethod.POST;
		if(params!=null){
			params.clear();
		}
		params=null;
		handler=null;
		isBreakClear=true;
		super.dispose();
	}
	
	

}
