package baseframe.tools;

import org.hibernate.HibernateException;

//import util.HttpSendError;

/**
 * 异常处理
 * 
 * 
 */
public class DbException extends HibernateException {
	private static final long serialVersionUID = 20061213L;

	public DbException(String s) {
		super(s);
	}

	public DbException() {
		super("DbException");
	}
	
	/**
	 * 数据库错误处理
	 * ORA-01653,ORA-27059数据库空间不足
	 * ORA-00257数据库日志满
	 *
	 */
	public static void errorProcess(Throwable cause) {
//		if (cause == null) {
//			return;
//		}
//		StringBuffer sb = new StringBuffer();
//		sb.append(cause.toString());
//		if (sb.indexOf("ORA-01653") >= 0 || 
//			sb.indexOf("ORA-27059") >= 0 || 
//			sb.indexOf("ORA-00257") >= 0) {
//			HttpSendError.send(HttpSendError.DATABASEERROR);//发送错误报告
//		}
	}
}
