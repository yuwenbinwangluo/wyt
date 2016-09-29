package baseframe.serverlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.hibernate.Session;
import org.hibernate.Transaction;

import baseframe.conf.ServerConfig;
import baseframe.db.model.Merchant;
import baseframe.tools.HibernateUtil;
import baseframe.tools.Util;

//商家相关
@WebServlet("/createBusiness")
public class CreateBusinessServlet  extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		doService(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		doService(req, res);
	}

	public void doService(HttpServletRequest req, HttpServletResponse res) {
		try {
			createBusinessUser(res.getWriter(),req,res);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void getBusinessUserParams(String pvalue,String fieldname,Merchant merchant)
	{
		if (fieldname.equals("uid")) {
			 merchant.setUid(Integer.parseInt(pvalue));
		 }else if (fieldname.equals("name")) {
			 merchant.setName(pvalue);
		 }else if(fieldname.equals("addr")){
			 merchant.setAddr(pvalue);
		 }else if(fieldname.equals("telnumber")){
			 merchant.setTelnumber(pvalue);
		 }else if(fieldname.equals("merchantType")){
			 merchant.setType(pvalue);
		 }else if(fieldname.equals("desc")){
			 merchant.setDesp(pvalue);
		 }else if(fieldname.equals("position")){
			 merchant.setPosition(Integer.parseInt(pvalue));
		 }else if(fieldname.equals("province")){
			 merchant.setProvince(Integer.parseInt(pvalue));
		 }else if(fieldname.equals("city")){
			 merchant.setCity(Integer.parseInt(pvalue));
		 }else if(fieldname.equals("district")){
			 merchant.setDistrict(Integer.parseInt(pvalue));
		 }else if(fieldname.equals("point")){
			 merchant.setPoint(pvalue);
		 }else if(fieldname.equals("qq")){
			 merchant.setQq(pvalue);
		 }else if(fieldname.equals("weixin")){
			 merchant.setWeixin(pvalue);
		 }
	}

	private void createBusinessUser(PrintWriter out,HttpServletRequest req,HttpServletResponse res) {
		boolean result = true;
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload sfu = new ServletFileUpload(factory);
		List<FileItem> items;
		String fileType = "";
		String image_server_name = "";
		Merchant merchant=new Merchant();
		try {
			items = sfu.parseRequest(req);
			for (int i = 0; i < items.size(); i++) {
				FileItem item = items.get(i);
				// isFormField为true，表示这不是文件上传表单域
				if (item.isFormField()) {
					String pvalue = item.getString("utf-8");
					String fieldname = item.getFieldName();
					getBusinessUserParams(pvalue,fieldname,merchant);
					int reg_time=Util.getCurrentDateWithSecond();
					int expire_time=Util.getCurrentDateWithSecond();
					merchant.setReg_time(reg_time);
					merchant.setExpire_time(expire_time);
				} else {
					//-----------------/////未完成
					image_server_name = item.getName();// 获取上传文件的名称
					String str[] = image_server_name.split("\\\\");
					image_server_name = str[str.length - 1];
					int posEnd = image_server_name.lastIndexOf(".");
					fileType = image_server_name.substring(posEnd);
					image_server_name = image_server_name.substring(0, posEnd);
					image_server_name = image_server_name + "_"
							+ Util.getCurrentDateStrNoF() + fileType;
					// product.setImage_server_name(image_server_name);//设置上传文件的名称
					String saveDir = ServerConfig.SERVER_REALPATH
							+ ServerConfig.SERVER_CLIENT_DIR_NAME;
					item.write(new File(saveDir + image_server_name));
				}
			}
			// save in database
			 result=saveCreateMerchant(merchant);
		} catch (Exception e) {
			result = false;
			Util.logError("", e);
		} finally {
			Util.writeToJson(null,result,out);
			
			if (result) {
				Util.logInfo(image_server_name + "--------上传成功!" + "-----");
				
			} else {
				Util.logInfo(image_server_name + "--------上传失败!" + "-----");
				
			}
		}
	}
	private boolean saveCreateMerchant(Merchant merchant)
	{
		boolean isSuccess=true;
		try{
		    Session session= HibernateUtil.getSession();
		 	Transaction transaction=session.beginTransaction();
		 	session.save(merchant);
		 	transaction.commit();
		}catch(Exception e)
		{
			isSuccess=false;
			e.printStackTrace();
		}
		return isSuccess;
	}
	@Override
	public void init() throws ServletException {
		super.init();
		ServerConfig.SERVER_REALPATH = getServletContext().getRealPath("/");
	}
	
}
