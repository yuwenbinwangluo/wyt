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
import baseframe.db.model.Merchant;
import baseframe.db.model.MerchantCategory;
import baseframe.tools.ClassRefUtil;
import baseframe.tools.HibernateUtil;
import baseframe.tools.Util;
import baseframe.tools.serializephp.PHPSerialize;
import baseframe.tools.serializephp.PHPValue;

import com.google.gson.Gson;

//商家相关
@WebServlet("/business")
public class BusinessServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//商家首页推荐
	private static final int GEN_MERCHANT=0;//普通
	private static final int TOP_MERCHANT=1;//首页

	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		doService(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		doService(req, res);
	}

	public void doService(HttpServletRequest req, HttpServletResponse res) {
		try {
			String type = req.getParameter("type");
			PrintWriter out=res.getWriter();
			switch (type) {
			case Command.C_GET_BUSINESS_USER:
				// 获取首页推荐商户
				getBusinessIndexTop(out,req, res);
				break;
			case Command.C_GET_BUSINESS_CATEGROY:
				// 获取商户类型
				getBusinessCategory(out,req, res);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	private void getBusinessCategory(PrintWriter out,HttpServletRequest req,HttpServletResponse res) 
	{
		ArrayList<MerchantCategory> records=null;
	 	   Session session= HibernateUtil.getSession();
	        Transaction tx = null;         
	 		try {	    			
	 			tx = session.beginTransaction();
	 			Query query = session.createSQLQuery("select * from dyr_category");
	 			List<Map<String,Object>> list =(List<Map<String,Object>>) (query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list());
	 			records=new ArrayList<MerchantCategory>();
	 			MerchantCategory category;
	 			for(int i=0;i<list.size();i++)
	 			{
	 				Map<String,Object> map=list.get(i);
	 				category=ClassRefUtil.inflateMap(MerchantCategory.class, map);
	 				records.add(category);
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
				Gson gson=new Gson();
				String str=gson.toJson(records);
				out.write(Command.Code0+"^"+str);//0:成功 1：失败
			}else{
				out.write(Command.Code1+"^-1");
			}
	}
	@SuppressWarnings("unchecked")
	private void getBusinessIndexTop(PrintWriter out,HttpServletRequest req,HttpServletResponse res) 
	{
		ArrayList<Merchant> records=null;
	 	   Session session= HibernateUtil.getSession();
	        Transaction tx = null;         
	 		try {	    			
	 			tx = session.beginTransaction();
	 			Query query = session.createSQLQuery("select * from dyr_merchant where `index`="+TOP_MERCHANT);
	 			List<Map<String,Object>> list =(List<Map<String,Object>>) (query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list());
	 			records=new ArrayList<Merchant>();
	 			Merchant merchant;
	 			for(int i=0;i<list.size();i++)
	 			{
	 				Map<String,Object> map=list.get(i);
	 				merchant=ClassRefUtil.inflateMap(Merchant.class, map);
	 				merchant.setPic(parsePHPDownLoadURL(merchant.getPic()));
	 				records.add(merchant);
	 			}
	 			tx.commit();
	 		} catch (Exception e) {
	 			Util.logError("", e);
	 			HibernateUtil.rollbackTransaction(tx);
	 		} finally {
	 			HibernateUtil.closeSession(session);
	 		}
	 		Util.writeToJson(records,records!=null&&records.size()>0,out);
	}

	  private String parsePHPDownLoadURL(String srcPath)
	  {
			PHPSerialize p = new PHPSerialize();
			PHPValue c = p.unserialize(srcPath);
			System.out.println("解析php路径："+c.toHashMap().toString());
	        return c.toHashMap().toString();
	  }




}
