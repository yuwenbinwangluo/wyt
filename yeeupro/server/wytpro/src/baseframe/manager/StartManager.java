package baseframe.manager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

//服务器启动器(跟随服务器启动而执行
public class StartManager implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
//	启动一些非数据库操作的任务
//		System.out.println("启动基本信息管理器-----成功");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}

}
