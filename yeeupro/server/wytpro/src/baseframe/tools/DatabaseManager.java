package baseframe.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;


//
public class DatabaseManager {
	private static DatabaseManager instance = null;

	public DatabaseManager() {
	}

	public static DatabaseManager getInstance() {
		if (instance == null) {
			instance = new DatabaseManager();
		}

		return instance;
	}

	// String excuteSQL = "create table testInfo(uid int auto_increment primary
	// key,name varchar(255),contentInfo text,leavetime Date,contentCode
	// varchar(255));//
	// alter table testinfo add(cosntactWay varchar(255));//
	// select * from testinfo;
	// DatabaseManager.getInstance().excuteSelect("select * from "+tableName+" where id=1");
	// alter table testinfo add(leavetime Date,contentCode varchar(255));
	// 插入联系信息
	public boolean excuteInsertInto(String sqlStr) {
		boolean flag = false;
		Connection conn = null;
		Statement stmt = null;
		Context initCtx;
		try {
			initCtx = new InitialContext();
			Context ctx;
			ctx = (Context) initCtx.lookup("java:comp/env");
			// 获取连接池对象
			DataSource ds;
			ds = (DataSource) ctx.lookup("jdbc/ConnectionPool");
			conn = ds.getConnection();
			stmt = conn.createStatement();
			stmt.execute(sqlStr);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn);
			DbUtils.closeQuietly(stmt);
		}
		return flag;
	}

	// 获取联系我么信息
	public List<Map<String, Object>> excuteSelect(String sqlStr,List<Object> params) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		try {
			Context initCtx;
			initCtx = new InitialContext();
			Context ctx;
			ctx = (Context) initCtx.lookup("java:comp/env");
			// 获取连接池对象
			DataSource ds;
			ds = (DataSource) ctx.lookup("jdbc/ConnectionPool");
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sqlStr);
			for (int i = 0; params != null && i < params.size(); i++) {
				Object o = params.get(i);
				if (o != null) {
					pstmt.setObject(i + 1, o);
				} else {
					System.err.println("数据查询中" + sqlStr + "第" + i + "个参数为空！");
				}
			}
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 1; i != rsmd.getColumnCount() + 1; ++i) {
					String label = rsmd.getColumnLabel(i).toLowerCase();
					map.put(label, rs.getObject(label));
				}
				results.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		} finally {
//			DbUtils.closeQuietly(conn, pstmt, rs);
		}
		return results;
	}
	// // 获取联系我么信息
	// public ArrayList<MarketProductData> excuteSelect(String sqlStr) {
	// Connection conn = null;
	// Statement stmt = null;
	//		
	// try {
	// Context initCtx;
	// initCtx = new InitialContext();
	// Context ctx;
	// ctx = (Context) initCtx.lookup("java:comp/env");
	// // 获取连接池对象
	// DataSource ds;
	// ds = (DataSource) ctx.lookup("jdbc/ConnectionPool");
	// conn = ds.getConnection();
	// stmt = conn.createStatement();
	// stmt.execute(sqlStr);
	// ResultSet rsResultSet = stmt.getResultSet();
	// ArrayList<MarketProductData> datas=new ArrayList<MarketProductData>();
	// int m = 0;
	// MarketProductData data =null;
	// String image_server_host = "";
	// String image_server_name = "";
	// while (rsResultSet.next()) {
	// data= new MarketProductData();
	// data.setId(rsResultSet.getString("pid"));
	// data.setName(rsResultSet.getString("name"));
	// data.setDesp(rsResultSet.getString("desp"));
	// image_server_host = rsResultSet.getString("image_server_host");
	// image_server_name = rsResultSet.getString("image_server_name");
	// data.setUrl(image_server_host + image_server_name);
	// datas.add(data);
	//				
	// m++;
	// if (m > 10000) {
	// break;
	// }
	// }
	// return datas;
	// } catch (Exception e) {
	// e.printStackTrace();
	// } finally {
	// if (conn != null) {
	// try {
	// conn.close();
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// }
	// if (stmt != null) {
	// try {
	// stmt.close();
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// }
	// }
	// return null;
	// }

}
