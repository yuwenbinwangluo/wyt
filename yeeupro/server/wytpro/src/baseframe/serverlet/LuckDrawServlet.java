package baseframe.serverlet;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
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
import baseframe.db.model.Coupon;
import baseframe.db.model.CouponConfig;
import baseframe.db.model.CouponResult;
import baseframe.db.model.IntegralRecord;
import baseframe.db.model.LotteryLog;
import baseframe.db.model.Merchant;
import baseframe.manager.InfoManager;
import baseframe.tools.ClassRefUtil;
import baseframe.tools.HibernateUtil;
import baseframe.tools.Util;

@WebServlet("/luckdraw")
public class LuckDrawServlet  extends HttpServlet{
	private static final long serialVersionUID = 1L;

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
			case Command.C_GET_LUCKDRAW_STATUS:
				///获取抽奖状态
				getIntegralRecord(out,req,res);
				break;
			case Command.C_GET_LUCKDRAW_GET:
				// 抽奖
				
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	private void getIntegralRecord(PrintWriter out,HttpServletRequest req, HttpServletResponse res)
	{
		   String cid=req.getParameter("cid");//用户id
			
		   ArrayList<LotteryLog> records=null;
	 	   Session session= HibernateUtil.getSession();
	        Transaction tx = null;         
	 		try {	    			
	 			tx = session.beginTransaction();
	 			Query query = session.createSQLQuery("select * from dyr_lottery_log where `mid`="+cid);
	 			List<Map<String,Object>> list =(List<Map<String,Object>>) (query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list());
	 			records=new ArrayList<LotteryLog>();
	 			LotteryLog merchant;
	 			for(int i=0;i<list.size();i++)
	 			{
	 				Map<String,Object> map=list.get(i);
	 				merchant=ClassRefUtil.inflateMap(LotteryLog.class, map);
	 				records.add(merchant);
	 			}
	 			tx.commit();
	 		} catch (Exception e) {
	 			Util.logError("", e);
	 			HibernateUtil.rollbackTransaction(tx);
	 		} finally {
	 			HibernateUtil.closeSession(session);
	 		}
	 		//奖品
 			CouponConfig couponConfig=InfoManager.getInstance().getCouponConfig();
	 		CouponResult result=new CouponResult();
			String startTimeStr=Util.getDateStrbyLongWithMini(Long.parseLong(couponConfig.getStart_time()));
			String endTimeStr=Util.getDateStrbyLongWithMini(Long.parseLong(couponConfig.getEnd_time()));
			result.setStartTime(startTimeStr);
			result.setEndTime(endTimeStr);
	 		if(records.size()>0)
	 		{
	 			int getCount=0;
	 			for(int m=0;m<records.size();m++)
	 			{
	 				//是同一天领取的
	 				if(isInDay(records.get(m).getDate()))
	 				{
	 					getCount++;
	 				}
	 			}
	 			//判断今天有没有抽奖机会（每天N次）
	 			//每天可抽取次数
		 		int everyCount=couponConfig.getCount();
	 			if(getCount<everyCount)
	 			{
	 				//发送可领取次数
	 				result.setCount(everyCount-getCount);
	 				Util.writeToJson(records, true, out);
	 				return;
	 			}
		 		//次数为0
		 		//发送历史最后一次抽取记录
	 			LotteryLog log=records.get(records.size()-1);
		 		result.setInfo(log.getInfo());
		 		Coupon coupon=getCouponFromeDatabase(log.getCid());
		 		if(coupon!=null)
		 		{
			 		result.setName(coupon.getName());
		 		}
	 		}
			Util.writeToJson(result, true, out);
	}
	
	@SuppressWarnings("unchecked")
	private Coupon getCouponFromeDatabase(int couponId)
	{
		 Coupon merchant=null;
	 	    Session session= HibernateUtil.getSession();
	        Transaction tx = null;         
	 		try {	    			
	 			tx = session.beginTransaction();
	 			Query query = session.createSQLQuery("select * from dyr_lottery_log where `id`="+couponId);
	 			List<Map<String,Object>> list =(List<Map<String,Object>>) (query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list());
	 			for(int i=0;i<list.size();i++)
	 			{
	 				Map<String,Object> map=list.get(i);
	 				merchant=ClassRefUtil.inflateMap(Coupon.class, map);
	 			}
	 			tx.commit();
	 		} catch (Exception e) {
	 			Util.logError("", e);
	 			HibernateUtil.rollbackTransaction(tx);
	 		} finally {
	 			HibernateUtil.closeSession(session);
	 		}
	 		return merchant;
	}
	
	//是否为本天
	private boolean isInDay(int lastDaySecond)
	{
		int dayOfSecond=InfoManager.getInstance().getDayOfSecond();
		int currentDaySecond=Util.getCurrentDateWithSecond();
		int offset=currentDaySecond%dayOfSecond-lastDaySecond%dayOfSecond;
		if(offset<dayOfSecond)
		{
			return true;
		}
		return false;
	}
	
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public static ArrayList<CouponConfig>  getCouponConfig()
	{
		   ArrayList<CouponConfig> records=new ArrayList<CouponConfig>();
	 	   Session session= HibernateUtil.getSession();
	        Transaction tx = null;         
	 		try {	    			
	 			tx = session.beginTransaction();
	 			Query query = session.createSQLQuery("select * from dyr_couponconfig");
	 			List<Map<String,Object>> list =(List<Map<String,Object>>) (query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list());
	 			records=new ArrayList<CouponConfig>();
	 			CouponConfig config;
	 			for(int i=0;i<list.size();i++)
	 			{
	 				Map<String,Object> map=list.get(i);
	 				config=ClassRefUtil.inflateMap(CouponConfig.class, map);
	 				records.add(config);
	 			}
	 			tx.commit();
	 		} catch (Exception e) {
	 			Util.logError("", e);
	 			HibernateUtil.rollbackTransaction(tx);
	 		} finally {
	 			HibernateUtil.closeSession(session);
	 		}
		return records;
	}
	
	
	
	
	
	
	
	
	
	
	

}
