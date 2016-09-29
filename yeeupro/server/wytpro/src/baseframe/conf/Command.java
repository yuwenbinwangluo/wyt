package baseframe.conf;
public class Command {
	public static final String SERVER_CLIENT_TYPE="type";//服务器客户端统一命令
	public static final String SERVER_CLIENT_CTYPE="ctype";//服务器客户端统一命令
	///////////////////////////////////
	public static final String LOGIN_MODEL="1";//登录模块
	public static final String C_LOGIN="1";//登录
	public static final String C_REGISTER="2";//注册
	public static final String C_LOGINOUT="3";//登出

	public static final String C_RECOMMEND="2";//获取推荐商户
//	public static final String C_CREATE_BUSINESS_USER="4";//创建商户
	public static final String C_GET_BUSINESS_USER="5";//获取推荐商户
	public static final String C_GET_BUSINESS_CATEGROY="6";//获取商户类型
	public static final String C_GET_LUCKDRAW_STATUS="7";//获取抽奖状态
	public static final String C_GET_LUCKDRAW_GET="8";//抽奖


	
	
	
	
	
	
	///////////////////////////////状态码//////////////////////////////
	public static final int Code0=0;//成功
	public static final int Code1=1;//未知的错误
	public static final int Code2=2;//冲突
	public static final int Code100=100;//手机号码错误
	public static final int Code101=101;//邮箱号码错误
	public static final int Code102=102;//手机号已经被占用
	public static final int Code103=103;//用户名已经被占用
	
	
	
	
	
	
	
	
}
