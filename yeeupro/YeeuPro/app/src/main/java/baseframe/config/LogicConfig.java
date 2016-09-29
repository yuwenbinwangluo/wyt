package baseframe.config;

import android.content.Context;
import android.os.Message;

import com.yu.wyt.R;

import baseframe.tools.Util;


public class LogicConfig {
	

	//
	public static final int COMMON_HANDLER_CODE=100000;//handler-code
	public static final int NETWORK_BREAK_CODE=100001;//
		

	
	


	

	
	
	
	
	
	
	
	public static boolean interceptHandler(Context context,Message msg){
		switch (msg.what) {
		case LogicConfig.NETWORK_BREAK_CODE://
			Util.logToastInfo(context,context.getResources().getString(R.string.network_is_break));
			break;
		}
		return msg.what>COMMON_HANDLER_CODE;
	}
	
	
	
	
	
	
	

}
