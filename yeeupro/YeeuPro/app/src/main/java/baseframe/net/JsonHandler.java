package baseframe.net;

import baseframe.config.BaseConfig;

public class JsonHandler {
	
	public static void sendData(HttpRequest req){
		if(req.url.equals(BaseConfig.NUM_STR)){
//			req.url=AppConfig.server_request_uri;
		}
		HttpManager.addLoadFile(req);

//		reciveData(AppConfig.PRO_DATA);
	}

	public static void reciveData(String result){
//		JSONObject json;
//		try {
//			json = new JSONObject(result);
//			int type=json.get(Command.COMMAND_MAX).toString();
//			switch(type){
//			case Command.LOGIN_MODEL:
//				LoginHandler.reciveData(json);
//				break;
//			case Command.MAIN_SUPERMARKET_PD:
//
//				break;
//
//
//
//			}
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
	}
	
	
	
	

}
