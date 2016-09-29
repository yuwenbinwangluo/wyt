package baseframe.serverlet;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

import baseframe.conf.Command;
import baseframe.db.model.Customer;
import baseframe.tools.ClassRefUtil;
import baseframe.tools.HibernateUtil;
import baseframe.tools.Util;

import com.google.gson.Gson;

//登录注册
@WebServlet("/login")
public class LoginServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;


	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		doService(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		doService(req, res);
	}
	public void doService(HttpServletRequest req, HttpServletResponse res) {
		try {
			String type=req.getParameter("type");
			PrintWriter out=res.getWriter();

			if(type.equals(Command.C_REGISTER))
			{
				String usertxt=req.getParameter("username");
				String passwordtxt=req.getParameter("password");
				String email=req.getParameter("email");
				String telnumber=req.getParameter("telnumber");
				String point=req.getParameter("point");
				String positionstr=req.getParameter("position");
				int position=Integer.parseInt(positionstr);
				//注册
				dealRegister(out,usertxt,passwordtxt,email,telnumber,point,position);
				
			}else if(type.equals(Command.C_LOGIN))
			{
				String usertxt=req.getParameter("username");
				String passwordtxt=req.getParameter("password");
				System.out.println(usertxt+"_____"+passwordtxt);
				//登录
				dealLogin(out,usertxt,passwordtxt,req);
			}else if(type.equals(Command.C_LOGINOUT))
			{
				//登出(客户端实现)
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//处理登录
	@SuppressWarnings("unchecked")
	private void dealLogin(PrintWriter out,String usertxt,String passwordtxt,HttpServletRequest req)
	{
	   ArrayList<Customer> records=null;
 	   Session session= HibernateUtil.getSession();
        Transaction tx = null;         
 		try {	    			
 			tx = session.beginTransaction();
 			passwordtxt=Util.getMd5(passwordtxt);
 			Query query = session.createSQLQuery("select * from dyr_customer where (username=\""+usertxt+"\" or telnumber=\""+usertxt+"\") and password=\""+passwordtxt+"\"");
 			List<Map<String,Object>> list =(List<Map<String,Object>>) (query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list());
 			records=new ArrayList<Customer>();
 			Customer user;
 			for(int i=0;i<list.size();i++)
 			{
 				Map<String,Object> map=list.get(i);
 				user=ClassRefUtil.inflateMap(Customer.class, map);
 				records.add(user);
 			}
 			tx.commit();
 		} catch (Exception e) {
 			Util.logError("", e);
 			HibernateUtil.rollbackTransaction(tx);
 		} finally {
 			HibernateUtil.closeSession(session);
 		}		
		if(records!=null&&records.size()>0)
		{
			boolean isadd=true;
			//需要添
			if(isadd)
			{
				Gson gson=new Gson();
				String str=gson.toJson(records);
				out.write(Command.Code0+"^"+str);//0:成功 1：失败,2:已经登录
			}else{
				out.write(Command.Code2+"^"+"2");//0:成功 1：失败,2:已经登录
			}
		}else{
			out.write(Command.Code1+"^-1");
		}
	}
	
	private int checkCondition(String username,String telnumber)
	{
	    ArrayList<Customer> records=null;
	    Session session= HibernateUtil.getSession();
        Transaction tx = null;         
 		try {	    			
 			tx = session.beginTransaction();
 			Query query = session.createSQLQuery("select * from dyr_customer where username=\""+username+"\" or telnumber=\""+telnumber+"\"");
 			@SuppressWarnings("unchecked")
			List<Map<String,Object>> list =(List<Map<String,Object>>) (query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list());
 			records=new ArrayList<Customer>();
 			Customer user;
 			for(int i=0;i<list.size();i++)
 			{
 				Map<String,Object> map=list.get(i);
 				user=ClassRefUtil.inflateMap(Customer.class, map);
 				records.add(user);
 			}
 			tx.commit();
 		}catch(Exception e)
 		{
 			e.printStackTrace();
 			return Command.Code1;
 		}
 		if(records!=null&&records.size()>0)
 		{
 			Customer user;
 			for(int m=0;m<records.size();m++)
 			{
 				user=records.get(m);
 				if(user.getUsername().equals(username))
 				{
 					return Command.Code103;
 				}
 				if(user.getTelnumber().equals(telnumber))
 				{
 					return Command.Code102;
 				}
 			}
 		}
 		return Command.Code0;
	}
	
	//处理注册
	private void dealRegister(PrintWriter out,String username,String password,String email,String telnumber,String point,int position)
	{
		int status=checkCondition(username,telnumber);
		
		if(!Util.checkEmail(email))
		{
			status=Command.Code101;
		}
		if(!Util.checkPhone(telnumber))
		{
			status=Command.Code100;
		}
		Customer user=new Customer();
		if(status==Command.Code0)
		{
			user.setUsername(username);
			user.setPassword(Util.getMd5(password));
			user.setEmail(email);
			user.setTelnumber(telnumber);
			user.setPoint(point);
			user.setPosition(position);
			//
		    Session session= HibernateUtil.getSession();
		 	Transaction transaction=session.beginTransaction();
		 	session.save(user);
		 	transaction.commit();
		 	System.out.println("------------注册用户成功-----------");
		}
		if(status!=Command.Code0)
		{
			out.write(status+"^"+-1);//注册失败
		 	System.out.println("------------注册用户失败-----------");
		}else{
			Gson gson=new Gson();
			String str=gson.toJson(user);
			out.write(status+"^"+str);//0:注册成功
		}
	}
	


}
