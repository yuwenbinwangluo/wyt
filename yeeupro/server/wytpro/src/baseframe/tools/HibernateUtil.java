package baseframe.tools;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import baseframe.manager.InfoManager;

/**
 * Hibernate基本配置
 * 
 * @author Administrator
 *
 */
public class HibernateUtil {
	private static Logger logger = Logger.getLogger(HibernateUtil.class);

	private static Configuration cfg; // 一个hibernate配置环境
	private static SessionFactory factory; // 一个sesssion工厂
	// static {
	// try {
	// configuration = new Configuration();
	// sessionFactory =
	// configuration.configure("hibernate.cfg.xml").buildSessionFactory();
	// logger.info("加载hibernate配置文件成功");
	// } catch (HibernateException e) {
	// logger.error("加载hibernate配置文件失败",e);
	// }
	// }

	/**
	 * 加载这个类
	 * 
	 */
	public static void loadHibernate() {
		try {
			cfg = new Configuration();
			cfg.configure();
			ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(cfg.getProperties()).buildServiceRegistry();
			factory = cfg.configure().buildSessionFactory(serviceRegistry);
			logger.info("加载hibernate配置文件成功");
			Util.logInfo("加载hibernate配置文件成功");
			
			InfoManager.getInstance().init();//启动基本信息管理器
		} catch (HibernateException e) {
			Util.logError("加载hibernate配置文件失败", e);
		}
	}

	public static void setClosed(boolean b) {
		closed = b;
	}

	private static boolean closed = false;

	/**
	 * 得到一个数据库连接
	 * 
	 * @return
	 * @throws DbException
	 */
	public static Session getSession() throws DbException {
		if (closed) {
			return null;
		}
		try {
			return factory.openSession();
		} catch (HibernateException e) {
			logger.error("HibernateUtil.getSession异常", e);
			throw new DbException(e.getMessage());
		}
	}

	/**
	 * 关闭连接池工厂
	 * 
	 */
	public static void close() {
		try {
			factory.close();
		} catch (HibernateException e) {
			logger.error("HibernateUtil.close异常", e);
			throw new DbException(e.getMessage());
		}
	}

	/**
	 * 关闭一个连接
	 * 
	 * @param session
	 * @throws DbException
	 */
	public static void closeSession(Session session) throws DbException {
		try {
			if (session != null) {
				session.close();
			}
		} catch (HibernateException e) {
			logger.error("HibernateUtil.closeSession异常", e);
			throw new DbException(e.getMessage());
		}
	}

	/**
	 * 回滚一个事务
	 * 
	 * @param transaction
	 * @throws DbException
	 */
	public static void rollbackTransaction(Transaction transaction)
			throws DbException {
		try {
			if (transaction != null) {
				transaction.rollback();
			}
		} catch (HibernateException e) {
			logger.error("HibernateUtil.rollbackTransaction异常", e);
			throw new DbException(e.getMessage());
		}
	}
}
