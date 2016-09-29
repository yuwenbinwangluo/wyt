package baseframe.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import baseframe.conf.ServerConfig;
import baseframe.tools.HibernateUtil;

//字符集过滤器(注意执行的顺序是和Filter类名称排序有关)
@WebFilter(filterName="filter0",urlPatterns={"/*"})
public class Filter0_CharactorEncoding implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		HibernateUtil.loadHibernate();
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
//		System.out.println("dofilter......"+this.getClass().getName());
		req.setCharacterEncoding("utf-8");
		res.setContentType("text/html;charset=utf-8");
		chain.doFilter(req, res);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
