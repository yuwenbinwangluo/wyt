package baseframe.tools;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import baseframe.tools.Servers.Server;


public class DBUtils {
	   private static DBUtils instance = new DBUtils();

	    public static DBUtils getInstance() {
	        return instance;
	    }

	    private DBUtils() {
	        try {
	            Class.forName("com.mysql.jdbc.Driver");
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	    }

	    public List<Map<String, Object>> search(Server server, String sql, List<Object> params) {
//	        Connection conn = null;
//	        PreparedStatement pstmt = null;
//	        ResultSet rs = null;
	        ArrayList<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
//	        try {
////	            conn = DriverManager.getConnection(server.getUrl(), server.getUser(), server.getPassword());
//	            pstmt = conn.prepareStatement(sql);
//	            for (int i = 0; params!= null && i < params.size(); i++) {
//	            	Object o = params.get(i);
//	            	if (o!=null){
//	            		pstmt.setObject(i + 1, o);
//	            	}else{
//	            		System.err.println("数据查询中"+sql+"第"+i+"个参数为空！");
//	            	}
//	            }
//	            rs = pstmt.executeQuery();
//	            ResultSetMetaData rsmd = rs.getMetaData();
//	            while (rs.next()) {
//	                Map<String, Object> map =new  HashMap<String, Object>();
//	                for (int i = 1; i != rsmd.getColumnCount() + 1; ++i) {
//	                	String label = rsmd.getColumnLabel(i).toLowerCase();
//	                	map.put(label, rs.getObject(label));
//	                }
//	                results.add(map);
//	            }
//
//	        } catch (Exception e) {
//	        	e.printStackTrace();
//	            return Collections.emptyList();
//	        } finally {
//	            DbUtils.closeQuietly(conn, pstmt, rs);
//	        }
	        return results;
	    }
}

