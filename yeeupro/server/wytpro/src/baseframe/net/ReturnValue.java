package baseframe.net;

public class ReturnValue {
       public static final int SUCCESSS=0;//成功
       public static final int LOSE=1;//失败



       
       public static final String STR_SUCCESSS="成功";//成功
       public static final String STR_LOSE="失败";//失败
       


  
       public static String getStrCode(int code) {
		String result = STR_SUCCESSS;
		switch (code) {
		case SUCCESSS:
			result = STR_SUCCESSS;
			break;
		case LOSE:
			result = STR_LOSE;
			break;		
		default:
			result = STR_LOSE;
			break;
		}
		return result;
	}
}
