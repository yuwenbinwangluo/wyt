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
import baseframe.db.model.DistrictData;
import baseframe.tools.ClassRefUtil;
import baseframe.tools.HibernateUtil;
import baseframe.tools.Util;

import com.google.gson.Gson;

//城市地区相关
@WebServlet("/district")
public class DistrictServlet  extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		doService(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		doService(req, res);
	}

	public void doService(HttpServletRequest req, HttpServletResponse res) {
		try {
			PrintWriter out=res.getWriter();
			getDistrictData(out,req,res);
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//获取城市地区信息
	private void getDistrictData(PrintWriter out,HttpServletRequest req,HttpServletResponse res)
	{
		ArrayList<DistrictData> records=null;
	 	   Session session= HibernateUtil.getSession();
	        Transaction tx = null;         
	 		try {	    			
	 			tx = session.beginTransaction();
	 			Query query = session.createSQLQuery("select * from dyr_district");
	 			@SuppressWarnings("unchecked")
				List<Map<String,Object>> list =(List<Map<String,Object>>) (query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list());
	 			records=new ArrayList<DistrictData>();
	 			DistrictData distinct;
	 			for(int i=0;i<list.size();i++)
	 			{
	 				Map<String,Object> map=list.get(i);
	 				distinct=ClassRefUtil.inflateMap(DistrictData.class, map);
	 				records.add(distinct);
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
	
	
	
	
}
